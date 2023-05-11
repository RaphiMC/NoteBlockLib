package net.raphimc.noteblocklib;

import net.raphimc.noteblocklib.format.future.FutureParser;
import net.raphimc.noteblocklib.format.midi.MidiParser;
import net.raphimc.noteblocklib.format.nbs.NBSParser;
import net.raphimc.noteblocklib.format.txt.TxtParser;
import net.raphimc.noteblocklib.model.Song;

import java.io.File;

public class NoteBlockLib {

    public static Song<?, ?, ?> parseSong(final File file) throws Exception {
        if (file.getName().toLowerCase().endsWith(".nbs")) {
            return NBSParser.parseFile(file);
        } else if (file.getName().toLowerCase().endsWith(".txt")) {
            return TxtParser.parseFile(file);
        } else if (file.getName().toLowerCase().endsWith(".notebot")) {
            return FutureParser.parseFile(file);
        } else if (file.getName().toLowerCase().endsWith(".mid")) {
            return MidiParser.parseFile(file);
        } else {
            throw new IllegalStateException("Unknown file type: " + file.getName());
        }
    }

}
