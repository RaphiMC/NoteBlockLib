package net.raphimc.noteblocklib.format.nbs.data.layer;

import net.raphimc.noteblocklib.format.nbs.note.NBSNote;

import java.util.Map;

public class NBSv4Layer extends NBSv2Layer {

    private boolean locked;

    public NBSv4Layer(final Map<Integer, NBSNote> notesAtTick, final String name, final byte volume, final short panning, final boolean locked) {
        super(notesAtTick, name, volume, panning);

        this.locked = locked;
    }

    /**
     * @return Whether this layer has been marked as locked.
     */
    public boolean isLocked() {
        return this.locked;
    }

    public void setLocked(final boolean locked) {
        this.locked = locked;
    }

}
