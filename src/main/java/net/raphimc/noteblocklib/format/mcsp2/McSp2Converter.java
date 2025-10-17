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
package net.raphimc.noteblocklib.format.mcsp2;

import net.raphimc.noteblocklib.format.mcsp2.model.McSp2Layer;
import net.raphimc.noteblocklib.format.mcsp2.model.McSp2Note;
import net.raphimc.noteblocklib.format.mcsp2.model.McSp2Song;
import net.raphimc.noteblocklib.format.minecraft.MinecraftInstrument;
import net.raphimc.noteblocklib.format.nbs.model.NbsSong;
import net.raphimc.noteblocklib.model.note.Note;
import net.raphimc.noteblocklib.model.song.Song;
import net.raphimc.noteblocklib.util.MathUtil;
import net.raphimc.noteblocklib.util.SongResampler;

import java.util.List;

public class McSp2Converter {

    /**
     * Creates a new MCSP2 song from the general data of the given song (Also copies some format specific fields if applicable).
     *
     * @param song The song
     * @return The new MCSP2 song
     */
    public static McSp2Song createSong(Song song) {
        song = song.copy();
        SongResampler.changeTickSpeed(song, MathUtil.clamp(Math.round(song.getTempoEvents().get(0)), McSp2Definitions.MIN_TEMPO, McSp2Definitions.MAX_TEMPO));

        final McSp2Song newSong = new McSp2Song();
        newSong.copyGeneralData(song);
        newSong.setTempo((int) song.getTempoEvents().get(0));

        for (int tick : song.getNotes().getTicks()) {
            final List<Note> notes = song.getNotes().get(tick);
            for (int i = 0; i < notes.size(); i++) {
                final Note note = notes.get(i);
                if (note.getInstrument() instanceof MinecraftInstrument && note.getVolume() > 0) {
                    final McSp2Note mcSp2Note = new McSp2Note();
                    mcSp2Note.setInstrument(((MinecraftInstrument) note.getInstrument()).nbsId());
                    mcSp2Note.setKey(note.getMcKey());

                    final McSp2Layer mcSp2Layer = newSong.getLayers().computeIfAbsent(i, k -> new McSp2Layer());
                    mcSp2Layer.getNotes().put(tick, mcSp2Note);
                }
            }
        }

        if (song instanceof McSp2Song) {
            final McSp2Song mcSp2Song = (McSp2Song) song;
            newSong.setAutoSaveInterval(mcSp2Song.getAutoSaveInterval());
            newSong.setMinutesSpent(mcSp2Song.getMinutesSpent());
            newSong.setLeftClicks(mcSp2Song.getLeftClicks());
            newSong.setRightClicks(mcSp2Song.getRightClicks());
            newSong.setNoteBlocksAdded(mcSp2Song.getNoteBlocksAdded());
            newSong.setNoteBlocksRemoved(mcSp2Song.getNoteBlocksRemoved());
        } else if (song instanceof NbsSong) {
            final NbsSong nbsSong = (NbsSong) song;
            newSong.setAutoSaveInterval(nbsSong.isAutoSave() ? nbsSong.getAutoSaveInterval() : 0);
            newSong.setMinutesSpent(nbsSong.getMinutesSpent());
            newSong.setLeftClicks(nbsSong.getLeftClicks());
            newSong.setRightClicks(nbsSong.getRightClicks());
            newSong.setNoteBlocksAdded(nbsSong.getNoteBlocksAdded());
            newSong.setNoteBlocksRemoved(nbsSong.getNoteBlocksRemoved());
        }

        return newSong;
    }

}
