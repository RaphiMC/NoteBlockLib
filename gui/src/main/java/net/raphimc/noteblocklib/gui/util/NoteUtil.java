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
package net.raphimc.noteblocklib.gui.util;

import net.raphimc.noteblocklib.format.nbs.NbsDefinitions;
import net.raphimc.noteblocklib.format.nbs.model.NbsNote;
import net.raphimc.noteblocklib.model.Note;
import net.raphimc.noteblocklib.util.MinecraftDefinitions;

public class NoteUtil {

    public static Note getCorrectedNote(final Note note, final PitchCorrection pitchCorrection) {
        if (note instanceof NbsNote) {
            final NbsNote nbsNote = (NbsNote) note;
            note.setKey((byte) NbsDefinitions.getKey(nbsNote));
        }

        switch (pitchCorrection) {
            case CLAMP:
                MinecraftDefinitions.clampNoteKey(note);
                break;
            case TRANSPOSE:
                MinecraftDefinitions.transposeNoteKey(note);
                break;
            case INSTRUMENT_SHIFT:
                MinecraftDefinitions.instrumentShiftNote(note);
                MinecraftDefinitions.clampNoteKey(note);
                break;
        }

        return note;
    }

    public enum PitchCorrection {
        CLAMP, TRANSPOSE, INSTRUMENT_SHIFT, NONE
    }

}
