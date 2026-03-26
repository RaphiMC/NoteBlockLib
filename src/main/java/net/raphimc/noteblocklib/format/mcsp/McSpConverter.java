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
package net.raphimc.noteblocklib.format.mcsp;

import net.raphimc.noteblocklib.format.mcsp.model.McSpNote;
import net.raphimc.noteblocklib.format.mcsp.model.McSpSong;
import net.raphimc.noteblocklib.format.minecraft.MinecraftInstrument;
import net.raphimc.noteblocklib.model.note.Note;

import java.util.Map;

public class McSpConverter {

    /**
     * Fills the general data of the given song from the MCSP specific data.
     *
     * @param song The song
     */
    public static void fillGeneralData(final McSpSong song) {
        song.getTempoEvents().set(0, McSpDefinitions.TEMPO);
        for (Map.Entry<Integer, McSpNote[]> entry : song.getMcSpNotes().entrySet()) {
            for (McSpNote mcSpNote : entry.getValue()) {
                if (mcSpNote == null) {
                    continue;
                }

                final Note note = new Note();
                note.setInstrument(MinecraftInstrument.fromNbsId(mcSpNote.getInstrument()));
                note.setMcKey(mcSpNote.getKey());
                song.getNotes().add(entry.getKey(), note);
            }
        }
    }

}
