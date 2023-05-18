/*
 * This file is part of NoteBlockLib - https://github.com/RaphiMC/NoteBlockLib
 * Copyright (C) 2023 RK_01/RaphiMC and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.raphimc.noteblocklib;

import com.google.common.io.ByteStreams;
import net.raphimc.noteblocklib.format.SongFormat;
import net.raphimc.noteblocklib.format.future.FutureParser;
import net.raphimc.noteblocklib.format.midi.MidiParser;
import net.raphimc.noteblocklib.format.nbs.NbsParser;
import net.raphimc.noteblocklib.format.nbs.NbsSong;
import net.raphimc.noteblocklib.format.nbs.model.NbsData;
import net.raphimc.noteblocklib.format.nbs.model.NbsHeader;
import net.raphimc.noteblocklib.format.txt.TxtParser;
import net.raphimc.noteblocklib.format.txt.TxtSong;
import net.raphimc.noteblocklib.model.Song;
import net.raphimc.noteblocklib.model.SongView;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

public class NoteBlockLib {

    public static Song<?, ?, ?> readSong(final File file) throws Exception {
        return readSong(file, getFormat(file));
    }

    public static Song<?, ?, ?> readSong(final File file, final SongFormat format) throws Exception {
        return readSong(Files.readAllBytes(file.toPath()), format, file);
    }

    public static Song<?, ?, ?> readSong(final InputStream is, final SongFormat format) throws Exception {
        return readSong(ByteStreams.toByteArray(is), format, null);
    }

    public static Song<?, ?, ?> readSong(final byte[] bytes, final SongFormat format) throws Exception {
        return readSong(bytes, format, null);
    }

    public static Song<?, ?, ?> readSong(final byte[] bytes, final SongFormat format, final File sourceFile) throws Exception {
        try {
            switch (format) {
                case NBS:
                    return NbsParser.read(bytes, sourceFile);
                case TXT:
                    return TxtParser.read(bytes, sourceFile);
                case FUTURE:
                    return FutureParser.read(bytes, sourceFile);
                case MIDI:
                    return MidiParser.read(bytes, sourceFile);
                default:
                    throw new IllegalStateException("Unknown format: " + format);
            }
        } catch (Throwable e) {
            throw new Exception("Failed to read song", e);
        }
    }

    public static void writeSong(final Song<?, ?, ?> song, final File file) throws Exception {
        Files.write(file.toPath(), writeSong(song));
    }

    public static void writeSong(final Song<?, ?, ?> song, final OutputStream os) throws Exception {
        os.write(writeSong(song));
    }

    public static byte[] writeSong(final Song<?, ?, ?> song) throws Exception {
        byte[] bytes = null;
        try {
            if (song instanceof NbsSong) {
                bytes = NbsParser.write((NbsSong) song);
            } else if (song instanceof TxtSong) {
                bytes = TxtParser.write((TxtSong) song);
            }
        } catch (Throwable e) {
            throw new Exception("Failed to write song", e);
        }

        if (bytes == null) {
            throw new Exception("Unsupported song type for writing: " + song.getClass().getSimpleName());
        }

        return bytes;
    }

    public static Song<?, ?, ?> createSongFromView(final SongView<?> songView, final SongFormat format) {
        if (format != SongFormat.NBS) {
            throw new IllegalArgumentException("Only NBS is supported for creating songs from views");
        }

        return new NbsSong(null, new NbsHeader(songView), new NbsData(songView));
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
