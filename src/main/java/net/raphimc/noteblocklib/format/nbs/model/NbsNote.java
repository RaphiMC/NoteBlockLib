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
package net.raphimc.noteblocklib.format.nbs.model;

import com.google.common.io.LittleEndianDataInputStream;
import com.google.common.io.LittleEndianDataOutputStream;
import net.raphimc.noteblocklib.model.Note;
import net.raphimc.noteblocklib.model.NoteWithVolume;

import java.io.IOException;

public class NbsNote extends Note implements NoteWithVolume {

    /**
     * @since v0
     */
    private NbsLayer layer;

    /**
     * @since v4
     */
    private byte velocity = 100;

    /**
     * @since v4
     */
    private short panning = 100;

    /**
     * @since v4
     */
    private short pitch = 0;

    @SuppressWarnings("UnstableApiUsage")
    public NbsNote(final NbsHeader header, final NbsLayer layer, final LittleEndianDataInputStream dis) throws IOException {
        super(dis.readByte(), dis.readByte());

        if (header.getNbsVersion() >= 4) {
            this.velocity = dis.readByte();
            this.panning = (short) dis.readUnsignedByte();
            this.pitch = dis.readShort();
        }

        this.layer = layer;
    }

    public NbsNote(final NbsLayer layer, final byte instrument, final byte key, final byte velocity, final short panning, final short pitch) {
        super(instrument, key);

        this.layer = layer;
        this.velocity = velocity;
        this.panning = panning;
        this.pitch = pitch;
    }

    public NbsNote(final NbsLayer layer, final byte instrument, final byte key) {
        super(instrument, key);

        this.layer = layer;
    }

    public NbsNote(final byte instrument, final byte key) {
        super(instrument, key);
    }

    @SuppressWarnings("UnstableApiUsage")
    public void write(final NbsHeader header, final LittleEndianDataOutputStream dos) throws IOException {
        dos.writeByte(this.instrument);
        dos.writeByte(this.key);

        if (header.getNbsVersion() >= 4) {
            dos.writeByte(this.velocity);
            dos.writeByte(this.panning);
            dos.writeShort(this.pitch);
        }
    }

    /**
     * @return The NBS layer this note is in.
     * @since v0
     */
    public NbsLayer getLayer() {
        return this.layer;
    }

    /**
     * @param layer The NBS layer this note is in.
     * @since v0
     */
    public void setLayer(final NbsLayer layer) {
        this.layer = layer;
    }

    /**
     * @return The velocity/volume of the note, from 0% to 100%.
     * @since v4
     */
    @Override
    public float getVolume() {
        return this.velocity;
    }

    /**
     * @param volume The velocity/volume of the note, from 0% to 100%.
     * @since v4
     */
    @Override
    public void setVolume(final float volume) {
        this.velocity = (byte) volume;
    }

    /**
     * @return The stereo position of the note block, from 0-200. 100 is center panning.
     * @since v4
     */
    public short getPanning() {
        return this.panning;
    }

    /**
     * @param panning The stereo position of the note block, from 0-200. 100 is center panning.
     * @since v4
     */
    public void setPanning(final short panning) {
        this.panning = panning;
    }

    /**
     * 100 = 1 key
     * 1200 = 1 octave
     *
     * @return The fine pitch of the note block, from -32,768 to 32,767 cents (but the max in Note Block Studio is limited to -1200 and +1200). 0 is no fine-tuning. ±100 cents is a single semitone difference.
     * @since v4
     */
    public short getPitch() {
        return this.pitch;
    }

    /**
     * @param pitch The fine pitch of the note block, from -32,768 to 32,767 cents (but the max in Note Block Studio is limited to -1200 and +1200). 0 is no fine-tuning. ±100 cents is a single semitone difference.
     * @since v4
     */
    public void setPitch(final short pitch) {
        this.pitch = pitch;
    }

    @Override
    public Note clone() {
        return new NbsNote(this.layer, this.instrument, this.key, this.velocity, this.panning, this.pitch);
    }

}