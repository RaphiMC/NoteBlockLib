package net.raphimc.noteblocklib.format.txt;

import net.raphimc.noteblocklib.format.txt.data.TxtData;
import net.raphimc.noteblocklib.format.txt.header.TxtV1Header;
import net.raphimc.noteblocklib.format.txt.header.TxtV2Header;
import net.raphimc.noteblocklib.format.txt.note.TxtNote;
import net.raphimc.noteblocklib.model.Song;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TxtSong extends Song<TxtV1Header, TxtData, TxtNote> {

    private final String title;
    private final int length;

    public TxtSong(final File sourceFile, final TxtV1Header header, final TxtData data) {
        super(sourceFile, header, data);

        this.title = this.getSourceFile() == null ? "Txt Song" : this.getSourceFile().getName();
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
        return this.getHeader() instanceof TxtV2Header ? ((TxtV2Header) this.getHeader()).getSpeed() : 20F;
    }

    @Override
    public List<TxtNote> getNotesAtTick(final int tick) {
        return this.getData().getNotes().getOrDefault(tick, new ArrayList<>());
    }

    @Override
    public Map<Integer, List<TxtNote>> getNotes() {
        return this.getData().getNotes();
    }

}
