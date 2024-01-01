/*
 * This file is part of NoteBlockLib - https://github.com/RaphiMC/NoteBlockLib
 * Copyright (C) 2022-2024 RK_01/RaphiMC and contributors
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
package net.raphimc.noteblocklib.format.txt;

import net.raphimc.noteblocklib.format.txt.model.TxtData;
import net.raphimc.noteblocklib.format.txt.model.TxtHeader;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class TxtParser {

    public static TxtSong read(final byte[] bytes, final String fileName) {
        final Scanner scanner = new Scanner(new ByteArrayInputStream(bytes));

        final TxtHeader header = new TxtHeader(scanner);
        final TxtData data = new TxtData(scanner);

        return new TxtSong(fileName, header, data);
    }

    public static byte[] write(final TxtSong song) {
        final StringBuilder builder = new StringBuilder();

        song.getHeader().write(builder);
        song.getData().write(builder);

        return builder.toString().getBytes(StandardCharsets.UTF_8);
    }

}
