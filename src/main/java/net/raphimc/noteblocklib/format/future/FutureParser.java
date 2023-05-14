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
package net.raphimc.noteblocklib.format.future;

import com.google.common.io.LittleEndianDataInputStream;
import net.raphimc.noteblocklib.format.future.data.FutureData;
import net.raphimc.noteblocklib.format.future.header.FutureHeader;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

@SuppressWarnings("UnstableApiUsage")
public class FutureParser {

    public static FutureSong parse(final byte[] bytes, final File sourceFile) throws IOException {
        final LittleEndianDataInputStream dis = new LittleEndianDataInputStream(new ByteArrayInputStream(bytes));

        final FutureHeader header = new FutureHeader(dis);
        final FutureData data = new FutureData(header, dis);

        return new FutureSong(sourceFile, header, data);
    }

}
