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
package net.raphimc.noteblocklib.util;

import net.raphimc.noteblocklib.format.nbs.NbsDefinitions;
import net.raphimc.noteblocklib.model.Note;

import java.util.HashMap;
import java.util.Map;

public class MinecraftDefinitions {

    public static final int MC_LOWEST_KEY = 33;
    public static final int MC_HIGHEST_KEY = 57;
    public static final int MC_KEYS = NbsDefinitions.KEYS_PER_OCTAVE * 2;

    // Instrument -> [lower shifts, upper shifts]
    private static final Map<Instrument, Instrument[][]> INSTRUMENT_SHIFTS = new HashMap<>();

    static {
        INSTRUMENT_SHIFTS.put(Instrument.HARP, new Instrument[][]{new Instrument[]{Instrument.BASS}, new Instrument[]{Instrument.BELL}});
        INSTRUMENT_SHIFTS.put(Instrument.BASS, new Instrument[][]{new Instrument[0], new Instrument[]{Instrument.HARP, Instrument.BELL}});
        INSTRUMENT_SHIFTS.put(Instrument.BASS_DRUM, new Instrument[][]{new Instrument[0], new Instrument[]{Instrument.SNARE}});
        INSTRUMENT_SHIFTS.put(Instrument.SNARE, new Instrument[][]{new Instrument[]{Instrument.BASS_DRUM}, new Instrument[]{Instrument.HAT}});
        INSTRUMENT_SHIFTS.put(Instrument.HAT, new Instrument[][]{new Instrument[]{Instrument.BASS_DRUM}, new Instrument[]{Instrument.CHIME}});
        INSTRUMENT_SHIFTS.put(Instrument.GUITAR, new Instrument[][]{new Instrument[]{Instrument.BASS}, new Instrument[]{Instrument.COW_BELL, Instrument.XYLOPHONE}});
        INSTRUMENT_SHIFTS.put(Instrument.FLUTE, new Instrument[][]{new Instrument[]{Instrument.DIDGERIDOO}, new Instrument[]{Instrument.BELL, Instrument.CHIME}});
        INSTRUMENT_SHIFTS.put(Instrument.BELL, new Instrument[][]{new Instrument[]{Instrument.HARP}, new Instrument[0]});
        INSTRUMENT_SHIFTS.put(Instrument.CHIME, new Instrument[][]{new Instrument[]{Instrument.BELL}, new Instrument[0]});
        INSTRUMENT_SHIFTS.put(Instrument.XYLOPHONE, new Instrument[][]{new Instrument[]{Instrument.COW_BELL}, new Instrument[]{Instrument.CHIME}});
        INSTRUMENT_SHIFTS.put(Instrument.IRON_XYLOPHONE, new Instrument[][]{new Instrument[]{Instrument.BASS}, new Instrument[]{Instrument.BELL}});
        INSTRUMENT_SHIFTS.put(Instrument.COW_BELL, new Instrument[][]{new Instrument[]{Instrument.GUITAR}, new Instrument[]{Instrument.XYLOPHONE}});
        INSTRUMENT_SHIFTS.put(Instrument.DIDGERIDOO, new Instrument[][]{new Instrument[]{Instrument.BASS}, new Instrument[]{Instrument.FLUTE, Instrument.BELL}});
        INSTRUMENT_SHIFTS.put(Instrument.BIT, new Instrument[][]{new Instrument[]{Instrument.DIDGERIDOO}, new Instrument[]{Instrument.BELL}});
        INSTRUMENT_SHIFTS.put(Instrument.BANJO, new Instrument[][]{new Instrument[]{Instrument.DIDGERIDOO}, new Instrument[]{Instrument.BELL}});
        INSTRUMENT_SHIFTS.put(Instrument.PLING, new Instrument[][]{new Instrument[]{Instrument.BASS}, new Instrument[]{Instrument.BELL}});
    }

    /**
     * @param mcKey The key of the note in the minecraft id system
     * @return The pitch of the note (Between 0 and 2 for input between 0 and 24)
     */
    public static float mcKeyToMcPitch(final int mcKey) {
        return (float) Math.pow(2D, (double) (mcKey - NbsDefinitions.KEYS_PER_OCTAVE) / NbsDefinitions.KEYS_PER_OCTAVE);
    }

