package net.raphimc.noteblocklib.format.nbs.note;

import com.google.common.io.LittleEndianDataInputStream;

import java.io.IOException;

@SuppressWarnings("UnstableApiUsage")
public class NBSv0Note extends NBSNote {

    @SuppressWarnings("UnstableApiUsage")
    public NBSv0Note(final LittleEndianDataInputStream dis) throws IOException {
        super(dis.readByte(), dis.readByte());
    }

    public NBSv0Note(final byte instrument, final byte key) {
        super(instrument, key);
    }

}
