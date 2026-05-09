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

public class MidiDefinitions {

    // Piano
    public static final byte INSTRUMENT_ACOUSTIC_GRAND_PIANO = 0;
    public static final byte INSTRUMENT_BRIGHT_ACOUSTIC_PIANO = 1;
    public static final byte INSTRUMENT_ELECTRIC_GRAND_PIANO = 2;
    public static final byte INSTRUMENT_HONKY_TONK_PIANO = 3;
    public static final byte INSTRUMENT_ELECTRIC_PIANO_1 = 4;
    public static final byte INSTRUMENT_ELECTRIC_PIANO_2 = 5;
    public static final byte INSTRUMENT_HARPSICHORD = 6;
    public static final byte INSTRUMENT_CLAVI = 7;

    // Chromatic Percussion
    public static final byte INSTRUMENT_CELESTA = 8;
    public static final byte INSTRUMENT_GLOCKENSPIEL = 9;
    public static final byte INSTRUMENT_MUSIC_BOX = 10;
    public static final byte INSTRUMENT_VIBRAPHONE = 11;
    public static final byte INSTRUMENT_MARIMBA = 12;
    public static final byte INSTRUMENT_XYLOPHONE = 13;
    public static final byte INSTRUMENT_TUBULAR_BELLS = 14;
    public static final byte INSTRUMENT_DULCIMER = 15;

    // Organ
    public static final byte INSTRUMENT_DRAWBAR_ORGAN = 16;
    public static final byte INSTRUMENT_PERCUSSIVE_ORGAN = 17;
    public static final byte INSTRUMENT_ROCK_ORGAN = 18;
    public static final byte INSTRUMENT_CHURCH_ORGAN = 19;
    public static final byte INSTRUMENT_REED_ORGAN = 20;
    public static final byte INSTRUMENT_ACCORDION = 21;
    public static final byte INSTRUMENT_HARMONICA = 22;
    public static final byte INSTRUMENT_TANGO_ACCORDION = 23;

    // Guitar
    public static final byte INSTRUMENT_ACOUSTIC_GUITAR_NYLON = 24;
    public static final byte INSTRUMENT_ACOUSTIC_GUITAR_STEEL = 25;
    public static final byte INSTRUMENT_ELECTRIC_GUITAR_JAZZ = 26;
    public static final byte INSTRUMENT_ELECTRIC_GUITAR_CLEAN = 27;
    public static final byte INSTRUMENT_ELECTRIC_GUITAR_MUTED = 28;
    public static final byte INSTRUMENT_OVERDRIVEN_GUITAR = 29;
    public static final byte INSTRUMENT_DISTORTION_GUITAR = 30;
    public static final byte INSTRUMENT_GUITAR_HARMONICS = 31;

    // Bass
    public static final byte INSTRUMENT_ACOUSTIC_BASS = 32;
    public static final byte INSTRUMENT_ELECTRIC_BASS_FINGER = 33;
    public static final byte INSTRUMENT_ELECTRIC_BASS_PICK = 34;
    public static final byte INSTRUMENT_FRETLESS_BASS = 35;
    public static final byte INSTRUMENT_SLAP_BASS_1 = 36;
    public static final byte INSTRUMENT_SLAP_BASS_2 = 37;
    public static final byte INSTRUMENT_SYNTH_BASS_1 = 38;
    public static final byte INSTRUMENT_SYNTH_BASS_2 = 39;

    // Strings
    public static final byte INSTRUMENT_VIOLIN = 40;
    public static final byte INSTRUMENT_VIOLA = 41;
    public static final byte INSTRUMENT_CELLO = 42;
    public static final byte INSTRUMENT_CONTRABASS = 43;
    public static final byte INSTRUMENT_TREMOLO_STRINGS = 44;
    public static final byte INSTRUMENT_PIZZICATO_STRINGS = 45;
    public static final byte INSTRUMENT_ORCHESTRAL_HARP = 46;
    public static final byte INSTRUMENT_TIMPANI = 47;

    // Ensemble
    public static final byte INSTRUMENT_STRING_ENSEMBLE_1 = 48;
    public static final byte INSTRUMENT_STRING_ENSEMBLE_2 = 49;
    public static final byte INSTRUMENT_SYNTH_STRINGS_1 = 50;
    public static final byte INSTRUMENT_SYNTH_STRINGS_2 = 51;
    public static final byte INSTRUMENT_CHOIR_AAHS = 52;
    public static final byte INSTRUMENT_VOICE_OOHS = 53;
    public static final byte INSTRUMENT_SYNTH_VOICE = 54;
    public static final byte INSTRUMENT_ORCHESTRA_HIT = 55;

    // Brass
    public static final byte INSTRUMENT_TRUMPET = 56;
    public static final byte INSTRUMENT_TROMBONE = 57;
    public static final byte INSTRUMENT_TUBA = 58;
    public static final byte INSTRUMENT_MUTED_TRUMPET = 59;
    public static final byte INSTRUMENT_FRENCH_HORN = 60;
    public static final byte INSTRUMENT_BRASS_SECTION = 61;
    public static final byte INSTRUMENT_SYNTH_BRASS_1 = 62;
    public static final byte INSTRUMENT_SYNTH_BRASS_2 = 63;

