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
package net.raphimc.noteblocklib.model;

public abstract class Note {

    private byte instrument;
    private byte key;

    public Note(final byte instrument, final byte key) {
        this.instrument = instrument;
        this.key = key;
    }

    /**
     * @return The instrument of the note. Uses the NBS id system. See {@link net.raphimc.noteblocklib.util.Instrument#fromNbsId(byte)}
     */
    public byte getInstrument() {
        return this.instrument;
    }

    public void setInstrument(final byte instrument) {
        this.instrument = instrument;
    }

    /**
     * @return The key of the note, from 0-87, where 0 is A0 and 87 is C8. 33-57 is within the 2-octave limit.
     */
    public byte getKey() {
        return this.key;
    }

    public void setKey(final byte key) {
        this.key = key;
    }

}
