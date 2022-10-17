package net.raphimc.noteblocklib.parser.nbs.note;

import com.google.common.io.LittleEndianDataInputStream;
import net.raphimc.noteblocklib.parser.Note;
import net.raphimc.noteblocklib.parser.nbs.data.layer.NBSLayer;

import java.io.IOException;

public class NBSNote extends Note {

    private NBSLayer layer;

    public NBSNote(final LittleEndianDataInputStream dis) throws IOException {
        super(dis.readByte(), dis.readByte());
    }

    public NBSNote(final byte instrument, final byte key) {
        super(instrument, key);
    }

    public void setLayer(final NBSLayer layer) {
        this.layer = layer;
    }

    /**
     * @return The NBS layer this note is in. Can be null.
     */
    public NBSLayer getLayer() {
        return this.layer;
    }

}
