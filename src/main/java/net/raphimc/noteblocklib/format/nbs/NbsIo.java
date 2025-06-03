/*
 * This file is part of NoteBlockLib - https://github.com/RaphiMC/NoteBlockLib
 * Copyright (C) 2022-2025 RK_01/RaphiMC and contributors
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
package net.raphimc.noteblocklib.format.nbs;

import com.google.common.io.LittleEndianDataInputStream;
import com.google.common.io.LittleEndianDataOutputStream;
import net.raphimc.noteblocklib.data.MinecraftInstrument;
import net.raphimc.noteblocklib.format.nbs.model.NbsCustomInstrument;
import net.raphimc.noteblocklib.format.nbs.model.NbsLayer;
import net.raphimc.noteblocklib.format.nbs.model.NbsNote;
import net.raphimc.noteblocklib.format.nbs.model.NbsSong;
import net.raphimc.noteblocklib.model.Note;

import java.io.*;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class NbsIo {

    private static final int BUFFER_SIZE = 1024 * 1024;

    public static NbsSong readSong(final InputStream is, final String fileName) throws IOException {
        final LittleEndianDataInputStream dis = new LittleEndianDataInputStream(new BufferedInputStream(is, BUFFER_SIZE));
        final NbsSong song = new NbsSong(fileName);

        final short length = dis.readShort();
        if (length == 0) {
            song.setVersion(dis.readByte());
            song.setVanillaInstrumentCount(dis.readUnsignedByte());
            if (song.getVersion() >= 3) {
                song.setLength(dis.readShort());
            } else {
                song.setLength((short) -1);
            }
        } else {
            song.setLength(length);
            song.setVersion((byte) 0);
            song.setVanillaInstrumentCount(10);
        }

        if (song.getVersion() < 0 || song.getVersion() > 5) {
            throw new IllegalStateException("Unsupported NBS version: " + song.getVersion());
        }

        song.setLayerCount(dis.readShort());
        song.setTitle(readString(dis));
        song.setAuthor(readString(dis));
        song.setOriginalAuthor(readString(dis));
        song.setDescription(readString(dis));
        song.setTempo(dis.readShort());
        song.setAutoSave(dis.readBoolean());
        song.setAutoSaveInterval(dis.readByte());
        song.setTimeSignature(dis.readByte());
        song.setMinutesSpent(dis.readInt());
        song.setLeftClicks(dis.readInt());
        song.setRightClicks(dis.readInt());
        song.setNoteBlocksAdded(dis.readInt());
        song.setNoteBlocksRemoved(dis.readInt());
        song.setSourceFileName(readString(dis));

        if (song.getVersion() >= 4) {
            song.setLoop(dis.readBoolean());
            song.setMaxLoopCount(dis.readByte());
            song.setLoopStartTick(dis.readShort());
        }

        final Map<Integer, NbsLayer> layers = song.getLayers();
        final List<NbsCustomInstrument> customInstruments = song.getCustomInstruments();

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

                final NbsNote note = new NbsNote();
                note.setInstrument((short) dis.readUnsignedByte());
                note.setKey(dis.readByte());
                if (song.getVersion() >= 4) {
                    note.setVelocity(dis.readByte());
                    note.setPanning((short) dis.readUnsignedByte());
                    note.setPitch(dis.readShort());
                }
                layers.computeIfAbsent(layer, k -> new NbsLayer()).getNotes().put(tick, note);
            }
        }

        if (dis.available() > 0) {
            for (int i = 0; i < song.getLayerCount(); i++) {
                final NbsLayer layer = layers.computeIfAbsent(i, k -> new NbsLayer());
                layer.setName(readString(dis));
                if (song.getVersion() >= 4) {
                    final byte lockedByte = dis.readByte();
                    switch (lockedByte) {
                        case 0:
                            layer.setStatus(NbsLayer.Status.NONE);
                            break;
                        case 1:
                            layer.setStatus(NbsLayer.Status.LOCKED);
                            break;
                        case 2:
                            layer.setStatus(NbsLayer.Status.SOLO);
                            break;
                    }
                }
                layer.setVolume(dis.readByte());
                if (song.getVersion() >= 2) {
                    layer.setPanning((short) dis.readUnsignedByte());
                }
            }
        }

        if (dis.available() > 0) {
            final int customInstrumentsAmount = dis.readUnsignedByte();
            for (int i = 0; i < customInstrumentsAmount; i++) {
                final NbsCustomInstrument customInstrument = new NbsCustomInstrument();
                customInstrument.setName(readString(dis));
                customInstrument.setSoundFilePath(readString(dis));
                customInstrument.setPitch(dis.readByte());
                customInstrument.setPressKey(dis.readBoolean());
                customInstruments.add(customInstrument);
            }
        }

        { // Fill generalized song structure with data
            final Map<NbsCustomInstrument, NbsCustomInstrument> customInstrumentMap = new IdentityHashMap<>(customInstruments.size()); // Cache map to avoid creating new instances for each note
            for (NbsCustomInstrument customInstrument : customInstruments) {
                customInstrumentMap.put(customInstrument, customInstrument.copy().setPitch((byte) NbsDefinitions.F_SHARP_4_NBS_KEY));
            }

            song.getTempoEvents().set(0, song.getTempo() / 100F);
            final boolean hasSoloLayers = layers.values().stream().anyMatch(layer -> layer.getStatus() == NbsLayer.Status.SOLO);
            for (NbsLayer layer : layers.values()) {
                for (Map.Entry<Integer, NbsNote> noteEntry : layer.getNotes().entrySet()) {
                    final NbsNote nbsNote = noteEntry.getValue();

                    final Note note = new Note();
                    note.setNbsKey((float) NbsDefinitions.getEffectivePitch(nbsNote) / NbsDefinitions.PITCHES_PER_KEY);
                    note.setVolume((layer.getVolume() / 100F) * (nbsNote.getVelocity() / 100F));
                    if (layer.getPanning() == NbsDefinitions.CENTER_PANNING) { // Special case
                        note.setPanning((nbsNote.getPanning() - NbsDefinitions.CENTER_PANNING) / 100F);
                    } else {
                        note.setPanning(((layer.getPanning() - NbsDefinitions.CENTER_PANNING) + (nbsNote.getPanning() - NbsDefinitions.CENTER_PANNING)) / 200F);
                    }

                    if (nbsNote.getInstrument() < song.getVanillaInstrumentCount()) {
                        note.setInstrument(MinecraftInstrument.fromNbsId((byte) nbsNote.getInstrument()));
                    } else {
                        final NbsCustomInstrument nbsCustomInstrument = customInstruments.get(nbsNote.getInstrument() - song.getVanillaInstrumentCount());
                        if (song.getVersion() >= 4 && NbsDefinitions.TEMPO_CHANGER_CUSTOM_INSTRUMENT_NAME.equals(nbsCustomInstrument.getName())) {
                            song.getTempoEvents().set(noteEntry.getKey(), Math.abs(nbsNote.getPitch() / 15F));
                            continue;
                        }

                        final int pitchModifier = nbsCustomInstrument.getPitch() - NbsDefinitions.F_SHARP_4_NBS_KEY;
                        if (pitchModifier != 0) { // Pre-apply pitch modifier to note to make it easier for player implementations
                            note.setNbsKey(note.getNbsKey() + pitchModifier);
                            note.setInstrument(customInstrumentMap.get(nbsCustomInstrument)); // Use custom instrument with no pitch modifier, because the pitch modifier is already applied to the note
                        } else {
                            note.setInstrument(nbsCustomInstrument);
                        }
                    }

                    if (layer.getStatus() == NbsLayer.Status.LOCKED) { // Locked layers are muted
                        note.setVolume(0F);
                    } else if (hasSoloLayers && layer.getStatus() != NbsLayer.Status.SOLO) { // Non-solo layers are muted if there are solo layers
                        note.setVolume(0F);
                    }

                    song.getNotes().add(noteEntry.getKey(), note);
                }
            }
        }

        return song;
    }

    public static void writeSong(final NbsSong song, final OutputStream os) throws IOException {
        if (song.getVersion() < 0 || song.getVersion() > 5) {
            throw new IllegalArgumentException("Unsupported NBS version: " + song.getVersion());
        }
        if (song.getLayerCount() > song.getLayers().size()) {
            throw new IllegalArgumentException("Layer count must be less than or equal to the amount of layers");
        }

        final LittleEndianDataOutputStream dos = new LittleEndianDataOutputStream(new BufferedOutputStream(os, BUFFER_SIZE));

        if (song.getVersion() == 0) {
            dos.writeShort(song.getLength());
        } else {
            dos.writeShort(0);
            dos.writeByte(song.getVersion());
            dos.writeByte(song.getVanillaInstrumentCount());
            if (song.getVersion() >= 3) {
                dos.writeShort(song.getLength());
            }
        }

        dos.writeShort(song.getLayerCount());
        writeString(dos, song.getTitleOr(""));
        writeString(dos, song.getAuthorOr(""));
        writeString(dos, song.getOriginalAuthorOr(""));
        writeString(dos, song.getDescriptionOr(""));
        dos.writeShort(song.getTempo());
        dos.writeBoolean(song.isAutoSave());
        dos.writeByte(song.getAutoSaveInterval());
        dos.writeByte(song.getTimeSignature());
        dos.writeInt(song.getMinutesSpent());
        dos.writeInt(song.getLeftClicks());
        dos.writeInt(song.getRightClicks());
        dos.writeInt(song.getNoteBlocksAdded());
        dos.writeInt(song.getNoteBlocksRemoved());
        writeString(dos, song.getSourceFileNameOr(""));

        if (song.getVersion() >= 4) {
            dos.writeBoolean(song.isLoop());
            dos.writeByte(song.getMaxLoopCount());
            dos.writeShort(song.getLoopStartTick());
        }

        final Map<Integer, Map<Integer, NbsNote>> notes = new TreeMap<>();
        for (Map.Entry<Integer, NbsLayer> layerEntry : song.getLayers().entrySet()) {
            for (Map.Entry<Integer, NbsNote> noteEntry : layerEntry.getValue().getNotes().entrySet()) {
                notes.computeIfAbsent(noteEntry.getKey(), k -> new TreeMap<>()).put(layerEntry.getKey(), noteEntry.getValue());
            }
        }

        int lastTick = -1;
        for (Map.Entry<Integer, Map<Integer, NbsNote>> tickEntry : notes.entrySet()) {
            dos.writeShort(tickEntry.getKey() - lastTick);
            lastTick = tickEntry.getKey();

            int lastLayer = -1;
            for (Map.Entry<Integer, NbsNote> layerEntry : tickEntry.getValue().entrySet()) {
                dos.writeShort(layerEntry.getKey() - lastLayer);
                lastLayer = layerEntry.getKey();

                final NbsNote note = layerEntry.getValue();
                dos.writeByte(note.getInstrument());
                dos.writeByte(note.getKey());
                if (song.getVersion() >= 4) {
                    dos.writeByte(note.getVelocity());
                    dos.writeByte(note.getPanning());
                    dos.writeShort(note.getPitch());
                }
            }
            dos.writeShort(0);
        }
        dos.writeShort(0);

        for (int i = 0; i < song.getLayerCount(); i++) {
            final NbsLayer layer = song.getLayers().get(i);
            writeString(dos, layer.getNameOr(""));
            if (song.getVersion() >= 4) {
                switch (layer.getStatus()) {
                    case NONE:
                        dos.writeByte(0);
                        break;
                    case LOCKED:
                        dos.writeByte(1);
                        break;
                    case SOLO:
                        dos.writeByte(2);
                        break;
                }
            }
            dos.writeByte(layer.getVolume());
            if (song.getVersion() >= 2) {
                dos.writeByte(layer.getPanning());
            }
        }

        dos.writeByte(song.getCustomInstruments().size());
        for (NbsCustomInstrument customInstrument : song.getCustomInstruments()) {
            writeString(dos, customInstrument.getNameOr(""));
            writeString(dos, customInstrument.getSoundFilePathOr(""));
            dos.writeByte(customInstrument.getPitch());
            dos.writeBoolean(customInstrument.isPressKey());
        }

        dos.flush();
    }

    private static String readString(final LittleEndianDataInputStream dis) throws IOException {
        int length = dis.readInt();
        final StringBuilder builder = new StringBuilder(length);
        while (length > 0) {
            builder.append((char) dis.readByte());
            length--;
        }
        return builder.toString();
    }

    private static void writeString(final LittleEndianDataOutputStream dos, final String string) throws IOException {
        dos.writeInt(string.length());
        for (char c : string.toCharArray()) {
            dos.writeByte(c);
        }
    }

}
