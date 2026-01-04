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
package net.raphimc.noteblocklib.format.futureclient.model;

import java.util.Objects;

public class FutureClientNote {

    private byte instrument;
    private byte key;

    public byte getInstrument() {
        return this.instrument;
    }

    public FutureClientNote setInstrument(final byte instrument) {
        this.instrument = instrument;
        return this;
    }

    public FutureClientNote setInstrument(final int instrument) {
        if (instrument < Byte.MIN_VALUE || instrument > Byte.MAX_VALUE) {
            throw new IllegalArgumentException("Instrument must be between " + Byte.MIN_VALUE + " and " + Byte.MAX_VALUE);
        }
        return this.setInstrument((byte) instrument);
    }

    public byte getKey() {
        return this.key;
    }

    public FutureClientNote setKey(final byte key) {
        this.key = key;
        return this;
    }

    public FutureClientNote setKey(final int key) {
        if (key < Byte.MIN_VALUE || key > Byte.MAX_VALUE) {
            throw new IllegalArgumentException("Key must be between " + Byte.MIN_VALUE + " and " + Byte.MAX_VALUE);
        }
        return this.setKey((byte) key);
    }

    public FutureClientNote copy() {
        final FutureClientNote copyNote = new FutureClientNote();
        copyNote.setInstrument(this.getInstrument());
        copyNote.setKey(this.getKey());
        return copyNote;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        FutureClientNote futureClientNote = (FutureClientNote) o;
        return instrument == futureClientNote.instrument && key == futureClientNote.key;
    }

    @Override
    public int hashCode() {
        return Objects.hash(instrument, key);
    }

}
