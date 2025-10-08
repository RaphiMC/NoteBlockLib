/*
 * This file is part of NoteBlockLib - https://github.com/RaphiMC/NoteBlockLib
 * Copyright (C) 2022-2025 RK_01/RaphiMC and contributors
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

import net.raphimc.noteblocklib.data.MinecraftDefinitions;
import net.raphimc.noteblocklib.format.midi.MidiDefinitions;
import net.raphimc.noteblocklib.format.nbs.NbsDefinitions;
import net.raphimc.noteblocklib.model.instrument.Instrument;

import java.util.Objects;

public class Note {

    private Instrument instrument;
    private float midiKey;
    private float volume;
    private float panning;

    public Note() {
        this.volume = 1F;
        this.panning = 0F;
    }

    /**
     * @return The instrument of the note. Default Minecraft instruments are stored in {@link net.raphimc.noteblocklib.data.MinecraftInstrument}.
     */
    public Instrument getInstrument() {
        return this.instrument;
    }

    /**
     * @param instrument The instrument of the note. Default Minecraft instruments are stored in {@link net.raphimc.noteblocklib.data.MinecraftInstrument}.
     * @return this
     */
    public Note setInstrument(final Instrument instrument) {
        if (instrument == null) {
            throw new IllegalArgumentException("Instrument cannot be null");
        }
        this.instrument = instrument;
        return this;
    }

    /**
     * @return The MIDI key of the note (21 = A0, 108 = C8). Fractional part is the fine-pitch.
     */
    public float getMidiKey() {
        return this.midiKey;
    }

    /**
     * @param midiKey The MIDI key of the note (21 = A0, 108 = C8). Fractional part is the fine-pitch.
     * @return this
     */
    public Note setMidiKey(final float midiKey) {
        if (midiKey < MidiDefinitions.LOWEST_KEY || midiKey > MidiDefinitions.HIGHEST_KEY) {
            throw new IllegalArgumentException("MIDI key must be between " + MidiDefinitions.LOWEST_KEY + " and " + MidiDefinitions.HIGHEST_KEY);
        }
        this.midiKey = midiKey;
        return this;
    }

    /**
     * Calling this has the same effect as calling {@link #getMidiKey()}.
     *
     * @return The key of the note in the Minecraft Note Block range (0-24). The center key (F#4) is 12. May be out of range, if the note has not been transposed.
     */
    public int getMcKey() {
        return Math.round(this.midiKey - MinecraftDefinitions.LOWEST_MIDI_KEY);
    }

    /**
     * Calling this has the same effect as calling {@link #setMidiKey(float)}.
     *
     * @param mcKey The key of the note in the Minecraft Note Block range (0-24). The center key (F#4) is 12.
     * @return this
     */
    public Note setMcKey(final int mcKey) {
        return this.setMidiKey(mcKey + MinecraftDefinitions.LOWEST_MIDI_KEY);
    }

    /**
     * Calling this has the same effect as calling {@link #getMidiKey()}.
     *
     * @return The key of the note in the Minecraft Note Block Studio range (0-87). The center key (F#4) is 45. May be out of range, if the note has not been transposed.
     */
    public int getNbsKey() {
        return Math.round(this.midiKey - NbsDefinitions.LOWEST_MIDI_KEY);
    }

    /**
     * Calling this has the same effect as calling {@link #setMidiKey(float)}.
     *
     * @param nbsKey The key of the note in the Minecraft Note Block Studio range (0-87). The center key (F#4) is 45.
     * @return this
     */
    public Note setNbsKey(final float nbsKey) {
        return this.setMidiKey(nbsKey + NbsDefinitions.LOWEST_MIDI_KEY);
    }

    /**
     * @return The fractional part of the note key (-0.5F = 50% lower, 0.0F = normal, 0.5F = 50% higher). Only useful if you need the key as an integer and the fine-pitch separately.
     */
    public float getFractionalKeyPart() {
        final int roundedKey = Math.round(this.midiKey);
        return this.midiKey - roundedKey;
    }

    /**
     * Calling this has the same effect as calling {@link #getMidiKey()}.
     *
     * @return The pitch of the note to use when playing the sample. (1.0F = normal speed, 2.0F = double speed, 0.5F = half speed). The center key (F#4) is 1.0F.
     */
    public float getPitch() {
        return (float) Math.pow(2D, (double) (this.midiKey - MidiDefinitions.F_SHARP_4_KEY) / MidiDefinitions.KEYS_PER_OCTAVE);
    }

    /**
     * Calling this has the same effect as calling {@link #setMidiKey(float)}.
     *
     * @param pitch The pitch of the note to use when playing the sample. (1.0F = normal speed, 2.0F = double speed, 0.5F = half speed). The center key (F#4) is 1.0F.
     * @return this
     */
    public Note setPitch(final float pitch) {
        if (pitch <= 0) {
            throw new IllegalArgumentException("Pitch must be greater than 0");
        }
        return this.setMidiKey((float) (MidiDefinitions.F_SHARP_4_KEY + MidiDefinitions.KEYS_PER_OCTAVE * Math.log(pitch) / Math.log(2D)));
    }

    /**
     * @return The volume of the note. (0.0F = 0%, 1.0F = 100%)
     */
    public float getVolume() {
        return this.volume;
    }

    /**
     * @param volume The volume of the note. (0.0F = 0%, 1.0F = 100%)
     * @return this
     */
    public Note setVolume(final float volume) {
        if (volume < 0F || volume > 1F) {
            throw new IllegalArgumentException("Volume must be between 0 and 1");
        }
        this.volume = volume;
        return this;
    }

    /**
     * @return The panning of the note. (-1.0F = 100% left, 0.0F = 0% center, 1.0F = 100% right)
     */
    public float getPanning() {
        return this.panning;
    }

    /**
     * @param panning The panning of the note. (-1.0F = 100% left, 0.0F = 0% center, 1.0F = 100% right)
     * @return this
     */
    public Note setPanning(final float panning) {
        if (panning < -1F || panning > 1F) {
            throw new IllegalArgumentException("Panning must be between -1 and 1");
        }
        this.panning = panning;
        return this;
    }

    /**
     * @return If the note is outside the vanilla Minecraft octave range.
     */
    public boolean isOutsideMinecraftOctaveRange() {
        return this.midiKey < MinecraftDefinitions.LOWEST_MIDI_KEY || this.midiKey > MinecraftDefinitions.HIGHEST_MIDI_KEY;
    }

    public Note copy() {
        final Note copyNote = new Note();
        copyNote.instrument = this.instrument.copy();
        copyNote.midiKey = this.midiKey;
        copyNote.volume = this.volume;
        copyNote.panning = this.panning;
        return copyNote;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return midiKey == note.midiKey && Float.compare(volume, note.volume) == 0 && Float.compare(panning, note.panning) == 0 && Objects.equals(instrument, note.instrument);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instrument, midiKey, volume, panning);
    }

}
