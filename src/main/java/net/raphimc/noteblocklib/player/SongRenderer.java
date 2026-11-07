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
package net.raphimc.noteblocklib.player;

import net.raphimc.noteblocklib.model.event.Event;
import net.raphimc.noteblocklib.model.note.Note;
import net.raphimc.noteblocklib.model.song.Song;

public abstract class SongRenderer<B> {

    private final Song song;
    private int tick;

    public SongRenderer(final Song song) {
        this.song = song;
    }

    public B tick() {
        for (Note note : this.song.getNotes().getOrEmpty(this.tick)) {
            this.playNote(note);
        }
        for (Event event : this.song.getEvents().getOrEmpty(this.tick)) {
            this.handleEvent(event);
        }
        return this.render(1000F / this.song.getTempoEvents().getEffectiveTempo(this.tick++));
    }

    /**
     * Called to play a note.
     *
     * @param note The note to play.
     */
    protected abstract void playNote(final Note note);

    /**
     * Called to handle an event.
     *
     * @param event The event to handle.
     */
    protected void handleEvent(final Event event) {
    }

    /**
     * Called to render the audio for the current tick.
     *
     * @param millis The length of the audio to render in milliseconds.
     * @return The rendered audio.
     */
    protected abstract B render(final float millis);

    /**
     * @return Whether the song has reached the end.
     */
    public boolean hasReachedEnd() {
        return this.tick >= this.song.getNotes().getLengthInTicks();
    }

    /**
     * @return The song that is being rendered.
     */
    public Song getSong() {
        return this.song;
    }

    /**
     * @return The current tick.
     */
    public int getTick() {
        return this.tick;
    }

    /**
     * Sets the current tick.
     *
     * @param tick The tick to set.
     */
    public void setTick(final int tick) {
        this.tick = tick;
    }

    /**
     * @return The current render position as a percentage (0-100).
     */
    public float getProgress() {
        return ((float) this.tick / this.song.getNotes().getLengthInTicks()) * 100F;
    }

    /**
     * Sets the current render position as a percentage (0-100).
     *
     * @param progress The percentage to set the playback position to.
     */
    public void setProgress(final float progress) {
        this.tick = Math.round((progress / 100F) * this.song.getNotes().getLengthInTicks());
    }

    /**
     * @return The current render position in milliseconds.
     */
    public int getMillisecondPosition() {
        return this.song.tickToMilliseconds(this.tick);
    }

    /**
     * Sets the current render position in milliseconds.
     *
     * @param milliseconds The time to set the render position to.
     */
    public void setMillisecondPosition(final int milliseconds) {
        this.tick = this.song.millisecondsToTick(milliseconds);
    }

}
