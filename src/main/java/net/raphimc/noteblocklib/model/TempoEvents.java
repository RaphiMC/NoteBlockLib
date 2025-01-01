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
package net.raphimc.noteblocklib.model;

import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeMap;

public class TempoEvents {

    private static final float DEFAULT_TEMPO = 20F;

    private final TreeMap<Integer, Float> tempoEvents = new TreeMap<>();

    public TempoEvents() {
        this.tempoEvents.put(0, DEFAULT_TEMPO);
    }

    public float getTempo(final int tick) {
        return this.tempoEvents.getOrDefault(tick, 0F);
    }

    public float getEffectiveTempo(final int tick) {
        return this.tempoEvents.floorEntry(tick).getValue();
    }

    public void setTempo(final int tick, final float tempo) {
        this.tempoEvents.put(tick, tempo);
    }

    public SortedSet<Integer> getTicks() {
        return Collections.unmodifiableSortedSet(this.tempoEvents.navigableKeySet());
    }

    public void remove(final int tick) {
        if (tick == 0) {
            throw new IllegalArgumentException("Cannot remove the initial tempo event");
        }

        this.tempoEvents.remove(tick);
    }

    public void clear() {
        this.tempoEvents.clear();
        this.tempoEvents.put(0, DEFAULT_TEMPO);
    }

    /**
     * @return A float[] with 2 elements, the first element is the slowest tempo, the second element is the fastest tempo.
     */
    public float[] getTempoRange() {
        final float minTempo = this.tempoEvents.values().stream().min(Float::compareTo).orElse(0F);
        final float maxTempo = this.tempoEvents.values().stream().max(Float::compareTo).orElse(0F);
        return new float[]{minTempo, maxTempo};
    }

    /**
     * @return A human readable tempo range string.
     */
    public String getHumanReadableTempoRange() {
        final float[] tempoRange = this.getTempoRange();
        return tempoRange[0] == tempoRange[1] ? String.format("%.2f", tempoRange[0]) : String.format("%.2f", tempoRange[0]) + " - " + String.format("%.2f", tempoRange[1]);
    }

    public TempoEvents copy() {
        final TempoEvents copyTempoEvents = new TempoEvents();
        copyTempoEvents.tempoEvents.putAll(this.tempoEvents);
        return copyTempoEvents;
    }

}
