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
package net.raphimc.noteblocklib.format.midi.mapping;

import net.raphimc.noteblocklib.util.Instrument;

import java.util.HashMap;
import java.util.Map;

// Thanks u3002 for the mappings (https://gist.github.com/u3002/cf4daa83bc82b5917fc86fb23815578a)
public class MidiMappings {

    public static final Map<Byte, InstrumentMapping> INSTRUMENT_MAPPINGS = new HashMap<>();
    public static final Map<Byte, PercussionMapping> PERCUSSION_MAPPINGS = new HashMap<>();

    static {
        INSTRUMENT_MAPPINGS.put((byte) 0, new InstrumentMapping(Instrument.HARP, (byte) 0));
        INSTRUMENT_MAPPINGS.put((byte) 1, new InstrumentMapping(Instrument.PLING, (byte) 0));
        INSTRUMENT_MAPPINGS.put((byte) 2, new InstrumentMapping(Instrument.PLING, (byte) 0));
        INSTRUMENT_MAPPINGS.put((byte) 3, new InstrumentMapping(Instrument.PLING, (byte) 0));
        INSTRUMENT_MAPPINGS.put((byte) 4, new InstrumentMapping(Instrument.HARP, (byte) 0));
        INSTRUMENT_MAPPINGS.put((byte) 5, new InstrumentMapping(Instrument.HARP, (byte) 0));
        INSTRUMENT_MAPPINGS.put((byte) 6, new InstrumentMapping(Instrument.GUITAR, (byte) 1));
        INSTRUMENT_MAPPINGS.put((byte) 7, new InstrumentMapping(Instrument.BANJO, (byte) 0));
        INSTRUMENT_MAPPINGS.put((byte) 8, new InstrumentMapping(Instrument.BELL, (byte) -2));
        INSTRUMENT_MAPPINGS.put((byte) 9, new InstrumentMapping(Instrument.BELL, (byte) -2));
        INSTRUMENT_MAPPINGS.put((byte) 10, new InstrumentMapping(Instrument.BELL, (byte) -2));
        INSTRUMENT_MAPPINGS.put((byte) 11, new InstrumentMapping(Instrument.IRON_XYLOPHONE, (byte) 0));
        INSTRUMENT_MAPPINGS.put((byte) 12, new InstrumentMapping(Instrument.IRON_XYLOPHONE, (byte) 0));
        INSTRUMENT_MAPPINGS.put((byte) 13, new InstrumentMapping(Instrument.XYLOPHONE, (byte) -2));
        INSTRUMENT_MAPPINGS.put((byte) 14, new InstrumentMapping(Instrument.BELL, (byte) -2));
        INSTRUMENT_MAPPINGS.put((byte) 15, new InstrumentMapping(Instrument.GUITAR, (byte) 1));
        INSTRUMENT_MAPPINGS.put((byte) 16, new InstrumentMapping(Instrument.FLUTE, (byte) -1));
        INSTRUMENT_MAPPINGS.put((byte) 17, new InstrumentMapping(Instrument.IRON_XYLOPHONE, (byte) 0));
        INSTRUMENT_MAPPINGS.put((byte) 18, new InstrumentMapping(Instrument.FLUTE, (byte) -1));
        INSTRUMENT_MAPPINGS.put((byte) 19, new InstrumentMapping(Instrument.FLUTE, (byte) -1));
        INSTRUMENT_MAPPINGS.put((byte) 20, new InstrumentMapping(Instrument.FLUTE, (byte) -1));
        INSTRUMENT_MAPPINGS.put((byte) 21, new InstrumentMapping(Instrument.FLUTE, (byte) -1));
        INSTRUMENT_MAPPINGS.put((byte) 22, new InstrumentMapping(Instrument.FLUTE, (byte) -1));
        INSTRUMENT_MAPPINGS.put((byte) 23, new InstrumentMapping(Instrument.FLUTE, (byte) -1));
        INSTRUMENT_MAPPINGS.put((byte) 24, new InstrumentMapping(Instrument.GUITAR, (byte) 1));
        INSTRUMENT_MAPPINGS.put((byte) 25, new InstrumentMapping(Instrument.GUITAR, (byte) 1));
        INSTRUMENT_MAPPINGS.put((byte) 26, new InstrumentMapping(Instrument.HARP, (byte) 0));
        INSTRUMENT_MAPPINGS.put((byte) 27, new InstrumentMapping(Instrument.GUITAR, (byte) 1));
        INSTRUMENT_MAPPINGS.put((byte) 28, new InstrumentMapping(Instrument.BASS, (byte) 2));
        INSTRUMENT_MAPPINGS.put((byte) 29, new InstrumentMapping(Instrument.DIDGERIDOO, (byte) 2));
        INSTRUMENT_MAPPINGS.put((byte) 30, new InstrumentMapping(Instrument.DIDGERIDOO, (byte) 2));
        INSTRUMENT_MAPPINGS.put((byte) 31, new InstrumentMapping(Instrument.GUITAR, (byte) 3));
        INSTRUMENT_MAPPINGS.put((byte) 32, new InstrumentMapping(Instrument.BASS, (byte) 2));
        INSTRUMENT_MAPPINGS.put((byte) 33, new InstrumentMapping(Instrument.BASS, (byte) 2));
        INSTRUMENT_MAPPINGS.put((byte) 34, new InstrumentMapping(Instrument.BASS, (byte) 2));
        INSTRUMENT_MAPPINGS.put((byte) 35, new InstrumentMapping(Instrument.BASS, (byte) 2));
        INSTRUMENT_MAPPINGS.put((byte) 36, new InstrumentMapping(Instrument.GUITAR, (byte) 1));
        INSTRUMENT_MAPPINGS.put((byte) 37, new InstrumentMapping(Instrument.GUITAR, (byte) 1));
        INSTRUMENT_MAPPINGS.put((byte) 38, new InstrumentMapping(Instrument.BASS, (byte) 2));
        INSTRUMENT_MAPPINGS.put((byte) 39, new InstrumentMapping(Instrument.PLING, (byte) 0));
        INSTRUMENT_MAPPINGS.put((byte) 40, new InstrumentMapping(Instrument.FLUTE, (byte) -1));
        INSTRUMENT_MAPPINGS.put((byte) 41, new InstrumentMapping(Instrument.FLUTE, (byte) -1));
        INSTRUMENT_MAPPINGS.put((byte) 42, new InstrumentMapping(Instrument.FLUTE, (byte) -1));
        INSTRUMENT_MAPPINGS.put((byte) 43, new InstrumentMapping(Instrument.FLUTE, (byte) -1));
        INSTRUMENT_MAPPINGS.put((byte) 44, new InstrumentMapping(Instrument.FLUTE, (byte) -1));
        INSTRUMENT_MAPPINGS.put((byte) 45, new InstrumentMapping(Instrument.BASS, (byte) 2));
        INSTRUMENT_MAPPINGS.put((byte) 46, new InstrumentMapping(Instrument.HARP, (byte) 0));
        INSTRUMENT_MAPPINGS.put((byte) 47, new InstrumentMapping(Instrument.SNARE, (byte) 0));
        INSTRUMENT_MAPPINGS.put((byte) 48, new InstrumentMapping(Instrument.FLUTE, (byte) -1));
        INSTRUMENT_MAPPINGS.put((byte) 49, new InstrumentMapping(Instrument.FLUTE, (byte) -1));
        INSTRUMENT_MAPPINGS.put((byte) 50, new InstrumentMapping(Instrument.FLUTE, (byte) -1));
        INSTRUMENT_MAPPINGS.put((byte) 51, new InstrumentMapping(Instrument.FLUTE, (byte) -1));
        INSTRUMENT_MAPPINGS.put((byte) 52, new InstrumentMapping(Instrument.FLUTE, (byte) -1));
        INSTRUMENT_MAPPINGS.put((byte) 53, new InstrumentMapping(Instrument.FLUTE, (byte) -1));
        INSTRUMENT_MAPPINGS.put((byte) 54, new InstrumentMapping(Instrument.FLUTE, (byte) -1));
        INSTRUMENT_MAPPINGS.put((byte) 55, new InstrumentMapping(Instrument.SNARE, (byte) -1));
        INSTRUMENT_MAPPINGS.put((byte) 56, new InstrumentMapping(Instrument.FLUTE, (byte) -1));
        INSTRUMENT_MAPPINGS.put((byte) 57, new InstrumentMapping(Instrument.FLUTE, (byte) -1));
        INSTRUMENT_MAPPINGS.put((byte) 58, new InstrumentMapping(Instrument.FLUTE, (byte) -1));
        INSTRUMENT_MAPPINGS.put((byte) 59, new InstrumentMapping(Instrument.DIDGERIDOO, (byte) 2));
        INSTRUMENT_MAPPINGS.put((byte) 60, new InstrumentMapping(Instrument.FLUTE, (byte) -1));
        INSTRUMENT_MAPPINGS.put((byte) 61, new InstrumentMapping(Instrument.DIDGERIDOO, (byte) 2));
        INSTRUMENT_MAPPINGS.put((byte) 62, new InstrumentMapping(Instrument.DIDGERIDOO, (byte) 2));
        INSTRUMENT_MAPPINGS.put((byte) 63, new InstrumentMapping(Instrument.FLUTE, (byte) -1));
        INSTRUMENT_MAPPINGS.put((byte) 64, new InstrumentMapping(Instrument.FLUTE, (byte) -1));
        INSTRUMENT_MAPPINGS.put((byte) 65, new InstrumentMapping(Instrument.FLUTE, (byte) -1));
        INSTRUMENT_MAPPINGS.put((byte) 66, new InstrumentMapping(Instrument.FLUTE, (byte) -1));
        INSTRUMENT_MAPPINGS.put((byte) 67, new InstrumentMapping(Instrument.FLUTE, (byte) -1));
        INSTRUMENT_MAPPINGS.put((byte) 68, new InstrumentMapping(Instrument.FLUTE, (byte) -1));
        INSTRUMENT_MAPPINGS.put((byte) 69, new InstrumentMapping(Instrument.FLUTE, (byte) -1));
        INSTRUMENT_MAPPINGS.put((byte) 70, new InstrumentMapping(Instrument.FLUTE, (byte) -1));
        INSTRUMENT_MAPPINGS.put((byte) 71, new InstrumentMapping(Instrument.FLUTE, (byte) -1));
        INSTRUMENT_MAPPINGS.put((byte) 72, new InstrumentMapping(Instrument.FLUTE, (byte) -1));
        INSTRUMENT_MAPPINGS.put((byte) 73, new InstrumentMapping(Instrument.FLUTE, (byte) -1));
        INSTRUMENT_MAPPINGS.put((byte) 74, new InstrumentMapping(Instrument.FLUTE, (byte) -1));
        INSTRUMENT_MAPPINGS.put((byte) 75, new InstrumentMapping(Instrument.FLUTE, (byte) -1));
        INSTRUMENT_MAPPINGS.put((byte) 76, new InstrumentMapping(Instrument.FLUTE, (byte) -1));
        INSTRUMENT_MAPPINGS.put((byte) 77, new InstrumentMapping(Instrument.FLUTE, (byte) -1));
        INSTRUMENT_MAPPINGS.put((byte) 78, new InstrumentMapping(Instrument.FLUTE, (byte) -1));
        INSTRUMENT_MAPPINGS.put((byte) 79, new InstrumentMapping(Instrument.FLUTE, (byte) -1));
        INSTRUMENT_MAPPINGS.put((byte) 80, new InstrumentMapping(Instrument.BIT, (byte) 0));
        INSTRUMENT_MAPPINGS.put((byte) 81, new InstrumentMapping(Instrument.FLUTE, (byte) -1));
        INSTRUMENT_MAPPINGS.put((byte) 82, new InstrumentMapping(Instrument.FLUTE, (byte) -1));
        INSTRUMENT_MAPPINGS.put((byte) 83, new InstrumentMapping(Instrument.FLUTE, (byte) -1));
        INSTRUMENT_MAPPINGS.put((byte) 84, new InstrumentMapping(Instrument.GUITAR, (byte) 1));
        INSTRUMENT_MAPPINGS.put((byte) 85, new InstrumentMapping(Instrument.FLUTE, (byte) -1));
        INSTRUMENT_MAPPINGS.put((byte) 86, new InstrumentMapping(Instrument.FLUTE, (byte) -1));
        INSTRUMENT_MAPPINGS.put((byte) 87, new InstrumentMapping(Instrument.BASS, (byte) 2));
        INSTRUMENT_MAPPINGS.put((byte) 88, new InstrumentMapping(Instrument.BELL, (byte) -2));
        INSTRUMENT_MAPPINGS.put((byte) 89, new InstrumentMapping(Instrument.FLUTE, (byte) -1));
        INSTRUMENT_MAPPINGS.put((byte) 90, new InstrumentMapping(Instrument.FLUTE, (byte) -1));
        INSTRUMENT_MAPPINGS.put((byte) 91, new InstrumentMapping(Instrument.FLUTE, (byte) -1));
        INSTRUMENT_MAPPINGS.put((byte) 92, new InstrumentMapping(Instrument.FLUTE, (byte) -1));
        INSTRUMENT_MAPPINGS.put((byte) 93, new InstrumentMapping(Instrument.FLUTE, (byte) -1));
        INSTRUMENT_MAPPINGS.put((byte) 94, new InstrumentMapping(Instrument.FLUTE, (byte) -1));
        INSTRUMENT_MAPPINGS.put((byte) 95, new InstrumentMapping(Instrument.CHIME, (byte) -2));
        INSTRUMENT_MAPPINGS.put((byte) 96, new InstrumentMapping(Instrument.CHIME, (byte) -2));
        INSTRUMENT_MAPPINGS.put((byte) 97, new InstrumentMapping(Instrument.FLUTE, (byte) -1));
        INSTRUMENT_MAPPINGS.put((byte) 98, new InstrumentMapping(Instrument.CHIME, (byte) -2));
        INSTRUMENT_MAPPINGS.put((byte) 99, new InstrumentMapping(Instrument.GUITAR, (byte) 1));
        INSTRUMENT_MAPPINGS.put((byte) 100, new InstrumentMapping(Instrument.PLING, (byte) 0));
        INSTRUMENT_MAPPINGS.put((byte) 101, new InstrumentMapping(Instrument.FLUTE, (byte) -1));
        INSTRUMENT_MAPPINGS.put((byte) 102, new InstrumentMapping(Instrument.FLUTE, (byte) -1));
        INSTRUMENT_MAPPINGS.put((byte) 103, new InstrumentMapping(Instrument.GUITAR, (byte) 1));
        INSTRUMENT_MAPPINGS.put((byte) 104, new InstrumentMapping(Instrument.BANJO, (byte) 0));
        INSTRUMENT_MAPPINGS.put((byte) 105, new InstrumentMapping(Instrument.BANJO, (byte) 0));
        INSTRUMENT_MAPPINGS.put((byte) 106, new InstrumentMapping(Instrument.BANJO, (byte) 0));
        INSTRUMENT_MAPPINGS.put((byte) 107, new InstrumentMapping(Instrument.GUITAR, (byte) 1));
        INSTRUMENT_MAPPINGS.put((byte) 108, new InstrumentMapping(Instrument.IRON_XYLOPHONE, (byte) 0));
        INSTRUMENT_MAPPINGS.put((byte) 109, new InstrumentMapping(Instrument.FLUTE, (byte) -1));
        INSTRUMENT_MAPPINGS.put((byte) 110, new InstrumentMapping(Instrument.FLUTE, (byte) -1));
        INSTRUMENT_MAPPINGS.put((byte) 111, new InstrumentMapping(Instrument.FLUTE, (byte) -1));
        INSTRUMENT_MAPPINGS.put((byte) 112, new InstrumentMapping(Instrument.CHIME, (byte) -2));
        INSTRUMENT_MAPPINGS.put((byte) 113, new InstrumentMapping(Instrument.COW_BELL, (byte) -1));
        INSTRUMENT_MAPPINGS.put((byte) 114, new InstrumentMapping(Instrument.IRON_XYLOPHONE, (byte) 0));
        INSTRUMENT_MAPPINGS.put((byte) 115, new InstrumentMapping(Instrument.XYLOPHONE, (byte) -2));
        INSTRUMENT_MAPPINGS.put((byte) 116, new InstrumentMapping(Instrument.BASS_DRUM, (byte) 0));
        INSTRUMENT_MAPPINGS.put((byte) 117, new InstrumentMapping(Instrument.SNARE, (byte) 0));
        INSTRUMENT_MAPPINGS.put((byte) 118, new InstrumentMapping(Instrument.SNARE, (byte) 0));
        INSTRUMENT_MAPPINGS.put((byte) 119, new InstrumentMapping(Instrument.CHIME, (byte) -2));
        INSTRUMENT_MAPPINGS.put((byte) 120, new InstrumentMapping(Instrument.HAT, (byte) 1));
        INSTRUMENT_MAPPINGS.put((byte) 121, new InstrumentMapping(Instrument.FLUTE, (byte) -1));
        INSTRUMENT_MAPPINGS.put((byte) 122, new InstrumentMapping(Instrument.CHIME, (byte) -2));
        INSTRUMENT_MAPPINGS.put((byte) 123, new InstrumentMapping(Instrument.FLUTE, (byte) 1));
        INSTRUMENT_MAPPINGS.put((byte) 124, new InstrumentMapping(Instrument.BELL, (byte) 2));
        INSTRUMENT_MAPPINGS.put((byte) 125, new InstrumentMapping(Instrument.BASS_DRUM, (byte) 0));
        INSTRUMENT_MAPPINGS.put((byte) 126, new InstrumentMapping(Instrument.SNARE, (byte) 0));
        INSTRUMENT_MAPPINGS.put((byte) 127, new InstrumentMapping(Instrument.SNARE, (byte) 0));

        PERCUSSION_MAPPINGS.put((byte) 24, new PercussionMapping(Instrument.BIT, (byte) 72));
        PERCUSSION_MAPPINGS.put((byte) 25, new PercussionMapping(Instrument.SNARE, (byte) 41));
        PERCUSSION_MAPPINGS.put((byte) 26, new PercussionMapping(Instrument.HAT, (byte) 58));
        PERCUSSION_MAPPINGS.put((byte) 27, new PercussionMapping(Instrument.SNARE, (byte) 51));
        PERCUSSION_MAPPINGS.put((byte) 28, new PercussionMapping(Instrument.SNARE, (byte) 60));
        PERCUSSION_MAPPINGS.put((byte) 29, new PercussionMapping(Instrument.HAT, (byte) 49));
        PERCUSSION_MAPPINGS.put((byte) 30, new PercussionMapping(Instrument.HAT, (byte) 46));
        PERCUSSION_MAPPINGS.put((byte) 31, new PercussionMapping(Instrument.HAT, (byte) 42));
        PERCUSSION_MAPPINGS.put((byte) 32, new PercussionMapping(Instrument.HAT, (byte) 39));
        PERCUSSION_MAPPINGS.put((byte) 33, new PercussionMapping(Instrument.HAT, (byte) 35));
        PERCUSSION_MAPPINGS.put((byte) 34, new PercussionMapping(Instrument.CHIME, (byte) 50));
        PERCUSSION_MAPPINGS.put((byte) 35, new PercussionMapping(Instrument.BASS_DRUM, (byte) 43));
        PERCUSSION_MAPPINGS.put((byte) 36, new PercussionMapping(Instrument.BASS_DRUM, (byte) 39));
        PERCUSSION_MAPPINGS.put((byte) 37, new PercussionMapping(Instrument.HAT, (byte) 39));
        PERCUSSION_MAPPINGS.put((byte) 38, new PercussionMapping(Instrument.SNARE, (byte) 41));
        PERCUSSION_MAPPINGS.put((byte) 39, new PercussionMapping(Instrument.HAT, (byte) 39));
        PERCUSSION_MAPPINGS.put((byte) 40, new PercussionMapping(Instrument.SNARE, (byte) 37));
        PERCUSSION_MAPPINGS.put((byte) 41, new PercussionMapping(Instrument.BASS_DRUM, (byte) 39));
        PERCUSSION_MAPPINGS.put((byte) 42, new PercussionMapping(Instrument.SNARE, (byte) 55));
        PERCUSSION_MAPPINGS.put((byte) 43, new PercussionMapping(Instrument.BASS_DRUM, (byte) 46));
        PERCUSSION_MAPPINGS.put((byte) 44, new PercussionMapping(Instrument.SNARE, (byte) 55));
        PERCUSSION_MAPPINGS.put((byte) 45, new PercussionMapping(Instrument.BASS_DRUM, (byte) 48));
        PERCUSSION_MAPPINGS.put((byte) 46, new PercussionMapping(Instrument.SNARE, (byte) 51));
        PERCUSSION_MAPPINGS.put((byte) 47, new PercussionMapping(Instrument.BASS_DRUM, (byte) 53));
        PERCUSSION_MAPPINGS.put((byte) 48, new PercussionMapping(Instrument.BASS_DRUM, (byte) 56));
        PERCUSSION_MAPPINGS.put((byte) 49, new PercussionMapping(Instrument.SNARE, (byte) 50));
        PERCUSSION_MAPPINGS.put((byte) 50, new PercussionMapping(Instrument.BASS_DRUM, (byte) 56));
        PERCUSSION_MAPPINGS.put((byte) 51, new PercussionMapping(Instrument.SNARE, (byte) 57));
        PERCUSSION_MAPPINGS.put((byte) 52, new PercussionMapping(Instrument.SNARE, (byte) 41));
        PERCUSSION_MAPPINGS.put((byte) 53, new PercussionMapping(Instrument.SNARE, (byte) 46));
        PERCUSSION_MAPPINGS.put((byte) 54, new PercussionMapping(Instrument.HAT, (byte) 51));
        PERCUSSION_MAPPINGS.put((byte) 55, new PercussionMapping(Instrument.SNARE, (byte) 51));
        PERCUSSION_MAPPINGS.put((byte) 56, new PercussionMapping(Instrument.COW_BELL, (byte) 38));
        PERCUSSION_MAPPINGS.put((byte) 57, new PercussionMapping(Instrument.SNARE, (byte) 46));
        PERCUSSION_MAPPINGS.put((byte) 58, new PercussionMapping(Instrument.HAT, (byte) 35));
        PERCUSSION_MAPPINGS.put((byte) 59, new PercussionMapping(Instrument.SNARE, (byte) 46));
        PERCUSSION_MAPPINGS.put((byte) 60, new PercussionMapping(Instrument.HAT, (byte) 42));
        PERCUSSION_MAPPINGS.put((byte) 61, new PercussionMapping(Instrument.HAT, (byte) 35));
        PERCUSSION_MAPPINGS.put((byte) 62, new PercussionMapping(Instrument.HAT, (byte) 41));
        PERCUSSION_MAPPINGS.put((byte) 63, new PercussionMapping(Instrument.BASS_DRUM, (byte) 55));
        PERCUSSION_MAPPINGS.put((byte) 64, new PercussionMapping(Instrument.BASS_DRUM, (byte) 48));
        PERCUSSION_MAPPINGS.put((byte) 65, new PercussionMapping(Instrument.SNARE, (byte) 46));
        PERCUSSION_MAPPINGS.put((byte) 66, new PercussionMapping(Instrument.SNARE, (byte) 41));
        PERCUSSION_MAPPINGS.put((byte) 67, new PercussionMapping(Instrument.XYLOPHONE, (byte) 45));
        PERCUSSION_MAPPINGS.put((byte) 68, new PercussionMapping(Instrument.XYLOPHONE, (byte) 38));
        PERCUSSION_MAPPINGS.put((byte) 69, new PercussionMapping(Instrument.HAT, (byte) 53));
        PERCUSSION_MAPPINGS.put((byte) 70, new PercussionMapping(Instrument.HAT, (byte) 56));
        PERCUSSION_MAPPINGS.put((byte) 71, new PercussionMapping(Instrument.FLUTE, (byte) 67));
        PERCUSSION_MAPPINGS.put((byte) 72, new PercussionMapping(Instrument.FLUTE, (byte) 66));
        PERCUSSION_MAPPINGS.put((byte) 73, new PercussionMapping(Instrument.HAT, (byte) 50));
        PERCUSSION_MAPPINGS.put((byte) 74, new PercussionMapping(Instrument.HAT, (byte) 44));
        PERCUSSION_MAPPINGS.put((byte) 75, new PercussionMapping(Instrument.HAT, (byte) 51));
        PERCUSSION_MAPPINGS.put((byte) 76, new PercussionMapping(Instrument.HAT, (byte) 43));
        PERCUSSION_MAPPINGS.put((byte) 77, new PercussionMapping(Instrument.HAT, (byte) 38));
        PERCUSSION_MAPPINGS.put((byte) 78, new PercussionMapping(Instrument.DIDGERIDOO, (byte) 58));
        PERCUSSION_MAPPINGS.put((byte) 79, new PercussionMapping(Instrument.DIDGERIDOO, (byte) 59));
        PERCUSSION_MAPPINGS.put((byte) 80, new PercussionMapping(Instrument.HAT, (byte) 49));
        PERCUSSION_MAPPINGS.put((byte) 81, new PercussionMapping(Instrument.CHIME, (byte) 52));
        PERCUSSION_MAPPINGS.put((byte) 82, new PercussionMapping(Instrument.SNARE, (byte) 55));
        PERCUSSION_MAPPINGS.put((byte) 83, new PercussionMapping(Instrument.CHIME, (byte) 39));
        PERCUSSION_MAPPINGS.put((byte) 84, new PercussionMapping(Instrument.CHIME, (byte) 48));
        PERCUSSION_MAPPINGS.put((byte) 85, new PercussionMapping(Instrument.HAT, (byte) 54));
        PERCUSSION_MAPPINGS.put((byte) 86, new PercussionMapping(Instrument.BASS_DRUM, (byte) 47));
        PERCUSSION_MAPPINGS.put((byte) 87, new PercussionMapping(Instrument.BASS_DRUM, (byte) 40));
    }

}
