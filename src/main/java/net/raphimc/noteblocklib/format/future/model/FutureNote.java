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
package net.raphimc.noteblocklib.format.future.model;

import net.raphimc.noteblocklib.model.Note;
import net.raphimc.noteblocklib.util.Instrument;
import net.raphimc.noteblocklib.util.MinecraftDefinitions;

public class FutureNote extends Note {

    public FutureNote(final byte key, final Instrument instrument) {
        super(instrument, key);
    }

    @Override
    public byte getKey() {
        return (byte) (super.getKey() + MinecraftDefinitions.MC_LOWEST_KEY);
    }

    @Override
    public void setKey(final byte key) {
        super.setKey((byte) (key - MinecraftDefinitions.MC_LOWEST_KEY));
    }

    @Override
    public FutureNote clone() {
        return new FutureNote(this.key, this.instrument);
    }

}
