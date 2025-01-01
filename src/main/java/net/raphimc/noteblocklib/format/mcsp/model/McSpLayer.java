/*
 * This file is part of NoteBlockLib - https://github.com/RaphiMC/NoteBlockLib
 * Copyright (C) 2022-2025 RK_01/RaphiMC and contributors
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
package net.raphimc.noteblocklib.format.mcsp.model;

import java.util.Map;
import java.util.TreeMap;

public class McSpLayer {

    private Map<Integer, McSpNote> notesAtTick = new TreeMap<>();

    public McSpLayer(final Map<Integer, McSpNote> notesAtTick) {
        this.notesAtTick = notesAtTick;
    }

    public McSpLayer() {
    }

    /**
     * @return A map of all notes in this layer, with the tick as the key.
     */
    public Map<Integer, McSpNote> getNotesAtTick() {
        return this.notesAtTick;
    }

    /**
     * @param notesAtTick A map of all notes in this layer, with the tick as the key.
     */
    public void setNotesAtTick(final Map<Integer, McSpNote> notesAtTick) {
        this.notesAtTick = notesAtTick;
    }

}
