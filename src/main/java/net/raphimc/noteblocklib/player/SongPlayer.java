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

    public void start() {
        if (this.isRunning()) this.stop();

        this.ticksPerSecond = this.song.getTempoEvents().getTempo(0);
        this.tick = 0;

        TimerHack.ensureRunning();
        this.scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
            final Thread thread = new Thread(r, "NoteBlockLib Song Player - " + this.song.getTitleOrFileNameOr("No Title"));
            thread.setPriority(Thread.NORM_PRIORITY + 1);
            thread.setDaemon(true);
            return thread;
        });
        this.createTickTask();
    }

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

    public boolean isRunning() {
        return this.scheduler != null && !this.scheduler.isTerminated();
    }

    public Song getSong() {
        return this.song;
    }

    protected void setSong(final Song song) {
        this.song = song;
    }

    public float getCurrentTicksPerSecond() {
        return this.ticksPerSecond;
    }

    public int getTick() {
        return this.tick;
    }

    public void setTick(final int tick) {
        this.tick = tick;
    }

    public int getMillisecondPosition() {
        return this.song.tickToMilliseconds(this.tick);
    }

    public void setMillisecondPosition(final int milliseconds) {
        this.tick = this.song.millisecondsToTick(milliseconds);
    }

    public boolean isPaused() {
        return this.paused;
    }

    public void setPaused(final boolean paused) {
        this.paused = paused;
    }

    protected void createTickTask() {
        if (this.tickTask != null) {
            this.tickTask.cancel(false);
        }
        final long period = (long) (1_000_000_000D / this.ticksPerSecond);
        this.tickTask = this.scheduler.scheduleAtFixedRate(this::tick, this.tickTask != null ? period : 0L, period, TimeUnit.NANOSECONDS);
    }

    protected void tick() {
        try {
            this.preTick();
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
                    this.createTickTask();
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

    protected void preTick() {
    }

    protected abstract void playNotes(final List<Note> notes);

    protected void onFinished() {
    }

    protected void postTick() {
    }

}
