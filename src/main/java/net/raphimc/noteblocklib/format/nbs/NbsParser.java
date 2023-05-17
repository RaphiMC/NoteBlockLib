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
import net.raphimc.noteblocklib.format.nbs.data.NbsV0Data;
import net.raphimc.noteblocklib.format.nbs.header.NbsBaseHeader;
import net.raphimc.noteblocklib.format.nbs.header.NbsV0Header;
import net.raphimc.noteblocklib.format.nbs.header.NbsV4Header;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@SuppressWarnings("UnstableApiUsage")
public class NbsParser {

    public static NbsSong parse(final byte[] bytes, final File sourceFile) throws IOException {
        final LittleEndianDataInputStream dis = new LittleEndianDataInputStream(new ByteArrayInputStream(bytes));
        dis.mark(6);
        final int nbsVersion = new NbsBaseHeader(dis).getNbsVersion();
        dis.reset();

        final NbsV0Header header = nbsVersion >= 4 ? new NbsV4Header(dis) : new NbsV0Header(dis);
        final NbsV0Data data = new NbsV0Data(header, dis);

        return new NbsSong(sourceFile, header, data);
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
