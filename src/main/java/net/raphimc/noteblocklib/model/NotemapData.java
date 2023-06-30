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
package net.raphimc.noteblocklib.model;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public abstract class NotemapData<N extends Note> implements Data<N> {

    protected Map<Integer, List<N>> notes;

    public NotemapData() {
        this.notes = new TreeMap<>();
    }

    public NotemapData(final Map<Integer, List<N>> notes) {
        this.notes = notes;
    }

    public Map<Integer, List<N>> getNotes() {
        return this.notes;
    }

    public void setNotes(final Map<Integer, List<N>> notes) {
        this.notes = notes;
    }

}
