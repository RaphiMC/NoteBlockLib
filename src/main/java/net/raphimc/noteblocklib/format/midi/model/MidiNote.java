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
package net.raphimc.noteblocklib.format.midi.model;

import net.raphimc.noteblocklib.model.Note;
import net.raphimc.noteblocklib.model.NoteWithVolume;

import static net.raphimc.noteblocklib.format.midi.MidiDefinitions.MAX_VELOCITY;

public class MidiNote extends Note implements NoteWithVolume {

    private byte velocity;

    public MidiNote(final byte instrument, final byte key, final byte velocity) {
        super(instrument, key);

        this.velocity = velocity;
    }

    @Override
    public float getVolume() {
        return (float) this.velocity / MAX_VELOCITY * 100F;
    }

    @Override
    public void setVolume(final float volume) {
        this.velocity = (byte) (volume / 100F * MAX_VELOCITY);
    }

    @Override
    public MidiNote clone() {
        return new MidiNote(this.instrument, this.key, this.velocity);
    }

}
