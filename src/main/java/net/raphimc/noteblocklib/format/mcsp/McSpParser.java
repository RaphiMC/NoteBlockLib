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
package net.raphimc.noteblocklib.format.mcsp;

import net.raphimc.noteblocklib.format.mcsp.model.McSpData;
import net.raphimc.noteblocklib.format.mcsp.model.McSpHeader;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

public class McSpParser {

    public static McSpSong read(final byte[] bytes, final String fileName) {
        final McSpHeader header = new McSpHeader(new Scanner(new ByteArrayInputStream(bytes)));
        final McSpData data = new McSpData(new Scanner(new ByteArrayInputStream(bytes)));

        return new McSpSong(fileName, header, data);
    }

}
