/*
 * This file is part of NoteBlockLib - https://github.com/RaphiMC/NoteBlockLib
 * Copyright (C) 2023 RK_01/RaphiMC and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.raphimc.noteblocklib.format.future.model;

import net.raphimc.noteblocklib.model.Note;
import net.raphimc.noteblocklib.util.Instrument;
import net.raphimc.noteblocklib.util.MinecraftDefinitions;

public class FutureNote extends Note {

    public FutureNote(final byte key, final byte instrument) {
        super(instrument, key);
    }

    @Override
    public byte getInstrument() {
        return Instrument.fromMcId(super.getInstrument()).nbsId();
    }

    @Override
    public byte getKey() {
        return (byte) (super.getKey() + MinecraftDefinitions.MC_LOWEST_KEY);
    }

}
