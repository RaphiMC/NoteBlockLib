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

import net.raphimc.noteblocklib.model.Note;
import net.raphimc.noteblocklib.model.Song;

import java.util.*;

public class SongResampler {

    /**
     * Changes the tick speed (sample rate) of a song, without changing the musical speed or length.<br>
     * Changing the speed to a lower one than original will result in a loss of timing precision.
     *
     * @param song The song
     * @param newTempo The new tick speed (Ticks per second)
     */
    public static void changeTickSpeed(final Song song, final float newTempo) {
        precomputeTempoEvents(song); // Ensure song has static tempo

        final float divider = song.getTempoEvents().get(0) / newTempo;
        if (divider == 1F) return;

        final Map<Integer, List<Note>> newNotes = new HashMap<>();
        for (int tick : song.getNotes().getTicks()) {
            newNotes.computeIfAbsent(Math.round(tick / divider), k -> new ArrayList<>()).addAll(song.getNotes().get(tick));
        }

        song.getNotes().clear();
        for (Map.Entry<Integer, List<Note>> entry : newNotes.entrySet()) {
            song.getNotes().set(entry.getKey(), entry.getValue());
        }
        song.getTempoEvents().set(0, newTempo);
    }

    /**
     * Converts a song with dynamic tempo changes into one with a static tempo. This allows the song to be played in players which don't support dynamic tempo changes.
     * @param song The song
     */
    public static void precomputeTempoEvents(final Song song) {
        if (song.getTempoEvents().getTicks().size() <= 1) {
            return; // Already static tempo
        }

        final float newTempo = song.getTempoEvents().getTempoRange()[1]; // Highest tempo in song
        final Map<Integer, List<Note>> newNotes = new HashMap<>();
        final Set<Integer> ticks = new TreeSet<>(song.getNotes().getTicks());
        ticks.addAll(song.getTempoEvents().getTicks());

        int lastTick = 0;
        float totalMilliseconds = 0;
        for (int tick : ticks) {
            final float tps = song.getTempoEvents().getEffectiveTempo(lastTick);
            final int ticksInSegment = tick - lastTick;
            final float segmentMilliseconds = (ticksInSegment / tps) * 1000F;
            totalMilliseconds += segmentMilliseconds;
            lastTick = tick;

            final List<Note> notes = song.getNotes().get(tick);
            if (notes != null) {
                final int newTick = Math.round(newTempo * totalMilliseconds / 1000F);
                newNotes.computeIfAbsent(newTick, k -> new ArrayList<>()).addAll(notes);
            }
        }

        song.getNotes().clear();
        for (Map.Entry<Integer, List<Note>> entry : newNotes.entrySet()) {
            song.getNotes().set(entry.getKey(), entry.getValue());
        }
        song.getTempoEvents().clear();
        song.getTempoEvents().set(0, newTempo);
    }

}
