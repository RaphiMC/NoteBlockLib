package net.raphimc.noteblocklib.format.future;

import net.raphimc.noteblocklib.format.future.data.FutureData;
import net.raphimc.noteblocklib.format.future.header.FutureHeader;
import net.raphimc.noteblocklib.format.future.note.FutureNote;
import net.raphimc.noteblocklib.model.Song;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FutureSong extends Song<FutureHeader, FutureData, FutureNote> {

    private final String title;
    private final int length;

    public FutureSong(final File sourceFile, final FutureHeader header, final FutureData data) {
        super(sourceFile, header, data);

        this.title = this.getSourceFile() == null ? "Future Song" : this.getSourceFile().getName();
        this.length = data.getNotes().keySet().stream().mapToInt(i -> i).max().orElse(0);
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
    public List<FutureNote> getNotesAtTick(final int tick) {
        return this.getData().getNotes().getOrDefault(tick, new ArrayList<>());
    }

    @Override
    public Map<Integer, List<FutureNote>> getNotes() {
        return this.getData().getNotes();
    }

}
