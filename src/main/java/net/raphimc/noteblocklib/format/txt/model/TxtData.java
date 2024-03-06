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
package net.raphimc.noteblocklib.format.txt.model;

import net.raphimc.noteblocklib.model.NotemapData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TxtData extends NotemapData<TxtNote> {

    public TxtData(final Scanner scanner) {
        scanner.useDelimiter("[:\\n]+");
        while (scanner.hasNext("\\d+")) {
            this.notes.computeIfAbsent(scanner.nextInt(), k -> new ArrayList<>()).add(new TxtNote(scanner));
        }
    }

    public void write(final StringBuilder builder) {
        for (final Map.Entry<Integer, List<TxtNote>> entry : this.notes.entrySet()) {
            for (final TxtNote note : entry.getValue()) {
                builder.append(entry.getKey()).append(":");
                note.write(builder);
                builder.append("\n");
            }
        }
    }

    public TxtData(final Map<Integer, List<TxtNote>> notes) {
        super(notes);
    }

}
