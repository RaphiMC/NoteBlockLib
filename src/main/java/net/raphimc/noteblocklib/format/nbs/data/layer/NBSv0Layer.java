package net.raphimc.noteblocklib.format.nbs.data.layer;

import net.raphimc.noteblocklib.format.nbs.note.NBSNote;

import java.util.Map;

public class NBSv0Layer extends NBSLayer {

    private String name;
    private byte volume;

    public NBSv0Layer(final Map<Integer, NBSNote> notesAtTick, final String name, final byte volume) {
        super(notesAtTick);

        this.name = name;
        this.volume = volume;
    }

    /**
     * @return The name of the layer.
     */
    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @return The volume of the layer (percentage). Ranges from 0-100.
     */
    public byte getVolume() {
        return this.volume;
    }

    public void setVolume(final byte volume) {
        this.volume = volume;
    }

}
