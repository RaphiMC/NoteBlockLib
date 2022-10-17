package net.raphimc.noteblocklib.parser.nbs.data.layer;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.raphimc.noteblocklib.parser.nbs.note.NBSNote;

public class NBSv2Layer extends NBSv0Layer {

    private short panning;

    public NBSv2Layer(final Int2ObjectMap<NBSNote> notesAtTick, final String name, final byte volume, final short panning) {
        super(notesAtTick, name, volume);
        this.panning = panning;
    }

    public void setPanning(final short panning) {
        this.panning = panning;
    }

    /**
     * @return How much this layer is panned to the left/right. 0 is 2 blocks right, 100 is center, 200 is 2 blocks left.
     */
    public short getPanning() {
        return this.panning;
    }

}
