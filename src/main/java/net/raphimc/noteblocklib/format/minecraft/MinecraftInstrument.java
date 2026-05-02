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

public enum MinecraftInstrument implements Instrument {

    HARP(0, 0, "block.note_block.harp"),
    BASS_DRUM(1, 2, "block.note_block.basedrum"),
    SNARE(2, 3, "block.note_block.snare"),
    HAT(3, 4, "block.note_block.hat"),
    BASS(4, 1, "block.note_block.bass"),
    FLUTE(5, 6, "block.note_block.flute"),
    BELL(6, 7, "block.note_block.bell"),
    GUITAR(7, 5, "block.note_block.guitar"),
    CHIME(8, 8, "block.note_block.chime"),
    XYLOPHONE(9, 9, "block.note_block.xylophone"),
    IRON_XYLOPHONE(10, 10, "block.note_block.iron_xylophone"),
    COW_BELL(11, 11, "block.note_block.cow_bell"),
    DIDGERIDOO(12, 12, "block.note_block.didgeridoo"),
    BIT(13, 13, "block.note_block.bit"),
    BANJO(14, 14, "block.note_block.banjo"),
    PLING(15, 15, "block.note_block.pling"),
    TRUMPET(16, 16, "block.note_block.trumpet"),
    TRUMPET_EXPOSED(17, 17, "block.note_block.trumpet_exposed"),
    TRUMPET_OXIDIZED(18, 18, "block.note_block.trumpet_oxidized"),
    TRUMPET_WEATHERED(19, 19, "block.note_block.trumpet_weathered");

    private final int mcId;
    private final int nbsId;
    private final String mcSoundName;

    MinecraftInstrument(final int mcId, final int nbsId, final String mcSoundName) {
        this.mcId = mcId;
        this.nbsId = nbsId;
        this.mcSoundName = mcSoundName;
    }

    public static MinecraftInstrument fromMcId(final int mcId) {
        for (MinecraftInstrument instrument : MinecraftInstrument.values()) {
            if (instrument.mcId == mcId) {
                return instrument;
            }
        }
        return null;
    }

    public static MinecraftInstrument fromNbsId(final int nbsId) {
        for (MinecraftInstrument instrument : MinecraftInstrument.values()) {
            if (instrument.nbsId == nbsId) {
                return instrument;
            }
        }
        return null;
    }

    public static MinecraftInstrument fromMcSoundName(final String mcSoundName) {
        for (MinecraftInstrument instrument : MinecraftInstrument.values()) {
            if (instrument.mcSoundName.equals(mcSoundName)) {
                return instrument;
            }
        }
        return null;
    }

    public int mcId() {
        return this.mcId;
    }

    public int nbsId() {
        return this.nbsId;
    }

    public String mcSoundName() {
        return this.mcSoundName;
    }

    @Override
    public MinecraftInstrument copy() {
        return this;
    }

}
