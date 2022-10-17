package net.raphimc.noteblocklib.parser.nbs.data.layer;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.raphimc.noteblocklib.parser.nbs.note.NBSNote;

public class NBSLayer {

    private Int2ObjectMap<NBSNote> notesAtTick;

    public NBSLayer(final Int2ObjectMap<NBSNote> notesAtTick) {
        this.notesAtTick = notesAtTick;
    }

    public Int2ObjectMap<NBSNote> getNotesAtTick() {
        return this.notesAtTick;
    }

    public void setNotesAtTick(final Int2ObjectMap<NBSNote> notesAtTick) {
        this.notesAtTick = notesAtTick;
    }

}
