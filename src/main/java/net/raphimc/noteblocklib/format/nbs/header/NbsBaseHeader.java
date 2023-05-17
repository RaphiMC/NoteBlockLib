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
import com.google.common.io.LittleEndianDataOutputStream;
import net.raphimc.noteblocklib.model.Header;

import java.io.IOException;

public class NbsBaseHeader implements Header {

    private short length;
    private byte nbsVersion;
    private byte vanillaInstrumentCount;

    @SuppressWarnings("UnstableApiUsage")
    public NbsBaseHeader(final LittleEndianDataInputStream dis) throws IOException {
        final short length = dis.readShort();
        if (length == 0) {
            this.nbsVersion = dis.readByte();
            this.vanillaInstrumentCount = dis.readByte();
            if (this.nbsVersion >= 3) {
                this.length = dis.readShort();
            } else {
                this.length = -1;
            }
        } else {
            this.length = length;
            this.nbsVersion = 0;
            this.vanillaInstrumentCount = 10;
        }
    }

    NbsBaseHeader(final short length, final byte nbsVersion, final byte vanillaInstrumentCount) {
        this.length = length;
        this.nbsVersion = nbsVersion;
        this.vanillaInstrumentCount = vanillaInstrumentCount;
    }

    @SuppressWarnings("UnstableApiUsage")
    public void write(final LittleEndianDataOutputStream dos) throws IOException {
        if (this.nbsVersion == 0) {
            dos.writeShort(this.length);
        } else {
            dos.writeShort(0);
            dos.writeByte(this.nbsVersion);
            dos.writeByte(this.vanillaInstrumentCount);
            if (this.nbsVersion >= 3) {
                dos.writeShort(this.length);
            }
        }
    }

    /**
     * Can be -1 if the nbsVersion did not support this field
     *
     * @return The length of the song, measured in ticks. Divide this by the tempo to get the length of the song in seconds.
     */
    public short getLength() {
        return this.length;
    }

    public void setLength(final short length) {
        this.length = length;
    }

    /**
     * @return The version of the new NBS format.
     */
    public byte getNbsVersion() {
        return this.nbsVersion;
    }

    public void setNbsVersion(final byte nbsVersion) {
        this.nbsVersion = nbsVersion;
    }

    /**
     * @return Amount of default instruments when the song was saved. This is needed to determine at what index custom instruments start.
     */
    public byte getVanillaInstrumentCount() {
        return this.vanillaInstrumentCount;
    }

    public void setVanillaInstrumentCount(final byte vanillaInstrumentCount) {
        this.vanillaInstrumentCount = vanillaInstrumentCount;
    }

}
