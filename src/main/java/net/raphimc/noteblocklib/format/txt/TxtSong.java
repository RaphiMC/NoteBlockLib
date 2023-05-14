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
package net.raphimc.noteblocklib.format.txt;

import net.raphimc.noteblocklib.format.txt.data.TxtData;
import net.raphimc.noteblocklib.format.txt.header.TxtV1Header;
import net.raphimc.noteblocklib.format.txt.header.TxtV2Header;
import net.raphimc.noteblocklib.format.txt.note.TxtNote;
import net.raphimc.noteblocklib.model.Song;
import net.raphimc.noteblocklib.model.SongView;

import java.io.File;

public class TxtSong extends Song<TxtV1Header, TxtData, TxtNote> {

    public TxtSong(final File sourceFile, final TxtV1Header header, final TxtData data) {
        super(sourceFile, header, data);
    }

    @Override
    protected SongView<TxtNote> createView() {
        final String title = this.getSourceFile() == null ? "Txt Song" : this.getSourceFile().getName();
        final float speed = this.getHeader() instanceof TxtV2Header ? ((TxtV2Header) this.getHeader()).getSpeed() : 20F;

        return new SongView<>(title, speed, this.getData().getNotes());
    }

}
