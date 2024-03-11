/*
 * This file is part of NoteBlockLib - https://github.com/RaphiMC/NoteBlockLib
 * Copyright (C) 2022-2024 RK_01/RaphiMC and contributors
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
package net.raphimc.noteblocklib.format.midi.model;

import net.raphimc.noteblocklib.model.Note;
import net.raphimc.noteblocklib.model.NoteWithPanning;
import net.raphimc.noteblocklib.model.NoteWithVolume;

import java.util.Objects;

import static net.raphimc.noteblocklib.format.midi.MidiDefinitions.CENTER_PAN;
import static net.raphimc.noteblocklib.format.midi.MidiDefinitions.MAX_VELOCITY;

public class MidiNote extends Note implements NoteWithVolume, NoteWithPanning {

    private final long midiTick;
    private byte velocity;
    private byte panning;

    public MidiNote(final long midiTick, final byte instrument, final byte key, final byte velocity, final byte panning) {
        super(instrument, key);

        this.midiTick = midiTick;
        this.velocity = velocity;
        this.panning = panning;
    }

    /**
     * The MIDI tick of this note.<br>
     * This value is excluded from equals and hashcode.
     *
     * @return The tick of the note in the midi sequence.
     */
    public long getMidiTick() {
        return this.midiTick;
    }

    @Override
    public float getVolume() {
        return (float) this.velocity / MAX_VELOCITY * 100F;
    }

    @Override
    public void setVolume(final float volume) {
        this.velocity = (byte) (volume / 100F * MAX_VELOCITY);
    }

    public byte getRawVelocity() {
        return this.velocity;
    }

    @Override
    public float getPanning() {
        return ((this.panning - CENTER_PAN) / (float) CENTER_PAN) * 100F;
    }

    @Override
    public void setPanning(final float panning) {
        this.panning = (byte) (panning / 100F * CENTER_PAN + CENTER_PAN);
    }

    public byte getRawPanning() {
        return this.panning;
    }

    @Override
    public MidiNote clone() {
        return new MidiNote(this.midiTick, this.instrument, this.key, this.velocity, this.panning);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MidiNote midiNote = (MidiNote) o;
        return velocity == midiNote.velocity && panning == midiNote.panning;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), velocity, panning);
    }

}
