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
package net.raphimc.noteblocklib.util;

import net.raphimc.noteblocklib.format.nbs.NbsSong;
import net.raphimc.noteblocklib.format.nbs.note.NbsV0Note;
import net.raphimc.noteblocklib.format.nbs.note.NbsV4Note;
import net.raphimc.noteblocklib.model.Note;
import net.raphimc.noteblocklib.model.SongView;

import java.util.*;

public class SongResampler {

    /**
     * Changes the tick speed (sample rate) of a song, without changing the musical speed or length.
     * Changing the speed to a lower one than original will result in a loss of timing precision.
     *
     * @param songView The song view
     * @param newSpeed The new tick speed (Ticks per second)
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
     * Applies the undocumented tempo changers from Note Block Studio.
     * Only updates the song view.
     *
     * @param song The song
     */
    public static void applyNbsTempoChangers(final NbsSong song) {
        if (song.getHeader().getNbsVersion() < 4) return;

        int tempoChangerId = -1;
        for (int i = 0; i < song.getData().getCustomInstruments().size(); i++) {
            if (song.getData().getCustomInstruments().get(i).getName().equals("Tempo Changer")) {
                tempoChangerId = i + song.getHeader().getVanillaInstrumentCount();
                break;
            }
        }
        if (tempoChangerId == -1) return;
        final SongView<NbsV0Note> view = song.getView();

        final List<TempoEvent> tempoEvents = new ArrayList<>();
        for (Map.Entry<Integer, List<NbsV0Note>> entry : view.getNotes().entrySet()) {
            for (NbsV0Note note : entry.getValue()) {
                if (note instanceof NbsV4Note && note.getInstrument() == tempoChangerId) {
                    final NbsV4Note v4Note = (NbsV4Note) note;
                    tempoEvents.add(new TempoEvent(entry.getKey(), Math.abs(v4Note.getPitch()) / 15F));
                }
            }
        }
        if (tempoEvents.isEmpty()) return;
        if (tempoEvents.get(0).getTick() != 0) {
            tempoEvents.add(0, new TempoEvent(0, view.getSpeed()));
        }
        tempoEvents.sort(Comparator.comparingInt(TempoEvent::getTick));

        final Map<Integer, List<NbsV0Note>> newNotes = new TreeMap<>();
        final float newSpeed = tempoEvents.stream().map(TempoEvent::getTicksPerSecond).max(Float::compareTo).orElse(view.getSpeed());

        double milliTime = 0;
        int lastTick = 0;
        float millisPerTick = tempoEvents.get(0).getMillisPerTick();
        int tempoEventIdx = 1;
        for (Map.Entry<Integer, List<NbsV0Note>> entry : view.getNotes().entrySet()) {
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
