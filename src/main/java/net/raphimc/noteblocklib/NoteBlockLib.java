/*
 * This file is part of NoteBlockLib - https://github.com/RaphiMC/NoteBlockLib
 * Copyright (C) 2022-2025 RK_01/RaphiMC and contributors
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
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

import net.raphimc.noteblocklib.format.SongFormat;
import net.raphimc.noteblocklib.format.futureclient.FutureClientIo;
import net.raphimc.noteblocklib.format.mcsp.McSpIo;
import net.raphimc.noteblocklib.format.mcsp2.McSp2Converter;
import net.raphimc.noteblocklib.format.mcsp2.McSp2Io;
import net.raphimc.noteblocklib.format.mcsp2.model.McSp2Song;
import net.raphimc.noteblocklib.format.midi.MidiIo;
import net.raphimc.noteblocklib.format.nbs.NbsConverter;
import net.raphimc.noteblocklib.format.nbs.NbsIo;
import net.raphimc.noteblocklib.format.nbs.model.NbsSong;
import net.raphimc.noteblocklib.format.txt.TxtConverter;
import net.raphimc.noteblocklib.format.txt.TxtIo;
import net.raphimc.noteblocklib.format.txt.model.TxtSong;
import net.raphimc.noteblocklib.model.Song;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.google.common.io.Files.getFileExtension;

public class NoteBlockLib {

    public static Song readSong(final File file) throws Exception {
        return readSong(file.toPath());
    }

    public static Song readSong(final Path path) throws Exception {
        return readSong(path, getFormat(path));
    }

    public static Song readSong(final Path path, final SongFormat format) throws Exception {
        return readSong(Files.newInputStream(path), format, path.getFileName().toString());
    }

    public static Song readSong(final byte[] bytes, final SongFormat format) throws Exception {
        return readSong(new ByteArrayInputStream(bytes), format);
    }

    public static Song readSong(final InputStream is, final SongFormat format) throws Exception {
        return readSong(is, format, null);
    }

    public static Song readSong(final InputStream is, final SongFormat format, final String fileName) throws Exception {
        try {
            switch (format) {
                case NBS:
                    return NbsIo.readSong(is, fileName);
                case MCSP:
                    return McSpIo.readSong(is, fileName);
                case MCSP2:
                    return McSp2Io.readSong(is, fileName);
                case TXT:
                    return TxtIo.readSong(is, fileName);
                case FUTURE_CLIENT:
                    return FutureClientIo.readSong(is, fileName);
                case MIDI:
                    return MidiIo.readSong(is, fileName);
                default:
                    throw new IllegalStateException("Unknown format");
            }
        } catch (Throwable e) {
            throw new Exception("Failed to read song", e);
        } finally {
            is.close();
        }
    }

    public static void writeSong(final Song song, final File file) throws Exception {
        writeSong(song, file.toPath());
    }

    public static void writeSong(final Song song, final Path path) throws Exception {
        writeSong(song, Files.newOutputStream(path));
    }

    public static void writeSong(final Song song, final OutputStream os) throws Exception {
        try {
            if (song instanceof NbsSong) {
                NbsIo.writeSong((NbsSong) song, os);
            } else if (song instanceof McSp2Song) {
                McSp2Io.writeSong((McSp2Song) song, os);
            } else if (song instanceof TxtSong) {
                TxtIo.writeSong((TxtSong) song, os);
            } else {
                throw new Exception("Unsupported song format for writing: " + song.getClass().getSimpleName());
            }
        } catch (Throwable e) {
            throw new Exception("Failed to write song", e);
        } finally {
            os.close();
        }
    }

    public static Song convertSong(final Song song, final SongFormat targetFormat) {
        switch (targetFormat) {
            case NBS:
                return NbsConverter.createSong(song);
            case MCSP2:
                return McSp2Converter.createSong(song);
            case TXT:
                return TxtConverter.createSong(song);
            default:
                throw new IllegalStateException("Unsupported target format: " + targetFormat);
        }
    }

    public static SongFormat getFormat(final Path path) {
        return SongFormat.getByExtension(getFileExtension(path.getFileName().toString()));
    }

}
