package net.raphimc.noteblocklib.format.nbs;

import net.raphimc.noteblocklib.format.nbs.data.layer.NBSv0Layer;
import net.raphimc.noteblocklib.format.nbs.data.layer.NBSv2Layer;
import net.raphimc.noteblocklib.format.nbs.note.NBSNote;
import net.raphimc.noteblocklib.format.nbs.note.NBSv4Note;

public class NBSDefinitions {

    public static final int NBS_LOWEST_KEY = 0;
    public static final int NBS_HIGHEST_KEY = 87;

    public static final int KEYS_PER_OCTAVE = 12;
    public static final int PITCHES_PER_KEY = 100;

    /**
     * Calculates the effective volume of a note. (0% - 100%)
     *
     * @param note The NBS note
     * @return The effective volume of the note
     */
    public static float getVolume(final NBSNote note) {
        byte layerVolume = 100;
        byte noteVolume = 100;
        if (note.getLayer() instanceof NBSv0Layer) {
            layerVolume = ((NBSv0Layer) note.getLayer()).getVolume();
        }
        if (note instanceof NBSv4Note) {
            noteVolume = ((NBSv4Note) note).getVelocity();
        }
        return (layerVolume * noteVolume) / 100F;
    }

    /**
     * Calculates the effective panning of a note. (0 is 2 blocks right, 100 is center, 200 is 2 blocks left)
     *
     * @param note The NBS note
     * @return The effective panning of the note
     */
    public static float getPanning(final NBSNote note) {
        short layerPanning = 100;
        short notePanning = 100;
        if (note.getLayer() instanceof NBSv2Layer) {
            layerPanning = ((NBSv2Layer) note.getLayer()).getPanning();
        }
        if (note instanceof NBSv4Note) {
            notePanning = ((NBSv4Note) note).getPanning();
        }
        return (layerPanning + notePanning) / 2F;
    }

    /**
     * Calculates the effective pitch of a note. (100 = 1 key, 1200 = 1 octave)
     *
     * @param note The NBS note
     * @return The effective pitch of the note
     */
    public static int getPitch(final NBSNote note) {
        byte key = note.getKey();
        short pitch = 0;
        if (note instanceof NBSv4Note) {
            pitch = ((NBSv4Note) note).getPitch();
        }
        return key * PITCHES_PER_KEY + pitch;
    }

    /**
     * Calculates the effective key of a note.
     *
     * @param note The NBS note
     * @return The effective key of the note
     */
    public static int getKey(final NBSNote note) {
        return (int) ((float) getPitch(note) / PITCHES_PER_KEY);
    }

}
