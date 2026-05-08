/*
 * This file is part of NoteBlockLib - https://github.com/RaphiMC/NoteBlockLib
 * Copyright (C) 2022-2026 RK_01/RaphiMC and contributors
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
package net.raphimc.noteblocklib.format.midi;

import net.raphimc.noteblocklib.format.midi.model.MidiSong;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import java.io.IOException;
import java.io.InputStream;

public class MidiIo {

    public static MidiSong readSong(final InputStream is, final String fileName) throws IOException, InvalidMidiDataException {
        return readSong(is, fileName, true);
    }

    public static MidiSong readSong(final InputStream is, final String fileName, final boolean skipOutOfRangeNotes) throws IOException, InvalidMidiDataException {
        return MidiConverter.createSong(MidiSystem.getSequence(is), fileName, skipOutOfRangeNotes);
    }

    @Deprecated
    public static MidiSong parseSong(final Sequence sequence, final String fileName) {
        return MidiConverter.createSong(sequence, fileName);
    }

    @Deprecated
    public static MidiSong parseSong(final Sequence sequence, final String fileName, final boolean skipOutOfNbsRangeNotes) {
        return MidiConverter.createSong(sequence, fileName, skipOutOfNbsRangeNotes);
    }

}
