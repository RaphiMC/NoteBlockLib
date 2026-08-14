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

import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteOrder;

public class BinaryInputStream extends FilterInputStream {

    private final ByteOrder byteOrder;
    private final byte[] primitiveBuffer = new byte[8];

    public BinaryInputStream(final InputStream in) {
        this(in, ByteOrder.BIG_ENDIAN);
    }

    public BinaryInputStream(final InputStream in, final ByteOrder byteOrder) {
        super(in);
        this.byteOrder = byteOrder;
    }

    public boolean readBoolean() throws IOException {
        return this.readUnsignedByte() != 0;
    }

    public byte readByte() throws IOException {
        return (byte) this.readUnsignedByte();
    }

    public int readUnsignedByte() throws IOException {
        final int v = this.read();
        if (v < 0) {
            throw new EOFException();
        }
        return v;
    }

    public short readShort() throws IOException {
        return this.readShort(this.byteOrder);
    }

    public short readShort(final ByteOrder byteOrder) throws IOException {
        return (short) this.readUnsignedShort(byteOrder);
    }

    public int readUnsignedShort() throws IOException {
        return this.readUnsignedShort(this.byteOrder);
    }

    public int readUnsignedShort(final ByteOrder byteOrder) throws IOException {
        this.readBytes(this.primitiveBuffer, 0, 2);
        if (byteOrder == ByteOrder.BIG_ENDIAN) {
            return ((this.primitiveBuffer[0] & 0xFF) << 8) | (this.primitiveBuffer[1] & 0xFF);
        } else if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
            return ((this.primitiveBuffer[1] & 0xFF) << 8) | (this.primitiveBuffer[0] & 0xFF);
        } else {
            throw new IllegalArgumentException("Unsupported byte order: " + byteOrder);
        }
    }

    public int readMedium() throws IOException {
        return this.readMedium(this.byteOrder);
    }

    public int readMedium(final ByteOrder byteOrder) throws IOException {
        return (this.readUnsignedMedium(byteOrder) << 8) >> 8;
    }

    public int readUnsignedMedium() throws IOException {
        return this.readUnsignedMedium(this.byteOrder);
    }

    public int readUnsignedMedium(final ByteOrder byteOrder) throws IOException {
        this.readBytes(this.primitiveBuffer, 0, 3);
        if (byteOrder == ByteOrder.BIG_ENDIAN) {
            return ((this.primitiveBuffer[0] & 0xFF) << 16) | ((this.primitiveBuffer[1] & 0xFF) << 8) | (this.primitiveBuffer[2] & 0xFF);
        } else if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
            return ((this.primitiveBuffer[2] & 0xFF) << 16) | ((this.primitiveBuffer[1] & 0xFF) << 8) | (this.primitiveBuffer[0] & 0xFF);
        } else {
            throw new IllegalArgumentException("Unsupported byte order: " + byteOrder);
        }
    }

    public int readInt() throws IOException {
        return this.readInt(this.byteOrder);
    }

    public int readInt(final ByteOrder byteOrder) throws IOException {
        this.readBytes(this.primitiveBuffer, 0, 4);
        if (byteOrder == ByteOrder.BIG_ENDIAN) {
            return ((this.primitiveBuffer[0] & 0xFF) << 24) | ((this.primitiveBuffer[1] & 0xFF) << 16) | ((this.primitiveBuffer[2] & 0xFF) << 8) | (this.primitiveBuffer[3] & 0xFF);
        } else if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
            return ((this.primitiveBuffer[3] & 0xFF) << 24) | ((this.primitiveBuffer[2] & 0xFF) << 16) | ((this.primitiveBuffer[1] & 0xFF) << 8) | (this.primitiveBuffer[0] & 0xFF);
        } else {
            throw new IllegalArgumentException("Unsupported byte order: " + byteOrder);
        }
    }

    public long readUnsignedInt() throws IOException {
        return this.readUnsignedInt(this.byteOrder);
    }

    public long readUnsignedInt(final ByteOrder byteOrder) throws IOException {
        return Integer.toUnsignedLong(this.readInt(byteOrder));
    }

    public long readLong() throws IOException {
        return this.readLong(this.byteOrder);
    }

    public long readLong(final ByteOrder byteOrder) throws IOException {
        this.readBytes(this.primitiveBuffer, 0, 8);
        if (byteOrder == ByteOrder.BIG_ENDIAN) {
            return ((long) (this.primitiveBuffer[0] & 0xFF) << 56)
                | ((long) (this.primitiveBuffer[1] & 0xFF) << 48)
                | ((long) (this.primitiveBuffer[2] & 0xFF) << 40)
                | ((long) (this.primitiveBuffer[3] & 0xFF) << 32)
                | ((long) (this.primitiveBuffer[4] & 0xFF) << 24)
                | ((long) (this.primitiveBuffer[5] & 0xFF) << 16)
                | ((long) (this.primitiveBuffer[6] & 0xFF) << 8)
                | ((long) (this.primitiveBuffer[7] & 0xFF));
        } else if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
            return ((long) (this.primitiveBuffer[7] & 0xFF) << 56)
                | ((long) (this.primitiveBuffer[6] & 0xFF) << 48)
                | ((long) (this.primitiveBuffer[5] & 0xFF) << 40)
                | ((long) (this.primitiveBuffer[4] & 0xFF) << 32)
                | ((long) (this.primitiveBuffer[3] & 0xFF) << 24)
                | ((long) (this.primitiveBuffer[2] & 0xFF) << 16)
                | ((long) (this.primitiveBuffer[1] & 0xFF) << 8)
                | ((long) (this.primitiveBuffer[0] & 0xFF));
        } else {
            throw new IllegalArgumentException("Unsupported byte order: " + byteOrder);
        }
    }

    public float readFloat() throws IOException {
        return this.readFloat(this.byteOrder);
    }

    public float readFloat(final ByteOrder byteOrder) throws IOException {
        return Float.intBitsToFloat(this.readInt(byteOrder));
    }

    public double readDouble() throws IOException {
        return this.readDouble(this.byteOrder);
    }

    public double readDouble(final ByteOrder byteOrder) throws IOException {
        return Double.longBitsToDouble(this.readLong(byteOrder));
    }

    public byte[] readBytes(final int length) throws IOException {
        final byte[] bytes = new byte[length];
        this.readBytes(bytes);
        return bytes;
    }

    public void readBytes(final byte[] bytes) throws IOException {
        this.readBytes(bytes, 0, bytes.length);
    }

    public void readBytes(final byte[] bytes, final int offset, final int length) throws IOException {
        int read = 0;
        while (read < length) {
            final int count = this.read(bytes, offset + read, length - read);
            if (count < 0) {
                throw new EOFException();
            }
            read += count;
        }
    }

}
