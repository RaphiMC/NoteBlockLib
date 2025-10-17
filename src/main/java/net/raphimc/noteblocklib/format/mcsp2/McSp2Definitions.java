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
package net.raphimc.noteblocklib.format.mcsp2;

import com.google.common.collect.Sets;
import net.raphimc.noteblocklib.format.minecraft.MinecraftInstrument;

import java.util.Set;
import java.util.regex.Pattern;

public class McSp2Definitions {

    public static final int MIN_TEMPO = 1;
    public static final int MAX_TEMPO = 20;

    public static final Pattern NOTE_DATA_PATTERN = Pattern.compile("(\\d+)?>(.)");
    public static final String NOTE_DATA_MAPPING = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!§½#£¤$%&/{[(])=}?\\+´`^~¨*'.;,:-_<µ€ÌìíÍïÏîÎóÓòÒöÖåÅäÄñÑõÕúÚùÙüûÜÛéÉèÈêÊë";

    public static final Set<MinecraftInstrument> SUPPORTED_INSTRUMENTS = Sets.immutableEnumSet(MinecraftInstrument.HARP, MinecraftInstrument.BASS, MinecraftInstrument.BASS_DRUM, MinecraftInstrument.SNARE, MinecraftInstrument.HAT);

}
