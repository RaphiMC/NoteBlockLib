/*
 * This file is part of NoteBlockLib - https://github.com/RaphiMC/NoteBlockLib
 * Copyright (C) 2023 RK_01/RaphiMC and contributors
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
package net.raphimc.noteblocklib.format.future.model;

import com.google.common.io.LittleEndianDataInputStream;
import net.raphimc.noteblocklib.model.NotemapData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FutureData extends NotemapData<FutureNote> {

    @SuppressWarnings("UnstableApiUsage")
    public FutureData(final FutureHeader header, final LittleEndianDataInputStream dis) throws IOException {
        int tick = 0;
        while (dis.available() > 0) {
            final byte b = dis.readByte();
            if (b == (header.useMagicValue() ? 5 : 64)) {
                tick += dis.readUnsignedShort();
            } else {
                this.notes.computeIfAbsent(tick, k -> new ArrayList<>()).add(new FutureNote(dis.readByte(), b));
            }
        }
    }

    public FutureData(final Map<Integer, List<FutureNote>> notes) {
        super(notes);
    }

}
