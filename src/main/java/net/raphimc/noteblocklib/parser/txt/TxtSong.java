package net.raphimc.noteblocklib.parser.txt;

import it.unimi.dsi.fastutil.ints.Int2ObjectAVLTreeMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.raphimc.noteblocklib.parser.*;
import net.raphimc.noteblocklib.parser.txt.data.TxtData;
import net.raphimc.noteblocklib.parser.txt.header.TxtV2Header;
import net.raphimc.noteblocklib.parser.txt.note.TxtNote;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TxtSong extends Song {

    private final TxtData data;

    private final String title;
    private final int length;

    public TxtSong(File sourceFile, Header header, TxtData data) {
        super(sourceFile, header, data);

        this.data = data;

        this.title = this.getSourceFile() == null ? "Txt Song" : this.getSourceFile().getName();
        this.length = this.data.getNotes().keySet().intStream().max().orElse(0);
    }

    @Override
    public TxtData getData() {
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
        return this.getHeader() instanceof TxtV2Header ? ((TxtV2Header) this.getHeader()).getSpeed() : 20F;
    }

    @Override
    public List<Note> getNotesAtTick(int tick) {
        return this.data.getNotes().getOrDefault(tick, new ArrayList<>()).stream().map(t -> (Note) t).collect(Collectors.toList());
    }

    @Override
    public Int2ObjectMap<List<Note>> getNotes() {
        final Int2ObjectMap<List<Note>> notes = new Int2ObjectAVLTreeMap<>();
        for (Int2ObjectMap.Entry<List<TxtNote>> entry : this.data.getNotes().int2ObjectEntrySet()) {
            final ArrayList<Note> noteList;
            notes.put(entry.getIntKey(), noteList = new ArrayList<>());
            noteList.addAll(entry.getValue());
        }
        return notes;
    }

}
