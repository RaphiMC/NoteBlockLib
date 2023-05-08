package net.raphimc.noteblocklib.player;

import net.raphimc.noteblocklib.model.Note;

public interface ISongPlayerCallback {

    default void onFinished() {
    }

    default boolean shouldTick() {
        return true;
    }

    default boolean shouldLoop() {
        return false;
    }

    default int getLoopDelay() {
        return 0;
    }

    void playNote(final Note note);

}
