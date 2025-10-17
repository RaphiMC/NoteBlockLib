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

import net.raphimc.noteblocklib.format.minecraft.MinecraftInstrument;

public class InstrumentMapping {

    private final MinecraftInstrument instrument;
    private final int octaveModifier;

    public InstrumentMapping(final MinecraftInstrument instrument, final int octaveModifier) {
        this.instrument = instrument;
        this.octaveModifier = octaveModifier;
    }

    public MinecraftInstrument getInstrument() {
        return this.instrument;
    }

    public int getOctaveModifier() {
        return this.octaveModifier;
    }

}
