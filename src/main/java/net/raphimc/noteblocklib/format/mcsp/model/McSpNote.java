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
package net.raphimc.noteblocklib.format.mcsp.model;

import java.util.Objects;

public class McSpNote {

    private int instrument;
    private int key;

    public int getInstrument() {
        return this.instrument;
    }

    public McSpNote setInstrument(final int instrument) {
        this.instrument = instrument;
        return this;
    }

    public int getKey() {
        return this.key;
    }

    public McSpNote setKey(final int key) {
        this.key = key;
        return this;
    }

    public McSpNote copy() {
        final McSpNote copyNote = new McSpNote();
        copyNote.setInstrument(this.getInstrument());
        copyNote.setKey(this.getKey());
        return copyNote;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        McSpNote mcSpNote = (McSpNote) o;
        return instrument == mcSpNote.instrument && key == mcSpNote.key;
    }

    @Override
    public int hashCode() {
        return Objects.hash(instrument, key);
    }

}
