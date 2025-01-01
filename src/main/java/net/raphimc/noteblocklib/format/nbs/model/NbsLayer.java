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
package net.raphimc.noteblocklib.format.nbs.model;

import net.raphimc.noteblocklib.format.nbs.NbsDefinitions;

import java.util.HashMap;
import java.util.Map;

public class NbsLayer {

    /**
     * @since v0
     */
    private final Map<Integer, NbsNote> notes = new HashMap<>();

    /**
     * @since v0
     */
    private String name;

    /**
     * @since v0
     */
    private byte volume;

    /**
     * @since v2
     */
    private short panning;

    /**
     * @since v4
     */
    private boolean locked;

    public NbsLayer() {
        this.volume = 100;
        this.panning = NbsDefinitions.CENTER_PANNING;
    }

    /**
     * @return A map of all notes in this layer, with the tick as the key.
     * @since v0
     */
    public Map<Integer, NbsNote> getNotes() {
        return this.notes;
    }

    /**
     * @return The name of the layer.
     * @since v0
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return The name of the layer.
     * @param fallback The fallback value if the layer name is not set.
     * @since v0
     */
    public String getNameOr(final String fallback) {
        return this.name == null ? fallback : this.name;
    }

    /**
     * @param name The name of the layer.
     * @return this
     * @since v0
     */
    public NbsLayer setName(final String name) {
        if (name != null && !name.isEmpty()) {
            this.name = name;
        } else {
            this.name = null;
        }
        return this;
    }

    /**
     * @return The volume of the layer (percentage). Ranges from 0-100.
     * @since v0
     */
    public byte getVolume() {
        return this.volume;
    }

    /**
     * @param volume The volume of the layer (percentage). Ranges from 0-100.
     * @return this
     * @since v0
     */
    public NbsLayer setVolume(final byte volume) {
        this.volume = volume;
        return this;
    }

    /**
     * @return How much this layer is panned to the left/right. 0 is 2 blocks right, 100 is center, 200 is 2 blocks left.
     * @since v2
     */
    public short getPanning() {
        return this.panning;
    }

    /**
     * @param panning How much this layer should be panned to the left/right. 0 is 2 blocks right, 100 is center, 200 is 2 blocks left.
     * @return this
     * @since v2
     */
    public NbsLayer setPanning(final short panning) {
        this.panning = panning;
        return this;
    }

    /**
     * @return Whether this layer has been marked as locked.
     * @since v4
     */
    public boolean isLocked() {
        return this.locked;
    }

    /**
     * @param locked Whether this layer should be marked as locked.
     * @return this
     * @since v4
     */
    public NbsLayer setLocked(final boolean locked) {
        this.locked = locked;
        return this;
    }

    public NbsLayer copy() {
        final NbsLayer copyLayer = new NbsLayer();
        copyLayer.setName(this.name);
        copyLayer.setVolume(this.volume);
        copyLayer.setPanning(this.panning);
        copyLayer.setLocked(this.locked);
        final Map<Integer, NbsNote> notes = this.getNotes();
        final Map<Integer, NbsNote> copyNotes = copyLayer.getNotes();
        for (final Map.Entry<Integer, NbsNote> entry : notes.entrySet()) {
            copyNotes.put(entry.getKey(), entry.getValue().copy());
        }
        return copyLayer;
    }

}
