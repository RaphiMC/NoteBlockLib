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
package net.raphimc.noteblocklib.format.midi;

import net.raphimc.noteblocklib.format.midi.model.MidiData;
import net.raphimc.noteblocklib.format.midi.model.MidiHeader;
import net.raphimc.noteblocklib.format.midi.model.MidiNote;
import net.raphimc.noteblocklib.model.Song;
import net.raphimc.noteblocklib.model.SongView;

import java.io.File;

import static net.raphimc.noteblocklib.format.midi.MidiDefinitions.SONG_TICKS_PER_SECOND;

public class MidiSong extends Song<MidiHeader, MidiData, MidiNote> {

    public MidiSong(final File sourceFile, final MidiHeader header, final MidiData data) {
        super(sourceFile, header, data);
    }

    @Override
    protected SongView<MidiNote> createView() {
        final String midiTitle = (String) this.getHeader().getMidiFileFormat().getProperty("title");
        final String title = midiTitle == null || midiTitle.isEmpty() ? this.getSourceFile() == null ? "MIDI Song" : this.getSourceFile().getName() : midiTitle;

        return new SongView<>(title, SONG_TICKS_PER_SECOND, this.getData().getNotes());
    }

}
