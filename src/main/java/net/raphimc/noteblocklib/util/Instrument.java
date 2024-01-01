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
package net.raphimc.noteblocklib.util;

public enum Instrument {

    HARP(0, 0, 0),
    BASS(1, 4, 4),
    BASS_DRUM(2, 1, 1),
    SNARE(3, 2, 2),
    HAT(4, 3, 3),
    GUITAR(5, 7, 8),
    FLUTE(6, 5, 6),
    BELL(7, 6, 5),
    CHIME(8, 8, 7),
    XYLOPHONE(9, 9, 9),
    IRON_XYLOPHONE(10, 10, 10),
    COW_BELL(11, 11, 11),
    DIDGERIDOO(12, 12, 12),
    BIT(13, 13, 13),
    BANJO(14, 14, 14),
    PLING(15, 15, 15),
    ;

    private final byte nbsId;
    private final byte mcId;
    private final byte mcbeId;

    Instrument(final int nbsId, final int mcId, final int mcbeId) {
        this.nbsId = (byte) nbsId;
        this.mcId = (byte) mcId;
        this.mcbeId = (byte) mcbeId;
    }

    public byte nbsId() {
        return this.nbsId;
    }

    public byte mcId() {
        return this.mcId;
    }

    public byte mcbeId() {
        return this.mcbeId;
    }

    public static Instrument fromNbsId(final byte nbsId) {
        for (final Instrument instrument : Instrument.values()) {
            if (instrument.nbsId == nbsId) {
                return instrument;
            }
        }
        return null;
    }

    public static Instrument fromMcId(final byte mcId) {
        for (final Instrument instrument : Instrument.values()) {
            if (instrument.mcId == mcId) {
                return instrument;
            }
        }
        return null;
    }

    public static Instrument fromMcbeId(final byte mcbeId) {
        for (final Instrument instrument : Instrument.values()) {
            if (instrument.mcbeId == mcbeId) {
                return instrument;
            }
        }
        return null;
    }

}
