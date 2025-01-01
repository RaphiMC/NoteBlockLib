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

import net.raphimc.noteblocklib.format.SongFormat;
import net.raphimc.noteblocklib.format.nbs.model.NbsData;
import net.raphimc.noteblocklib.format.nbs.model.NbsHeader;
import net.raphimc.noteblocklib.format.nbs.model.NbsLayer;
import net.raphimc.noteblocklib.format.nbs.model.NbsNote;
import net.raphimc.noteblocklib.model.Song;
import net.raphimc.noteblocklib.model.SongView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class NbsSong extends Song<NbsHeader, NbsData, NbsNote> {

    public NbsSong(final String fileName, final NbsHeader header, final NbsData data) {
        super(SongFormat.NBS, fileName, header, data);
    }

    @Override
    protected SongView<NbsNote> createView() {
        final String title = this.getHeader().getTitle().isEmpty() ? this.fileName == null ? "NBS Song" : this.fileName : this.getHeader().getTitle();

        final Map<Integer, List<NbsNote>> notes = new TreeMap<>();
        for (NbsLayer layer : this.getData().getLayers()) {
            for (Map.Entry<Integer, NbsNote> note : layer.getNotesAtTick().entrySet()) {
                notes.computeIfAbsent(note.getKey(), k -> new ArrayList<>()).add(note.getValue());
            }
        }

        return new SongView<>(title, this.getHeader().getSpeed() / 100F, notes);
    }

}
