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

import java.util.Objects;

public class TxtNote {

    private int instrument;
    private int key;

    public int getInstrument() {
        return this.instrument;
    }

    public TxtNote setInstrument(final int instrument) {
        this.instrument = instrument;
        return this;
    }

    public int getKey() {
        return this.key;
    }

    public TxtNote setKey(final int key) {
        this.key = key;
        return this;
    }

    public TxtNote copy() {
        final TxtNote copyNote = new TxtNote();
        copyNote.setInstrument(this.getInstrument());
        copyNote.setKey(this.getKey());
        return copyNote;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TxtNote txtNote = (TxtNote) o;
        return instrument == txtNote.instrument && key == txtNote.key;
    }

    @Override
    public int hashCode() {
        return Objects.hash(instrument, key);
    }

}
