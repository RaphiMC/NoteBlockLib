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
package net.raphimc.noteblocklib.format.midi.note;

import net.raphimc.noteblocklib.model.Note;

public class MidiNote extends Note {

    private byte velocity;

    public MidiNote(final byte instrument, final byte key, final byte velocity) {
        super(instrument, key);

        this.velocity = velocity;
    }

    /**
     * @return The velocity/volume of the note, from 0 to 127.
     */
    public byte getVelocity() {
        return this.velocity;
    }

    public void setVelocity(final byte velocity) {
        this.velocity = velocity;
    }

}
