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
package net.raphimc.noteblocklib.format.future.header;

import com.google.common.io.ByteStreams;
import net.raphimc.noteblocklib.model.Header;

import java.io.IOException;
import java.io.InputStream;

public class FutureHeader implements Header {

    private boolean useMagicValue = true;

    public FutureHeader(final InputStream is) throws IOException {
        is.mark(is.available());
        final byte[] data = ByteStreams.toByteArray(is);
        for (byte b : data) {
            if (b == 64) {
                this.useMagicValue = false;
                break;
            }
        }
        is.reset();
    }

    public FutureHeader(final boolean useMagicValue) {
        this.useMagicValue = useMagicValue;
    }

    public boolean useMagicValue() {
        return this.useMagicValue;
    }

    public void setUseMagicValue(final boolean useMagicValue) {
        this.useMagicValue = useMagicValue;
    }

}
