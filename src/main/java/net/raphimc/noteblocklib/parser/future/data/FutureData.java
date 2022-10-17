package net.raphimc.noteblocklib.parser.future.data;

import com.google.common.io.LittleEndianDataInputStream;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.raphimc.noteblocklib.parser.Data;
import net.raphimc.noteblocklib.parser.future.header.FutureHeader;
import net.raphimc.noteblocklib.parser.future.note.FutureNote;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FutureData extends Data {

    private Int2ObjectMap<List<FutureNote>> notes;

    public FutureData(final FutureHeader header, final LittleEndianDataInputStream is) throws IOException {
        super();

        this.notes = new Int2ObjectOpenHashMap<>();

        int tick = 0;
        while (is.available() > 0) {
            final byte b = is.readByte();
            if (b == (header.isUseMagicValue() ? 5 : 64)) {
                tick += is.readUnsignedShort();
            } else {
                this.notes.computeIfAbsent(tick, k -> new ArrayList<>()).add(new FutureNote(is.readByte(), b));
            }
        }
    }

    public FutureData(final Int2ObjectMap<List<FutureNote>> notes) {
        super();

        this.notes = notes;
    }

    public void setNotes(final Int2ObjectMap<List<FutureNote>> notes) {
        this.notes = notes;
    }

    public Int2ObjectMap<List<FutureNote>> getNotes() {
        return this.notes;
    }

}
