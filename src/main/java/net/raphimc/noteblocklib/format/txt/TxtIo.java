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
package net.raphimc.noteblocklib.format.txt;

import net.raphimc.noteblocklib.data.MinecraftInstrument;
import net.raphimc.noteblocklib.format.txt.model.TxtNote;
import net.raphimc.noteblocklib.format.txt.model.TxtSong;
import net.raphimc.noteblocklib.model.Note;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TxtIo {

    private static final int BUFFER_SIZE = 1024 * 1024;

    public static TxtSong readSong(final InputStream is, final String fileName) throws IOException {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8), BUFFER_SIZE);
        final TxtSong song = new TxtSong(fileName);

        final Map<Integer, List<TxtNote>> notes = song.getTxtNotes();
        while (true) {
            final String line = reader.readLine();
            if (line == null) break;
            if (line.isEmpty()) continue;

            if (line.startsWith("// Name: ")) {
                song.setTitle(line.substring(9));
            } else if (line.startsWith("// Author: ")) {
                song.setAuthor(line.substring(11));
            } else {
                try {
                    final String[] split = line.split(":");
                    final TxtNote note = new TxtNote();
                    note.setInstrument(Integer.parseInt(split[2]));
                    note.setKey(Integer.parseInt(split[1]));
                    notes.computeIfAbsent(Integer.parseInt(split[0]), k -> new ArrayList<>()).add(note);
                } catch (NumberFormatException | IndexOutOfBoundsException ignored) {
                }
            }
        }

        { // Fill generalized song structure with data
            song.getTempoEvents().set(0, TxtDefinitions.TEMPO);
            for (Map.Entry<Integer, List<TxtNote>> entry : notes.entrySet()) {
                for (TxtNote txtNote : entry.getValue()) {
                    final Note note = new Note();
                    note.setInstrument(MinecraftInstrument.fromMcId(txtNote.getInstrument()));
                    note.setMcKey(txtNote.getKey());
                    song.getNotes().add(entry.getKey(), note);
                }
            }
        }

        return song;
    }

    public static void writeSong(final TxtSong song, final OutputStream os) throws IOException {
        final OutputStreamWriter writer = new OutputStreamWriter(new BufferedOutputStream(os, BUFFER_SIZE), StandardCharsets.UTF_8);
        if (song.getTitle() != null) {
            writer.write("// Name: " + song.getTitle() + "\n");
        }
        if (song.getAuthor() != null) {
            writer.write("// Author: " + song.getAuthor() + "\n");
        }

        for (Map.Entry<Integer, List<TxtNote>> entry : song.getTxtNotes().entrySet()) {
            for (TxtNote txtNote : entry.getValue()) {
                writer.write(entry.getKey() + ":" + txtNote.getKey() + ":" + txtNote.getInstrument() + "\n");
            }
        }

        writer.flush();
    }

}
