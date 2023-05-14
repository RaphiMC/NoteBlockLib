/*
 * This file is part of NoteBlockLib - https://github.com/RaphiMC/NoteBlockLib
 * Copyright (C) 2023 RK_01/RaphiMC and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
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

public class MinecraftDefinitions {

    public static final int MC_LOWEST_KEY = 33;
    public static final int MC_HIGHEST_KEY = 57;
    public static final int MC_KEYS = NbsDefinitions.KEYS_PER_OCTAVE * 2;

    public static float mcKeyToMcPitch(final int mcKey) {
        return (float) Math.pow(2D, (double) (mcKey - NbsDefinitions.KEYS_PER_OCTAVE) / NbsDefinitions.KEYS_PER_OCTAVE);
    }

    public static int mcPitchToMcKey(final float mcPitch) {
        return (int) Math.round(Math.log(mcPitch) / Math.log(2D) * NbsDefinitions.KEYS_PER_OCTAVE + NbsDefinitions.KEYS_PER_OCTAVE);
    }

    public static int nbsKeyToMcKey(final int nbsKey) {
        return nbsKey - MC_LOWEST_KEY;
    }

    public static int mcKeyToNbsKey(final int mcKey) {
        return mcKey + MC_LOWEST_KEY;
    }

    public static boolean isOutsideOctaveRange(final int nbsKey) {
        return nbsKey < MC_LOWEST_KEY || nbsKey > MC_HIGHEST_KEY;
    }

    public static int getClampedNbsKey(final int nbsKey) {
        return Math.max(MC_LOWEST_KEY, Math.min(MC_HIGHEST_KEY, nbsKey));
    }

    public static int getOctaveTransposedNbsKey(final int nbsKey) {
        return getTransposedNbsKey(nbsKey, NbsDefinitions.KEYS_PER_OCTAVE);
    }

    public static int get2OctaveTransposedNbsKey(final int nbsKey) {
        return getTransposedNbsKey(nbsKey, MC_KEYS);
    }

    public static int getTransposedNbsKey(int nbsKey, final int transposeAmount) {
        while (nbsKey < MC_LOWEST_KEY) {
            nbsKey += transposeAmount;
        }
        while (nbsKey > MC_HIGHEST_KEY) {
            nbsKey -= transposeAmount;
        }
        return nbsKey;
    }

}
