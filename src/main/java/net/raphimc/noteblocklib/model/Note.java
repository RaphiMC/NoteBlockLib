package net.raphimc.noteblocklib.model;

public abstract class Note {

    private byte instrument;
    private byte key;

    public Note(final byte instrument, final byte key) {
        this.instrument = instrument;
        this.key = key;
    }

    /**
     * @return The instrument of the note. Uses the NBS id system. See {@link net.raphimc.noteblocklib.util.Instrument#fromNbsId(byte)}
     */
    public byte getInstrument() {
        return this.instrument;
    }

    public void setInstrument(final byte instrument) {
        this.instrument = instrument;
    }

    /**
     * @return The key of the note, from 0-87, where 0 is A0 and 87 is C8. 33-57 is within the 2-octave limit.
     */
    public byte getKey() {
        return this.key;
    }

    public void setKey(final byte key) {
        this.key = key;
    }

}
