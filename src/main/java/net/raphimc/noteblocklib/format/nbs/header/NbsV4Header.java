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
package net.raphimc.noteblocklib.format.nbs.header;

import com.google.common.io.LittleEndianDataInputStream;

import java.io.IOException;

public class NbsV4Header extends NbsV0Header {

    private boolean loop;
    private byte maxLoopCount;
    private short loopStartTick;

    @SuppressWarnings("UnstableApiUsage")
    public NbsV4Header(final LittleEndianDataInputStream dis) throws IOException {
        super(dis);

        this.loop = dis.readBoolean();
        this.maxLoopCount = dis.readByte();
        this.loopStartTick = dis.readShort();
    }

    public NbsV4Header(final short length, final byte nbsVersion, final byte vanillaInstrumentCount, final short layerCount, final String title, final String author, final String originalAuthor, final String description, final short speed, final boolean autoSave, final byte autoSaveInterval, final byte timeSignature, final int minutesSpent, final int leftClicks, final int rightClicks, final int noteBlocksAdded, final int noteBlocksRemoved, final String sourceFileName, final boolean loop, final byte maxLoopCount, final short loopStartTick) {
        super(length, nbsVersion, vanillaInstrumentCount, layerCount, title, author, originalAuthor, description, speed, autoSave, autoSaveInterval, timeSignature, minutesSpent, leftClicks, rightClicks, noteBlocksAdded, noteBlocksRemoved, sourceFileName);

        this.loop = loop;
        this.maxLoopCount = maxLoopCount;
        this.loopStartTick = loopStartTick;
    }

    /**
     * @return Whether looping is on or off.
     */
    public boolean isLoop() {
        return this.loop;
    }

    public void setLoop(final boolean loop) {
        this.loop = loop;
    }

    /**
     * @return 0 = infinite. Other values mean the amount of times the song loops.
     */
    public byte getMaxLoopCount() {
        return this.maxLoopCount;
    }

    public void setMaxLoopCount(final byte maxLoopCount) {
        this.maxLoopCount = maxLoopCount;
    }

    /**
     * @return Determines which part of the song (in ticks) it loops back to.
     */
    public short getLoopStartTick() {
        return this.loopStartTick;
    }

    public void setLoopStartTick(final short loopStartTick) {
        this.loopStartTick = loopStartTick;
    }

}
