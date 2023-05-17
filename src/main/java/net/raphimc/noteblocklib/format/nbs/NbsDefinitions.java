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
package net.raphimc.noteblocklib.format.nbs;

import net.raphimc.noteblocklib.format.nbs.data.layer.NbsV0Layer;
import net.raphimc.noteblocklib.format.nbs.data.layer.NbsV2Layer;
import net.raphimc.noteblocklib.format.nbs.note.NbsV0Note;
import net.raphimc.noteblocklib.format.nbs.note.NbsV4Note;

public class NbsDefinitions {

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
    public static float getVolume(final NbsV0Note note) {
        float layerVolume = 100;
        float noteVolume = 100;
        if (note.getLayer() instanceof NbsV0Layer) {
            layerVolume = ((NbsV0Layer) note.getLayer()).getVolume();
        }
        if (note instanceof NbsV4Note) {
            noteVolume = ((NbsV4Note) note).getVolume();
        }
        return (layerVolume * noteVolume) / 100F;
    }

    /**
     * Calculates the effective panning of a note. (0 is 2 blocks right, 100 is center, 200 is 2 blocks left)
     *
     * @param note The NBS note
     * @return The effective panning of the note
     */
    public static float getPanning(final NbsV0Note note) {
        short layerPanning = 100;
        short notePanning = 100;
        if (note.getLayer() instanceof NbsV2Layer) {
            layerPanning = ((NbsV2Layer) note.getLayer()).getPanning();
        }
        if (note instanceof NbsV4Note) {
            notePanning = ((NbsV4Note) note).getPanning();
        }
        return (layerPanning + notePanning) / 2F;
    }

    /**
     * Calculates the effective pitch of a note. (100 = 1 key, 1200 = 1 octave)
     *
     * @param note The NBS note
     * @return The effective pitch of the note
     */
    public static int getPitch(final NbsV0Note note) {
        byte key = note.getKey();
        short pitch = 0;
        if (note instanceof NbsV4Note) {
            pitch = ((NbsV4Note) note).getPitch();
        }
        return key * PITCHES_PER_KEY + pitch;
    }

    /**
     * Calculates the effective key of a note.
     *
     * @param note The NBS note
     * @return The effective key of the note
     */
    public static int getKey(final NbsV0Note note) {
        return (int) ((float) getPitch(note) / PITCHES_PER_KEY);
    }

}