    /**
     * @param mcPitch The pitch of the note (Between 0 and 2)
     * @return The key of the note in the minecraft id system (Between 0 and 24 if input between 0 and 2)
     */
    public static int mcPitchToMcKey(final float mcPitch) {
        return (int) Math.round(Math.log(mcPitch) / Math.log(2D) * NbsDefinitions.KEYS_PER_OCTAVE + NbsDefinitions.KEYS_PER_OCTAVE);
    }

    /**
     * Converts a key from the NBS system to the minecraft system
     *
     * @param nbsKey The key of the note in the NBS system
     * @return The key of the note in the minecraft id system
     */
    public static int nbsKeyToMcKey(final int nbsKey) {
        return nbsKey - MC_LOWEST_KEY;
    }

    /**
     * Converts a key from the minecraft system to the NBS system
     *
     * @param mcKey The key of the note in the minecraft id system
     * @return The key of the note in the NBS system
     */
    public static int mcKeyToNbsKey(final int mcKey) {
        return mcKey + MC_LOWEST_KEY;
    }

    /**
     * @param nbsKey The key of the note in the NBS system
     * @return If the note is outside the minecraft octave range
     */
    public static boolean isOutsideOctaveRange(final int nbsKey) {
        return nbsKey < MC_LOWEST_KEY || nbsKey > MC_HIGHEST_KEY;
    }

    /**
     * Clamps the key of the note to fall within minecraft octave range.
     * Any key below 33 will be set to 33, and any key above 57 will be set to 57.
     *
     * @param note The note to clamp
     */
    public static void clampNoteKey(final Note note) {
        note.setKey((byte) Math.max(MC_LOWEST_KEY, Math.min(MC_HIGHEST_KEY, note.getKey())));
    }

    /**
     * Transposes the key of the note to fall within minecraft octave range.
     * Any key below 33 will be transposed up an octave, and any key above 57 will be transposed down an octave.
     *
     * @param note The note to transpose
     */
    public static void transposeNoteKey(final Note note) {
        transposeNoteKey(note, NbsDefinitions.KEYS_PER_OCTAVE);
    }

    /**
     * Transposes the key of the note to fall within minecraft octave range.
     * Any key below 33 will be transposed up by transposeAmount, and any key above 57 will be transposed down by transposeAmount.
     *
     * @param note            The note to transpose
     * @param transposeAmount The amount of keys to transpose by
     */
    public static void transposeNoteKey(final Note note, final int transposeAmount) {
        byte nbsKey = note.getKey();
        while (nbsKey < MC_LOWEST_KEY) {
            nbsKey += transposeAmount;
        }
        while (nbsKey > MC_HIGHEST_KEY) {
            nbsKey -= transposeAmount;
        }
        note.setKey(nbsKey);
    }

    /**
     * "Transposes" the key of the note by shifting the instrument to a higher or lower sounding one.
     * This often sounds the best of the three methods as it keeps the musical key the same and only changes the instrument.
     * <b>The note might still be slightly outside of the minecraft octave range.</b> Use one of the other methods to fix this. Clamp is recommended.
     *
     * @param note The note to transpose
     */
    public static void instrumentShiftNote(final Note note) {
        Instrument instrument = Instrument.fromNbsId(note.getInstrument());
        if (instrument == null) { // Most likely a custom instrument
            return;
        }

        final Instrument[][] shifts = INSTRUMENT_SHIFTS.get(instrument);
        if (shifts == null) { // No shifts defined for this instrument
            return;
        }

        byte nbsKey = note.getKey();
        int downShifts = 0;
        while (nbsKey < MC_LOWEST_KEY && downShifts < shifts[0].length) {
            instrument = shifts[0][downShifts++];
            nbsKey += MinecraftDefinitions.MC_KEYS;
        }

        int upShifts = 0;
        while (nbsKey > MC_HIGHEST_KEY && upShifts < shifts[1].length) {
            instrument = shifts[1][upShifts++];
            nbsKey -= MinecraftDefinitions.MC_KEYS;
        }

        note.setInstrument(instrument.nbsId());
        note.setKey(nbsKey);
    }

}
