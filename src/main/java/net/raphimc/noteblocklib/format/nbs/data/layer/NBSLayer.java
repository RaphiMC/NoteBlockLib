package net.raphimc.noteblocklib.format.nbs.data.layer;

import net.raphimc.noteblocklib.format.nbs.note.NBSNote;

import java.util.Map;

public class NBSLayer {

    private Map<Integer, NBSNote> notesAtTick;

    public NBSLayer(final Map<Integer, NBSNote> notesAtTick) {
        this.notesAtTick = notesAtTick;
    }

    public Map<Integer, NBSNote> getNotesAtTick() {
        return this.notesAtTick;
    }

    public void setNotesAtTick(final Map<Integer, NBSNote> notesAtTick) {
        this.notesAtTick = notesAtTick;
    }

}
