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
package net.raphimc.noteblocklib.format.mcsp;

import net.raphimc.noteblocklib.data.MinecraftInstrument;
import net.raphimc.noteblocklib.format.mcsp.model.McSpNote;
import net.raphimc.noteblocklib.format.mcsp.model.McSpSong;
import net.raphimc.noteblocklib.model.Note;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Scanner;

public class McSpIo {

    private static final int BUFFER_SIZE = 1024 * 1024;

    public static McSpSong readSong(final InputStream is, final String fileName) {
        final Scanner scanner = new Scanner(new BufferedInputStream(is, BUFFER_SIZE), StandardCharsets.ISO_8859_1.name()).useDelimiter("\\|");
        final McSpSong song = new McSpSong(fileName);

        scanner.nextInt(); // version? Is ignored by Minecraft Song Planner v2.5

        final Map<Integer, McSpNote[]> notes = song.getMcSpNotes();

        int tick = 0;
        while (scanner.hasNext()) {
            tick += scanner.nextInt();
            final char[] noteData = scanner.next().toCharArray();
            if (noteData.length != McSpDefinitions.NOTE_COUNT * 2) {
                throw new IllegalArgumentException("Invalid note data: " + new String(noteData));
            }
            final McSpNote[] noteArray = new McSpNote[McSpDefinitions.NOTE_COUNT];
            for (int i = 0; i < noteArray.length; i++) {
                final int instrument = noteData[i * 2] - '0';
                final int key = noteData[i * 2 + 1] - 'A';
                if (instrument == 0) continue;

                final McSpNote note = new McSpNote();
                note.setInstrument((byte) (instrument - 1));
                note.setKey((byte) key);
                noteArray[i] = note;
            }
            notes.put(tick, noteArray);
        }

        { // Fill generalized song structure with data
            song.getTempoEvents().setTempo(0, 10);
            for (Map.Entry<Integer, McSpNote[]> entry : notes.entrySet()) {
                for (McSpNote mcSpNote : entry.getValue()) {
                    if (mcSpNote == null) continue;

                    final Note note = new Note();
                    note.setInstrument(MinecraftInstrument.fromNbsId(mcSpNote.getInstrument()));
                    note.setMcKey(mcSpNote.getKey());
                    song.getNotes().add(entry.getKey(), note);
                }
            }
        }

        return song;
    }

}
