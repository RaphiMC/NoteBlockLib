package net.raphimc.noteblocklib.player;

import net.raphimc.noteblocklib.parser.Note;
import net.raphimc.noteblocklib.parser.Song;
import net.raphimc.noteblocklib.util.SleepTimer;

public class SongPlayer implements Runnable {

    private final Song song;
    private final ISongPlayerCallback callback;
    private final SleepTimer timer;

    private Thread thread;
    private int tick = -1;
    private boolean paused;


    public SongPlayer(final Song song, final ISongPlayerCallback callback) {
        this.song = song;
        this.callback = callback;
        this.timer = new SleepTimer((long) (1000F / this.song.getSpeed()));
    }

    public Song getSong() {
        return this.song;
    }

    public boolean isRunning() {
        return this.thread != null && this.thread.isAlive();
    }

    public int getTick() {
        return this.tick;
    }

    public void setTick(int tick) {
        this.tick = tick;
    }

    public void play() {
        if (this.paused) {
            this.paused = false;
            return;
        }
        if (this.isRunning()) this.stop();

        this.thread = new Thread(this, "Song Player");
        this.thread.setDaemon(true);
        this.thread.start();
    }

    public void setPaused(final boolean paused) {
        this.paused = paused;
    }

    public boolean isPaused() {
        return this.paused;
    }

    public void stop() {
        if (!this.isRunning()) return;

        this.thread.interrupt();
    }


    @Override
    public void run() {
        try {
            while (!this.thread.isInterrupted()) {
                while (this.paused || !this.callback.shouldTick()) Thread.sleep(50);
                this.tick++;

                this.timer.begin();
                if (this.tick > this.song.getLength()) {
                    if (this.callback.shouldLoop()) {
                        this.tick = -this.callback.getLoopDelay();
                        continue;
                    } else {
                        this.callback.onFinished();
                        this.tick = -1;
                        break;
                    }
                }

                for (Note note : this.song.getNotesAtTick(this.tick)) {
                    this.callback.playNote(note);
                }
                this.timer.end();

                if (!this.timer.sleep()) {
                    return;
                }
            }
        } catch (InterruptedException ignored) {
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

}
