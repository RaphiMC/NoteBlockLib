package net.raphimc.noteblocklib.format.nbs.note;

import net.raphimc.noteblocklib.format.nbs.data.layer.NBSLayer;
import net.raphimc.noteblocklib.model.Note;

public class NBSNote extends Note {

    private NBSLayer layer;

    public NBSNote(final byte instrument, final byte key) {
        super(instrument, key);
    }

    /**
     * @return The NBS layer this note is in. Can be null.
     */
    public NBSLayer getLayer() {
        return this.layer;
    }

    public void setLayer(final NBSLayer layer) {
        this.layer = layer;
    }

}
