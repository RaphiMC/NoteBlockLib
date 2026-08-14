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
package net.raphimc.noteblocklib.util.io;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteOrder;

public class BinaryOutputStream extends FilterOutputStream {

    private static final int MEDIUM_MIN_VALUE = -8388608;
    private static final int MEDIUM_MAX_VALUE = 8388607;
    private static final int UNSIGNED_BYTE_MAX_VALUE = 0xFF;
    private static final int UNSIGNED_SHORT_MAX_VALUE = 0xFFFF;
    private static final int UNSIGNED_MEDIUM_MAX_VALUE = 0xFFFFFF;
    private static final long UNSIGNED_INT_MAX_VALUE = 0xFFFFFFFFL;

    private final ByteOrder byteOrder;
    private final byte[] primitiveBuffer = new byte[8];

    public BinaryOutputStream(final OutputStream out) {
        this(out, ByteOrder.BIG_ENDIAN);
    }

    public BinaryOutputStream(final OutputStream out, final ByteOrder byteOrder) {
        super(out);
        this.byteOrder = byteOrder;
    }

    public void writeBoolean(final boolean v) throws IOException {
        this.writeUnsignedByte(v ? 1 : 0);
    }

    public void writeByte(final int v) throws IOException {
        if (v < Byte.MIN_VALUE || v > Byte.MAX_VALUE) {
            throw new IllegalArgumentException("Value out of range for byte: " + v);
        }
        this.writeByte((byte) v);
    }

    public void writeByte(final byte v) throws IOException {
        this.write(v);
    }

    public void writeUnsignedByte(final int v) throws IOException {
        if (v < 0 || v > UNSIGNED_BYTE_MAX_VALUE) {
            throw new IllegalArgumentException("Value out of range for unsigned byte: " + v);
        }
        this.writeByte((byte) v);
    }

    public void writeShort(final int v) throws IOException {
        if (v < Short.MIN_VALUE || v > Short.MAX_VALUE) {
            throw new IllegalArgumentException("Value out of range for short: " + v);
        }
        this.writeShort((short) v);
    }

    public void writeShort(final short v) throws IOException {
        this.writeShort(v, this.byteOrder);
    }

