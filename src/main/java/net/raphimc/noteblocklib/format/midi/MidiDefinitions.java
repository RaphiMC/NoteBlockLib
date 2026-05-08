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
