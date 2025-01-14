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
package net.raphimc.noteblocklib.data;

import net.raphimc.noteblocklib.model.Note;
import net.raphimc.noteblocklib.model.instrument.Instrument;

import java.util.EnumMap;
import java.util.Map;

import static net.raphimc.noteblocklib.data.MinecraftInstrument.*;

public class MinecraftDefinitions {

    public static final int MC_LOWEST_MIDI_KEY = 54;
    public static final int MC_HIGHEST_MIDI_KEY = 78;
    public static final int MC_LOWEST_KEY = 0;
    public static final int MC_HIGHEST_KEY = 24;
    public static final int MC_KEYS = Constants.KEYS_PER_OCTAVE * 2;

    // Instrument -> [lower shifts, upper shifts]
    private static final Map<MinecraftInstrument, MinecraftInstrument[][]> INSTRUMENT_SHIFTS = new EnumMap<>(MinecraftInstrument.class);

    static {
        INSTRUMENT_SHIFTS.put(HARP, new MinecraftInstrument[][]{new MinecraftInstrument[]{BASS}, new MinecraftInstrument[]{BELL}});
        INSTRUMENT_SHIFTS.put(BASS, new MinecraftInstrument[][]{new MinecraftInstrument[0], new MinecraftInstrument[]{HARP, BELL}});
        INSTRUMENT_SHIFTS.put(BASS_DRUM, new MinecraftInstrument[][]{new MinecraftInstrument[0], new MinecraftInstrument[]{SNARE}});
        INSTRUMENT_SHIFTS.put(SNARE, new MinecraftInstrument[][]{new MinecraftInstrument[]{BASS_DRUM}, new MinecraftInstrument[]{HAT}});
        INSTRUMENT_SHIFTS.put(HAT, new MinecraftInstrument[][]{new MinecraftInstrument[]{BASS_DRUM}, new MinecraftInstrument[]{CHIME}});
        INSTRUMENT_SHIFTS.put(GUITAR, new MinecraftInstrument[][]{new MinecraftInstrument[]{BASS}, new MinecraftInstrument[]{COW_BELL, XYLOPHONE}});
        INSTRUMENT_SHIFTS.put(FLUTE, new MinecraftInstrument[][]{new MinecraftInstrument[]{DIDGERIDOO}, new MinecraftInstrument[]{BELL, CHIME}});
        INSTRUMENT_SHIFTS.put(BELL, new MinecraftInstrument[][]{new MinecraftInstrument[]{HARP}, new MinecraftInstrument[0]});
        INSTRUMENT_SHIFTS.put(CHIME, new MinecraftInstrument[][]{new MinecraftInstrument[]{BELL}, new MinecraftInstrument[0]});
        INSTRUMENT_SHIFTS.put(XYLOPHONE, new MinecraftInstrument[][]{new MinecraftInstrument[]{COW_BELL}, new MinecraftInstrument[]{CHIME}});
        INSTRUMENT_SHIFTS.put(IRON_XYLOPHONE, new MinecraftInstrument[][]{new MinecraftInstrument[]{BASS}, new MinecraftInstrument[]{BELL}});
        INSTRUMENT_SHIFTS.put(COW_BELL, new MinecraftInstrument[][]{new MinecraftInstrument[]{GUITAR}, new MinecraftInstrument[]{XYLOPHONE}});
        INSTRUMENT_SHIFTS.put(DIDGERIDOO, new MinecraftInstrument[][]{new MinecraftInstrument[]{BASS}, new MinecraftInstrument[]{FLUTE, BELL}});
        INSTRUMENT_SHIFTS.put(BIT, new MinecraftInstrument[][]{new MinecraftInstrument[]{DIDGERIDOO}, new MinecraftInstrument[]{BELL}});
        INSTRUMENT_SHIFTS.put(BANJO, new MinecraftInstrument[][]{new MinecraftInstrument[]{DIDGERIDOO}, new MinecraftInstrument[]{BELL}});
        INSTRUMENT_SHIFTS.put(PLING, new MinecraftInstrument[][]{new MinecraftInstrument[]{BASS}, new MinecraftInstrument[]{BELL}});
    }

