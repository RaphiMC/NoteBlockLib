package net.raphimc.noteblocklib.util;

import net.raphimc.noteblocklib.format.nbs.NBSDefinitions;

public class MinecraftDefinitions {

    public static final int MC_LOWEST_KEY = 33;
    public static final int MC_HIGHEST_KEY = 57;
    public static final int MC_KEYS = NBSDefinitions.KEYS_PER_OCTAVE * 2;

    public static float mcKeyToMcPitch(final int mcKey) {
        return (float) Math.pow(2D, (double) (mcKey - NBSDefinitions.KEYS_PER_OCTAVE) / NBSDefinitions.KEYS_PER_OCTAVE);
    }

    public static int mcPitchToMcKey(final float mcPitch) {
        return (int) Math.round(Math.log(mcPitch) / Math.log(2D) * NBSDefinitions.KEYS_PER_OCTAVE + NBSDefinitions.KEYS_PER_OCTAVE);
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
        return getTransposedNbsKey(nbsKey, NBSDefinitions.KEYS_PER_OCTAVE);
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
