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
package net.raphimc.noteblocklib.format.midi;

public class MidiDefinitions {

    public static final int META_COPYRIGHT_NOTICE = 0x02;
    public static final int META_TRACK_NAME = 0x03;
    public static final int META_SET_TEMPO = 0x51;

    public static final int PERCUSSION_CHANNEL = 9;
    public static final int VOLUME_CONTROL_MSB = 0x07;
    public static final int PAN_CONTROL_MSB = 0x0A;
    public static final int RESET_CONTROLS = 0x79;

    public static final int CHANNEL_COUNT = 16;
    public static final int DEFAULT_TEMPO_MPQ = 500_000;
    public static final byte MAX_VELOCITY = 127;
    public static final byte CENTER_PAN = 64;

    public static final float SONG_TARGET_TEMPO = 100F;

}
