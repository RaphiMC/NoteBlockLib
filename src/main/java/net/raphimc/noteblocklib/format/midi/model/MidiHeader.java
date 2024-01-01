/*
 * This file is part of NoteBlockLib - https://github.com/RaphiMC/NoteBlockLib
 * Copyright (C) 2022-2024 RK_01/RaphiMC and contributors
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
package net.raphimc.noteblocklib.format.midi.model;

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
