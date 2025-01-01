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
package net.raphimc.noteblocklib.util;

import net.raphimc.noteblocklib.data.MinecraftInstrument;
import net.raphimc.noteblocklib.format.nbs.model.NbsCustomInstrument;
import net.raphimc.noteblocklib.model.Note;
import net.raphimc.noteblocklib.model.Song;

import java.text.DecimalFormat;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class SongUtil {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");

    /**
     * @param song The song
     * @return True if the song contains notes which are outside the vanilla Minecraft octave range.
     */
    public static boolean hasOutsideMinecraftOctaveRangeNotes(final Song song) {
        return song.getNotes().testEach(Note::isOutsideMinecraftOctaveRange);
    }

    /**
     * Returns a list of all used vanilla instruments in a song.
     *
     * @param song The song
     * @return The used instruments
     */
    public static Set<MinecraftInstrument> getUsedVanillaInstruments(final Song song) {
        final Set<MinecraftInstrument> usedInstruments = EnumSet.noneOf(MinecraftInstrument.class);
        song.getNotes().forEach(note -> {
            if (note.getInstrument() instanceof MinecraftInstrument) {
                usedInstruments.add((MinecraftInstrument) note.getInstrument());
            }
        });
        return usedInstruments;
    }

    /**
     * Returns a list of all used NBS custom instruments in a song.
     *
     * @param song The song
     * @return The used custom instruments
     */
    public static Set<NbsCustomInstrument> getUsedNbsCustomInstruments(final Song song) {
        final Set<NbsCustomInstrument> usedInstruments = new HashSet<>();
        song.getNotes().forEach(note -> {
            if (note.getInstrument() instanceof NbsCustomInstrument) {
                usedInstruments.add((NbsCustomInstrument) note.getInstrument());
            }
        });
        return usedInstruments;
    }

}
