/*
 * This file is part of NoteBlockLib - https://github.com/RaphiMC/NoteBlockLib
 * Copyright (C) 2022-2026 RK_01/RaphiMC and contributors
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

import net.raphimc.noteblocklib.format.nbs.model.NbsCustomInstrument;
import net.raphimc.noteblocklib.format.nbs.model.NbsLayer;
import net.raphimc.noteblocklib.format.nbs.model.NbsNote;
import net.raphimc.noteblocklib.format.nbs.model.NbsSong;
import net.raphimc.noteblocklib.util.io.BinaryInputStream;
import net.raphimc.noteblocklib.util.io.BinaryOutputStream;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteOrder;
import java.util.Map;
import java.util.TreeMap;

public final class NbsIo {

    private static final int BUFFER_SIZE = 128 * 1024;

    private NbsIo() {
    }

    public static NbsSong readSong(final InputStream is, final String fileName) throws IOException {
        final BinaryInputStream bis = new BinaryInputStream(new BufferedInputStream(is, BUFFER_SIZE), ByteOrder.LITTLE_ENDIAN);
        final NbsSong song = new NbsSong(fileName);

        final short length = bis.readShort();
        if (length == 0) {
            song.setVersion(bis.readUnsignedByte());
            song.setVanillaInstrumentCount(bis.readUnsignedByte());
            if (song.getVersion() >= 3) {
                song.setLength(bis.readShort());
            } else {
                song.setLength((short) -1);
            }
        } else {
            song.setLength(length);
            song.setVersion(0);
            song.setVanillaInstrumentCount(10);
        }

        if (song.getVersion() < 0 || song.getVersion() > 6) {
            throw new IllegalStateException("Unsupported NBS version: " + song.getVersion());
        }

        song.setLayerCount(bis.readShort());
        song.setTitle(readString(bis));
        song.setAuthor(readString(bis));
        song.setOriginalAuthor(readString(bis));
        song.setDescription(readString(bis));
        song.setTempo(bis.readShort());
        song.setAutoSave(bis.readBoolean());
        song.setAutoSaveInterval(bis.readUnsignedByte());
        song.setTimeSignature(bis.readUnsignedByte());
        song.setMinutesSpent(bis.readInt());
        song.setLeftClicks(bis.readInt());
        song.setRightClicks(bis.readInt());
        song.setNoteBlocksAdded(bis.readInt());
        song.setNoteBlocksRemoved(bis.readInt());
        song.setSourceFileName(readString(bis));

        if (song.getVersion() >= 4) {
            song.setLoop(bis.readBoolean());
            song.setMaxLoopCount(bis.readUnsignedByte());
            song.setLoopStartTick(bis.readShort());
        }

        final Map<Integer, NbsLayer> layers = song.getLayers();
        int tick = -1;
        while (true) {
            final short jumpTicks = bis.readShort();
            if (jumpTicks == 0) {
                break;
            }
            tick += jumpTicks;

            int layer = -1;
            while (true) {
                final short jumpLayers = bis.readShort();
                if (jumpLayers == 0) {
                    break;
                }
                layer += jumpLayers;

                final NbsNote note = new NbsNote();
                note.setInstrument(bis.readUnsignedByte());
                note.setKey(bis.readUnsignedByte());
                if (song.getVersion() >= 4) {
                    note.setVelocity(bis.readUnsignedByte());
                    note.setPanning(bis.readUnsignedByte());
                    note.setPitch(bis.readShort());
                }
                layers.computeIfAbsent(layer, k -> new NbsLayer()).getNotes().put(tick, note);
            }
        }

        if (bis.available() > 0) {
            for (int i = 0; i < song.getLayerCount(); i++) {
                final NbsLayer layer = layers.computeIfAbsent(i, k -> new NbsLayer());
                layer.setName(readString(bis));
                if (song.getVersion() >= 4) {
                    final int lockedByte = bis.readUnsignedByte();
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
                layer.setVolume(bis.readUnsignedByte());
                if (song.getVersion() >= 2) {
                    layer.setPanning(bis.readUnsignedByte());
                }
            }
        }

        if (bis.available() > 0) {
            final int customInstrumentCount = bis.readUnsignedByte();
            for (int i = 0; i < customInstrumentCount; i++) {
                final NbsCustomInstrument customInstrument = new NbsCustomInstrument();
                customInstrument.setName(readString(bis));
                customInstrument.setSoundFilePath(readString(bis));
                customInstrument.setPitch(bis.readUnsignedByte());
                customInstrument.setPressKey(bis.readBoolean());
                song.getCustomInstruments().add(customInstrument);
            }
        }

        NbsConverter.fillGeneralData(song);
        return song;
    }

    public static void writeSong(final NbsSong song, final OutputStream os) throws IOException {
        if (song.getVersion() < 0 || song.getVersion() > 6) {
            throw new IllegalArgumentException("Unsupported NBS version: " + song.getVersion());
        }
        if (song.getLayerCount() > song.getLayers().size()) {
            throw new IllegalArgumentException("Layer count must be less than or equal to the amount of layers");
        }
        final BinaryOutputStream bos = new BinaryOutputStream(new BufferedOutputStream(os, BUFFER_SIZE), ByteOrder.LITTLE_ENDIAN);

        if (song.getVersion() == 0) {
            bos.writeShort(song.getLength());
        } else {
            bos.writeShort(0);
            bos.writeUnsignedByte(song.getVersion());
            bos.writeUnsignedByte(song.getVanillaInstrumentCount());
            if (song.getVersion() >= 3) {
                bos.writeShort(song.getLength());
            }
        }

        bos.writeShort(song.getLayerCount());
        writeString(bos, song.getTitleOr(""));
        writeString(bos, song.getAuthorOr(""));
        writeString(bos, song.getOriginalAuthorOr(""));
        writeString(bos, song.getDescriptionOr(""));
        bos.writeShort(song.getTempo());
        bos.writeBoolean(song.isAutoSave());
        bos.writeUnsignedByte(song.getAutoSaveInterval());
        bos.writeUnsignedByte(song.getTimeSignature());
        bos.writeInt(song.getMinutesSpent());
        bos.writeInt(song.getLeftClicks());
        bos.writeInt(song.getRightClicks());
        bos.writeInt(song.getNoteBlocksAdded());
        bos.writeInt(song.getNoteBlocksRemoved());
        writeString(bos, song.getSourceFileNameOr(""));

        if (song.getVersion() >= 4) {
            bos.writeBoolean(song.isLoop());
            bos.writeUnsignedByte(song.getMaxLoopCount());
            bos.writeShort(song.getLoopStartTick());
        }

        final Map<Integer, Map<Integer, NbsNote>> notes = new TreeMap<>();
        for (Map.Entry<Integer, NbsLayer> layerEntry : song.getLayers().entrySet()) {
            for (Map.Entry<Integer, NbsNote> noteEntry : layerEntry.getValue().getNotes().entrySet()) {
                notes.computeIfAbsent(noteEntry.getKey(), k -> new TreeMap<>()).put(layerEntry.getKey(), noteEntry.getValue());
            }
        }

        int lastTick = -1;
        for (Map.Entry<Integer, Map<Integer, NbsNote>> tickEntry : notes.entrySet()) {
            bos.writeShort(tickEntry.getKey() - lastTick);
            lastTick = tickEntry.getKey();

            int lastLayer = -1;
            for (Map.Entry<Integer, NbsNote> layerEntry : tickEntry.getValue().entrySet()) {
                bos.writeShort(layerEntry.getKey() - lastLayer);
                lastLayer = layerEntry.getKey();

                final NbsNote note = layerEntry.getValue();
                bos.writeUnsignedByte(note.getInstrument());
                bos.writeUnsignedByte(note.getKey());
                if (song.getVersion() >= 4) {
                    bos.writeUnsignedByte(note.getVelocity());
                    bos.writeUnsignedByte(note.getPanning());
                    bos.writeShort(note.getPitch());
                }
            }
            bos.writeShort(0);
        }
        bos.writeShort(0);

        for (int i = 0; i < song.getLayerCount(); i++) {
            final NbsLayer layer = song.getLayers().get(i);
            writeString(bos, layer.getNameOr(""));
            if (song.getVersion() >= 4) {
                switch (layer.getStatus()) {
                    case NONE:
                        bos.writeUnsignedByte(0);
                        break;
                    case LOCKED:
                        bos.writeUnsignedByte(1);
                        break;
                    case SOLO:
                        bos.writeUnsignedByte(2);
                        break;
                    default:
                        throw new IllegalStateException("Unsupported layer status: " + layer.getStatus());
                }
            }
            bos.writeUnsignedByte(layer.getVolume());
            if (song.getVersion() >= 2) {
                bos.writeUnsignedByte(layer.getPanning());
            }
        }

        bos.writeUnsignedByte(song.getCustomInstruments().size());
        for (NbsCustomInstrument customInstrument : song.getCustomInstruments()) {
            writeString(bos, customInstrument.getNameOr(""));
            writeString(bos, customInstrument.getSoundFilePathOr(""));
            bos.writeUnsignedByte(customInstrument.getPitch());
            bos.writeBoolean(customInstrument.isPressKey());
        }

        bos.flush();
    }

    private static String readString(final BinaryInputStream bis) throws IOException {
        final char[] buffer = new char[bis.readInt()];
        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = (char) bis.readUnsignedByte();
        }
        return new String(buffer);
    }

    private static void writeString(final BinaryOutputStream bos, final String string) throws IOException {
        bos.writeInt(string.length());
        for (char c : string.toCharArray()) {
            bos.writeUnsignedByte(c);
        }
    }

}
