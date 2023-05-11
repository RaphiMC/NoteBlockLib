package net.raphimc.noteblocklib.format.midi;

import net.raphimc.noteblocklib.format.midi.data.MidiData;
import net.raphimc.noteblocklib.format.midi.header.MidiHeader;

import javax.sound.midi.InvalidMidiDataException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public class MidiParser {

    public static MidiSong parse(final byte[] bytes, final File sourceFile) throws InvalidMidiDataException, IOException {
        final MidiHeader header = new MidiHeader(new ByteArrayInputStream(bytes));
        final MidiData data = new MidiData(header, new ByteArrayInputStream(bytes));

        return new MidiSong(sourceFile, header, data);
    }

}
