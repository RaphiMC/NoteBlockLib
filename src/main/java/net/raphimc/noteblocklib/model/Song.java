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
package net.raphimc.noteblocklib.model;

import net.raphimc.noteblocklib.format.SongFormat;

public abstract class Song<H extends Header, D extends Data<N>, N extends Note> {

    private final SongFormat format;
    protected final String fileName;
    private final H header;
    private final D data;

    private SongView<N> view;

    public Song(final SongFormat format, final String fileName, final H header, final D data) {
        this.format = format;
        this.fileName = fileName;
        this.header = header;
        this.data = data;

        this.view = this.createView();
    }

    protected abstract SongView<N> createView();

    public void refreshView() {
        this.view = this.createView();
    }

    public SongFormat getFormat() {
        return this.format;
    }

    public H getHeader() {
        return this.header;
    }

    public D getData() {
        return this.data;
    }

    /**
     * Returns an abstracted, generalized and unified view of this song.<br>
     * Any changes made to this view will not be reflected in the original song data.<br>
     * The view may be recreated by using {@link #refreshView()}.
     *
     * @return The song view
     */
    public SongView<N> getView() {
        return this.view;
    }

}
