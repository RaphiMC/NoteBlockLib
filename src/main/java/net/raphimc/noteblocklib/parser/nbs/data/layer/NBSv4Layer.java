package net.raphimc.noteblocklib.parser.nbs.data.layer;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.raphimc.noteblocklib.parser.nbs.note.NBSNote;

public class NBSv4Layer extends NBSv2Layer {

    private boolean locked;

    public NBSv4Layer(final Int2ObjectMap<NBSNote> notesAtTick, final String name, final byte volume, final short panning, final boolean locked) {
        super(notesAtTick, name, volume, panning);
        this.locked = locked;
    }

    public void setLocked(final boolean locked) {
        this.locked = locked;
    }

    /**
     * @return Whether this layer has been marked as locked.
     */
    public boolean isLocked() {
        return this.locked;
    }

}
