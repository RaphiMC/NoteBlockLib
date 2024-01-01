/*
 * This file is part of NoteBlockLib - https://github.com/RaphiMC/NoteBlockLib
 * Copyright (C) 2022-2024 RK_01/RaphiMC and contributors
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
package net.raphimc.noteblocklib.format.txt.model;

import net.raphimc.noteblocklib.model.Header;

import java.util.Scanner;

public class TxtHeader implements Header {

    /**
     * @since v2
     */
    private float speed = 20F;

    public TxtHeader(final Scanner scanner) {
        if (scanner.hasNext("#{3}\\d+")) {
            this.speed = Float.parseFloat(scanner.skip("#{3}").next("\\d+(|\\.\\d+)"));
        }
    }

    public void write(final StringBuilder builder) {
        builder.append("###").append(this.speed).append('\n');
    }

    public TxtHeader(final float speed) {
        this.speed = speed;
    }

    public TxtHeader() {
    }

    /**
     * @return The speed of the song in ticks per second.
     * @since v2
     */
    public float getSpeed() {
        return this.speed;
    }

    /**
     * @param speed The speed of the song in ticks per second.
     * @since v2
     */
    public void setSpeed(final float speed) {
        this.speed = speed;
    }

}
