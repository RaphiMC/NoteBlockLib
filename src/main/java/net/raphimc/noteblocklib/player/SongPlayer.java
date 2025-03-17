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
package net.raphimc.noteblocklib.player;

import net.raphimc.noteblocklib.model.Note;
import net.raphimc.noteblocklib.model.Song;
import net.raphimc.noteblocklib.util.TimerHack;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public abstract class SongPlayer {

    private Song song;
    private ScheduledExecutorService scheduler;
    private ScheduledFuture<?> tickTask;
    private float ticksPerSecond;
    private int tick;
    private boolean paused;

    public SongPlayer(final Song song) {
        this.song = song;
    }

    /**
     * Starts playing the song from the beginning.
     */
    public void start() {
        this.start(0);
    }

    /**
     * Starts playing the song from the beginning.
     * @param delay The delay in milliseconds before starting the song.
     */
    public void start(final int delay) {
        if (this.isRunning()) this.stop();

        this.ticksPerSecond = this.song.getTempoEvents().get(0);
        this.tick = 0;

        TimerHack.ensureRunning();
        this.scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
            final Thread thread = new Thread(r, "NoteBlockLib Song Player - " + this.song.getTitleOrFileNameOr("No Title"));
            thread.setPriority(Thread.NORM_PRIORITY + 1);
            thread.setDaemon(true);
            return thread;
        });
        this.createTickTask(TimeUnit.MILLISECONDS.toNanos(delay));
    }

    /**
     * Stops playing the song.
     */
    public void stop() {
        if (!this.isRunning()) return;

        this.scheduler.shutdownNow();
        try {
            this.scheduler.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException ignored) {
        }
        this.scheduler = null;
        this.tickTask = null;
        this.paused = false;
    }

    /**
     * @return Whether the player is in the running state (playing or paused).
     */
    public boolean isRunning() {
        return this.scheduler != null && !this.scheduler.isTerminated();
    }

    /**
     * @return The song that is being played.
     */
    public Song getSong() {
        return this.song;
    }

    /**
     * Sets the song that should be played.<br>
     * Can be called in {@link #onFinished()}.
     * @param song The song to play.
     */
    protected void setSong(final Song song) {
        this.song = song;
    }

    /**
     * @return The current tempo in ticks per second.
     */
    public float getCurrentTicksPerSecond() {
        return this.ticksPerSecond;
    }

    /**
     * @return The current tick.
     */
    public int getTick() {
        return this.tick;
    }

    /**
     * Sets the current tick.
     * @param tick The tick to set.
     */
    public void setTick(final int tick) {
        this.tick = tick;
    }

    /**
     * @return The current playback position in milliseconds.
     */
    public int getMillisecondPosition() {
        return this.song.tickToMilliseconds(this.tick);
    }

    /**
     * Sets the current playback position in milliseconds.
     * @param milliseconds The time to set the playback position to.
     */
    public void setMillisecondPosition(final int milliseconds) {
        this.tick = this.song.millisecondsToTick(milliseconds);
    }

    /**
     * @return Whether the player is paused.
     */
    public boolean isPaused() {
        return this.paused;
    }

    /**
     * Pauses or resumes the player.
     * @param paused Whether the player should be paused.
     */
    public void setPaused(final boolean paused) {
        this.paused = paused;
    }

    /**
     * Create the internal tick task.
     * @param initialDelay The initial delay in nanoseconds.
     */
    protected void createTickTask(final long initialDelay) {
        if (this.tickTask != null) {
            this.tickTask.cancel(false);
        }
        this.tickTask = this.scheduler.scheduleAtFixedRate(this::tick, initialDelay, (long) (1_000_000_000D / this.ticksPerSecond), TimeUnit.NANOSECONDS);
    }

    /**
     * Called every tick to play the notes.
     */
    protected void tick() {
        try {
            if (!this.preTick()) {
                return;
            }
            try {
                if (this.paused) {
                    return;
                }

                this.playNotes(this.song.getNotes().getOrEmpty(this.tick));

                this.tick++;
                if (this.tick >= this.song.getNotes().getLengthInTicks()) {
                    this.stop();
                    this.onFinished();
                    return;
                }
                if (this.ticksPerSecond != this.song.getTempoEvents().getEffectiveTempo(this.tick)) {
                    this.ticksPerSecond = this.song.getTempoEvents().getEffectiveTempo(this.tick);
                    this.createTickTask((long) (1_000_000_000D / this.ticksPerSecond));
                }
            } finally {
                this.postTick();
            }
        } catch (Throwable e) {
            if (e.getCause() instanceof InterruptedException) return;

            e.printStackTrace();
            this.stop();
        }
    }

    /**
     * Called before each tick (Even when paused).
     * @return Whether the tick should be executed.
     */
    protected boolean preTick() {
        return true;
    }

    /**
     * Plays the notes.
     * @param notes The notes to play.
     */
    protected abstract void playNotes(final List<Note> notes);

    /**
     * Called when the song has finished playing.
     */
    protected void onFinished() {
    }

    /**
     * Called after each tick.
     */
    protected void postTick() {
    }

}
