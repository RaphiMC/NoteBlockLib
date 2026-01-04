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
package net.raphimc.noteblocklib.model.event;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Events {

    private final Map<Integer, List<Event>> events = new HashMap<>();

    public List<Event> get(final int tick) {
        return this.events.get(tick);
    }

    public List<Event> getOrEmpty(final int tick) {
        return this.events.getOrDefault(tick, new ArrayList<>());
    }

    public void set(final int tick, final List<Event> events) {
        if (events != null) {
            this.events.put(tick, events);
        } else {
            this.events.remove(tick);
        }
    }

    public void add(final int tick, final Event event) {
        this.events.computeIfAbsent(tick, k -> new ArrayList<>()).add(event);
    }

    public void add(final int tick, final List<Event> events) {
        this.events.computeIfAbsent(tick, k -> new ArrayList<>()).addAll(events);
    }

    public Set<Integer> getTicks() {
        return Collections.unmodifiableSet(this.events.keySet());
    }

    public void clearTick(final int tick) {
        this.events.remove(tick);
    }

    public void clear() {
        this.events.clear();
    }

    /**
     * Applies the given consumer to all events.
     *
     * @param eventConsumer The consumer
     */
    public void forEach(final Consumer<Event> eventConsumer) {
        this.events.values().stream().flatMap(Collection::stream).forEach(eventConsumer);
    }

    /**
     * Applies the given predicate to all events.<br>
     * The predicate can return true to break the iteration.<br>
     * Use cases for this method can be for example to check if the song contains any specific event.
     *
     * @param eventPredicate The predicate
     * @return True if the predicate returned true for any event
     */
    public boolean testEach(final Predicate<Event> eventPredicate) {
        for (List<Event> list : this.events.values()) {
            for (Event event : list) {
                if (eventPredicate.test(event)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Removes all events which match the given predicate.
     *
     * @param eventPredicate The predicate
     */
    public void removeIf(final Predicate<Event> eventPredicate) {
        for (List<Event> list : this.events.values()) {
            list.removeIf(eventPredicate);
        }
        this.compact();
    }

    /**
     * Removes empty event lists from the events map.
     */
    public void compact() {
        this.events.entrySet().removeIf(entry -> entry.getValue().isEmpty());
    }

    /**
     * @return The total amount of events in a song.
     */
    public int getEventCount() {
        return this.events.values().stream().mapToInt(List::size).sum();
    }

    public Events copy() {
        final Events copyEvents = new Events();
        for (Map.Entry<Integer, List<Event>> entry : this.events.entrySet()) {
            final List<Event> eventList = new ArrayList<>();
            for (Event event : entry.getValue()) {
                eventList.add(event.copy());
            }
            copyEvents.events.put(entry.getKey(), eventList);
        }
        return copyEvents;
    }

}
