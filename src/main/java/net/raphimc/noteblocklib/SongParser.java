package net.raphimc.noteblocklib;

import net.raphimc.noteblocklib.midi.MidiConverter;
import net.raphimc.noteblocklib.parser.Song;
import net.raphimc.noteblocklib.parser.future.FutureParser;
import net.raphimc.noteblocklib.parser.nbs.NBSParser;
import net.raphimc.noteblocklib.parser.txt.TxtParser;
import org.apache.commons.io.input.ReaderInputStream;

import java.io.File;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

public class SongParser {

    public static Song parseSong(final File file) throws Exception {
        if (file.getName().toLowerCase().endsWith(".nbs")) {
            return NBSParser.parseFile(file);
        } else if (file.getName().toLowerCase().endsWith(".txt")) {
            return TxtParser.parseFile(file);
        } else if (file.getName().toLowerCase().endsWith(".notebot")) {
            return FutureParser.parseFile(file);
        } else if (file.getName().toLowerCase().endsWith(".mid")) {
            return TxtParser.parse(file, new ReaderInputStream(new StringReader(MidiConverter.readMidi(file)), StandardCharsets.UTF_8));
        } else {
            throw new IllegalStateException("Unknown file type: " + file.getName());
        }
    }

}
