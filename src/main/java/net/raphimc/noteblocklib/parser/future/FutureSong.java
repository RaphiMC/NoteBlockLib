package net.raphimc.noteblocklib.parser.future;

import it.unimi.dsi.fastutil.ints.Int2ObjectAVLTreeMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.raphimc.noteblocklib.parser.Note;
import net.raphimc.noteblocklib.parser.Song;
import net.raphimc.noteblocklib.parser.future.data.FutureData;
import net.raphimc.noteblocklib.parser.future.header.FutureHeader;
import net.raphimc.noteblocklib.parser.future.note.FutureNote;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FutureSong extends Song {

    private final FutureHeader header;
    private final FutureData data;

    private final String title;
    private final int length;

    public FutureSong(File sourceFile, FutureHeader header, FutureData data) {
        super(sourceFile, header, data);

        this.header = header;
        this.data = data;

        this.title = this.getSourceFile() == null ? "Future Song" : this.getSourceFile().getName();
        this.length = this.data.getNotes().keySet().intStream().max().orElse(0);
    }

    @Override
    public FutureHeader getHeader() {
        return this.header;
    }

    @Override
    public FutureData getData() {
        return this.data;
    }

    /**
     * @return The title of the song
     */
    @Override
    public String getTitle() {
        return this.title;
    }

    /**
     * @return The length of the song, measured in ticks.
     */
    @Override
    public int getLength() {
        return this.length;
    }

    /**
     * @return The tempo of the song, measured in ticks per second.
     */
    @Override
    public float getSpeed() {
        return 20F;
    }

    @Override
    public List<Note> getNotesAtTick(int tick) {
        return this.data.getNotes().getOrDefault(tick, new ArrayList<>()).stream().map(t -> (Note) t).collect(Collectors.toList());
    }

    @Override
    public Int2ObjectMap<List<Note>> getNotes() {
        final Int2ObjectMap<List<Note>> notes = new Int2ObjectAVLTreeMap<>();
        for (Int2ObjectMap.Entry<List<FutureNote>> entry : this.data.getNotes().int2ObjectEntrySet()) {
            final ArrayList<Note> noteList;
            notes.put(entry.getIntKey(), noteList = new ArrayList<>());
            noteList.addAll(entry.getValue());
        }
        return notes;
    }

}
