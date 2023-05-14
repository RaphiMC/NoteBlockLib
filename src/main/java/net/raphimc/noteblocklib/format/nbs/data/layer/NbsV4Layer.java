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
package net.raphimc.noteblocklib.format.nbs.data.layer;

import net.raphimc.noteblocklib.format.nbs.note.NbsNote;

import java.util.Map;

public class NbsV4Layer extends NbsV2Layer {

    private boolean locked;

    public NbsV4Layer(final Map<Integer, NbsNote> notesAtTick, final String name, final byte volume, final short panning, final boolean locked) {
        super(notesAtTick, name, volume, panning);

        this.locked = locked;
    }

    /**
     * @return Whether this layer has been marked as locked.
     */
    public boolean isLocked() {
        return this.locked;
    }

    public void setLocked(final boolean locked) {
        this.locked = locked;
    }

}