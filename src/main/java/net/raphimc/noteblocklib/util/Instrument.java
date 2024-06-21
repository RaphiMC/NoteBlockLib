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

    HARP(0, 0, 0, "block.note_block.harp"),
    BASS(1, 4, 4, "block.note_block.bass"),
    BASS_DRUM(2, 1, 1, "block.note_block.basedrum"),
    SNARE(3, 2, 2, "block.note_block.snare"),
    HAT(4, 3, 3, "block.note_block.hat"),
    GUITAR(5, 7, 8, "block.note_block.guitar"),
    FLUTE(6, 5, 6, "block.note_block.flute"),
    BELL(7, 6, 5, "block.note_block.bell"),
    CHIME(8, 8, 7, "block.note_block.chime"),
    XYLOPHONE(9, 9, 9, "block.note_block.xylophone"),
    IRON_XYLOPHONE(10, 10, 10, "block.note_block.iron_xylophone"),
    COW_BELL(11, 11, 11, "block.note_block.cow_bell"),
    DIDGERIDOO(12, 12, 12, "block.note_block.didgeridoo"),
    BIT(13, 13, 13, "block.note_block.bit"),
    BANJO(14, 14, 14, "block.note_block.banjo"),
    PLING(15, 15, 15, "block.note_block.pling");

    private final byte nbsId;
    private final byte mcId;
    private final byte mcbeId;
    private final String mcSoundName;

    Instrument(final int nbsId, final int mcId, final int mcbeId, final String mcSoundName) {
        this.nbsId = (byte) nbsId;
        this.mcId = (byte) mcId;
        this.mcbeId = (byte) mcbeId;
        this.mcSoundName = mcSoundName;
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

    public String mcSoundName() {
        return this.mcSoundName;
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

    public static Instrument fromMcSoundName(final String mcSoundName) {
        for (final Instrument instrument : Instrument.values()) {
            if (instrument.mcSoundName.equals(mcSoundName)) {
                return instrument;
            }
        }
        return null;
    }

}
