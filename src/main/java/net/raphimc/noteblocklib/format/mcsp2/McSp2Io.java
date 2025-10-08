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
package net.raphimc.noteblocklib.format.mcsp2;

import net.raphimc.noteblocklib.data.MinecraftInstrument;
import net.raphimc.noteblocklib.format.mcsp2.model.McSp2Layer;
import net.raphimc.noteblocklib.format.mcsp2.model.McSp2Note;
import net.raphimc.noteblocklib.format.mcsp2.model.McSp2Song;
import net.raphimc.noteblocklib.model.Note;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Matcher;

public class McSp2Io {

    private static final int BUFFER_SIZE = 1024 * 1024;

    public static McSp2Song readSong(final InputStream is, final String fileName) {
        final Scanner scanner = new Scanner(new BufferedInputStream(is, BUFFER_SIZE), StandardCharsets.ISO_8859_1.name()).useDelimiter("[|\\n]");
        final McSp2Song song = new McSp2Song(fileName);
        final Map<Integer, McSp2Layer> layers = song.getLayers();

        scanner.nextInt(); // version? Is ignored by Minecraft Song Planner v2.5

        song.setAutoSaveInterval(scanner.nextInt());
        song.setTitle(scanner.next());
        song.setAuthor(scanner.next());
        song.setOriginalAuthor(scanner.next());
        if (!scanner.next().isEmpty()) {
            throw new IllegalStateException("Invalid MCSP2 header");
        }

        int tick = 0;
        while (scanner.hasNext()) {
            tick += scanner.nextInt();
            final Matcher noteData = McSp2Definitions.NOTE_DATA_PATTERN.matcher(scanner.next());

            int layer = 0;
            while (noteData.find()) {
                if (noteData.groupCount() == 2) {
                    layer += Integer.parseInt(noteData.group(1));
                    layers.computeIfAbsent(layer, k -> new McSp2Layer()).getNotes().put(tick, new McSp2Note().setInstrumentAndKey(noteData.group(2).charAt(0)));
                } else if (noteData.groupCount() == 1) {
                    layers.computeIfAbsent(layer, k -> new McSp2Layer()).getNotes().put(tick, new McSp2Note().setInstrumentAndKey(noteData.group(1).charAt(0)));
                } else if (noteData.groupCount() != 0) {
                    throw new IllegalArgumentException("Invalid note data: " + noteData.group(0));
                }
            }
        }

        try { // Optional metadata
            scanner.nextLine();
            song.setTempo(scanner.nextInt());
            song.setLeftClicks(scanner.nextInt());
            song.setRightClicks(scanner.nextInt());
            song.setNoteBlocksAdded(scanner.nextInt());
            song.setNoteBlocksRemoved(scanner.nextInt());
            song.setMinutesSpent(scanner.nextInt());
        } catch (NoSuchElementException ignored) {
        }

        { // Fill generalized song structure with data
            song.getTempoEvents().set(0, song.getTempo());
            for (McSp2Layer layer : song.getLayers().values()) {
                for (Map.Entry<Integer, McSp2Note> noteEntry : layer.getNotes().entrySet()) {
                    final McSp2Note mcSp2Note = noteEntry.getValue();

                    final Note note = new Note();
                    note.setInstrument(MinecraftInstrument.fromNbsId(mcSp2Note.getInstrument()));
                    note.setMcKey(mcSp2Note.getKey());
                    song.getNotes().add(noteEntry.getKey(), note);
                }
            }
        }

        return song;
    }

    public static void writeSong(final McSp2Song song, final OutputStream os) throws IOException {
        final OutputStreamWriter writer = new OutputStreamWriter(new BufferedOutputStream(os, BUFFER_SIZE), StandardCharsets.ISO_8859_1);
        writer.write("2");
        writer.write("|");
        writer.write(String.valueOf(song.getAutoSaveInterval()));
        writer.write("|");
        writer.write(song.getTitleOr("").replace("|", "_"));
        writer.write("|");
        writer.write(song.getAuthorOr("").replace("|", "_"));
        writer.write("|");
        writer.write(song.getOriginalAuthorOr("").replace("|", "_"));
        writer.write("|");

        final Map<Integer, Map<Integer, McSp2Note>> notes = new TreeMap<>();
        for (Map.Entry<Integer, McSp2Layer> layerEntry : song.getLayers().entrySet()) {
            for (Map.Entry<Integer, McSp2Note> noteEntry : layerEntry.getValue().getNotes().entrySet()) {
                notes.computeIfAbsent(noteEntry.getKey(), k -> new TreeMap<>()).put(layerEntry.getKey(), noteEntry.getValue());
            }
        }

        int lastTick = 0;
        for (Map.Entry<Integer, Map<Integer, McSp2Note>> tickEntry : notes.entrySet()) {
            writer.write("|");
            writer.write(String.valueOf(tickEntry.getKey() - lastTick));
            lastTick = tickEntry.getKey();

            int lastLayer = 0;
            final StringBuilder noteData = new StringBuilder();
            for (Map.Entry<Integer, McSp2Note> layerEntry : tickEntry.getValue().entrySet()) {
                noteData.append(layerEntry.getKey() - lastLayer);
                noteData.append('>');
                noteData.append(layerEntry.getValue().getInstrumentAndKey());
                lastLayer = layerEntry.getKey();
            }
            writer.write("|");
            writer.write(noteData.toString());
        }
        writer.write("\n");

        writer.write(String.valueOf(song.getTempo()));
        writer.write("|");
        writer.write(String.valueOf(song.getLeftClicks()));
        writer.write("|");
        writer.write(String.valueOf(song.getRightClicks()));
        writer.write("|");
        writer.write(String.valueOf(song.getNoteBlocksAdded()));
        writer.write("|");
        writer.write(String.valueOf(song.getNoteBlocksRemoved()));
        writer.write("|");
        writer.write(String.valueOf(song.getMinutesSpent()));

        writer.flush();
    }

}
