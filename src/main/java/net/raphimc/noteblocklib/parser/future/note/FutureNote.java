package net.raphimc.noteblocklib.parser.future.note;

import net.raphimc.noteblocklib.parser.Note;
import net.raphimc.noteblocklib.util.Instrument;

public class FutureNote extends Note {

    public FutureNote(final byte key, final byte instrument) {
        super(instrument, key);
    }

    @Override
    public byte getInstrument() {
        return Instrument.fromMcId(super.getInstrument()).nbsId();
    }

    @Override
    public byte getKey() {
        return (byte) (super.getKey() + 33);
    }

}
