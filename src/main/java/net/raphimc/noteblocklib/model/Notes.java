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

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Notes {

    private final Map<Integer, List<Note>> notes = new HashMap<>();

    private int lastTick;
    private boolean recomputeLastTick = true;

    public List<Note> get(final int tick) {
        return this.notes.get(tick);
    }

    public List<Note> getOrEmpty(final int tick) {
        return this.notes.getOrDefault(tick, new ArrayList<>());
    }

    public void set(final int tick, final List<Note> notes) {
        if (notes != null) {
            if (this.notes.put(tick, notes) == null) {
                this.recomputeLastTick = true;
            }
        } else {
            if (this.notes.remove(tick) != null) {
                this.recomputeLastTick = true;
            }
        }
    }

    public void add(final int tick, final Note note) {
        this.notes.computeIfAbsent(tick, k -> {
            this.recomputeLastTick = true;
            return new ArrayList<>();
        }).add(note);
    }

    public void add(final int tick, final List<Note> notes) {
        this.notes.computeIfAbsent(tick, k -> {
            this.recomputeLastTick = true;
            return new ArrayList<>();
        }).addAll(notes);
    }

    public Set<Integer> getTicks() {
        return Collections.unmodifiableSet(this.notes.keySet());
    }

    public void clearTick(final int tick) {
        this.notes.remove(tick);
    }

    public void clear() {
        this.notes.clear();
    }

    /**
     * Applies the given consumer to all notes.<br>
     * Use cases for this method can be for example to transpose all notes in a song to be within the minecraft octave range.
     *
     * @param noteConsumer The consumer
     */
    public void forEach(final Consumer<Note> noteConsumer) {
        this.notes.values().stream().flatMap(Collection::stream).forEach(noteConsumer);
    }

    /**
     * Applies the given predicate to all notes.<br>
     * The predicate can return true to break the iteration.<br>
     * Use cases for this method can be for example to check if any note is outside the minecraft octave range.
     *
     * @param notePredicate The predicate
     * @return True if the predicate returned true for any note
     */
    public boolean testEach(final Predicate<Note> notePredicate) {
        for (List<Note> list : this.notes.values()) {
            for (Note note : list) {
                if (notePredicate.test(note)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Removes all notes which match the given predicate.
     *
     * @param notePredicate The predicate
     */
    public void removeIf(final Predicate<Note> notePredicate) {
        for (List<Note> list : this.notes.values()) {
            list.removeIf(notePredicate);
        }
        this.compact();
    }

    /**
     * Removes duplicate notes which are on the same tick.<br>
     * Useful when handling large MIDI files with a lot of duplicate notes.
     */
    public void removeDoubleNotes() {
        for (List<Note> list : this.notes.values()) {
            final Set<Note> set = new HashSet<>(list);
            list.clear();
            list.addAll(set);
        }
    }

    /**
     * Removes all notes which have a volume of 0.
     */
    public void removeSilentNotes() {
        this.removeSilentNotes(0F);
    }

    /**
     * Removes all notes which have a volume lower than or equal the given threshold.
     *
     * @param threshold The threshold (0.0 - 1.0)
     */
    public void removeSilentNotes(final float threshold) {
        this.removeIf(note -> note.getVolume() <= threshold);
        this.compact();
    }

    /**
     * Removes empty note lists from the notes map.
     */
    public void compact() {
        this.notes.entrySet().removeIf(entry -> entry.getValue().isEmpty());
        this.recomputeLastTick = true;
    }

    /**
     * @return The last tick in the song.
     */
    public int getLastTick() {
        if (this.recomputeLastTick) {
            this.compact();
            this.lastTick = this.notes.keySet().stream().max(Integer::compareTo).orElse(0);
            this.recomputeLastTick = false;
        }
        return this.lastTick;
    }

    /**
     * @return The length of the song in ticks.
     */
    public int getLengthInTicks() {
        return this.getLastTick() + 1;
    }

    /**
     * @return The total amount of notes in a song.
     */
    public long getNoteCount() {
        return this.notes.values().stream().mapToLong(List::size).sum();
    }

    public Notes copy() {
        final Notes copyNotes = new Notes();
        for (Map.Entry<Integer, List<Note>> entry : this.notes.entrySet()) {
            final List<Note> noteList = new ArrayList<>();
            for (Note note : entry.getValue()) {
                noteList.add(note.copy());
            }
            copyNotes.notes.put(entry.getKey(), noteList);
        }
        return copyNotes;
    }

}
