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
package net.raphimc.noteblocklib.format.futureclient.model;

import net.raphimc.noteblocklib.format.SongFormat;
import net.raphimc.noteblocklib.model.song.Song;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FutureClientSong extends Song {

    private final Map<Integer, List<FutureClientNote>> notes = new HashMap<>();

    public FutureClientSong() {
        this(null);
    }

    public FutureClientSong(final String fileName) {
        super(SongFormat.TXT, fileName);
    }

    /**
     * @return A map of all notes, with the tick as the key.
     */
    public Map<Integer, List<FutureClientNote>> getFutureClientNotes() {
        return this.notes;
    }

    @Override
    public FutureClientSong copy() {
        final FutureClientSong copySong = new FutureClientSong(this.getFileName());
        copySong.copyGeneralData(this);
        final Map<Integer, List<FutureClientNote>> notes = this.getFutureClientNotes();
        final Map<Integer, List<FutureClientNote>> copyNotes = copySong.getFutureClientNotes();
        for (Map.Entry<Integer, List<FutureClientNote>> entry : notes.entrySet()) {
            final List<FutureClientNote> copyNoteList = new ArrayList<>(entry.getValue().size());
            for (FutureClientNote note : entry.getValue()) {
                copyNoteList.add(note.copy());
            }
            copyNotes.put(entry.getKey(), copyNoteList);
        }
        return copySong;
    }

}
