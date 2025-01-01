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
package net.raphimc.noteblocklib.player;

import net.raphimc.noteblocklib.format.nbs.NbsDefinitions;
import net.raphimc.noteblocklib.format.nbs.model.NbsCustomInstrument;
import net.raphimc.noteblocklib.format.nbs.model.NbsNote;
import net.raphimc.noteblocklib.model.Note;
import net.raphimc.noteblocklib.model.NoteWithPanning;
import net.raphimc.noteblocklib.model.NoteWithVolume;
import net.raphimc.noteblocklib.util.Instrument;
import net.raphimc.noteblocklib.util.MinecraftDefinitions;

public interface FullNoteConsumer extends NoteConsumer {

    @Override
    default void playNote(final Note note) {
        final float volume;
        if (note instanceof NoteWithVolume) {
            final NoteWithVolume noteWithVolume = (NoteWithVolume) note;
            volume = noteWithVolume.getVolume();
        } else {
            volume = 100F;
        }
        if (volume <= 0) return;

        final float panning;
        if (note instanceof NoteWithPanning) {
            final NoteWithPanning noteWithPanning = (NoteWithPanning) note;
            panning = noteWithPanning.getPanning();
        } else {
            panning = 0F;
        }

        final float pitch;
        if (note instanceof NbsNote) {
            NbsNote nbsNote = (NbsNote) note;
            if (nbsNote.getCustomInstrument() != null) {
                nbsNote = nbsNote.clone();
                nbsNote.setKey((byte) (nbsNote.getKey() + nbsNote.getCustomInstrument().getPitch() - 45));
            }
            pitch = MinecraftDefinitions.nbsPitchToMcPitch(NbsDefinitions.getPitch(nbsNote));
        } else {
            pitch = MinecraftDefinitions.mcKeyToMcPitch(MinecraftDefinitions.nbsKeyToMcKey(note.getKey()));
        }

        final float playerVolume = volume / 100F;
        final float playerPanning = panning / 100F;
        if (note.getInstrument() != null) {
            this.playNote(note.getInstrument(), pitch, playerVolume, playerPanning);
        } else if (note instanceof NbsNote && ((NbsNote) note).getCustomInstrument() != null) {
            this.playCustomNote(((NbsNote) note).getCustomInstrument(), pitch, playerVolume, playerPanning);
        }
    }

    /**
     * Plays a note with the given instrument, volume, pitch and panning.
     *
     * @param instrument The instrument of the note
     * @param pitch      The pitch of the note
     * @param volume     The volume of the note (0.0 to 1.0)
     * @param panning    The panning of the note (-1.0 to 1.0)
     */
    void playNote(final Instrument instrument, final float pitch, final float volume, final float panning);

    /**
     * Plays a note with the given custom instrument, volume, pitch and panning. The pitch offset of the custom instrument is already applied to the note.
     *
     * @param customInstrument The custom instrument of the note
     * @param pitch            The pitch of the note
     * @param volume           The volume of the note (0.0 to 1.0)
     * @param panning          The panning of the note (-1.0 to 1.0)
     */
    default void playCustomNote(final NbsCustomInstrument customInstrument, final float pitch, final float volume, final float panning) {
    }

}
