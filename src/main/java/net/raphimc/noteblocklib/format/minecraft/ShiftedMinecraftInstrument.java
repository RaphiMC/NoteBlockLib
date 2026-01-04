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
package net.raphimc.noteblocklib.format.minecraft;

import net.raphimc.noteblocklib.model.note.Instrument;

import java.util.Objects;

public class ShiftedMinecraftInstrument implements Instrument {

    private final MinecraftInstrument instrument;
    private final int octavesShift;

    public ShiftedMinecraftInstrument(final MinecraftInstrument instrument, final int octavesShift) {
        if (instrument == null) {
            throw new IllegalArgumentException("Instrument cannot be null");
        }
        this.instrument = instrument;
        this.octavesShift = octavesShift;
    }

    public String mcSoundName() {
        if (this.octavesShift == 0) {
            return this.instrument.mcSoundName();
        } else {
            return this.instrument.mcSoundName() + '_' + this.octavesShift;
        }
    }

    public MinecraftInstrument getInstrument() {
        return this.instrument;
    }

    public int getOctavesShift() {
        return this.octavesShift;
    }

    @Override
    public ShiftedMinecraftInstrument copy() {
        return new ShiftedMinecraftInstrument(this.instrument, this.octavesShift);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ShiftedMinecraftInstrument that = (ShiftedMinecraftInstrument) o;
        return octavesShift == that.octavesShift && instrument == that.instrument;
    }

    @Override
    public int hashCode() {
        return Objects.hash(instrument, octavesShift);
    }

}
