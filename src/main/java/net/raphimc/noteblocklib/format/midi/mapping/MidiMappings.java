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
// Also credit to OpenNoteBlockStudio (https://github.com/OpenNBS/NoteBlockStudio/blob/development/scripts/midi_instruments/midi_instruments.gml)
public class MidiMappings {

    public static final InstrumentMapping[] INSTRUMENT_MAPPINGS = new InstrumentMapping[MidiDefinitions.KEY_COUNT];
    public static final PercussionMapping[] PERCUSSION_MAPPINGS = new PercussionMapping[MidiDefinitions.KEY_COUNT];

    static {
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_ACOUSTIC_GRAND_PIANO] = new InstrumentMapping(MinecraftInstrument.HARP, 0);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_BRIGHT_ACOUSTIC_PIANO] = new InstrumentMapping(MinecraftInstrument.PLING, 0);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_ELECTRIC_GRAND_PIANO] = new InstrumentMapping(MinecraftInstrument.PLING, 0);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_HONKY_TONK_PIANO] = new InstrumentMapping(MinecraftInstrument.PLING, 0);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_ELECTRIC_PIANO_1] = new InstrumentMapping(MinecraftInstrument.HARP, 0);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_ELECTRIC_PIANO_2] = new InstrumentMapping(MinecraftInstrument.HARP, 0);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_HARPSICHORD] = new InstrumentMapping(MinecraftInstrument.GUITAR, 1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_CLAVI] = new InstrumentMapping(MinecraftInstrument.BANJO, 0);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_CELESTA] = new InstrumentMapping(MinecraftInstrument.BELL, -2);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_GLOCKENSPIEL] = new InstrumentMapping(MinecraftInstrument.BELL, -2);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_MUSIC_BOX] = new InstrumentMapping(MinecraftInstrument.BELL, -2);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_VIBRAPHONE] = new InstrumentMapping(MinecraftInstrument.IRON_XYLOPHONE, 0);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_MARIMBA] = new InstrumentMapping(MinecraftInstrument.IRON_XYLOPHONE, 0);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_XYLOPHONE] = new InstrumentMapping(MinecraftInstrument.XYLOPHONE, -2);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_TUBULAR_BELLS] = new InstrumentMapping(MinecraftInstrument.BELL, -2);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_DULCIMER] = new InstrumentMapping(MinecraftInstrument.GUITAR, 1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_DRAWBAR_ORGAN] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_PERCUSSIVE_ORGAN] = new InstrumentMapping(MinecraftInstrument.IRON_XYLOPHONE, 0);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_ROCK_ORGAN] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_CHURCH_ORGAN] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_REED_ORGAN] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_ACCORDION] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_HARMONICA] = new InstrumentMapping(MinecraftInstrument.TRUMPET, 0);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_TANGO_ACCORDION] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_ACOUSTIC_GUITAR_NYLON] = new InstrumentMapping(MinecraftInstrument.GUITAR, 1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_ACOUSTIC_GUITAR_STEEL] = new InstrumentMapping(MinecraftInstrument.GUITAR, 1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_ELECTRIC_GUITAR_JAZZ] = new InstrumentMapping(MinecraftInstrument.HARP, 0);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_ELECTRIC_GUITAR_CLEAN] = new InstrumentMapping(MinecraftInstrument.GUITAR, 1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_ELECTRIC_GUITAR_MUTED] = new InstrumentMapping(MinecraftInstrument.BASS, 2);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_OVERDRIVEN_GUITAR] = new InstrumentMapping(MinecraftInstrument.DIDGERIDOO, 2);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_DISTORTION_GUITAR] = new InstrumentMapping(MinecraftInstrument.GUITAR, 1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_GUITAR_HARMONICS] = new InstrumentMapping(MinecraftInstrument.GUITAR, 3);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_ACOUSTIC_BASS] = new InstrumentMapping(MinecraftInstrument.BASS, 2);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_ELECTRIC_BASS_FINGER] = new InstrumentMapping(MinecraftInstrument.BASS, 2);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_ELECTRIC_BASS_PICK] = new InstrumentMapping(MinecraftInstrument.BASS, 2);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_FRETLESS_BASS] = new InstrumentMapping(MinecraftInstrument.BASS, 2);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_SLAP_BASS_1] = new InstrumentMapping(MinecraftInstrument.GUITAR, 1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_SLAP_BASS_2] = new InstrumentMapping(MinecraftInstrument.GUITAR, 1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_SYNTH_BASS_1] = new InstrumentMapping(MinecraftInstrument.BASS, 2);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_SYNTH_BASS_2] = new InstrumentMapping(MinecraftInstrument.PLING, 0);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_VIOLIN] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_VIOLA] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_CELLO] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_CONTRABASS] = new InstrumentMapping(MinecraftInstrument.DIDGERIDOO, 2);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_TREMOLO_STRINGS] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_PIZZICATO_STRINGS] = new InstrumentMapping(MinecraftInstrument.BASS, 2);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_ORCHESTRAL_HARP] = new InstrumentMapping(MinecraftInstrument.HARP, 0);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_TIMPANI] = new InstrumentMapping(MinecraftInstrument.SNARE, 0);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_STRING_ENSEMBLE_1] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_STRING_ENSEMBLE_2] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_SYNTH_STRINGS_1] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_SYNTH_STRINGS_2] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_CHOIR_AAHS] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_VOICE_OOHS] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_SYNTH_VOICE] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_ORCHESTRA_HIT] = new InstrumentMapping(MinecraftInstrument.SNARE, 0);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_TRUMPET] = new InstrumentMapping(MinecraftInstrument.TRUMPET, 0);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_TROMBONE] = new InstrumentMapping(MinecraftInstrument.TRUMPET_EXPOSED, 0);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_TUBA] = new InstrumentMapping(MinecraftInstrument.TRUMPET_WEATHERED, 2);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_MUTED_TRUMPET] = new InstrumentMapping(MinecraftInstrument.TRUMPET_OXIDIZED, 1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_FRENCH_HORN] = new InstrumentMapping(MinecraftInstrument.TRUMPET_EXPOSED, 0);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_BRASS_SECTION] = new InstrumentMapping(MinecraftInstrument.TRUMPET_EXPOSED, 0);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_SYNTH_BRASS_1] = new InstrumentMapping(MinecraftInstrument.TRUMPET_EXPOSED, 0);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_SYNTH_BRASS_2] = new InstrumentMapping(MinecraftInstrument.TRUMPET, 0);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_SOPRANO_SAX] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_ALTO_SAX] = new InstrumentMapping(MinecraftInstrument.TRUMPET, 0);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_TENOR_SAX] = new InstrumentMapping(MinecraftInstrument.TRUMPET, 0);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_BARITONE_SAX] = new InstrumentMapping(MinecraftInstrument.TRUMPET_EXPOSED, 0);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_OBOE] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_ENGLISH_HORN] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_BASSOON] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_CLARINET] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_PICCOLO] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_FLUTE] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_RECORDER] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_PAN_FLUTE] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_BLOWN_BOTTLE] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_SHAKUHACHI] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_WHISTLE] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_OCARINA] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_LEAD_1_SQUARE] = new InstrumentMapping(MinecraftInstrument.BIT, 0);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_LEAD_2_SAWTOOTH] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_LEAD_3_CALLIOPE] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_LEAD_4_CHIFF] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_LEAD_5_CHARANG] = new InstrumentMapping(MinecraftInstrument.GUITAR, 1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_LEAD_6_VOICE] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_LEAD_7_FIFTHS] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_LEAD_8_BASS_PLUS_LEAD] = new InstrumentMapping(MinecraftInstrument.BASS, 2);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_PAD_1_NEW_AGE] = new InstrumentMapping(MinecraftInstrument.BELL, -2);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_PAD_2_WARM] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_PAD_3_POLYSYNTH] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_PAD_4_CHOIR] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_PAD_5_BOWED] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_PAD_6_METALLIC] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_PAD_7_HALO] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_PAD_8_SWEEP] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_FX_1_RAIN] = new InstrumentMapping(MinecraftInstrument.CHIME, -2);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_FX_2_SOUNDTRACK] = new InstrumentMapping(MinecraftInstrument.CHIME, -2);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_FX_3_CRYSTAL] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_FX_4_ATMOSPHERE] = new InstrumentMapping(MinecraftInstrument.CHIME, -2);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_FX_5_BRIGHTNESS] = new InstrumentMapping(MinecraftInstrument.GUITAR, 1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_FX_6_GOBLINS] = new InstrumentMapping(MinecraftInstrument.PLING, 0);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_FX_7_ECHOES] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_FX_8_SCI_FI] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_SITAR] = new InstrumentMapping(MinecraftInstrument.GUITAR, 1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_BANJO] = new InstrumentMapping(MinecraftInstrument.BANJO, 0);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_SHAMISEN] = new InstrumentMapping(MinecraftInstrument.BANJO, 0);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_KOTO] = new InstrumentMapping(MinecraftInstrument.BANJO, 0);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_KALIMBA] = new InstrumentMapping(MinecraftInstrument.GUITAR, 1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_BAG_PIPE] = new InstrumentMapping(MinecraftInstrument.IRON_XYLOPHONE, 0);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_FIDDLE] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_SHANAI] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_TINKLE_BELL] = new InstrumentMapping(MinecraftInstrument.CHIME, -2);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_AGOGO] = new InstrumentMapping(MinecraftInstrument.COW_BELL, -1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_STEEL_DRUMS] = new InstrumentMapping(MinecraftInstrument.IRON_XYLOPHONE, 0);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_WOODBLOCK] = new InstrumentMapping(MinecraftInstrument.XYLOPHONE, -2);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_TAIKO_DRUM] = new InstrumentMapping(MinecraftInstrument.BASS_DRUM, 0);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_MELODIC_TOM] = new InstrumentMapping(MinecraftInstrument.BASS_DRUM, 0);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_SYNTH_DRUM] = new InstrumentMapping(MinecraftInstrument.BASS_DRUM, 0);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_REVERSE_CYMBAL] = new InstrumentMapping(MinecraftInstrument.SNARE, 0);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_GUITAR_FRET_NOISE] = new InstrumentMapping(MinecraftInstrument.HAT, 1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_BREATH_NOISE] = new InstrumentMapping(MinecraftInstrument.FLUTE, -1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_SEASHORE] = new InstrumentMapping(MinecraftInstrument.CHIME, -2);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_BIRD_TWEET] = new InstrumentMapping(MinecraftInstrument.FLUTE, 1);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_TELEPHONE_RING] = new InstrumentMapping(MinecraftInstrument.BELL, 2);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_HELICOPTER] = new InstrumentMapping(MinecraftInstrument.BASS_DRUM, 0);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_APPLAUSE] = new InstrumentMapping(MinecraftInstrument.SNARE, 0);
        INSTRUMENT_MAPPINGS[MidiDefinitions.INSTRUMENT_GUNSHOT] = new InstrumentMapping(MinecraftInstrument.SNARE, 0);

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
        PERCUSSION_MAPPINGS[MidiDefinitions.PERCUSSION_ACOUSTIC_BASS_DRUM] = new PercussionMapping(MinecraftInstrument.BASS_DRUM, 4);
        PERCUSSION_MAPPINGS[MidiDefinitions.PERCUSSION_BASS_DRUM_1] = new PercussionMapping(MinecraftInstrument.BASS_DRUM, 8);
        PERCUSSION_MAPPINGS[MidiDefinitions.PERCUSSION_SIDE_STICK] = new PercussionMapping(MinecraftInstrument.HAT, 8);
        PERCUSSION_MAPPINGS[MidiDefinitions.PERCUSSION_ACOUSTIC_SNARE] = new PercussionMapping(MinecraftInstrument.SNARE, 15);
        PERCUSSION_MAPPINGS[MidiDefinitions.PERCUSSION_HAND_CLAP] = new PercussionMapping(MinecraftInstrument.SNARE, 19);
        PERCUSSION_MAPPINGS[MidiDefinitions.PERCUSSION_ELECTRIC_SNARE] = new PercussionMapping(MinecraftInstrument.SNARE, 16);
        PERCUSSION_MAPPINGS[MidiDefinitions.PERCUSSION_LOW_FLOOR_TOM] = new PercussionMapping(MinecraftInstrument.BASS_DRUM, 6);
        PERCUSSION_MAPPINGS[MidiDefinitions.PERCUSSION_CLOSED_HI_HAT] = new PercussionMapping(MinecraftInstrument.HAT, 21);
        PERCUSSION_MAPPINGS[MidiDefinitions.PERCUSSION_HIGH_FLOOR_TOM] = new PercussionMapping(MinecraftInstrument.BASS_DRUM, 9);
        PERCUSSION_MAPPINGS[MidiDefinitions.PERCUSSION_PEDAL_HI_HAT] = new PercussionMapping(MinecraftInstrument.HAT, 23);
        PERCUSSION_MAPPINGS[MidiDefinitions.PERCUSSION_LOW_TOM] = new PercussionMapping(MinecraftInstrument.BASS_DRUM, 14);
        PERCUSSION_MAPPINGS[MidiDefinitions.PERCUSSION_OPEN_HI_HAT] = new PercussionMapping(MinecraftInstrument.SNARE, 22);
        PERCUSSION_MAPPINGS[MidiDefinitions.PERCUSSION_LOW_MID_TOM] = new PercussionMapping(MinecraftInstrument.BASS_DRUM, 17);
        PERCUSSION_MAPPINGS[MidiDefinitions.PERCUSSION_HI_MID_TOM] = new PercussionMapping(MinecraftInstrument.BASS_DRUM, 20);
        PERCUSSION_MAPPINGS[MidiDefinitions.PERCUSSION_CRASH_CYMBAL_1] = new PercussionMapping(MinecraftInstrument.SNARE, 20);
        PERCUSSION_MAPPINGS[MidiDefinitions.PERCUSSION_HIGH_TOM] = new PercussionMapping(MinecraftInstrument.BASS_DRUM, 23);
        PERCUSSION_MAPPINGS[MidiDefinitions.PERCUSSION_RIDE_CYMBAL_1] = new PercussionMapping(MinecraftInstrument.SNARE, 17);
        PERCUSSION_MAPPINGS[MidiDefinitions.PERCUSSION_CHINESE_CYMBAL] = new PercussionMapping(MinecraftInstrument.SNARE, 14);
        PERCUSSION_MAPPINGS[MidiDefinitions.PERCUSSION_RIDE_BELL] = new PercussionMapping(MinecraftInstrument.BELL, 17);
        PERCUSSION_MAPPINGS[MidiDefinitions.PERCUSSION_TAMBOURINE] = new PercussionMapping(MinecraftInstrument.SNARE, 23);
        PERCUSSION_MAPPINGS[MidiDefinitions.PERCUSSION_SPLASH_CYMBAL] = new PercussionMapping(MinecraftInstrument.SNARE, 18);
        PERCUSSION_MAPPINGS[MidiDefinitions.PERCUSSION_COWBELL] = new PercussionMapping(MinecraftInstrument.COW_BELL, 6);
        PERCUSSION_MAPPINGS[MidiDefinitions.PERCUSSION_CRASH_CYMBAL_2] = new PercussionMapping(MinecraftInstrument.SNARE, 21);
        PERCUSSION_MAPPINGS[MidiDefinitions.PERCUSSION_VIBRASLAP] = new PercussionMapping(MinecraftInstrument.HAT, 17);
        PERCUSSION_MAPPINGS[MidiDefinitions.PERCUSSION_RIDE_CYMBAL_2] = new PercussionMapping(MinecraftInstrument.SNARE, 24);
        PERCUSSION_MAPPINGS[MidiDefinitions.PERCUSSION_HI_BONGO] = new PercussionMapping(MinecraftInstrument.COW_BELL, 16);
        PERCUSSION_MAPPINGS[MidiDefinitions.PERCUSSION_LOW_BONGO] = new PercussionMapping(MinecraftInstrument.COW_BELL, 9);
        PERCUSSION_MAPPINGS[MidiDefinitions.PERCUSSION_MUTE_HI_CONGA] = new PercussionMapping(MinecraftInstrument.HAT, -3);
        PERCUSSION_MAPPINGS[MidiDefinitions.PERCUSSION_OPEN_HI_CONGA] = new PercussionMapping(MinecraftInstrument.COW_BELL, -1);
        PERCUSSION_MAPPINGS[MidiDefinitions.PERCUSSION_LOW_CONGA] = new PercussionMapping(MinecraftInstrument.COW_BELL, -9);
        PERCUSSION_MAPPINGS[MidiDefinitions.PERCUSSION_HIGH_TIMBALE] = new PercussionMapping(MinecraftInstrument.COW_BELL, 5);
        PERCUSSION_MAPPINGS[MidiDefinitions.PERCUSSION_LOW_TIMBALE] = new PercussionMapping(MinecraftInstrument.COW_BELL, -4);
        PERCUSSION_MAPPINGS[MidiDefinitions.PERCUSSION_HIGH_AGOGO] = new PercussionMapping(MinecraftInstrument.XYLOPHONE, 12);
        PERCUSSION_MAPPINGS[MidiDefinitions.PERCUSSION_LOW_AGOGO] = new PercussionMapping(MinecraftInstrument.XYLOPHONE, 5);
        PERCUSSION_MAPPINGS[MidiDefinitions.PERCUSSION_CABASA] = new PercussionMapping(MinecraftInstrument.HAT, 35);
        PERCUSSION_MAPPINGS[MidiDefinitions.PERCUSSION_MARACAS] = new PercussionMapping(MinecraftInstrument.HAT, 32);
        PERCUSSION_MAPPINGS[MidiDefinitions.PERCUSSION_SHORT_WHISTLE] = new PercussionMapping(MinecraftInstrument.FLUTE, 34);
        PERCUSSION_MAPPINGS[MidiDefinitions.PERCUSSION_LONG_WHISTLE] = new PercussionMapping(MinecraftInstrument.FLUTE, 33);
        PERCUSSION_MAPPINGS[MidiDefinitions.PERCUSSION_SHORT_GUIRO] = new PercussionMapping(MinecraftInstrument.HAT, 19);
        PERCUSSION_MAPPINGS[MidiDefinitions.PERCUSSION_LONG_GUIRO] = new PercussionMapping(MinecraftInstrument.HAT, 20);
        PERCUSSION_MAPPINGS[MidiDefinitions.PERCUSSION_CLAVES] = new PercussionMapping(MinecraftInstrument.XYLOPHONE, 19);
        PERCUSSION_MAPPINGS[MidiDefinitions.PERCUSSION_HI_WOOD_BLOCK] = new PercussionMapping(MinecraftInstrument.XYLOPHONE, 7);
        PERCUSSION_MAPPINGS[MidiDefinitions.PERCUSSION_LOW_WOOD_BLOCK] = new PercussionMapping(MinecraftInstrument.XYLOPHONE, 1);
        PERCUSSION_MAPPINGS[MidiDefinitions.PERCUSSION_MUTE_CUICA] = new PercussionMapping(MinecraftInstrument.DIDGERIDOO, 22);
        PERCUSSION_MAPPINGS[MidiDefinitions.PERCUSSION_OPEN_CUICA] = new PercussionMapping(MinecraftInstrument.DIDGERIDOO, 13);
        PERCUSSION_MAPPINGS[MidiDefinitions.PERCUSSION_MUTE_TRIANGLE] = new PercussionMapping(MinecraftInstrument.BELL, 19);
        PERCUSSION_MAPPINGS[MidiDefinitions.PERCUSSION_OPEN_TRIANGLE] = new PercussionMapping(MinecraftInstrument.CHIME, 19);
        PERCUSSION_MAPPINGS[82] = new PercussionMapping(MinecraftInstrument.HAT, 36);
        PERCUSSION_MAPPINGS[83] = new PercussionMapping(MinecraftInstrument.BELL, 21);
        PERCUSSION_MAPPINGS[84] = new PercussionMapping(MinecraftInstrument.CHIME, 17);
        PERCUSSION_MAPPINGS[85] = new PercussionMapping(MinecraftInstrument.XYLOPHONE, 15);
        PERCUSSION_MAPPINGS[86] = new PercussionMapping(MinecraftInstrument.BASS_DRUM, 12);
        PERCUSSION_MAPPINGS[87] = new PercussionMapping(MinecraftInstrument.BASS_DRUM, 7);
        PERCUSSION_MAPPINGS[88] = new PercussionMapping(MinecraftInstrument.SNARE, 10);
    }

}
