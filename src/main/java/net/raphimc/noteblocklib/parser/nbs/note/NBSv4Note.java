package net.raphimc.noteblocklib.parser.nbs.note;

import com.google.common.io.LittleEndianDataInputStream;

import java.io.IOException;

public class NBSv4Note extends NBSv0Note {

    private final byte velocity;
    private final short panning;
    private final short pitch;

    public NBSv4Note(final LittleEndianDataInputStream dis) throws IOException {
        super(dis);
        this.velocity = dis.readByte();
        this.panning = (short) dis.readUnsignedByte();
        this.pitch = dis.readShort();
    }

    public NBSv4Note(final byte instrument, final byte key, final byte velocity, final short panning, final short pitch) {
        super(instrument, key);
        this.velocity = velocity;
        this.panning = panning;
        this.pitch = pitch;
    }

    /**
     * @return The velocity/volume of the note block, from 0% to 100%.
     */
    public byte getVelocity() {
        return this.velocity;
    }

    /**
     * @return The stereo position of the note block, from 0-200. 100 is center panning.
     */
    public short getPanning() {
        return this.panning;
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

}
