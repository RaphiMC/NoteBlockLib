package net.raphimc.noteblocklib.player;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import net.raphimc.noteblocklib.model.Note;
import net.raphimc.noteblocklib.model.Song;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class SongPlayer {

    private static final ScheduledExecutorService SCHEDULER = Executors.newSingleThreadScheduledExecutor(new ThreadFactoryBuilder().setNameFormat("Song Player").setDaemon(true).build());

    private final Song<?, ?, ?> song;
    private final ISongPlayerCallback callback;

    private ScheduledFuture<?> timer;
    private int tick = -1;
    private boolean paused;

    public SongPlayer(final Song<?, ?, ?> song, final ISongPlayerCallback callback) {
        this.song = song;
        this.callback = callback;
    }

    public Song<?, ?, ?> getSong() {
        return this.song;
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
        if (this.paused) {
            this.paused = false;
            return;
        }
        if (this.isRunning()) this.stop();

        this.timer = SCHEDULER.scheduleAtFixedRate(this::tick, 0, (long) (1_000_000_000D / this.song.getSpeed()), TimeUnit.NANOSECONDS);
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
    }

    private void tick() {
        try {
            if (this.paused || !this.callback.shouldTick()) {
                return;
            }
            this.tick++;

            if (this.tick > this.song.getLength()) {
                if (this.callback.shouldLoop()) {
                    this.tick = -this.callback.getLoopDelay();
                } else {
                    this.callback.onFinished();
                    this.tick = -1;
                    this.stop();
                }
                return;
            }

            for (Note note : this.song.getNotesAtTick(this.tick)) {
                this.callback.playNote(note);
            }
        } catch (Throwable e) {
            if (e.getCause() instanceof InterruptedException) return;

            e.printStackTrace();
            this.stop();
        }
    }

}
