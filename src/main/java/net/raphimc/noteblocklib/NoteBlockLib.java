package net.raphimc.noteblocklib;

import net.raphimc.noteblocklib.format.future.FutureParser;
import net.raphimc.noteblocklib.format.nbs.NBSParser;
import net.raphimc.noteblocklib.format.txt.TxtParser;
import net.raphimc.noteblocklib.midi.MidiConverter;
import net.raphimc.noteblocklib.model.Song;
import org.apache.commons.io.input.ReaderInputStream;

import java.io.File;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

public class NoteBlockLib {

    public static Song<?, ?, ?> parseSong(final File file) throws Exception {
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
