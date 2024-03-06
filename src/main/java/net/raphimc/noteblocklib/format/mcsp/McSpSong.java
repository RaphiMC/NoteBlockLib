/*
 * This file is part of NoteBlockLib - https://github.com/RaphiMC/NoteBlockLib
 * Copyright (C) 2022-2024 RK_01/RaphiMC and contributors
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

import net.raphimc.noteblocklib.format.mcsp.model.McSpData;
import net.raphimc.noteblocklib.format.mcsp.model.McSpHeader;
import net.raphimc.noteblocklib.format.mcsp.model.McSpLayer;
import net.raphimc.noteblocklib.format.mcsp.model.McSpNote;
import net.raphimc.noteblocklib.model.Song;
import net.raphimc.noteblocklib.model.SongView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class McSpSong extends Song<McSpHeader, McSpData, McSpNote> {

    public McSpSong(final String fileName, final McSpHeader header, final McSpData data) {
        super(fileName, header, data);
    }

    @Override
    protected SongView<McSpNote> createView() {
        final String title = this.getHeader().getTitle().isEmpty() ? this.fileName == null ? "MCSP Song" : this.fileName : this.getHeader().getTitle();

        final Map<Integer, List<McSpNote>> notes = new TreeMap<>();
        for (McSpLayer layer : this.getData().getLayers()) {
            for (Map.Entry<Integer, McSpNote> note : layer.getNotesAtTick().entrySet()) {
                notes.computeIfAbsent(note.getKey(), k -> new ArrayList<>()).add(note.getValue());
            }
        }

        return new SongView<>(title, this.getHeader().getSpeed(), notes);
    }

}
