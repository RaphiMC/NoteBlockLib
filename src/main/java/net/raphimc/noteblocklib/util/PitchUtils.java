package net.raphimc.noteblocklib.util;

import net.raphimc.noteblocklib.parser.Note;
import net.raphimc.noteblocklib.parser.nbs.note.NBSv4Note;

public class PitchUtils {

    private static final float[] pitches;

    static {
        pitches = new float[2401];

        for (int i = 0; i < 2401; i++) {
            pitches[i] = (float) Math.pow(2, (i - 1200d) / 1200d);
        }
    }

    public static float getCorrectedPitch(final Note note, final PitchCorrection pitchCorrection) {
        switch (pitchCorrection) {
            case PITCH_CORRECT_ALIGN:
                return PitchUtils.getPitchInOctaveAligned(note);
            case PITCH_CORRECT_CLAMP:
                return keyToPitch(PitchUtils.getKeyInOctaveClamped(note));
            case CLAMP:
                return keyToPitch(PitchUtils.getKeyInOctaveClamped(note.getKey(), (short) 0));
            default:
                return 0F;
        }
    }

    public static byte getCorrectedKey(final Note note, final PitchCorrection pitchCorrection) {
        switch (pitchCorrection) {
            case PITCH_CORRECT_ALIGN:
                return PitchUtils.getKeyInOctaveAligned(note);
            case PITCH_CORRECT_CLAMP:
                return PitchUtils.getKeyInOctaveClamped(note);
            case CLAMP:
                return PitchUtils.getKeyInOctaveClamped(note.getKey(), (short) 0);
            default:
                return 0;
        }
    }

    // --- Octave Clamp ---

    public static byte getKeyInOctaveClamped(final Note note) {
        if (note instanceof NBSv4Note) {
            return getKeyInOctaveClamped(note.getKey(), ((NBSv4Note) note).getPitch());
        } else {
            return getKeyInOctaveClamped(note.getKey(), (short) 0);
        }
    }

    public static byte getKeyInOctaveClamped(byte key, short pitch) {
        return (byte) Math.min(24, Math.max(applyPitchToKey(key, pitch) - 33, 0));
    }

    // --- Octave Align ---

    public static float getPitchInOctaveAligned(final Note note) {
        if (note instanceof NBSv4Note) {
            return getPitchInOctaveAligned(note.getKey(), ((NBSv4Note) note).getPitch());
        } else {
            return getPitchInOctaveAligned(note.getKey(), (short) 0);
        }
    }

    public static float getPitchInOctaveAligned(byte key, short pitch) {
        key = applyPitchToKey(key, pitch);
        pitch %= 100;
        if (pitch < 0) pitch = (short) (100 + pitch);

        while (key < 33) key += 24;
        while (key > 56) key -= 24;
        key -= 33;

        return pitches[key * 100 + pitch];
    }

    public static byte getKeyInOctaveAligned(final Note note) {
        if (note instanceof NBSv4Note) {
            return getKeyInOctaveAligned(note.getKey(), ((NBSv4Note) note).getPitch());
        } else {
            return getKeyInOctaveAligned(note.getKey(), (short) 0);
        }
    }

    public static byte getKeyInOctaveAligned(byte key, short pitch) {
        key = applyPitchToKey(key, pitch);

        while (key < 33) key += 24;
        while (key > 56) key -= 24;
        key -= 33;

        return key;
    }

    // --- Utilities ---

    public static byte applyPitchToKey(byte key, short pitch) {
        if (pitch == 0) return key;
        if (pitch < 0) return (byte) (key - (-pitch / 100) - (Math.abs(pitch) % 100 != 0 ? 1 : 0));
        return (byte) (key + (pitch / 100));
    }

    public static float keyToPitch(byte key) {
        return NotePitch.getPitch(key);
    }

    public static boolean isOutOfMinecraftRange(byte key, short pitch) {
        key = applyPitchToKey(key, pitch);

        if (key < 33) return true;
        else return key >= 57;
    }

}
