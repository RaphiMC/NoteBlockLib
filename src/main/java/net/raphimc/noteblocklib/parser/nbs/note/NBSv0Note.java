package net.raphimc.noteblocklib.parser.nbs.note;

import com.google.common.io.LittleEndianDataInputStream;

import java.io.IOException;

public class NBSv0Note extends NBSNote {

    public NBSv0Note(final LittleEndianDataInputStream dis) throws IOException {
        super(dis);
    }

    public NBSv0Note(final byte instrument, final byte key) {
        super(instrument, key);
    }

}
