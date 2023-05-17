/*
 * This file is part of NoteBlockLib - https://github.com/RaphiMC/NoteBlockLib
 * Copyright (C) 2023 RK_01/RaphiMC and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.raphimc.noteblocklib.format.nbs.data;

import com.google.common.io.LittleEndianDataInputStream;
import com.google.common.io.LittleEndianDataOutputStream;
import net.raphimc.noteblocklib.format.nbs.data.layer.NbsLayer;
import net.raphimc.noteblocklib.format.nbs.data.layer.NbsV0Layer;
import net.raphimc.noteblocklib.format.nbs.data.layer.NbsV2Layer;
import net.raphimc.noteblocklib.format.nbs.data.layer.NbsV4Layer;
import net.raphimc.noteblocklib.format.nbs.header.NbsV0Header;
import net.raphimc.noteblocklib.format.nbs.note.NbsV0Note;
import net.raphimc.noteblocklib.format.nbs.note.NbsV4Note;
import net.raphimc.noteblocklib.model.Data;
import net.raphimc.noteblocklib.util.Instrument;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static net.raphimc.noteblocklib.format.nbs.NbsParser.readString;
import static net.raphimc.noteblocklib.format.nbs.NbsParser.writeString;

public class NbsV0Data implements Data<NbsV0Note> {

    private List<NbsLayer> layers;
    private List<NbsV0CustomInstrument> customInstruments;

    @SuppressWarnings("UnstableApiUsage")
    public NbsV0Data(final NbsV0Header header, final LittleEndianDataInputStream dis) throws IOException {
        this.layers = new ArrayList<>(header.getLayerCount());
        this.customInstruments = new ArrayList<>();

        final int customInstrumentDiff = Instrument.values().length - header.getVanillaInstrumentCount();
        header.setVanillaInstrumentCount((byte) Instrument.values().length);

        for (int i = 0; i < header.getLayerCount(); i++) {
            layers.add(new NbsLayer(new TreeMap<>()));
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

                final NbsV0Note note = header.getNbsVersion() >= 4 ? new NbsV4Note(dis) : new NbsV0Note(dis);
                if (note.getInstrument() >= header.getVanillaInstrumentCount() && customInstrumentDiff > 0) {
                    note.setInstrument((byte) (note.getInstrument() + customInstrumentDiff));
                }
                this.layers.get(layer).getNotesAtTick().put(tick, note);
                note.setLayer(this.layers.get(layer));
            }
        }

        if (dis.available() > 0) {
            for (int i = 0; i < header.getLayerCount(); i++) {
                final String name = readString(dis);
                final boolean locked = header.getNbsVersion() >= 4 && dis.readBoolean();
                final byte volume = dis.readByte();
                final short panning = (short) (header.getNbsVersion() >= 2 ? dis.readUnsignedByte() : 100);

                final NbsLayer layer = this.layers.get(i);
                if (header.getNbsVersion() >= 4) {
                    this.layers.set(i, new NbsV4Layer(layer.getNotesAtTick(), name, volume, panning, locked));
                } else if (header.getNbsVersion() >= 2) {
                    this.layers.set(i, new NbsV2Layer(layer.getNotesAtTick(), name, volume, panning));
                } else {
                    this.layers.set(i, new NbsV0Layer(layer.getNotesAtTick(), name, volume));
                }
                for (NbsV0Note note : this.layers.get(i).getNotesAtTick().values()) {
                    note.setLayer(this.layers.get(i));
                }
            }
        }

        if (dis.available() > 0) {
            final int customInstrumentsAmount = dis.readUnsignedByte();
            for (int i = 0; i < customInstrumentsAmount; i++) {
                this.customInstruments.add(new NbsV0CustomInstrument(dis));
            }
        }
    }

    public NbsV0Data(final List<NbsLayer> layers, final List<NbsV0CustomInstrument> customInstruments) {
        this.layers = layers;
        this.customInstruments = customInstruments;
    }

    @SuppressWarnings("UnstableApiUsage")
    public void write(final NbsV0Header header, final LittleEndianDataOutputStream dos) throws IOException {
        if (this.layers.size() != header.getLayerCount()) {
            throw new IllegalArgumentException("Layer count mismatch");
        }

        final Map<Integer, List<NbsV0Note>> notes = new TreeMap<>();
        for (NbsLayer layer : this.layers) {
            for (Map.Entry<Integer, NbsV0Note> note : layer.getNotesAtTick().entrySet()) {
                notes.computeIfAbsent(note.getKey(), k -> new ArrayList<>()).add(note.getValue());
            }
        }

        int lastTick = -1;
        for (Map.Entry<Integer, List<NbsV0Note>> entry : notes.entrySet()) {
            dos.writeShort(entry.getKey() - lastTick);
            lastTick = entry.getKey();

            int lastLayer = -1;
            for (NbsV0Note note : entry.getValue()) {
                dos.writeShort(this.layers.indexOf(note.getLayer()) - lastLayer);
                lastLayer = this.layers.indexOf(note.getLayer());
                note.write(dos);
            }
            dos.writeShort(0);
        }
        dos.writeShort(0);

        for (NbsLayer layer : this.layers) {
            if (layer instanceof NbsV0Layer) {
                final NbsV0Layer v0Layer = (NbsV0Layer) layer;
                writeString(dos, v0Layer.getName());
                if (layer instanceof NbsV4Layer) {
                    dos.writeBoolean(((NbsV4Layer) layer).isLocked());
                }
                dos.writeByte(v0Layer.getVolume());
                if (layer instanceof NbsV2Layer) {
                    dos.writeByte(((NbsV2Layer) layer).getPanning());
                }
            }
        }

        dos.writeByte(this.customInstruments.size());
        for (NbsV0CustomInstrument customInstrument : this.customInstruments) {
            customInstrument.write(dos);
        }
    }

    public List<NbsLayer> getLayers() {
        return this.layers;
    }

    public void setLayers(final List<NbsLayer> layers) {
        this.layers = layers;
    }

    public List<NbsV0CustomInstrument> getCustomInstruments() {
        return this.customInstruments;
    }

    public void setCustomInstruments(final List<NbsV0CustomInstrument> customInstruments) {
        this.customInstruments = customInstruments;
    }

}
