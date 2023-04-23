package net.raphimc.noteblocklib.util;

import static net.raphimc.noteblocklib.util.Constants.*;

public class MinecraftOctave {

    public static float mcKeyToMcPitch(final int mcKey) {
        return (float) Math.pow(2D, (double) (mcKey - KEYS_PER_OCTAVE) / KEYS_PER_OCTAVE);
    }

    public static int mcPitchToMcKey(final float mcPitch) {
        return (int) Math.round(Math.log(mcPitch) / Math.log(2D) * KEYS_PER_OCTAVE + KEYS_PER_OCTAVE);
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

    public static int getAlignedNbsKey(int nbsKey) {
        while (nbsKey < MC_LOWEST_KEY) {
            nbsKey += MC_KEYS;
        }
        while (nbsKey > MC_HIGHEST_KEY) {
            nbsKey -= MC_KEYS;
        }
        return nbsKey;
    }

}
