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
package net.raphimc.noteblocklib.util;

import net.raphimc.noteblocklib.format.minecraft.MinecraftDefinitions;
import net.raphimc.noteblocklib.model.note.Note;
import net.raphimc.noteblocklib.model.song.Song;

public enum MinecraftOctaveCorrection {

    NONE {
        @Override
        public void apply(final Note note) {
        }
    },
    INSTRUMENT_SHIFT {
        @Override
        public void apply(final Note note) {
            MinecraftDefinitions.instrumentShiftNote(note);
            MinecraftDefinitions.clampNoteKey(note);
        }
    },
    TRANSPOSE {
        @Override
        public void apply(final Note note) {
            MinecraftDefinitions.transposeNoteKey(note);
        }
    },
    CLAMP {
        @Override
        public void apply(final Note note) {
            MinecraftDefinitions.clampNoteKey(note);
        }
    };

    public abstract void apply(final Note note);

    public void apply(final Song song) {
        song.getNotes().forEach(this::apply);
    }

}
