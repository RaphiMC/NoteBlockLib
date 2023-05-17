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
package net.raphimc.noteblocklib.format.future;

import net.raphimc.noteblocklib.format.future.model.FutureData;
import net.raphimc.noteblocklib.format.future.model.FutureHeader;
import net.raphimc.noteblocklib.format.future.model.FutureNote;
import net.raphimc.noteblocklib.model.Song;
import net.raphimc.noteblocklib.model.SongView;

import java.io.File;

public class FutureSong extends Song<FutureHeader, FutureData, FutureNote> {

    public FutureSong(final File sourceFile, final FutureHeader header, final FutureData data) {
        super(sourceFile, header, data);
    }

    @Override
    protected SongView<FutureNote> createView() {
        final String title = this.getSourceFile() == null ? "Future Song" : this.getSourceFile().getName();

        return new SongView<>(title, 20F, this.getData().getNotes());
    }

}
