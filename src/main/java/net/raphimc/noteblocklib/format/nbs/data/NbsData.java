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
import net.raphimc.noteblocklib.format.nbs.data.layer.NBSv0Layer;
import net.raphimc.noteblocklib.format.nbs.data.layer.NbsLayer;
import net.raphimc.noteblocklib.format.nbs.data.layer.NbsV2Layer;
import net.raphimc.noteblocklib.format.nbs.data.layer.NbsV4Layer;
import net.raphimc.noteblocklib.format.nbs.header.NbsV0Header;
import net.raphimc.noteblocklib.format.nbs.note.NbsNote;
import net.raphimc.noteblocklib.format.nbs.note.NbsV0Note;
import net.raphimc.noteblocklib.format.nbs.note.NbsV4Note;
import net.raphimc.noteblocklib.model.Data;
import net.raphimc.noteblocklib.util.Instrument;

import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static net.raphimc.noteblocklib.format.nbs.NbsParser.readString;

public class NbsData implements Data<NbsNote> {

    private Map<Integer, NbsLayer> layers;
    private List<NbsV0CustomInstrument> customInstruments;

    @SuppressWarnings("UnstableApiUsage")
    public NbsData(final NbsV0Header header, final LittleEndianDataInputStream dis) throws IOException {
        this.layers = new TreeMap<>();
        this.customInstruments = new ArrayList<>();

        final int customInstrumentDiff = Instrument.values().length - header.getVanillaInstrumentCount();
        header.setVanillaInstrumentCount((byte) Instrument.values().length);

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
                this.layers.computeIfAbsent(layer, k -> new NbsLayer(new TreeMap<>())).getNotesAtTick().put(tick, note);
                note.setLayer(this.layers.get(layer));
            }
        }

        try {
            for (int i = 0; i < header.getLayerCount(); i++) {
                final String name = readString(dis);
                final boolean locked = header.getNbsVersion() >= 4 && dis.readBoolean();
                final byte volume = dis.readByte();
                final short panning = (short) (header.getNbsVersion() >= 2 ? dis.readUnsignedByte() : 100);

                final NbsLayer layer = this.layers.get(i);
                if (layer == null) continue;
                if (header.getNbsVersion() >= 4) {
                    this.layers.put(i, new NbsV4Layer(layer.getNotesAtTick(), name, volume, panning, locked));
                } else if (header.getNbsVersion() >= 2) {
                    this.layers.put(i, new NbsV2Layer(layer.getNotesAtTick(), name, volume, panning));
                } else {
                    this.layers.put(i, new NBSv0Layer(layer.getNotesAtTick(), name, volume));
                }
                for (NbsNote note : this.layers.get(i).getNotesAtTick().values()) {
                    note.setLayer(this.layers.get(i));
                }
            }
        } catch (EOFException ignored) {
        }

        try {
            final int customInstrumentsAmount = dis.readUnsignedByte();
            for (int i = 0; i < customInstrumentsAmount; i++) {
                this.customInstruments.add(new NbsV0CustomInstrument(dis));
            }
        } catch (EOFException ignored) {
        }
    }

    public NbsData(final Map<Integer, NbsLayer> layers, final List<NbsV0CustomInstrument> customInstruments) {
        this.layers = layers;
        this.customInstruments = customInstruments;
    }

    public Map<Integer, NbsLayer> getLayers() {
        return this.layers;
    }

    public void setLayers(final Map<Integer, NbsLayer> layers) {
        this.layers = layers;
    }

    public List<NbsV0CustomInstrument> getCustomInstruments() {
        return this.customInstruments;
    }

    public void setCustomInstruments(final List<NbsV0CustomInstrument> customInstruments) {
        this.customInstruments = customInstruments;
    }

}