    // Reed
    public static final byte INSTRUMENT_SOPRANO_SAX = 64;
    public static final byte INSTRUMENT_ALTO_SAX = 65;
    public static final byte INSTRUMENT_TENOR_SAX = 66;
    public static final byte INSTRUMENT_BARITONE_SAX = 67;
    public static final byte INSTRUMENT_OBOE = 68;
    public static final byte INSTRUMENT_ENGLISH_HORN = 69;
    public static final byte INSTRUMENT_BASSOON = 70;
    public static final byte INSTRUMENT_CLARINET = 71;

    // Pipe
    public static final byte INSTRUMENT_PICCOLO = 72;
    public static final byte INSTRUMENT_FLUTE = 73;
    public static final byte INSTRUMENT_RECORDER = 74;
    public static final byte INSTRUMENT_PAN_FLUTE = 75;
    public static final byte INSTRUMENT_BLOWN_BOTTLE = 76;
    public static final byte INSTRUMENT_SHAKUHACHI = 77;
    public static final byte INSTRUMENT_WHISTLE = 78;
    public static final byte INSTRUMENT_OCARINA = 79;

    // Synth Lead
    public static final byte INSTRUMENT_LEAD_1_SQUARE = 80;
    public static final byte INSTRUMENT_LEAD_2_SAWTOOTH = 81;
    public static final byte INSTRUMENT_LEAD_3_CALLIOPE = 82;
    public static final byte INSTRUMENT_LEAD_4_CHIFF = 83;
    public static final byte INSTRUMENT_LEAD_5_CHARANG = 84;
    public static final byte INSTRUMENT_LEAD_6_VOICE = 85;
    public static final byte INSTRUMENT_LEAD_7_FIFTHS = 86;
    public static final byte INSTRUMENT_LEAD_8_BASS_PLUS_LEAD = 87;

    // Synth Pad
    public static final byte INSTRUMENT_PAD_1_NEW_AGE = 88;
    public static final byte INSTRUMENT_PAD_2_WARM = 89;
    public static final byte INSTRUMENT_PAD_3_POLYSYNTH = 90;
    public static final byte INSTRUMENT_PAD_4_CHOIR = 91;
    public static final byte INSTRUMENT_PAD_5_BOWED = 92;
    public static final byte INSTRUMENT_PAD_6_METALLIC = 93;
    public static final byte INSTRUMENT_PAD_7_HALO = 94;
    public static final byte INSTRUMENT_PAD_8_SWEEP = 95;

    // Synth Effects
    public static final byte INSTRUMENT_FX_1_RAIN = 96;
    public static final byte INSTRUMENT_FX_2_SOUNDTRACK = 97;
    public static final byte INSTRUMENT_FX_3_CRYSTAL = 98;
    public static final byte INSTRUMENT_FX_4_ATMOSPHERE = 99;
    public static final byte INSTRUMENT_FX_5_BRIGHTNESS = 100;
    public static final byte INSTRUMENT_FX_6_GOBLINS = 101;
    public static final byte INSTRUMENT_FX_7_ECHOES = 102;
    public static final byte INSTRUMENT_FX_8_SCI_FI = 103;

    // Ethnic
    public static final byte INSTRUMENT_SITAR = 104;
    public static final byte INSTRUMENT_BANJO = 105;
    public static final byte INSTRUMENT_SHAMISEN = 106;
    public static final byte INSTRUMENT_KOTO = 107;
    public static final byte INSTRUMENT_KALIMBA = 108;
    public static final byte INSTRUMENT_BAG_PIPE = 109;
    public static final byte INSTRUMENT_FIDDLE = 110;
    public static final byte INSTRUMENT_SHANAI = 111;

    // Percussive
    public static final byte INSTRUMENT_TINKLE_BELL = 112;
    public static final byte INSTRUMENT_AGOGO = 113;
    public static final byte INSTRUMENT_STEEL_DRUMS = 114;
    public static final byte INSTRUMENT_WOODBLOCK = 115;
    public static final byte INSTRUMENT_TAIKO_DRUM = 116;
    public static final byte INSTRUMENT_MELODIC_TOM = 117;
    public static final byte INSTRUMENT_SYNTH_DRUM = 118;
    public static final byte INSTRUMENT_REVERSE_CYMBAL = 119;

    // Sound Effects
    public static final byte INSTRUMENT_GUITAR_FRET_NOISE = 120;
    public static final byte INSTRUMENT_BREATH_NOISE = 121;
    public static final byte INSTRUMENT_SEASHORE = 122;
    public static final byte INSTRUMENT_BIRD_TWEET = 123;
    public static final byte INSTRUMENT_TELEPHONE_RING = 124;
    public static final byte INSTRUMENT_HELICOPTER = 125;
    public static final byte INSTRUMENT_APPLAUSE = 126;
    public static final byte INSTRUMENT_GUNSHOT = 127;

