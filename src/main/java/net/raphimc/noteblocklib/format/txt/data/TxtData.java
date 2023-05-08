package net.raphimc.noteblocklib.format.txt.data;

import net.raphimc.noteblocklib.format.txt.note.TxtNote;
import net.raphimc.noteblocklib.model.Data;

import java.util.*;

public class TxtData implements Data {

    private Map<Integer, List<TxtNote>> notes;

    public TxtData(final Scanner scanner) {
        this.notes = new HashMap<>();

        while (scanner.hasNext("\\d+")) {
            this.notes.computeIfAbsent(scanner.nextInt(), k -> new ArrayList<>()).add(new TxtNote(scanner));
        }
    }

    public TxtData(final Map<Integer, List<TxtNote>> notes) {
        this.notes = notes;
    }

    public Map<Integer, List<TxtNote>> getNotes() {
        return this.notes;
    }

    public void setNotes(final Map<Integer, List<TxtNote>> notes) {
        this.notes = notes;
    }

}