    public void writeShort(final short v, final ByteOrder byteOrder) throws IOException {
        if (byteOrder == ByteOrder.BIG_ENDIAN) {
            this.primitiveBuffer[0] = (byte) (v >>> 8);
            this.primitiveBuffer[1] = (byte) v;
        } else if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
            this.primitiveBuffer[1] = (byte) (v >>> 8);
            this.primitiveBuffer[0] = (byte) v;
        } else {
            throw new IllegalArgumentException("Unsupported byte order: " + byteOrder);
        }
        this.write(this.primitiveBuffer, 0, 2);
    }

    public void writeUnsignedShort(final int v) throws IOException {
        this.writeUnsignedShort(v, this.byteOrder);
    }

    public void writeUnsignedShort(final int v, final ByteOrder byteOrder) throws IOException {
        if (v < 0 || v > UNSIGNED_SHORT_MAX_VALUE) {
            throw new IllegalArgumentException("Value out of range for unsigned short: " + v);
        }
        this.writeShort((short) v, byteOrder);
    }

    public void writeMedium(final int v) throws IOException {
        this.writeMedium(v, this.byteOrder);
    }

    public void writeMedium(final int v, final ByteOrder byteOrder) throws IOException {
        if (v < MEDIUM_MIN_VALUE || v > MEDIUM_MAX_VALUE) {
            throw new IllegalArgumentException("Value out of range for medium: " + v);
        }
        if (byteOrder == ByteOrder.BIG_ENDIAN) {
            this.primitiveBuffer[0] = (byte) (v >>> 16);
            this.primitiveBuffer[1] = (byte) (v >>> 8);
            this.primitiveBuffer[2] = (byte) v;
        } else if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
            this.primitiveBuffer[2] = (byte) (v >>> 16);
            this.primitiveBuffer[1] = (byte) (v >>> 8);
            this.primitiveBuffer[0] = (byte) v;
        } else {
            throw new IllegalArgumentException("Unsupported byte order: " + byteOrder);
        }
        this.write(this.primitiveBuffer, 0, 3);
    }

    public void writeUnsignedMedium(final int v) throws IOException {
        this.writeUnsignedMedium(v, this.byteOrder);
    }

    public void writeUnsignedMedium(final int v, final ByteOrder byteOrder) throws IOException {
        if (v < 0 || v > UNSIGNED_MEDIUM_MAX_VALUE) {
            throw new IllegalArgumentException("Value out of range for unsigned medium: " + v);
        }
        if (byteOrder == ByteOrder.BIG_ENDIAN) {
            this.primitiveBuffer[0] = (byte) (v >>> 16);
            this.primitiveBuffer[1] = (byte) (v >>> 8);
            this.primitiveBuffer[2] = (byte) v;
        } else if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
            this.primitiveBuffer[2] = (byte) (v >>> 16);
            this.primitiveBuffer[1] = (byte) (v >>> 8);
            this.primitiveBuffer[0] = (byte) v;
        } else {
            throw new IllegalArgumentException("Unsupported byte order: " + byteOrder);
        }
        this.write(this.primitiveBuffer, 0, 3);
    }

    public void writeInt(final int v) throws IOException {
        this.writeInt(v, this.byteOrder);
    }

    public void writeInt(final int v, final ByteOrder byteOrder) throws IOException {
        if (byteOrder == ByteOrder.BIG_ENDIAN) {
            this.primitiveBuffer[0] = (byte) (v >>> 24);
            this.primitiveBuffer[1] = (byte) (v >>> 16);
            this.primitiveBuffer[2] = (byte) (v >>> 8);
            this.primitiveBuffer[3] = (byte) v;
        } else if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
            this.primitiveBuffer[3] = (byte) (v >>> 24);
            this.primitiveBuffer[2] = (byte) (v >>> 16);
            this.primitiveBuffer[1] = (byte) (v >>> 8);
            this.primitiveBuffer[0] = (byte) v;
        } else {
            throw new IllegalArgumentException("Unsupported byte order: " + byteOrder);
        }
        this.write(this.primitiveBuffer, 0, 4);
    }

    public void writeUnsignedInt(final long v) throws IOException {
        this.writeUnsignedInt(v, this.byteOrder);
    }

    public void writeUnsignedInt(final long v, final ByteOrder byteOrder) throws IOException {
        if (v < 0 || v > UNSIGNED_INT_MAX_VALUE) {
            throw new IllegalArgumentException("Value out of range for unsigned int: " + v);
        }
        this.writeInt((int) v, byteOrder);
    }

    public void writeLong(final long v) throws IOException {
        this.writeLong(v, this.byteOrder);
    }

    public void writeLong(final long v, final ByteOrder byteOrder) throws IOException {
        if (byteOrder == ByteOrder.BIG_ENDIAN) {
            this.primitiveBuffer[0] = (byte) (v >>> 56);
            this.primitiveBuffer[1] = (byte) (v >>> 48);
            this.primitiveBuffer[2] = (byte) (v >>> 40);
            this.primitiveBuffer[3] = (byte) (v >>> 32);
            this.primitiveBuffer[4] = (byte) (v >>> 24);
            this.primitiveBuffer[5] = (byte) (v >>> 16);
            this.primitiveBuffer[6] = (byte) (v >>> 8);
            this.primitiveBuffer[7] = (byte) v;
        } else if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
            this.primitiveBuffer[7] = (byte) (v >>> 56);
            this.primitiveBuffer[6] = (byte) (v >>> 48);
            this.primitiveBuffer[5] = (byte) (v >>> 40);
            this.primitiveBuffer[4] = (byte) (v >>> 32);
            this.primitiveBuffer[3] = (byte) (v >>> 24);
            this.primitiveBuffer[2] = (byte) (v >>> 16);
            this.primitiveBuffer[1] = (byte) (v >>> 8);
            this.primitiveBuffer[0] = (byte) v;
        } else {
            throw new IllegalArgumentException("Unsupported byte order: " + byteOrder);
        }
        this.write(this.primitiveBuffer, 0, 8);
    }

    public void writeFloat(final float v) throws IOException {
        this.writeFloat(v, this.byteOrder);
    }

    public void writeFloat(final float v, final ByteOrder byteOrder) throws IOException {
        this.writeInt(Float.floatToRawIntBits(v), byteOrder);
    }

    public void writeDouble(final double v) throws IOException {
        this.writeDouble(v, this.byteOrder);
    }

    public void writeDouble(final double v, final ByteOrder byteOrder) throws IOException {
        this.writeLong(Double.doubleToRawLongBits(v), byteOrder);
    }

}
