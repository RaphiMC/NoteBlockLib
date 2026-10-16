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

    HARP(0, 0, "block.note_block.harp", "note/harp2"),
    BASS_DRUM(1, 2, "block.note_block.basedrum", "note/bd"),
    SNARE(2, 3, "block.note_block.snare", "note/snare"),
    HAT(3, 4, "block.note_block.hat", "note/hat"),
    BASS(4, 1, "block.note_block.bass", "note/bassattack"),
    FLUTE(5, 6, "block.note_block.flute", "note/flute"),
    BELL(6, 7, "block.note_block.bell", "note/bell"),
    GUITAR(7, 5, "block.note_block.guitar", "note/guitar"),
    CHIME(8, 8, "block.note_block.chime", "note/icechime"),
    XYLOPHONE(9, 9, "block.note_block.xylophone", "note/xylobone"),
    IRON_XYLOPHONE(10, 10, "block.note_block.iron_xylophone", "note/iron_xylophone"),
    COW_BELL(11, 11, "block.note_block.cow_bell", "note/cow_bell"),
    DIDGERIDOO(12, 12, "block.note_block.didgeridoo", "note/didgeridoo"),
    BIT(13, 13, "block.note_block.bit", "note/bit"),
    BANJO(14, 14, "block.note_block.banjo", "note/banjo"),
    PLING(15, 15, "block.note_block.pling", "note/pling"),
    TRUMPET(16, 16, "block.note_block.trumpet", "note/trumpet"),
    TRUMPET_EXPOSED(17, 17, "block.note_block.trumpet_exposed", "note/trumpet_exposed"),
    TRUMPET_OXIDIZED(18, 19, "block.note_block.trumpet_oxidized", "note/trumpet_oxidized"),
    TRUMPET_WEATHERED(19, 18, "block.note_block.trumpet_weathered", "note/trumpet_weathered");

    private final int mcId;
    private final int nbsId;
    private final String mcSoundName;
    private final String mcSoundPath;

    MinecraftInstrument(final int mcId, final int nbsId, final String mcSoundName, final String mcSoundPath) {
        this.mcId = mcId;
        this.nbsId = nbsId;
        this.mcSoundName = mcSoundName;
        this.mcSoundPath = mcSoundPath;
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

    public String mcSoundPath() {
        return this.mcSoundPath;
    }

    @Override
    public MinecraftInstrument copy() {
        return this;
    }

}
