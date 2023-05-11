package net.raphimc.noteblocklib;

import com.google.common.io.ByteStreams;
import net.raphimc.noteblocklib.format.SongFormat;
import net.raphimc.noteblocklib.format.future.FutureParser;
import net.raphimc.noteblocklib.format.midi.MidiParser;
import net.raphimc.noteblocklib.format.nbs.NBSParser;
import net.raphimc.noteblocklib.format.txt.TxtParser;
import net.raphimc.noteblocklib.model.Song;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;

public class NoteBlockLib {

    public static Song<?, ?, ?> parseSong(final File file) throws Exception {
        return parseSong(file, getFormat(file));
    }

    public static Song<?, ?, ?> parseSong(final File file, final SongFormat format) throws Exception {
        return parseSong(Files.readAllBytes(file.toPath()), format, file);
    }

    public static Song<?, ?, ?> parseSong(final InputStream is, final SongFormat format) throws Exception {
        return parseSong(ByteStreams.toByteArray(is), format, null);
    }

    public static Song<?, ?, ?> parseSong(final byte[] bytes, final SongFormat format) throws Exception {
        return parseSong(bytes, format, null);
    }

    public static Song<?, ?, ?> parseSong(final byte[] bytes, final SongFormat format, final File sourceFile) throws Exception {
        try {
            switch (format) {
                case NBS:
                    return NBSParser.parse(bytes, sourceFile);
                case TXT:
                    return TxtParser.parse(bytes, sourceFile);
                case FUTURE:
                    return FutureParser.parse(bytes, sourceFile);
                case MIDI:
                    return MidiParser.parse(bytes, sourceFile);
                default:
                    throw new IllegalStateException("Unknown format: " + format);
            }
        } catch (Throwable e) {
            throw new Exception("Failed to parse song", e);
        }
    }

    public static SongFormat getFormat(final File file) {
        final String name = file.getName().toLowerCase();
        if (name.endsWith(".nbs")) {
            return SongFormat.NBS;
        } else if (name.endsWith(".txt")) {
            return SongFormat.TXT;
        } else if (name.endsWith(".notebot")) {
            return SongFormat.FUTURE;
        } else if (name.endsWith(".mid")) {
            return SongFormat.MIDI;
        } else {
            throw new IllegalStateException("Unknown file type: " + file.getName());
        }
    }

}
