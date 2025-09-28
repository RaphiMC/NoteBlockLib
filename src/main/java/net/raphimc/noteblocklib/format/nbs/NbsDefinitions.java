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

    public static final int NBS_LOWEST_MIDI_KEY = 21;
    public static final int NBS_HIGHEST_MIDI_KEY = 108;
    public static final int NBS_LOWEST_KEY = 0;
    public static final int NBS_HIGHEST_KEY = 87;
    public static final int F_SHARP_4_NBS_KEY = 45;

    public static final int PITCHES_PER_KEY = 100;
    public static final int PITCHES_PER_OCTAVE = 1200;

    public static final int CENTER_PANNING = 100;

    public static final String TOGGLE_RAINBOW_CUSTOM_INSTRUMENT_NAME = "Toggle Rainbow";
    public static final String TEMPO_CHANGER_CUSTOM_INSTRUMENT_NAME = "Tempo Changer";
    public static final String SOUND_STOPPER_CUSTOM_INSTRUMENT_NAME = "Sound Stopper";
    public static final String SHOW_SAVE_POPUP_CUSTOM_INSTRUMENT_NAME = "Show Save Popup";
    public static final String CHANGE_COLOR_CUSTOM_INSTRUMENT_NAME = "Change Color to #";
    public static final String TOGGLE_BACKGROUND_ACCENT_CUSTOM_INSTRUMENT_NAME = "Toggle Background Accent";

    /**
     * Calculates the effective pitch of a note. (100 = 1 key, 1200 = 1 octave)
     *
     * @param note The NBS note
     * @return The effective pitch of the note
     */
    public static int getEffectivePitch(final NbsNote note) {
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
    public static int getEffectiveKey(final NbsNote note) {
        return (int) ((float) getEffectivePitch(note) / PITCHES_PER_KEY);
    }

}
