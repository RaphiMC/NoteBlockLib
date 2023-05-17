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
package net.raphimc.noteblocklib.format.txt;

import net.raphimc.noteblocklib.format.txt.data.TxtData;
import net.raphimc.noteblocklib.format.txt.header.TxtV1Header;
import net.raphimc.noteblocklib.format.txt.header.TxtV2Header;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class TxtParser {

    public static TxtSong parse(final byte[] bytes, final File sourceFile) {
        final Scanner scanner = new Scanner(new ByteArrayInputStream(bytes));

        final TxtV1Header header = scanner.hasNext("#{3}\\d+") ? new TxtV2Header(scanner) : new TxtV1Header();
        final TxtData data = new TxtData(scanner);

        return new TxtSong(sourceFile, header, data);
    }

    public static byte[] write(final TxtSong song) {
        final StringBuilder builder = new StringBuilder();

        song.getHeader().write(builder);
        song.getData().write(builder);

        return builder.toString().getBytes(StandardCharsets.UTF_8);
    }

}
