package net.raphimc.noteblocklib.format.txt.note;

import net.raphimc.noteblocklib.model.Note;
import net.raphimc.noteblocklib.util.Instrument;
import net.raphimc.noteblocklib.util.MinecraftDefinitions;

import java.util.Scanner;

public class TxtNote extends Note {

    public TxtNote(final Scanner scanner) {
        this(scanner.nextByte(), scanner.nextByte());
    }

    public TxtNote(final byte key, final byte instrument) {
        super(instrument, key);
    }

    @Override
    public byte getInstrument() {
        return Instrument.fromMcId(super.getInstrument()).nbsId();
    }

    @Override
    public byte getKey() {
        return (byte) (super.getKey() + MinecraftDefinitions.MC_LOWEST_KEY);
    }

}
