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
package net.raphimc.noteblocklib.format.nbs.model;

import net.raphimc.noteblocklib.format.nbs.NbsDefinitions;

import java.util.Objects;

public class NbsNote {

    /**
     * @since v0
     */
    private byte instrument;

    /**
     * @since v0
     */
    private byte key;

    /**
     * @since v4
     */
    private byte velocity;

    /**
     * @since v4
     */
    private byte panning;

    /**
     * @since v4
     */
    private short pitch;

    public NbsNote() {
        this.velocity = 100;
        this.panning = NbsDefinitions.CENTER_PANNING;
    }

    /**
     * @return The instrument of the note block. This is 0-15, or higher if the song uses custom instruments.
     * @since v0
     */
    public int getInstrument() {
        return this.instrument & 0xFF;
    }

    /**
     * @param instrument The instrument of the note block. This is 0-15, or higher if the song uses custom instruments.
     * @return this
     * @since v0
     */
    public NbsNote setInstrument(final int instrument) {
        if (instrument < 0 || instrument > 255) {
            throw new IllegalArgumentException("Instrument must be between 0 and 255");
        }
        this.instrument = (byte) instrument;
        return this;
    }

    /**
     * @return The key of the note block, from 0-87, where 0 is A0 and 87 is C8. 33-57 is within the 2-octave limit.
     * @since v0
     */
    public int getKey() {
        return this.key & 0xFF;
    }

    /**
     * @param key The key of the note block, from 0-87, where 0 is A0 and 87 is C8. 33-57 is within the 2-octave limit.
     * @return this
     * @since v0
     */
    public NbsNote setKey(final int key) {
        if (key < 0 || key > 255) {
            throw new IllegalArgumentException("Key must be between 0 and 255");
        }
        this.key = (byte) key;
        return this;
    }

    /**
     * @return The velocity/volume of the note block, from 0% to 100%.
     * @since v4
     */
    public int getVelocity() {
        return this.velocity & 0xFF;
    }

    /**
     * @param velocity The velocity/volume of the note block, from 0% to 100%.
     * @return this
     * @since v4
     */
    public NbsNote setVelocity(final int velocity) {
        if (velocity < 0 || velocity > 255) {
            throw new IllegalArgumentException("Velocity must be between 0 and 255");
        }
        this.velocity = (byte) velocity;
        return this;
    }

    /**
     * @return The stereo position of the note block, from 0-200. 0 is 2 blocks right, 100 is center, 200 is 2 blocks left.
     * @since v4
     */
    public int getPanning() {
        return this.panning & 0xFF;
    }

    /**
     * @param panning The stereo position of the note block, from 0-200. 0 is 2 blocks right, 100 is center, 200 is 2 blocks left.
     * @return this
     * @since v4
     */
    public NbsNote setPanning(final int panning) {
        if (panning < 0 || panning > 255) {
            throw new IllegalArgumentException("Panning must be between 0 and 255");
        }
        this.panning = (byte) panning;
        return this;
    }

    /**
     * 100 = 1 key<br>
     * 1200 = 1 octave
     *
     * @return The fine pitch of the note block, from -32,768 to 32,767 cents (but the max in Note Block Studio is limited to -1200 and +1200). 0 is no fine-tuning. ±100 cents is a single semitone difference.
     * @since v4
     */
    public short getPitch() {
        return this.pitch;
    }

    /**
     * 100 = 1 key<br>
     * 1200 = 1 octave
     *
     * @param pitch The fine pitch of the note block, from -32,768 to 32,767 cents (but the max in Note Block Studio is limited to -1200 and +1200). 0 is no fine-tuning. ±100 cents is a single semitone difference.
     * @return this
     * @since v4
     */
    public NbsNote setPitch(final short pitch) {
        this.pitch = pitch;
        return this;
    }

    public NbsNote copy() {
        final NbsNote copyNote = new NbsNote();
        copyNote.setInstrument(this.getInstrument());
        copyNote.setKey(this.getKey());
        copyNote.setVelocity(this.getVelocity());
        copyNote.setPanning(this.getPanning());
        copyNote.setPitch(this.getPitch());
        return copyNote;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        NbsNote nbsNote = (NbsNote) o;
        return instrument == nbsNote.instrument && key == nbsNote.key && velocity == nbsNote.velocity && panning == nbsNote.panning && pitch == nbsNote.pitch;
    }

    @Override
    public int hashCode() {
        return Objects.hash(instrument, key, velocity, panning, pitch);
    }

}
