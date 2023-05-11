package net.raphimc.noteblocklib.format.midi.header;

import net.raphimc.noteblocklib.model.Header;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiFileFormat;
import javax.sound.midi.MidiSystem;
import java.io.IOException;
import java.io.InputStream;

public class MidiHeader implements Header {

    private MidiFileFormat midiFileFormat;

    public MidiHeader(final InputStream is) throws IOException, InvalidMidiDataException {
        this.midiFileFormat = MidiSystem.getMidiFileFormat(is);
    }

    public MidiHeader(final MidiFileFormat midiFileFormat) {
        this.midiFileFormat = midiFileFormat;
    }

    public MidiFileFormat getMidiFileFormat() {
        return this.midiFileFormat;
    }

    public void setMidiFileFormat(final MidiFileFormat midiFileFormat) {
        this.midiFileFormat = midiFileFormat;
    }

}
