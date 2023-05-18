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
package net.raphimc.noteblocklib.format.nbs;

import com.google.common.io.LittleEndianDataInputStream;
import com.google.common.io.LittleEndianDataOutputStream;
import net.raphimc.noteblocklib.format.nbs.model.NbsData;
import net.raphimc.noteblocklib.format.nbs.model.NbsHeader;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@SuppressWarnings("UnstableApiUsage")
public class NbsParser {

    public static NbsSong read(final byte[] bytes, final String fileName) throws IOException {
        final LittleEndianDataInputStream dis = new LittleEndianDataInputStream(new ByteArrayInputStream(bytes));

        final NbsHeader header = new NbsHeader(dis);
        final NbsData data = new NbsData(header, dis);

        return new NbsSong(fileName, header, data);
    }

    public static byte[] write(final NbsSong song) throws IOException {
        final ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        final LittleEndianDataOutputStream dos = new LittleEndianDataOutputStream(bytes);

        song.getHeader().write(dos);
        song.getData().write(song.getHeader(), dos);

        return bytes.toByteArray();
    }

    public static String readString(final LittleEndianDataInputStream dis) throws IOException {
        int length = dis.readInt();
        final StringBuilder builder = new StringBuilder(length);
        while (length > 0) {
            builder.append((char) dis.readByte());
            length--;
        }
        return builder.toString();
    }

    public static void writeString(final LittleEndianDataOutputStream dos, final String string) throws IOException {
        dos.writeInt(string.length());
        for (final char c : string.toCharArray()) {
            dos.writeByte(c);
        }
    }

}
