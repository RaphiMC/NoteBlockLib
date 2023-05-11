package net.raphimc.noteblocklib.format.midi.mapping;

import net.raphimc.noteblocklib.util.Instrument;

public class InstrumentMapping {

    private final Instrument instrument;
    private final int octaveModifier;

    public InstrumentMapping(final Instrument instrument, final int octaveModifier) {
        this.instrument = instrument;
        this.octaveModifier = octaveModifier;
    }

    public Instrument getInstrument() {
        return this.instrument;
    }

    public int getOctaveModifier() {
        return this.octaveModifier;
    }

}
