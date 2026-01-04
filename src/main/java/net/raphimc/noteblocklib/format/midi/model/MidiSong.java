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
package net.raphimc.noteblocklib.format.midi.model;

import net.raphimc.noteblocklib.format.SongFormat;
import net.raphimc.noteblocklib.model.song.Song;

import java.util.HashMap;
import java.util.Map;

public class MidiSong extends Song {

    private final Map<Integer, String> trackNames = new HashMap<>();

    public MidiSong() {
        this(null);
    }

    public MidiSong(final String fileName) {
        super(SongFormat.MIDI, fileName);
    }

    public Map<Integer, String> getTrackNames() {
        return this.trackNames;
    }

    @Override
    public MidiSong copy() {
        final MidiSong copySong = new MidiSong(this.getFileName());
        copySong.copyGeneralData(this);
        copySong.getTrackNames().putAll(this.getTrackNames());
        return copySong;
    }

}
