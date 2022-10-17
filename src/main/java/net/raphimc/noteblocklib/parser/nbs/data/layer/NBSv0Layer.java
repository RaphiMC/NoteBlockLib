package net.raphimc.noteblocklib.parser.nbs.data.layer;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.raphimc.noteblocklib.parser.nbs.note.NBSNote;

public class NBSv0Layer extends NBSLayer {

    private String name;
    private byte volume;

    public NBSv0Layer(final Int2ObjectMap<NBSNote> notesAtTick, final String name, final byte volume) {
        super(notesAtTick);
        this.name = name;
        this.volume = volume;
    }

    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @return The name of the layer.
     */
    public String getName() {
        return this.name;
    }

    public void setVolume(final byte volume) {
        this.volume = volume;
    }

    /**
     * @return The volume of the layer (percentage). Ranges from 0-100.
     */
    public byte getVolume() {
        return this.volume;
    }

}
