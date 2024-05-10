/*
 * This file is part of NoteBlockLib - https://github.com/RaphiMC/NoteBlockLib
 * Copyright (C) 2022-2024 RK_01/RaphiMC and contributors
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.raphimc.noteblocklib.format.nbs.model;

import com.google.common.io.LittleEndianDataInputStream;
import com.google.common.io.LittleEndianDataOutputStream;
import net.raphimc.noteblocklib.model.*;
import net.raphimc.noteblocklib.util.SongUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class NbsData implements Data<NbsNote> {

    /**
     * @since v0
     */
    private List<NbsLayer> layers;

    /**
     * @since v0
     */
    private List<NbsCustomInstrument> customInstruments;

    public NbsData(final NbsHeader header, final LittleEndianDataInputStream dis) throws IOException {
        this.layers = new ArrayList<>(header.getLayerCount());
        this.customInstruments = new ArrayList<>();

        for (int i = 0; i < header.getLayerCount(); i++) {
            layers.add(new NbsLayer());
        }

        int tick = -1;
        while (true) {
            final short jumpTicks = dis.readShort();
            if (jumpTicks == 0) break;
            tick += jumpTicks;

            int layer = -1;
            while (true) {
                final short jumpLayers = dis.readShort();
                if (jumpLayers == 0) break;
                layer += jumpLayers;
                while (this.layers.size() <= layer) {
                    this.layers.add(new NbsLayer());
                }

                this.layers.get(layer).getNotesAtTick().put(tick, new NbsNote(header, this.layers.get(layer), dis));
            }
        }

        if (dis.available() > 0) {
            for (int i = 0; i < header.getLayerCount(); i++) {
                final NbsLayer layer = new NbsLayer(header, dis);
                layer.setNotesAtTick(this.layers.get(i).getNotesAtTick());
                this.layers.set(i, layer);

                for (NbsNote note : layer.getNotesAtTick().values()) {
                    note.setLayer(layer);
                }
            }
        }

        if (dis.available() > 0) {
            final int customInstrumentsAmount = dis.readUnsignedByte();
            for (int i = 0; i < customInstrumentsAmount; i++) {
                this.customInstruments.add(new NbsCustomInstrument(dis));
            }

            for (NbsLayer layer : this.layers) {
                for (NbsNote note : layer.getNotesAtTick().values()) {
                    note.resolveCustomInstrument(header, this);
                }
            }
        }
    }

    public NbsData(final List<NbsLayer> layers, final List<NbsCustomInstrument> customInstruments) {
        this.layers = layers;
        this.customInstruments = customInstruments;
    }

    public <N extends Note> NbsData(final SongView<N> songView) {
        this.layers = new ArrayList<>();
        this.customInstruments = new ArrayList<>();

        for (Map.Entry<Integer, List<N>> entry : songView.getNotes().entrySet()) {
            for (int i = 0; i < entry.getValue().size(); i++) {
                final N note = entry.getValue().get(i);
                final NbsLayer layer;
                if (this.layers.size() <= i) {
                    this.layers.add(layer = new NbsLayer());
                } else {
                    layer = this.layers.get(i);
                }
                if (note instanceof NbsNote) {
                    final NbsNote clonedNote = (NbsNote) note.clone();
                    clonedNote.setLayer(layer);
                    layer.getNotesAtTick().put(entry.getKey(), clonedNote);
                } else {
                    final NbsNote nbsNote = new NbsNote(layer, note.getInstrument(), note.getKey());
                    if (note instanceof NoteWithVolume) {
                        final NoteWithVolume noteWithVolume = (NoteWithVolume) note;
                        nbsNote.setVolume(noteWithVolume.getVolume());
                    }
                    if (note instanceof NoteWithPanning) {
                        final NoteWithPanning noteWithPanning = (NoteWithPanning) note;
                        nbsNote.setPanning(noteWithPanning.getPanning());
                    }

                    layer.getNotesAtTick().put(entry.getKey(), nbsNote);
                }
            }
        }

        this.customInstruments.addAll(SongUtil.getUsedCustomInstruments(songView));
    }

    public void write(final NbsHeader header, final LittleEndianDataOutputStream dos) throws IOException {
        final Map<Integer, List<NbsNote>> notes = new TreeMap<>();
        for (NbsLayer layer : this.layers) {
            for (Map.Entry<Integer, NbsNote> note : layer.getNotesAtTick().entrySet()) {
                notes.computeIfAbsent(note.getKey(), k -> new ArrayList<>()).add(note.getValue());
            }
        }

        int lastTick = -1;
        for (Map.Entry<Integer, List<NbsNote>> entry : notes.entrySet()) {
            dos.writeShort(entry.getKey() - lastTick);
            lastTick = entry.getKey();

            int lastLayer = -1;
            for (NbsNote note : entry.getValue()) {
                if (!this.layers.contains(note.getLayer())) {
                    throw new IllegalArgumentException("Note layer not found in NbsData layers list");
                }
                dos.writeShort(this.layers.indexOf(note.getLayer()) - lastLayer);
                lastLayer = this.layers.indexOf(note.getLayer());
                note.write(header, this, dos);
            }
            dos.writeShort(0);
        }
        dos.writeShort(0);

        for (int i = 0; i < header.getLayerCount(); i++) {
            this.layers.get(i).write(header, dos);
        }

        dos.writeByte(this.customInstruments.size());
        for (NbsCustomInstrument customInstrument : this.customInstruments) {
            customInstrument.write(dos);
        }
    }

    /**
     * @return The layers of this song
     * @since v0
     */
    public List<NbsLayer> getLayers() {
        return this.layers;
    }

    /**
     * @param layers The layers of this song
     * @since v0
     */
    public void setLayers(final List<NbsLayer> layers) {
        this.layers = layers;
    }

    /**
     * @return The custom instruments of this song
     * @since v0
     */
    public List<NbsCustomInstrument> getCustomInstruments() {
        return this.customInstruments;
    }

    /**
     * @param customInstruments The custom instruments of this song
     * @since v0
     */
    public void setCustomInstruments(final List<NbsCustomInstrument> customInstruments) {
        this.customInstruments = customInstruments;
    }

}