    // Percussion key numbers
    public static final byte PERCUSSION_ACOUSTIC_BASS_DRUM = 35;
    public static final byte PERCUSSION_BASS_DRUM_1 = 36;
    public static final byte PERCUSSION_SIDE_STICK = 37;
    public static final byte PERCUSSION_ACOUSTIC_SNARE = 38;
    public static final byte PERCUSSION_HAND_CLAP = 39;
    public static final byte PERCUSSION_ELECTRIC_SNARE = 40;
    public static final byte PERCUSSION_LOW_FLOOR_TOM = 41;
    public static final byte PERCUSSION_CLOSED_HI_HAT = 42;
    public static final byte PERCUSSION_HIGH_FLOOR_TOM = 43;
    public static final byte PERCUSSION_PEDAL_HI_HAT = 44;
    public static final byte PERCUSSION_LOW_TOM = 45;
    public static final byte PERCUSSION_OPEN_HI_HAT = 46;
    public static final byte PERCUSSION_LOW_MID_TOM = 47;
    public static final byte PERCUSSION_HI_MID_TOM = 48;
    public static final byte PERCUSSION_CRASH_CYMBAL_1 = 49;
    public static final byte PERCUSSION_HIGH_TOM = 50;
    public static final byte PERCUSSION_RIDE_CYMBAL_1 = 51;
    public static final byte PERCUSSION_CHINESE_CYMBAL = 52;
    public static final byte PERCUSSION_RIDE_BELL = 53;
    public static final byte PERCUSSION_TAMBOURINE = 54;
    public static final byte PERCUSSION_SPLASH_CYMBAL = 55;
    public static final byte PERCUSSION_COWBELL = 56;
    public static final byte PERCUSSION_CRASH_CYMBAL_2 = 57;
    public static final byte PERCUSSION_VIBRASLAP = 58;
    public static final byte PERCUSSION_RIDE_CYMBAL_2 = 59;
    public static final byte PERCUSSION_HI_BONGO = 60;
    public static final byte PERCUSSION_LOW_BONGO = 61;
    public static final byte PERCUSSION_MUTE_HI_CONGA = 62;
    public static final byte PERCUSSION_OPEN_HI_CONGA = 63;
    public static final byte PERCUSSION_LOW_CONGA = 64;
    public static final byte PERCUSSION_HIGH_TIMBALE = 65;
    public static final byte PERCUSSION_LOW_TIMBALE = 66;
    public static final byte PERCUSSION_HIGH_AGOGO = 67;
    public static final byte PERCUSSION_LOW_AGOGO = 68;
    public static final byte PERCUSSION_CABASA = 69;
    public static final byte PERCUSSION_MARACAS = 70;
    public static final byte PERCUSSION_SHORT_WHISTLE = 71;
    public static final byte PERCUSSION_LONG_WHISTLE = 72;
    public static final byte PERCUSSION_SHORT_GUIRO = 73;
    public static final byte PERCUSSION_LONG_GUIRO = 74;
    public static final byte PERCUSSION_CLAVES = 75;
    public static final byte PERCUSSION_HI_WOOD_BLOCK = 76;
    public static final byte PERCUSSION_LOW_WOOD_BLOCK = 77;
    public static final byte PERCUSSION_MUTE_CUICA = 78;
    public static final byte PERCUSSION_OPEN_CUICA = 79;
    public static final byte PERCUSSION_MUTE_TRIANGLE = 80;
    public static final byte PERCUSSION_OPEN_TRIANGLE = 81;

    // MetaMessage types
    public static final int META_TEXT = 0x01;
    public static final int META_COPYRIGHT_NOTICE = 0x02;
    public static final int META_TRACK_NAME = 0x03;
    public static final int META_SET_TEMPO = 0x51;

    // CONTROL_CHANGE commands
    public static final int CONTROL_CHANNEL_VOLUME_MSB = 0x07;
    public static final int CONTROL_PAN_MSB = 0x0A;
    public static final int CONTROL_EXPRESSION_CONTROLLER_MSB = 0x0B;
    public static final int CONTROL_RESET_ALL_CONTROLLERS = 0x79;

    // SysexMessage
    public static final int SYSEX_UNIVERSAL_NON_REALTIME_MESSAGE = 0x7E;
    public static final int SYSEX_DEVICE_ALL = 0x7F;
    public static final int SYSEX_SUB_ID_GENERAL_MIDI = 0x09;
    public static final int SYSEX_GENERAL_MIDI_GM1_SYSTEM_ON = 0x01;
    public static final int SYSEX_GENERAL_MIDI_GM2_SYSTEM_ON = 0x03;

    public static final int PERCUSSION_CHANNEL = 9;

    public static final int LOWEST_KEY = 0;
    public static final int HIGHEST_KEY = 127;
    public static final int KEY_COUNT = 128;
    public static final int KEYS_PER_OCTAVE = 12;
    public static final int F_SHARP_4_KEY = 66;

    public static final int CHANNEL_COUNT = 16;
    public static final int DEFAULT_TEMPO_MPQ = 500_000;
    public static final byte DEFAULT_VOLUME = 100;
    public static final byte MAX_VELOCITY = 127;
    public static final byte CENTER_PAN = 64;

    public static final float SONG_TARGET_TEMPO = 100F;

}
