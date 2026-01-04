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
package net.raphimc.noteblocklib.format.mcsp2.model;

import java.util.HashMap;
import java.util.Map;

public class McSp2Layer {

    private final Map<Integer, McSp2Note> notes = new HashMap<>();

    /**
     * @return A map of all notes in this layer, with the tick as the key.
     */
    public Map<Integer, McSp2Note> getNotes() {
        return this.notes;
    }

    public McSp2Layer copy() {
        final McSp2Layer copyLayer = new McSp2Layer();
        final Map<Integer, McSp2Note> notes = this.getNotes();
        final Map<Integer, McSp2Note> copyNotes = copyLayer.getNotes();
        for (final Map.Entry<Integer, McSp2Note> entry : notes.entrySet()) {
            copyNotes.put(entry.getKey(), entry.getValue().copy());
        }
        return copyLayer;
    }

}
