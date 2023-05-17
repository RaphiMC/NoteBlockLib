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
package net.raphimc.noteblocklib.format.nbs.data;

import com.google.common.io.LittleEndianDataInputStream;
import com.google.common.io.LittleEndianDataOutputStream;

import java.io.IOException;

import static net.raphimc.noteblocklib.format.nbs.NbsParser.readString;
import static net.raphimc.noteblocklib.format.nbs.NbsParser.writeString;

public class NbsV0CustomInstrument {

    private String name;
    private String soundFileName;
    private byte pitch;
    private boolean pressKey;

    @SuppressWarnings("UnstableApiUsage")
    public NbsV0CustomInstrument(final LittleEndianDataInputStream dis) throws IOException {
        this.name = readString(dis);
        this.soundFileName = readString(dis);
        this.pitch = dis.readByte();
        this.pressKey = dis.readBoolean();
    }

    public NbsV0CustomInstrument(final String name, final String soundFileName, final byte pitch, final boolean pressKey) {
        this.name = name;
        this.soundFileName = soundFileName;
        this.pitch = pitch;
        this.pressKey = pressKey;
    }

    @SuppressWarnings("UnstableApiUsage")
    public void write(final LittleEndianDataOutputStream dos) throws IOException {
        writeString(dos, this.name);
        writeString(dos, this.soundFileName);
        dos.writeByte(this.pitch);
        dos.writeBoolean(this.pressKey);
    }

    /**
     * @return The name of the instrument.
     */
    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @return The sound file of the instrument (just the file name, not the path).
     */
    public String getSoundFileName() {
        return this.soundFileName;
    }

    public void setSoundFileName(final String soundFileName) {
        this.soundFileName = soundFileName;
    }

    /**
     * @return The pitch of the sound file. Just like the note blocks, this ranges from 0-87. Default is 45 (F#4).
     */
    public byte getPitch() {
        return this.pitch;
    }

    public void setPitch(final byte pitch) {
        this.pitch = pitch;
    }

    /**
     * @return Whether the piano should automatically press keys with this instrument when the marker passes them.
     */
    public boolean isPressKey() {
        return this.pressKey;
    }

    public void setPressKey(final boolean pressKey) {
        this.pressKey = pressKey;
    }

}
