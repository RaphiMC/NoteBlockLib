package net.raphimc.noteblocklib.util;

public class SleepTimer {

    private volatile long delay;

    private volatile long start;
    private volatile long end;
    private volatile long runtime;

    public SleepTimer(final long delay) {
        this.delay = delay;
    }

    public void begin() {
        this.start = System.currentTimeMillis();
    }

    public void end() {
        this.end = System.currentTimeMillis();
        this.runtime = this.end - this.start;
    }

    public boolean sleep() {
        final long remainingTime = this.delay - this.runtime;
        if (remainingTime > 0) {
            try {
                Thread.sleep(remainingTime);
            } catch (InterruptedException e) {
                return false;
            }
        }
        return true;
    }

    public void setDelay(final long delay) {
        this.delay = delay;
    }

    public long getDelay() {
        return this.delay;
    }

    public long getStart() {
        return this.start;
    }

    public long getEnd() {
        return this.end;
    }

    public long getRuntime() {
        return this.runtime;
    }

}
