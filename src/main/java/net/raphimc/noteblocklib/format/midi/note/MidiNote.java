package net.raphimc.noteblocklib.format.midi.note;

import net.raphimc.noteblocklib.model.Note;

public class MidiNote extends Note {

    private byte velocity;

    public MidiNote(final byte instrument, final byte key, final byte velocity) {
        super(instrument, key);

        this.velocity = velocity;
    }

    /**
     * @return The velocity/volume of the note, from 0 to 127.
     */
    public byte getVelocity() {
        return this.velocity;
    }

    public void setVelocity(final byte velocity) {
        this.velocity = velocity;
    }

}
