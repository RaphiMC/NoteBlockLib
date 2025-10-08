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
    private byte panning;

    /**
     * @since v4
     */
    private Status status;

    public NbsLayer() {
        this.volume = 100;
        this.panning = NbsDefinitions.CENTER_PANNING;
        this.status = Status.NONE;
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
     * @param fallback The fallback value if the layer name is not set.
     * @return The name of the layer.
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
    public int getVolume() {
        return this.volume & 0xFF;
    }

    /**
     * @param volume The volume of the layer (percentage). Ranges from 0-100.
     * @return this
     * @since v0
     */
    public NbsLayer setVolume(final int volume) {
        if (volume < 0 || volume > 255) {
            throw new IllegalArgumentException("Volume must be between 0 and 255");
        }
        this.volume = (byte) volume;
        return this;
    }

    /**
     * @return How much this layer is panned to the left/right. 0 is 2 blocks right, 100 is center, 200 is 2 blocks left.
     * @since v2
     */
    public int getPanning() {
        return this.panning & 0xFF;
    }

    /**
     * @param panning How much this layer should be panned to the left/right. 0 is 2 blocks right, 100 is center, 200 is 2 blocks left.
     * @return this
     * @since v2
     */
    public NbsLayer setPanning(final int panning) {
        if (panning < 0 || panning > 255) {
            throw new IllegalArgumentException("Panning must be between 0 and 255");
        }
        this.panning = (byte) panning;
        return this;
    }

    /**
     * @return The status of this layer (none, locked or solo).
     * @since v4
     */
    public Status getStatus() {
        return this.status;
    }

    /**
     * @param status The status of this layer (none, locked or solo).
     * @return this
     * @since v4
     */
    public NbsLayer setStatus(final Status status) {
        this.status = status;
        return this;
    }

    public NbsLayer copy() {
        final NbsLayer copyLayer = new NbsLayer();
        copyLayer.setName(this.getName());
        copyLayer.setVolume(this.getVolume());
        copyLayer.setPanning(this.getPanning());
        copyLayer.setStatus(this.getStatus());
        final Map<Integer, NbsNote> notes = this.getNotes();
        final Map<Integer, NbsNote> copyNotes = copyLayer.getNotes();
        for (final Map.Entry<Integer, NbsNote> entry : notes.entrySet()) {
            copyNotes.put(entry.getKey(), entry.getValue().copy());
        }
        return copyLayer;
    }

    public enum Status {

        NONE,
        LOCKED,
        SOLO,

    }

}
