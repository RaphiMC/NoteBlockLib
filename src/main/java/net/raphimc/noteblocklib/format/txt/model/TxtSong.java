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
package net.raphimc.noteblocklib.format.txt.model;

import net.raphimc.noteblocklib.format.SongFormat;
import net.raphimc.noteblocklib.model.Song;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TxtSong extends Song {

    private final Map<Integer, List<TxtNote>> notes = new HashMap<>();

    public TxtSong() {
        this(null);
    }

    public TxtSong(final String fileName) {
        super(SongFormat.TXT, fileName);
    }

    /**
     * @return A map of all notes, with the tick as the key.
     */
    public Map<Integer, List<TxtNote>> getTxtNotes() {
        return this.notes;
    }

    @Override
    public TxtSong copy() {
        final TxtSong copySong = new TxtSong(this.getFileName());
        copySong.copyGeneralData(this);
        final Map<Integer, List<TxtNote>> notes = this.getTxtNotes();
        final Map<Integer, List<TxtNote>> copyNotes = copySong.getTxtNotes();
        for (Map.Entry<Integer, List<TxtNote>> entry : notes.entrySet()) {
            final List<TxtNote> copyNoteList = new ArrayList<>(entry.getValue().size());
            for (TxtNote note : entry.getValue()) {
                copyNoteList.add(note.copy());
            }
            copyNotes.put(entry.getKey(), copyNoteList);
        }
        return copySong;
    }

}
