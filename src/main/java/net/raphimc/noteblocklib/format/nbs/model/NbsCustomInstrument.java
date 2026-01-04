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
package net.raphimc.noteblocklib.format.nbs.model;

import net.raphimc.noteblocklib.format.nbs.NbsDefinitions;
import net.raphimc.noteblocklib.model.note.Instrument;

import java.util.Objects;

public class NbsCustomInstrument implements Instrument {

    /**
     * @since v0
     */
    private String name;

    /**
     * @since v0
     */
    private String soundFilePath;

    /**
     * @since v0
     */
    private byte pitch;

    /**
     * @since v0
     */
    private boolean pressKey;

    public NbsCustomInstrument() {
        this.pitch = NbsDefinitions.F_SHARP_4_KEY;
    }

    /**
     * @return The name of the instrument.
     * @since v0
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return The name of the instrument.
     * @param fallback The fallback value if the name is not set.
     * @since v0
     */
    public String getNameOr(final String fallback) {
        return this.name == null ? fallback : this.name;
    }

    /**
     * @param name The name of the instrument.
     * @return this
     * @since v0
     */
    public NbsCustomInstrument setName(final String name) {
        if (name != null && !name.isEmpty()) {
            this.name = name;
        } else {
            this.name = null;
        }
        return this;
    }

    /**
     * @return The sound file of the instrument.
     * @since v0
     */
    public String getSoundFilePath() {
        return this.soundFilePath;
    }

    /**
     * @return The sound file of the instrument.
     * @param fallback The fallback value if the sound file path is not set.
     * @since v0
     */
    public String getSoundFilePathOr(final String fallback) {
        return this.soundFilePath == null ? fallback : this.soundFilePath;
    }

    /**
     * @param soundFilePath The sound file of the instrument.
     * @return this
     * @since v0
     */
    public NbsCustomInstrument setSoundFilePath(final String soundFilePath) {
        if (soundFilePath != null && !soundFilePath.isEmpty()) {
            this.soundFilePath = soundFilePath;
        } else {
            this.soundFilePath = null;
        }
        return this;
    }

    /**
     * @return The pitch of the sound file. Just like the note blocks, this ranges from 0-87. Default is 45 (F#4).
     * @since v0
     */
    public int getPitch() {
        return this.pitch & 0xFF;
    }

    /**
     * @param pitch The pitch of the sound file. Just like the note blocks, this ranges from 0-87. Default is 45 (F#4).
     * @return this
     * @since v0
     */
    public NbsCustomInstrument setPitch(final int pitch) {
        if (pitch < 0 || pitch > 255) {
            throw new IllegalArgumentException("Pitch must be between 0 and 255");
        }
        this.pitch = (byte) pitch;
        return this;
    }

    /**
     * @return Whether the piano should automatically press keys with this instrument when the marker passes them.
     * @since v0
     */
    public boolean isPressKey() {
        return this.pressKey;
    }

    /**
     * @param pressKey Whether the piano should automatically press keys with this instrument when the marker passes them.
     * @return this
     * @since v0
     */
    public NbsCustomInstrument setPressKey(final boolean pressKey) {
        this.pressKey = pressKey;
        return this;
    }

    public NbsCustomInstrument copy() {
        final NbsCustomInstrument copyCustomInstrument = new NbsCustomInstrument();
        copyCustomInstrument.setName(this.getName());
        copyCustomInstrument.setSoundFilePath(this.getSoundFilePath());
        copyCustomInstrument.setPitch(this.getPitch());
        copyCustomInstrument.setPressKey(this.isPressKey());
        return copyCustomInstrument;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        NbsCustomInstrument that = (NbsCustomInstrument) o;
        return pitch == that.pitch && pressKey == that.pressKey && Objects.equals(name, that.name) && Objects.equals(soundFilePath, that.soundFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, soundFilePath, pitch, pressKey);
    }

}
