package net.raphimc.noteblocklib.parser.nbs;

import it.unimi.dsi.fastutil.ints.*;
import net.raphimc.noteblocklib.parser.Note;
import net.raphimc.noteblocklib.parser.Song;
import net.raphimc.noteblocklib.parser.nbs.data.NBSData;
import net.raphimc.noteblocklib.parser.nbs.data.layer.NBSLayer;
import net.raphimc.noteblocklib.parser.nbs.header.NBSv0Header;
import net.raphimc.noteblocklib.parser.nbs.note.NBSNote;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NBSSong extends Song {

    private final NBSv0Header header;
    private final NBSData data;

    private final String title;
    private final int length;

    public NBSSong(final File sourceFile, final NBSv0Header header, final NBSData data) {
        super(sourceFile, header, data);

        this.header = header;
        this.data = data;

        this.title = this.getHeader().getTitle().isEmpty() ? this.getSourceFile() == null ? "NBS Song" : this.getSourceFile().getName() : this.getHeader().getTitle();
        this.length = this.data.getLayers().values().stream().map(l -> l.getNotesAtTick().keySet()).flatMapToInt(IntCollection::intStream).max().orElse(0);
    }

    @Override
    public NBSv0Header getHeader() {
        return this.header;
    }

    @Override
    public NBSData getData() {
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
        return this.header.getSpeed() / 100F;
    }

    @Override
    public List<Note> getNotesAtTick(int tick) {
        return this.data.getLayers().values().stream().map(NBSLayer::getNotesAtTick).filter(m -> m.containsKey(tick)).map(m -> m.get(tick)).collect(Collectors.toList());
    }

    @Override
    public Int2ObjectMap<List<Note>> getNotes() {
        final Int2ObjectMap<List<Note>> notes = new Int2ObjectAVLTreeMap<>();
        for (Int2ObjectMap.Entry<NBSLayer> entry : this.data.getLayers().int2ObjectEntrySet()) {
            for (Int2ObjectMap.Entry<NBSNote> noteEntry : entry.getValue().getNotesAtTick().int2ObjectEntrySet()) {
                notes.computeIfAbsent(noteEntry.getIntKey(), k -> new ArrayList<>()).add(noteEntry.getValue());
            }
        }
        return notes;
    }

}
