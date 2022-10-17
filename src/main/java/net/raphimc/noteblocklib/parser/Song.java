package net.raphimc.noteblocklib.parser;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;

import java.io.File;
import java.util.List;

public abstract class Song {

    private final File sourceFile;
    private final Header header;
    private final Data data;

    public Song(final File sourceFile, final Header header, final Data data) {
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

    public Header getHeader() {
        return this.header;
    }

    public Data getData() {
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

    public abstract List<Note> getNotesAtTick(final int tick);

    public abstract Int2ObjectMap<List<Note>> getNotes();

}
