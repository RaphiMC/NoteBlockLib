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
package net.raphimc.noteblocklib.format.nbs;

import net.raphimc.noteblocklib.format.nbs.model.NbsNote;

public class NbsDefinitions {

    public static final int NBS_LOWEST_KEY = 0;
    public static final int NBS_HIGHEST_KEY = 87;

    public static final int KEYS_PER_OCTAVE = 12;
    public static final int PITCHES_PER_KEY = 100;

    /**
     * Calculates the effective pitch of a note. (100 = 1 key, 1200 = 1 octave)
     *
     * @param note The NBS note
     * @return The effective pitch of the note
     */
    public static int getPitch(final NbsNote note) {
        final byte key = note.getKey();
        final short pitch = note.getPitch();
        return key * PITCHES_PER_KEY + pitch;
    }

    /**
     * Calculates the effective key of a note.
     *
     * @param note The NBS note
     * @return The effective key of the note
     */
    public static int getKey(final NbsNote note) {
        return (int) ((float) getPitch(note) / PITCHES_PER_KEY);
    }

    /**
     * Applies the pitch value to the key and updates the note. This results in the pitch value of the note being set to the smallest possible value (-99 - 99).
     *
     * @param note The NBS note
     */
    public static void applyPitchToKey(final NbsNote note) {
        final int pitch = getPitch(note);
        note.setKey((byte) getKey(note));
        note.setPitch((short) (pitch % PITCHES_PER_KEY));
    }

}
