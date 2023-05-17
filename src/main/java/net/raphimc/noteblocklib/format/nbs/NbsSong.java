/*
 * This file is part of NoteBlockLib - https://github.com/RaphiMC/NoteBlockLib
 * Copyright (C) 2023 RK_01/RaphiMC and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
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

import net.raphimc.noteblocklib.format.nbs.data.NbsV0Data;
import net.raphimc.noteblocklib.format.nbs.data.layer.NbsLayer;
import net.raphimc.noteblocklib.format.nbs.header.NbsV0Header;
import net.raphimc.noteblocklib.format.nbs.note.NbsV0Note;
import net.raphimc.noteblocklib.model.Song;
import net.raphimc.noteblocklib.model.SongView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class NbsSong extends Song<NbsV0Header, NbsV0Data, NbsV0Note> {

    public NbsSong(final File sourceFile, final NbsV0Header header, final NbsV0Data data) {
        super(sourceFile, header, data);
    }

    @Override
    protected SongView<NbsV0Note> createView() {
        final String title = this.getHeader().getTitle().isEmpty() ? this.getSourceFile() == null ? "NBS Song" : this.getSourceFile().getName() : this.getHeader().getTitle();

        final Map<Integer, List<NbsV0Note>> notes = new TreeMap<>();
        for (NbsLayer layer : this.getData().getLayers()) {
            for (Map.Entry<Integer, NbsV0Note> note : layer.getNotesAtTick().entrySet()) {
                notes.computeIfAbsent(note.getKey(), k -> new ArrayList<>()).add(note.getValue());
            }
        }

        return new SongView<>(title, this.getHeader().getSpeed() / 100F, notes);
    }

}
