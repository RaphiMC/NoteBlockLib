/*
 * This file is part of NoteBlockLib - https://github.com/RaphiMC/NoteBlockLib
 * Copyright (C) 2022-2024 RK_01/RaphiMC and contributors
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

import net.raphimc.noteblocklib.format.nbs.NbsSong;
import net.raphimc.noteblocklib.format.nbs.model.NbsCustomInstrument;
import net.raphimc.noteblocklib.format.nbs.model.NbsNote;
import net.raphimc.noteblocklib.model.Note;
import net.raphimc.noteblocklib.model.SongView;

import java.util.*;

public class SongResampler {

    /**
     * Changes the tick speed (sample rate) of a song, without changing the musical speed or length.<br>
     * Changing the speed to a lower one than original will result in a loss of timing precision.
     *
     * @param songView The song view
     * @param newSpeed The new tick speed (Ticks per second)
     * @param <N>      The note type
     */
    public static <N extends Note> void changeTickSpeed(final SongView<N> songView, final float newSpeed) {
        final float divider = songView.getSpeed() / newSpeed;
        if (divider == 1F) return;

        final Map<Integer, List<N>> newNotes = new TreeMap<>();
        for (Map.Entry<Integer, List<N>> entry : songView.getNotes().entrySet()) {
            newNotes.computeIfAbsent(Math.round(entry.getKey() / divider), k -> new ArrayList<>()).addAll(entry.getValue());
        }

        songView.setNotes(newNotes);
        songView.setSpeed(newSpeed);
        songView.recalculateLength();
    }

    /**
     * Applies the undocumented tempo changers from Note Block Studio.<br>
     * Only updates the song view.
     *
     * @param song The song
     */
    public static void applyNbsTempoChangers(final NbsSong song) {
        applyNbsTempoChangers(song, song.getView());
    }

    /**
     * Applies the undocumented tempo changers from Note Block Studio.
     *
     * @param song The song
     * @param view The song view to modify
     */
    public static void applyNbsTempoChangers(final NbsSong song, final SongView<NbsNote> view) {
        if (song.getHeader().getVersion() < 4) return;

        boolean hasTempoChanger = false;
        for (NbsCustomInstrument customInstrument : song.getData().getCustomInstruments()) {
            if (customInstrument.getName().equals("Tempo Changer")) {
                song.getData().getCustomInstruments().remove(customInstrument);
                hasTempoChanger = true;
                break;
            }
        }
        if (!hasTempoChanger) return;

        final List<TempoEvent> tempoEvents = new ArrayList<>();
        for (Map.Entry<Integer, List<NbsNote>> entry : view.getNotes().entrySet()) {
            for (NbsNote note : entry.getValue()) {
                if (note.getCustomInstrument() != null && note.getCustomInstrument().getName().equals("Tempo Changer")) {
                    tempoEvents.add(new TempoEvent(entry.getKey(), Math.abs(note.getPitch()) / 15F));
                    entry.getValue().remove(note);
                    break;
                }
            }
        }
        if (tempoEvents.isEmpty()) return;

        if (tempoEvents.get(0).getTick() != 0) {
            tempoEvents.add(0, new TempoEvent(0, view.getSpeed()));
        }
        tempoEvents.sort(Comparator.comparingInt(TempoEvent::getTick));

        final Map<Integer, List<NbsNote>> newNotes = new TreeMap<>();
        final float newSpeed = tempoEvents.stream().map(TempoEvent::getTicksPerSecond).max(Float::compareTo).orElse(view.getSpeed());

        double milliTime = 0;
        int lastTick = 0;
        float millisPerTick = tempoEvents.get(0).getMillisPerTick();
        int tempoEventIdx = 1;
        for (Map.Entry<Integer, List<NbsNote>> entry : view.getNotes().entrySet()) {
            while (tempoEventIdx < tempoEvents.size() && entry.getKey() > tempoEvents.get(tempoEventIdx).getTick()) {
                final TempoEvent tempoEvent = tempoEvents.get(tempoEventIdx++);
                milliTime += (tempoEvent.getTick() - lastTick) * millisPerTick;
                lastTick = tempoEvent.getTick();
                millisPerTick = tempoEvent.getMillisPerTick();
            }
            milliTime += (entry.getKey() - lastTick) * millisPerTick;
            lastTick = entry.getKey();

            newNotes.computeIfAbsent((int) Math.round(milliTime * newSpeed / 1000D), k -> new ArrayList<>()).addAll(entry.getValue());
        }

        view.setNotes(newNotes);
        view.setSpeed(newSpeed);
        view.recalculateLength();
    }

    private static class TempoEvent {
        private final int tick;
        private final float ticksPerSecond;

        public TempoEvent(final int tick, final float ticksPerSecond) {
            this.tick = tick;
            this.ticksPerSecond = ticksPerSecond;
        }

        public int getTick() {
            return this.tick;
        }

        public float getTicksPerSecond() {
            return this.ticksPerSecond;
        }

        public float getMillisPerTick() {
            return 1000F / this.ticksPerSecond;
        }
    }

}
