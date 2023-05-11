package net.raphimc.noteblocklib.format.midi;

import com.google.common.io.ByteStreams;
import net.raphimc.noteblocklib.format.midi.data.MidiData;
import net.raphimc.noteblocklib.format.midi.header.MidiHeader;

import javax.sound.midi.InvalidMidiDataException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class MidiParser {

    public static MidiSong parseFile(final File file) throws IOException, InvalidMidiDataException {
        return parse(file, new ByteArrayInputStream(Files.readAllBytes(file.toPath())));
    }

    public static MidiSong parseStream(final InputStream is) throws IOException, InvalidMidiDataException {
        return parse(null, new ByteArrayInputStream(ByteStreams.toByteArray(is)));
    }

    public static MidiSong parse(final File sourceFile, final InputStream is) throws InvalidMidiDataException, IOException {
        final byte[] bytes = ByteStreams.toByteArray(is);
        final MidiHeader header = new MidiHeader(new ByteArrayInputStream(bytes));
        final MidiData data = new MidiData(header, new ByteArrayInputStream(bytes));

        return new MidiSong(sourceFile, header, data);
    }

}