    /**
     * Clamps the key of the note to fall within minecraft octave range.<br>
     * Any key below 33 (NBS) will be set to 33 (NBS), and any key above 57 (NBS) will be set to 57 (NBS).
     *
     * @param note The note to clamp
     */
    public static void clampNoteKey(final Note note) {
        note.setMidiKey(Math.max(MC_LOWEST_MIDI_KEY, Math.min(MC_HIGHEST_MIDI_KEY, note.getMidiKey())));
    }

    /**
     * Transposes the key of the note to fall within minecraft octave range.<br>
     * Any key below 33 (NBS) will be transposed up an octave, and any key above 57 (NBS) will be transposed down an octave.
     *
     * @param note The note to transpose
     */
    public static void transposeNoteKey(final Note note) {
        transposeNoteKey(note, Constants.KEYS_PER_OCTAVE);
    }

    /**
     * Transposes the key of the note to fall within minecraft octave range.<br>
     * Any key below 33 (NBS) will be transposed up by transposeAmount, and any key above 57 (NBS) will be transposed down by transposeAmount.
     *
     * @param note The note to transpose
     * @param transposeAmount The amount of keys to transpose by
     */
    public static void transposeNoteKey(final Note note, final int transposeAmount) {
        float key = note.getMidiKey();
        while (key < MC_LOWEST_MIDI_KEY) {
            key += transposeAmount;
        }
        while (key > MC_HIGHEST_MIDI_KEY) {
            key -= transposeAmount;
        }
        note.setMidiKey(key);
    }

    /**
     * "Transposes" the key of the note by shifting the instrument to a higher or lower sounding one.<br>
     * This often sounds the best of the three methods as it keeps the musical key the same and only changes the instrument.<br>
     * <b>The note might still be slightly outside of the minecraft octave range.</b> Use one of the other methods to fix this. Clamp is recommended.
     *
     * @param note The note to transpose
     */
    public static void instrumentShiftNote(final Note note) {
        Instrument instrument = note.getInstrument();
        if (!(instrument instanceof MinecraftInstrument)) { // Custom instrument
            return;
        }

        final MinecraftInstrument[][] shifts = INSTRUMENT_SHIFTS.get(instrument);
        if (shifts == null) { // No shifts defined for this instrument
            return;
        }

        float key = note.getMidiKey();
        int downShifts = 0;
        while (key < MC_LOWEST_MIDI_KEY && downShifts < shifts[0].length) {
            instrument = shifts[0][downShifts++];
            key += MinecraftDefinitions.MC_KEYS;
        }

        int upShifts = 0;
        while (key > MC_HIGHEST_MIDI_KEY && upShifts < shifts[1].length) {
            instrument = shifts[1][upShifts++];
            key -= MinecraftDefinitions.MC_KEYS;
        }

        note.setInstrument(instrument);
        note.setMidiKey(key);
    }

    /**
     * Returns the octave delta to use as a suffix for the sound name and modifies the note key to be within the Minecraft octave range.<br>
     * The octave delta value has to be appended to the sound name to play the note at the correct pitch. (For example: "block.note_block.harp_" + octaveDelta)<br>
     * Link to the resource pack: <a href="https://github.com/RaphiMC/NoteBlockLib/raw/main/Extended%20Octave%20Range%20Notes%20Pack.zip">Extended Notes</a>
     *
     * @param note The note to modify
     * @return The octave delta to use as a suffix for the sound name
     */
    public static int applyExtendedNotesResourcePack(final Note note) {
        int octavesDelta = 0;
        while (note.getMidiKey() < MC_LOWEST_MIDI_KEY) {
            note.setMidiKey(note.getMidiKey() + MC_KEYS);
            octavesDelta--;
        }
        while (note.getMidiKey() > MC_HIGHEST_MIDI_KEY) {
            note.setMidiKey(note.getMidiKey() - MC_KEYS);
            octavesDelta++;
        }
        return octavesDelta;
    }

}
