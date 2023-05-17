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

import net.raphimc.noteblocklib.format.nbs.note.NbsV0Note;

import java.util.Map;

public class NbsV0Layer extends NbsLayer {

    private String name;
    private byte volume;

    public NbsV0Layer(final Map<Integer, NbsV0Note> notesAtTick, final String name, final byte volume) {
        super(notesAtTick);

        this.name = name;
        this.volume = volume;
    }

    /**
     * @return The name of the layer.
     */
    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @return The volume of the layer (percentage). Ranges from 0-100.
     */
    public byte getVolume() {
        return this.volume;
    }

    public void setVolume(final byte volume) {
        this.volume = volume;
    }

}
