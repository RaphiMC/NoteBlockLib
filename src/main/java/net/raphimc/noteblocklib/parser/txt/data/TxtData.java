package net.raphimc.noteblocklib.parser.txt.data;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.raphimc.noteblocklib.parser.Data;
import net.raphimc.noteblocklib.parser.txt.note.TxtNote;

import java.util.*;

public class TxtData extends Data {

    private Int2ObjectMap<List<TxtNote>> notes;

    public TxtData(final Scanner scanner) {
        super();

        this.notes = new Int2ObjectOpenHashMap<>();

        while (scanner.hasNext("\\d+")) {
            this.notes.computeIfAbsent(scanner.nextInt(), k -> new ArrayList<>()).add(new TxtNote(scanner));
        }
    }

    public TxtData(final Int2ObjectMap<List<TxtNote>> notes) {
        super();

        this.notes = notes;
    }

    public void setNotes(final Int2ObjectMap<List<TxtNote>> notes) {
        this.notes = notes;
    }

    public Int2ObjectMap<List<TxtNote>> getNotes() {
        return this.notes;
    }

}
