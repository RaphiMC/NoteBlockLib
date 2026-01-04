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
package net.raphimc.noteblocklib.format.nbs;

import net.raphimc.noteblocklib.model.note.Note;
import net.raphimc.noteblocklib.util.MathUtil;

public class NbsDefinitions {

    public static final int LOWEST_MIDI_KEY = 21;
    public static final int HIGHEST_MIDI_KEY = 108;
    public static final int LOWEST_KEY = 0;
    public static final int HIGHEST_KEY = 87;
    public static final int KEY_COUNT = 88;
    public static final int F_SHARP_4_KEY = 45;

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
     * Clamps the key of the note to fall within NBS octave range.<br>
     * Any key below 21 (NBS) will be set to 21 (NBS), and any key above 108 (NBS) will be set to 108 (NBS).
     *
     * @param note The note to clamp
     */
    public static void clampNoteKey(final Note note) {
        note.setMidiKey(MathUtil.clamp(note.getMidiKey(), LOWEST_MIDI_KEY, HIGHEST_MIDI_KEY));
    }

}
