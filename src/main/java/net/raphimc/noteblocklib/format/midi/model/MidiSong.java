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
package net.raphimc.noteblocklib.format.midi.model;

import net.raphimc.noteblocklib.format.SongFormat;
import net.raphimc.noteblocklib.model.Song;

public class MidiSong extends Song {

    public MidiSong() {
        this(null);
    }

    public MidiSong(final String fileName) {
        super(SongFormat.MIDI, fileName);
    }

    @Override
    public MidiSong copy() {
        final MidiSong copySong = new MidiSong(this.getFileName());
        copySong.copyGeneralData(this);

        return copySong;
    }

}
