package net.raphimc.noteblocklib.model;

import java.io.File;
import java.util.List;
import java.util.Map;

public abstract class Song<H extends Header, D extends Data, N extends Note> {

    private final File sourceFile;
    private final H header;
    private final D data;

    public Song(final File sourceFile, final H header, final D data) {
        this.sourceFile = sourceFile;
        this.header = header;
        this.data = data;
    }

    /**
     * @return The file this song originated from or null, if read from InputStream
     */
    public File getSourceFile() {
        return this.sourceFile;
    }

    public H getHeader() {
        return this.header;
    }

    public D getData() {
        return this.data;
    }

    /**
     * @return The title of the song
     */
    public abstract String getTitle();

    /**
     * @return The length of the song, measured in ticks.
     */
    public abstract int getLength();

    /**
     * @return The tempo of the song, measured in ticks per second.
     */
    public abstract float getSpeed();

    public abstract List<N> getNotesAtTick(final int tick);

    public abstract Map<Integer, List<N>> getNotes();

}
