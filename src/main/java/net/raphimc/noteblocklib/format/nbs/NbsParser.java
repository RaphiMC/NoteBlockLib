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
import net.raphimc.noteblocklib.format.nbs.data.NbsData;
import net.raphimc.noteblocklib.format.nbs.header.NbsHeader;
import net.raphimc.noteblocklib.format.nbs.header.NbsV0Header;
import net.raphimc.noteblocklib.format.nbs.header.NbsV4Header;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

@SuppressWarnings("UnstableApiUsage")
public class NbsParser {

    public static NbsSong parse(final byte[] bytes, final File sourceFile) throws IOException {
        final LittleEndianDataInputStream dis = new LittleEndianDataInputStream(new ByteArrayInputStream(bytes));
        dis.mark(6);
        final int nbsVersion = new NbsHeader(dis).getNbsVersion();
        dis.reset();

        final NbsV0Header header = nbsVersion >= 4 ? new NbsV4Header(dis) : new NbsV0Header(dis);
        final NbsData data = new NbsData(header, dis);

        return new NbsSong(sourceFile, header, data);
    }

    public static String readString(final LittleEndianDataInputStream dis) throws IOException {
        int length = dis.readInt();
        final StringBuilder builder = new StringBuilder(length);
        while (length > 0) {
            char c = (char) dis.readByte();
            if (c == (char) 0x0D) {
                c = ' ';
            }
            builder.append(c);
            length--;
        }
        return builder.toString();
    }

}
