package net.raphimc.noteblocklib.format.future.data;

import com.google.common.io.LittleEndianDataInputStream;
import net.raphimc.noteblocklib.format.future.header.FutureHeader;
import net.raphimc.noteblocklib.format.future.note.FutureNote;
import net.raphimc.noteblocklib.model.Data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FutureData implements Data {

    private Map<Integer, List<FutureNote>> notes;

    @SuppressWarnings("UnstableApiUsage")
    public FutureData(final FutureHeader header, final LittleEndianDataInputStream dis) throws IOException {
        this.notes = new HashMap<>();

        int tick = 0;
        while (dis.available() > 0) {
            final byte b = dis.readByte();
            if (b == (header.useMagicValue() ? 5 : 64)) {
                tick += dis.readUnsignedShort();
            } else {
                this.notes.computeIfAbsent(tick, k -> new ArrayList<>()).add(new FutureNote(dis.readByte(), b));
            }
        }
    }

    public FutureData(final Map<Integer, List<FutureNote>> notes) {
        this.notes = notes;
    }

    public Map<Integer, List<FutureNote>> getNotes() {
        return this.notes;
    }

    public void setNotes(final Map<Integer, List<FutureNote>> notes) {
        this.notes = notes;
    }

}
