package net.raphimc.noteblocklib.parser.nbs.header;

import com.google.common.io.LittleEndianDataInputStream;

import java.io.IOException;

public class NBSv4Header extends NBSv0Header {

    private boolean loop;
    private byte maxLoopCount;
    private short loopStartTick;

    public NBSv4Header(final short length, final byte nbsVersion, final byte vanillaInstrumentCount, final short layerCount, final String title, final String author, final String originalAuthor, final String description, final short speed, final boolean autoSave, final byte autoSaveInterval, final byte timeSignature, final int minutesSpent, final int leftClicks, final int rightClicks, final int noteBlocksAdded, final int noteBlocksRemoved, final String sourceFileName, final boolean loop, final byte maxLoopCount, final short loopStartTick) {
        super(length, nbsVersion, vanillaInstrumentCount, layerCount, title, author, originalAuthor, description, speed, autoSave, autoSaveInterval, timeSignature, minutesSpent, leftClicks, rightClicks, noteBlocksAdded, noteBlocksRemoved, sourceFileName);

        this.loop = loop;
        this.maxLoopCount = maxLoopCount;
        this.loopStartTick = loopStartTick;
    }

    public NBSv4Header(final LittleEndianDataInputStream dis) throws IOException {
        super(dis);

        this.loop = dis.readBoolean();
        this.maxLoopCount = dis.readByte();
        this.loopStartTick = dis.readShort();
    }

    public void setLoop(final boolean loop) {
        this.loop = loop;
    }

    /**
     * @return Whether looping is on or off.
     */
    public boolean isLoop() {
        return this.loop;
    }

    public void setMaxLoopCount(final byte maxLoopCount) {
        this.maxLoopCount = maxLoopCount;
    }

    /**
     * @return 0 = infinite. Other values mean the amount of times the song loops.
     */
    public byte getMaxLoopCount() {
        return this.maxLoopCount;
    }

    public void setLoopStartTick(final short loopStartTick) {
        this.loopStartTick = loopStartTick;
    }

    /**
     * @return Determines which part of the song (in ticks) it loops back to.
     */
    public short getLoopStartTick() {
        return this.loopStartTick;
    }

}
