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
package net.raphimc.noteblocklib.format.mcsp2.model;

import net.raphimc.noteblocklib.format.mcsp2.McSp2Definitions;

import java.util.Objects;

public class McSp2Note {

    private byte instrument;
    private byte key;

    public int getInstrument() {
        return this.instrument;
    }

    public McSp2Note setInstrument(final int instrument) {
        if (instrument < 0 || instrument > 4) {
            throw new IllegalArgumentException("Instrument must be between 0 and 4");
        }
        this.instrument = (byte) instrument;
        return this;
    }

    public int getKey() {
        return this.key;
    }

    public McSp2Note setKey(final int key) {
        if (key < 0 || key > 24) {
            throw new IllegalArgumentException("Key must be between 0 and 24");
        }
        this.key = (byte) key;
        return this;
    }

    public McSp2Note setInstrumentAndKey(final char data) {
        final int index = McSp2Definitions.NOTE_DATA_MAPPING.indexOf(data);
        if (index == -1) {
            throw new IllegalArgumentException("Invalid note data: " + data);
        }
        this.instrument = (byte) (index / 25);
        this.key = (byte) (index % 25);
        return this;
    }

    public char getInstrumentAndKey() {
        return McSp2Definitions.NOTE_DATA_MAPPING.charAt(this.instrument * 25 + this.key);
    }

    public McSp2Note copy() {
        final McSp2Note copyNote = new McSp2Note();
        copyNote.setInstrument(this.getInstrument());
        copyNote.setKey(this.getKey());
        return copyNote;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        McSp2Note mcSp2Note = (McSp2Note) o;
        return instrument == mcSp2Note.instrument && key == mcSp2Note.key;
    }

    @Override
    public int hashCode() {
        return Objects.hash(instrument, key);
    }

}
