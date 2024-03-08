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
package net.raphimc.noteblocklib.player;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import net.raphimc.noteblocklib.model.Note;
import net.raphimc.noteblocklib.model.SongView;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class SongPlayer {

    private static final ScheduledExecutorService SCHEDULER = Executors.newSingleThreadScheduledExecutor(new ThreadFactoryBuilder().setNameFormat("Song Player").setDaemon(true).build());

    private final SongView<?> songView;
    private final ISongPlayerCallback callback;

    private ScheduledFuture<?> timer;
    private int tick = -1;
    private boolean paused;

    public SongPlayer(final SongView<?> songView, final ISongPlayerCallback callback) {
        this.songView = songView;
        this.callback = callback;
    }

    public SongView<?> getSongView() {
        return this.songView;
    }

    public boolean isRunning() {
        return this.timer != null && !this.timer.isDone() && !this.timer.isCancelled();
    }

    public int getTick() {
        return this.tick;
    }

    public void setTick(final int tick) {
        this.tick = tick;
    }

    public void play() {
        this.paused = false;
        if (this.isRunning()) this.stop();

        this.timer = SCHEDULER.scheduleAtFixedRate(this::tick, 0, (long) (1_000_000_000D / this.songView.getSpeed()), TimeUnit.NANOSECONDS);
    }

    public void setPaused(final boolean paused) {
        this.paused = paused;
    }

    public boolean isPaused() {
        return this.paused;
    }

    public void stop() {
        if (!this.isRunning()) return;

        this.timer.cancel(true);
        this.paused = false;
    }

    private void tick() {
        try {
            if (this.paused || !this.callback.shouldTick()) {
                return;
            }
            this.tick++;

            if (this.tick > this.songView.getLength()) {
                if (this.callback.shouldLoop()) {
                    this.tick = -this.callback.getLoopDelay();
                } else {
                    this.callback.onFinished();
                    this.tick = -1;
                    this.stop();
                }
                return;
            }

            for (Note note : this.songView.getNotesAtTick(this.tick)) {
                this.callback.playNote(note);
            }
        } catch (Throwable e) {
            if (e.getCause() instanceof InterruptedException) return;

            e.printStackTrace();
            this.stop();
        }
    }

}
