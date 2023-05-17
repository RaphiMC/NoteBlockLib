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
package net.raphimc.noteblocklib.format.nbs.note;

import com.google.common.io.LittleEndianDataInputStream;
import com.google.common.io.LittleEndianDataOutputStream;
import net.raphimc.noteblocklib.model.NoteWithVolume;

import java.io.IOException;

public class NbsV4Note extends NbsV0Note implements NoteWithVolume {

    private byte velocity;
    private short panning;
    private short pitch;

    @SuppressWarnings("UnstableApiUsage")
    public NbsV4Note(final LittleEndianDataInputStream dis) throws IOException {
        super(dis);

        this.velocity = dis.readByte();
        this.panning = (short) dis.readUnsignedByte();
        this.pitch = dis.readShort();
    }

    public NbsV4Note(final byte instrument, final byte key, final byte velocity, final short panning, final short pitch) {
        super(instrument, key);

        this.velocity = velocity;
        this.panning = panning;
        this.pitch = pitch;
    }

    @Override
    @SuppressWarnings("UnstableApiUsage")
    public void write(final LittleEndianDataOutputStream dos) throws IOException {
        super.write(dos);

        dos.writeByte(this.velocity);
        dos.writeByte(this.panning);
        dos.writeShort(this.pitch);
    }

    /**
     * @inheritDoc
     */
    @Override
    public float getVolume() {
        return this.velocity;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setVolume(final float volume) {
        this.velocity = (byte) volume;
    }

    /**
     * @return The stereo position of the note block, from 0-200. 100 is center panning.
     */
    public short getPanning() {
        return this.panning;
    }

    public void setPanning(final short panning) {
        this.panning = panning;
    }

    /**
     * 100 = 1 key
     * 1200 = 1 octave
     *
     * @return The fine pitch of the note block, from -32,768 to 32,767 cents (but the max in Note Block Studio is limited to -1200 and +1200). 0 is no fine-tuning. ±100 cents is a single semitone difference.
     */
    public short getPitch() {
        return this.pitch;
    }

    public void setPitch(final short pitch) {
        this.pitch = pitch;
    }

}
