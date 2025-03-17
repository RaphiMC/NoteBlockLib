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
package net.raphimc.noteblocklib.format.mcsp.model;

import net.raphimc.noteblocklib.format.SongFormat;
import net.raphimc.noteblocklib.model.Song;

import java.util.HashMap;
import java.util.Map;

public class McSpSong extends Song {

    private final Map<Integer, McSpNote[]> notes = new HashMap<>();

    public McSpSong() {
        this(null);
    }

    public McSpSong(final String fileName) {
        super(SongFormat.MCSP, fileName);
    }

    /**
     * @return A map of all notes, with the tick as the key. The notes array must be 7 elements long.
     */
    public Map<Integer, McSpNote[]> getMcSpNotes() {
        return this.notes;
    }

    @Override
    public McSpSong copy() {
        final McSpSong copySong = new McSpSong(this.getFileName());
        copySong.copyGeneralData(this);
        final Map<Integer, McSpNote[]> notes = this.getMcSpNotes();
        final Map<Integer, McSpNote[]> copyNotes = copySong.getMcSpNotes();
        for (Map.Entry<Integer, McSpNote[]> entry : notes.entrySet()) {
            final McSpNote[] copyNotesArray = new McSpNote[entry.getValue().length];
            for (int i = 0; i < copyNotesArray.length; i++) {
                final McSpNote note = entry.getValue()[i];
                if (note != null) {
                    copyNotesArray[i] = note.copy();
                }
            }
            copyNotes.put(entry.getKey(), copyNotesArray);
        }
        return copySong;
    }

}
