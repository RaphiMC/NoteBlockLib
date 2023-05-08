package net.raphimc.noteblocklib.format.nbs.data.layer;

import net.raphimc.noteblocklib.format.nbs.note.NBSNote;

import java.util.Map;

public class NBSv2Layer extends NBSv0Layer {

    private short panning;

    public NBSv2Layer(final Map<Integer, NBSNote> notesAtTick, final String name, final byte volume, final short panning) {
        super(notesAtTick, name, volume);

        this.panning = panning;
    }

    /**
     * @return How much this layer is panned to the left/right. 0 is 2 blocks right, 100 is center, 200 is 2 blocks left.
     */
    public short getPanning() {
        return this.panning;
    }

    public void setPanning(final short panning) {
        this.panning = panning;
    }

}
