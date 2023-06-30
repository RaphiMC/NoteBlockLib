/*
 * This file is part of NoteBlockLib - https://github.com/RaphiMC/NoteBlockLib
 * Copyright (C) 2023 RK_01/RaphiMC and contributors
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

import net.raphimc.noteblocklib.format.txt.model.TxtData;
import net.raphimc.noteblocklib.format.txt.model.TxtHeader;
import net.raphimc.noteblocklib.format.txt.model.TxtNote;
import net.raphimc.noteblocklib.model.Song;
import net.raphimc.noteblocklib.model.SongView;

public class TxtSong extends Song<TxtHeader, TxtData, TxtNote> {

    public TxtSong(final String fileName, final TxtHeader header, final TxtData data) {
        super(fileName, header, data);
    }

    @Override
    protected SongView<TxtNote> createView() {
        final String title = this.fileName == null ? "Txt Song" : this.fileName;

        return new SongView<>(title, this.getHeader().getSpeed(), this.getData().getNotes());
    }

}
