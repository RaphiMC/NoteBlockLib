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
package net.raphimc.noteblocklib.model.song;

import net.raphimc.noteblocklib.format.SongFormat;
import net.raphimc.noteblocklib.model.event.TempoEvents;
import net.raphimc.noteblocklib.model.note.Notes;

import java.util.Set;
import java.util.TreeSet;

public abstract class Song {

    private final SongFormat format;
    private Notes notes = new Notes();
    private TempoEvents tempoEvents = new TempoEvents();
    private final String fileName;
    private String title;
    private String author;
    private String originalAuthor;
    private String description;

    protected Song(final SongFormat format, final String fileName) {
        this.format = format;
        this.fileName = fileName;
    }

    public int getLengthInMilliseconds() {
        return this.tickToMilliseconds(this.notes.getLengthInTicks());
    }

    public int getLengthInSeconds() {
        return (int) Math.ceil(this.getLengthInMilliseconds() / 1000F);
    }

    public String getHumanReadableLength() {
        final int length = this.getLengthInSeconds();
        return String.format("%02d:%02d:%02d", length / 3600, (length / 60) % 60, length % 60);
    }

    public int tickToMilliseconds(final int tick) {
        final Set<Integer> tempoEventTicks = new TreeSet<>(this.tempoEvents.getTicks());
        tempoEventTicks.add(tick);

        int lastTick = 0;
        float totalMilliseconds = 0;
        for (int tempoTick : tempoEventTicks) {
            if (tempoTick > tick) {
                break;
            }

            final float tps = this.tempoEvents.getEffectiveTempo(lastTick);
            final int ticksInSegment = tempoTick - lastTick;
            final float segmentMilliseconds = (ticksInSegment / tps) * 1000F;
            totalMilliseconds += segmentMilliseconds;
            lastTick = tempoTick;
        }

        return (int) Math.ceil(totalMilliseconds);
    }

    public int millisecondsToTick(final int milliseconds) {
        final Set<Integer> tempoEventTicks = new TreeSet<>(this.tempoEvents.getTicks());
        tempoEventTicks.add(this.notes.getLengthInTicks());

        int lastTick = 0;
        float totalMilliseconds = 0;
        for (int tempoTick : tempoEventTicks) {
            final float tps = this.tempoEvents.getEffectiveTempo(lastTick);
            final int ticksInSegment = tempoTick - lastTick;
            final float segmentMilliseconds = (ticksInSegment / tps) * 1000F;

            if (totalMilliseconds + segmentMilliseconds >= milliseconds) {
                final float remainingMilliseconds = milliseconds - totalMilliseconds;
                final int ticksToAdd = Math.round((remainingMilliseconds / 1000F) * tps);
                return lastTick + ticksToAdd;
            }

            totalMilliseconds += segmentMilliseconds;
            lastTick = tempoTick;
        }

        return this.notes.getLengthInTicks();
    }

    public SongFormat getFormat() {
        return this.format;
    }

    public Notes getNotes() {
        return this.notes;
    }

    public TempoEvents getTempoEvents() {
        return this.tempoEvents;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getFileNameOr(final String fallback) {
        return this.fileName == null ? fallback : this.fileName;
    }

    public String getTitle() {
        return this.title;
    }

    public String getTitleOr(final String fallback) {
        return this.title == null ? fallback : this.title;
    }

    public Song setTitle(final String title) {
        if (title != null && !title.isEmpty()) {
            this.title = title;
        } else {
            this.title = null;
        }
        return this;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getAuthorOr(final String fallback) {
        return this.author == null ? fallback : this.author;
    }

    public Song setAuthor(final String author) {
        if (author != null && !author.isEmpty()) {
            this.author = author;
        } else {
            this.author = null;
        }
        return this;
    }

    public String getOriginalAuthor() {
        return this.originalAuthor;
    }

    public String getOriginalAuthorOr(final String fallback) {
        return this.originalAuthor == null ? fallback : this.originalAuthor;
    }

    public Song setOriginalAuthor(final String originalAuthor) {
        if (originalAuthor != null && !originalAuthor.isEmpty()) {
            this.originalAuthor = originalAuthor;
        } else {
            this.originalAuthor = null;
        }
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public String getDescriptionOr(final String fallback) {
        return this.description == null ? fallback : this.description;
    }

    public Song setDescription(final String description) {
        if (description != null && !description.isEmpty()) {
            this.description = description;
        } else {
            this.description = null;
        }
        return this;
    }

    public String getTitleOrFileName() {
        return this.title == null ? this.fileName : this.title;
    }

    public String getTitleOrFileNameOr(final String fallback) {
        return this.getTitleOrFileName() == null ? fallback : this.getTitleOrFileName();
    }

    public void copyGeneralData(final Song song) {
        this.notes = song.getNotes().copy();
        this.tempoEvents = song.getTempoEvents().copy();
        this.setTitle(song.getTitle());
        this.setAuthor(song.getAuthor());
        this.setOriginalAuthor(song.getOriginalAuthor());
        this.setDescription(song.getDescription());
    }

    public abstract Song copy();

}
