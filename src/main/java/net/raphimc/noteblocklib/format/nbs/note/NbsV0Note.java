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
package net.raphimc.noteblocklib.format.nbs.note;

import com.google.common.io.LittleEndianDataInputStream;
import com.google.common.io.LittleEndianDataOutputStream;
import net.raphimc.noteblocklib.format.nbs.data.layer.NbsLayer;
import net.raphimc.noteblocklib.model.Note;

import java.io.IOException;

public class NbsV0Note extends Note {

    private NbsLayer layer;

    @SuppressWarnings("UnstableApiUsage")
    public NbsV0Note(final LittleEndianDataInputStream dis) throws IOException {
        super(dis.readByte(), dis.readByte());
    }

    public NbsV0Note(final byte instrument, final byte key) {
        super(instrument, key);
    }

    @SuppressWarnings("UnstableApiUsage")
    public void write(final LittleEndianDataOutputStream dos) throws IOException {
        dos.writeByte(this.instrument);
        dos.writeByte(this.key);
    }

    /**
     * @return The NBS layer this note is in.
     */
    public NbsLayer getLayer() {
        return this.layer;
    }

    public void setLayer(final NbsLayer layer) {
        this.layer = layer;
    }

}
