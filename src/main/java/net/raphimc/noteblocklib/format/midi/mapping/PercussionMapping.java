package net.raphimc.noteblocklib.format.midi.mapping;

import net.raphimc.noteblocklib.util.Instrument;

public class PercussionMapping {

    private final Instrument instrument;
    private final byte key;

    public PercussionMapping(final Instrument instrument, final byte key) {
        this.instrument = instrument;
        this.key = key;
    }

    public Instrument getInstrument() {
        return this.instrument;
    }

    public byte getKey() {
        return this.key;
    }

}
