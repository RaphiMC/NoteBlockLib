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
package net.raphimc.noteblocklib.format.midi.mapping;

import net.raphimc.noteblocklib.format.midi.MidiDefinitions;
import net.raphimc.noteblocklib.format.minecraft.MinecraftInstrument;

// Thanks u3002 for the mappings (https://gist.github.com/u3002/cf4daa83bc82b5917fc86fb23815578a)
public class MidiMappings {

    public static final InstrumentMapping[] INSTRUMENT_MAPPINGS = new InstrumentMapping[MidiDefinitions.KEY_COUNT];
    public static final PercussionMapping[] PERCUSSION_MAPPINGS = new PercussionMapping[MidiDefinitions.KEY_COUNT];

    static {
        INSTRUMENT_MAPPINGS[0] = new InstrumentMapping(MinecraftInstrument.HARP, 0);
        INSTRUMENT_MAPPINGS[1] = new InstrumentMapping(MinecraftInstrument.PLING, 0);
        INSTRUMENT_MAPPINGS[2] = new InstrumentMapping(MinecraftInstrument.PLING, 0);
        INSTRUMENT_MAPPINGS[3] = new InstrumentMapping(MinecraftInstrument.PLING, 0);
        INSTRUMENT_MAPPINGS[4] = new InstrumentMapping(MinecraftInstrument.HARP, 0);
        INSTRUMENT_MAPPINGS[5] = new InstrumentMapping(MinecraftInstrument.HARP, 0);
        INSTRUMENT_MAPPINGS[6] = new InstrumentMapping(MinecraftInstrument.GUITAR, 1);
        INSTRUMENT_MAPPINGS[7] = new InstrumentMapping(MinecraftInstrument.BANJO, 0);
        INSTRUMENT_MAPPINGS[8] = new InstrumentMapping(MinecraftInstrument.BELL, -2);
        INSTRUMENT_MAPPINGS[9] = new InstrumentMapping(MinecraftInstrument.BELL, -2);
        INSTRUMENT_MAPPINGS[10] = new InstrumentMapping(MinecraftInstrument.BELL, -2);
        INSTRUMENT_MAPPINGS[11] = new InstrumentMapping(MinecraftInstrument.IRON_XYLOPHONE, 0);
        INSTRUMENT_MAPPINGS[12] = new InstrumentMapping(MinecraftInstrument.IRON_XYLOPHONE, 0);
        INSTRUMENT_MAPPINGS[13] = new InstrumentMapping(MinecraftInstrument.XYLOPHONE, -2);
        INSTRUMENT_MAPPINGS[14] = new InstrumentMapping(MinecraftInstrument.BELL, -2);
        INSTRUMENT_MAPPINGS[15] = new InstrumentMapping(MinecraftInstrument.GUITAR, 1);
        INSTRUMENT_MAPPINGS[16] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[17] = new InstrumentMapping(MinecraftInstrument.IRON_XYLOPHONE, 0);
        INSTRUMENT_MAPPINGS[18] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[19] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[20] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[21] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[22] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[23] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[24] = new InstrumentMapping(MinecraftInstrument.GUITAR, 1);
        INSTRUMENT_MAPPINGS[25] = new InstrumentMapping(MinecraftInstrument.GUITAR, 1);
        INSTRUMENT_MAPPINGS[26] = new InstrumentMapping(MinecraftInstrument.HARP, 0);
        INSTRUMENT_MAPPINGS[27] = new InstrumentMapping(MinecraftInstrument.GUITAR, 1);
        INSTRUMENT_MAPPINGS[28] = new InstrumentMapping(MinecraftInstrument.BASS, 2);
        INSTRUMENT_MAPPINGS[29] = new InstrumentMapping(MinecraftInstrument.DIDGERIDOO, 2);
        INSTRUMENT_MAPPINGS[30] = new InstrumentMapping(MinecraftInstrument.GUITAR, 1);
        INSTRUMENT_MAPPINGS[31] = new InstrumentMapping(MinecraftInstrument.GUITAR, 3);
        INSTRUMENT_MAPPINGS[32] = new InstrumentMapping(MinecraftInstrument.BASS, 2);
        INSTRUMENT_MAPPINGS[33] = new InstrumentMapping(MinecraftInstrument.BASS, 2);
        INSTRUMENT_MAPPINGS[34] = new InstrumentMapping(MinecraftInstrument.BASS, 2);
        INSTRUMENT_MAPPINGS[35] = new InstrumentMapping(MinecraftInstrument.BASS, 2);
        INSTRUMENT_MAPPINGS[36] = new InstrumentMapping(MinecraftInstrument.GUITAR, 1);
        INSTRUMENT_MAPPINGS[37] = new InstrumentMapping(MinecraftInstrument.GUITAR, 1);
        INSTRUMENT_MAPPINGS[38] = new InstrumentMapping(MinecraftInstrument.BASS, 2);
        INSTRUMENT_MAPPINGS[39] = new InstrumentMapping(MinecraftInstrument.PLING, 0);
        INSTRUMENT_MAPPINGS[40] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[41] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[42] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[43] = new InstrumentMapping(MinecraftInstrument.DIDGERIDOO, 2);
        INSTRUMENT_MAPPINGS[44] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[45] = new InstrumentMapping(MinecraftInstrument.BASS, 2);
        INSTRUMENT_MAPPINGS[46] = new InstrumentMapping(MinecraftInstrument.HARP, 0);
        INSTRUMENT_MAPPINGS[47] = new InstrumentMapping(MinecraftInstrument.SNARE, 0);
        INSTRUMENT_MAPPINGS[48] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[49] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[50] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[51] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[52] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[53] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[54] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[55] = new InstrumentMapping(MinecraftInstrument.SNARE, 0);
        INSTRUMENT_MAPPINGS[56] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[57] = new InstrumentMapping(MinecraftInstrument.DIDGERIDOO, 2);
        INSTRUMENT_MAPPINGS[58] = new InstrumentMapping(MinecraftInstrument.DIDGERIDOO, 2);
        INSTRUMENT_MAPPINGS[59] = new InstrumentMapping(MinecraftInstrument.DIDGERIDOO, 2);
        INSTRUMENT_MAPPINGS[60] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[61] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[62] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[63] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[64] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[65] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[66] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[67] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[68] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[69] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[70] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[71] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[72] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[73] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[74] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[75] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[76] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[77] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[78] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[79] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[80] = new InstrumentMapping(MinecraftInstrument.BIT, 0);
        INSTRUMENT_MAPPINGS[81] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[82] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[83] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[84] = new InstrumentMapping(MinecraftInstrument.GUITAR, 1);
        INSTRUMENT_MAPPINGS[85] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[86] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[87] = new InstrumentMapping(MinecraftInstrument.BASS, 2);
        INSTRUMENT_MAPPINGS[88] = new InstrumentMapping(MinecraftInstrument.BELL, -2);
        INSTRUMENT_MAPPINGS[89] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[90] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[91] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[92] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[93] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[94] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[95] = new InstrumentMapping(MinecraftInstrument.CHIME, -2);
        INSTRUMENT_MAPPINGS[96] = new InstrumentMapping(MinecraftInstrument.CHIME, -2);
        INSTRUMENT_MAPPINGS[97] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[98] = new InstrumentMapping(MinecraftInstrument.CHIME, -2);
        INSTRUMENT_MAPPINGS[99] = new InstrumentMapping(MinecraftInstrument.GUITAR, 1);
        INSTRUMENT_MAPPINGS[100] = new InstrumentMapping(MinecraftInstrument.PLING, 0);
        INSTRUMENT_MAPPINGS[101] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[102] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[103] = new InstrumentMapping(MinecraftInstrument.GUITAR, 1);
        INSTRUMENT_MAPPINGS[104] = new InstrumentMapping(MinecraftInstrument.BANJO, 0);
        INSTRUMENT_MAPPINGS[105] = new InstrumentMapping(MinecraftInstrument.BANJO, 0);
        INSTRUMENT_MAPPINGS[106] = new InstrumentMapping(MinecraftInstrument.BANJO, 0);
        INSTRUMENT_MAPPINGS[107] = new InstrumentMapping(MinecraftInstrument.GUITAR, 1);
        INSTRUMENT_MAPPINGS[108] = new InstrumentMapping(MinecraftInstrument.IRON_XYLOPHONE, 0);
        INSTRUMENT_MAPPINGS[109] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[110] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[111] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[112] = new InstrumentMapping(MinecraftInstrument.CHIME, -2);
        INSTRUMENT_MAPPINGS[113] = new InstrumentMapping(MinecraftInstrument.COW_BELL, -1);
        INSTRUMENT_MAPPINGS[114] = new InstrumentMapping(MinecraftInstrument.IRON_XYLOPHONE, 0);
        INSTRUMENT_MAPPINGS[115] = new InstrumentMapping(MinecraftInstrument.XYLOPHONE, -2);
        INSTRUMENT_MAPPINGS[116] = new InstrumentMapping(MinecraftInstrument.BASS_DRUM, 0);
        INSTRUMENT_MAPPINGS[117] = new InstrumentMapping(MinecraftInstrument.BASS_DRUM, 0);
        INSTRUMENT_MAPPINGS[118] = new InstrumentMapping(MinecraftInstrument.BASS_DRUM, 0);
        INSTRUMENT_MAPPINGS[119] = new InstrumentMapping(MinecraftInstrument.SNARE, 0);
        INSTRUMENT_MAPPINGS[120] = new InstrumentMapping(MinecraftInstrument.HAT, 1);
        INSTRUMENT_MAPPINGS[121] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[122] = new InstrumentMapping(MinecraftInstrument.CHIME, -2);
        INSTRUMENT_MAPPINGS[123] = new InstrumentMapping(MinecraftInstrument.FLUTE, 1);
        INSTRUMENT_MAPPINGS[124] = new InstrumentMapping(MinecraftInstrument.BELL, 2);
        INSTRUMENT_MAPPINGS[125] = new InstrumentMapping(MinecraftInstrument.BASS_DRUM, 0);
        INSTRUMENT_MAPPINGS[126] = new InstrumentMapping(MinecraftInstrument.SNARE, 0);
        INSTRUMENT_MAPPINGS[127] = new InstrumentMapping(MinecraftInstrument.SNARE, 0);

        PERCUSSION_MAPPINGS[24] = new PercussionMapping(MinecraftInstrument.SNARE, 12);
        PERCUSSION_MAPPINGS[25] = new PercussionMapping(MinecraftInstrument.SNARE, 5);
        PERCUSSION_MAPPINGS[26] = new PercussionMapping(MinecraftInstrument.HAT, 18);
        PERCUSSION_MAPPINGS[27] = new PercussionMapping(MinecraftInstrument.SNARE, 2);
        PERCUSSION_MAPPINGS[28] = new PercussionMapping(MinecraftInstrument.SNARE, 9);
        PERCUSSION_MAPPINGS[29] = new PercussionMapping(MinecraftInstrument.HAT, 6);
        PERCUSSION_MAPPINGS[30] = new PercussionMapping(MinecraftInstrument.HAT, 2);
        PERCUSSION_MAPPINGS[31] = new PercussionMapping(MinecraftInstrument.HAT, 13);
        PERCUSSION_MAPPINGS[32] = new PercussionMapping(MinecraftInstrument.HAT, 9);
        PERCUSSION_MAPPINGS[33] = new PercussionMapping(MinecraftInstrument.HAT, 15);
        PERCUSSION_MAPPINGS[34] = new PercussionMapping(MinecraftInstrument.CHIME, 18);
        PERCUSSION_MAPPINGS[35] = new PercussionMapping(MinecraftInstrument.BASS_DRUM, 4);
        PERCUSSION_MAPPINGS[36] = new PercussionMapping(MinecraftInstrument.BASS_DRUM, 8);
        PERCUSSION_MAPPINGS[37] = new PercussionMapping(MinecraftInstrument.HAT, 8);
        PERCUSSION_MAPPINGS[38] = new PercussionMapping(MinecraftInstrument.SNARE, 15);
        PERCUSSION_MAPPINGS[39] = new PercussionMapping(MinecraftInstrument.SNARE, 19);
        PERCUSSION_MAPPINGS[40] = new PercussionMapping(MinecraftInstrument.SNARE, 16);
        PERCUSSION_MAPPINGS[41] = new PercussionMapping(MinecraftInstrument.BASS_DRUM, 6);
        PERCUSSION_MAPPINGS[42] = new PercussionMapping(MinecraftInstrument.HAT, 21);
        PERCUSSION_MAPPINGS[43] = new PercussionMapping(MinecraftInstrument.BASS_DRUM, 9);
        PERCUSSION_MAPPINGS[44] = new PercussionMapping(MinecraftInstrument.HAT, 23);
        PERCUSSION_MAPPINGS[45] = new PercussionMapping(MinecraftInstrument.BASS_DRUM, 14);
        PERCUSSION_MAPPINGS[46] = new PercussionMapping(MinecraftInstrument.SNARE, 22);
        PERCUSSION_MAPPINGS[47] = new PercussionMapping(MinecraftInstrument.BASS_DRUM, 17);
        PERCUSSION_MAPPINGS[48] = new PercussionMapping(MinecraftInstrument.BASS_DRUM, 20);
        PERCUSSION_MAPPINGS[49] = new PercussionMapping(MinecraftInstrument.SNARE, 20);
        PERCUSSION_MAPPINGS[50] = new PercussionMapping(MinecraftInstrument.BASS_DRUM, 23);
        PERCUSSION_MAPPINGS[51] = new PercussionMapping(MinecraftInstrument.SNARE, 17);
        PERCUSSION_MAPPINGS[52] = new PercussionMapping(MinecraftInstrument.SNARE, 14);
        PERCUSSION_MAPPINGS[53] = new PercussionMapping(MinecraftInstrument.BELL, 17);
        PERCUSSION_MAPPINGS[54] = new PercussionMapping(MinecraftInstrument.SNARE, 23);
        PERCUSSION_MAPPINGS[55] = new PercussionMapping(MinecraftInstrument.SNARE, 18);
        PERCUSSION_MAPPINGS[56] = new PercussionMapping(MinecraftInstrument.COW_BELL, 6);
        PERCUSSION_MAPPINGS[57] = new PercussionMapping(MinecraftInstrument.SNARE, 21);
        PERCUSSION_MAPPINGS[58] = new PercussionMapping(MinecraftInstrument.HAT, 17);
        PERCUSSION_MAPPINGS[59] = new PercussionMapping(MinecraftInstrument.SNARE, 24);
        PERCUSSION_MAPPINGS[60] = new PercussionMapping(MinecraftInstrument.COW_BELL, 16);
        PERCUSSION_MAPPINGS[61] = new PercussionMapping(MinecraftInstrument.COW_BELL, 9);
        PERCUSSION_MAPPINGS[62] = new PercussionMapping(MinecraftInstrument.HAT, -3);
        PERCUSSION_MAPPINGS[63] = new PercussionMapping(MinecraftInstrument.COW_BELL, -1);
        PERCUSSION_MAPPINGS[64] = new PercussionMapping(MinecraftInstrument.COW_BELL, -9);
        PERCUSSION_MAPPINGS[65] = new PercussionMapping(MinecraftInstrument.COW_BELL, 5);
        PERCUSSION_MAPPINGS[66] = new PercussionMapping(MinecraftInstrument.COW_BELL, -4);
        PERCUSSION_MAPPINGS[67] = new PercussionMapping(MinecraftInstrument.XYLOPHONE, 12);
        PERCUSSION_MAPPINGS[68] = new PercussionMapping(MinecraftInstrument.XYLOPHONE, 5);
        PERCUSSION_MAPPINGS[69] = new PercussionMapping(MinecraftInstrument.HAT, 35);
        PERCUSSION_MAPPINGS[70] = new PercussionMapping(MinecraftInstrument.HAT, 32);
        PERCUSSION_MAPPINGS[71] = new PercussionMapping(MinecraftInstrument.FLUTE, 34);
        PERCUSSION_MAPPINGS[72] = new PercussionMapping(MinecraftInstrument.FLUTE, 33);
        PERCUSSION_MAPPINGS[73] = new PercussionMapping(MinecraftInstrument.HAT, 19);
        PERCUSSION_MAPPINGS[74] = new PercussionMapping(MinecraftInstrument.HAT, 20);
        PERCUSSION_MAPPINGS[75] = new PercussionMapping(MinecraftInstrument.XYLOPHONE, 19);
        PERCUSSION_MAPPINGS[76] = new PercussionMapping(MinecraftInstrument.XYLOPHONE, 7);
        PERCUSSION_MAPPINGS[77] = new PercussionMapping(MinecraftInstrument.XYLOPHONE, 1);
        PERCUSSION_MAPPINGS[78] = new PercussionMapping(MinecraftInstrument.DIDGERIDOO, 22);
        PERCUSSION_MAPPINGS[79] = new PercussionMapping(MinecraftInstrument.DIDGERIDOO, 13);
        PERCUSSION_MAPPINGS[80] = new PercussionMapping(MinecraftInstrument.BELL, 19);
        PERCUSSION_MAPPINGS[81] = new PercussionMapping(MinecraftInstrument.CHIME, 19);
        PERCUSSION_MAPPINGS[82] = new PercussionMapping(MinecraftInstrument.HAT, 36);
        PERCUSSION_MAPPINGS[83] = new PercussionMapping(MinecraftInstrument.BELL, 21);
        PERCUSSION_MAPPINGS[84] = new PercussionMapping(MinecraftInstrument.CHIME, 17);
        PERCUSSION_MAPPINGS[85] = new PercussionMapping(MinecraftInstrument.XYLOPHONE, 15);
        PERCUSSION_MAPPINGS[86] = new PercussionMapping(MinecraftInstrument.BASS_DRUM, 12);
        PERCUSSION_MAPPINGS[87] = new PercussionMapping(MinecraftInstrument.BASS_DRUM, 7);
        PERCUSSION_MAPPINGS[88] = new PercussionMapping(MinecraftInstrument.SNARE, 10);
    }

}
