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
package net.raphimc.noteblocklib.format.txt;

import net.raphimc.noteblocklib.format.minecraft.MinecraftInstrument;
import net.raphimc.noteblocklib.format.txt.model.TxtNote;
import net.raphimc.noteblocklib.format.txt.model.TxtSong;
import net.raphimc.noteblocklib.model.note.Note;
import net.raphimc.noteblocklib.model.song.Song;
import net.raphimc.noteblocklib.util.SongResampler;

import java.util.ArrayList;

public class TxtConverter {

    /**
     * Creates a new TXT song from the general data of the given song (Also copies some format specific fields if applicable).
     *
     * @param song The song
     * @return The new TXT song
     */
    public static TxtSong createSong(Song song) {
        song = song.copy();
        SongResampler.changeTickSpeed(song, TxtDefinitions.TEMPO);

        final TxtSong newSong = new TxtSong();
        newSong.copyGeneralData(song);

        for (int tick : song.getNotes().getTicks()) {
            for (Note note : song.getNotes().get(tick)) {
                if (note.getInstrument() instanceof MinecraftInstrument && note.getVolume() > 0) {
                    final TxtNote txtNote = new TxtNote();
                    txtNote.setInstrument(((MinecraftInstrument) note.getInstrument()).mcId());
                    txtNote.setKey(note.getMcKey());
                    newSong.getTxtNotes().computeIfAbsent(tick, k -> new ArrayList<>()).add(txtNote);
                }
            }
        }

        return newSong;
    }

}
