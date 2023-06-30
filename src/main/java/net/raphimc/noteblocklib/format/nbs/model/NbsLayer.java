/*
 * This file is part of NoteBlockLib - https://github.com/RaphiMC/NoteBlockLib
 * Copyright (C) 2023 RK_01/RaphiMC and contributors
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

import com.google.common.io.LittleEndianDataInputStream;
import com.google.common.io.LittleEndianDataOutputStream;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import static net.raphimc.noteblocklib.format.nbs.NbsParser.readString;
import static net.raphimc.noteblocklib.format.nbs.NbsParser.writeString;

public class NbsLayer {

    /**
     * @since v0
     */
    private Map<Integer, NbsNote> notesAtTick = new TreeMap<>();

    /**
     * @since v0
     */
    private String name = "";

    /**
     * @since v0
     */
    private byte volume = 100;

    /**
     * @since v2
     */
    private short panning = 100;

    /**
     * @since v4
     */
    private boolean locked = false;

    @SuppressWarnings("UnstableApiUsage")
    public NbsLayer(final NbsHeader header, final LittleEndianDataInputStream dis) throws IOException {
        this.name = readString(dis);
        if (header.getNbsVersion() >= 4) {
            this.locked = dis.readBoolean();
        }
        this.volume = dis.readByte();
        if (header.getNbsVersion() >= 2) {
            this.panning = dis.readByte();
        }
    }

    public NbsLayer(final Map<Integer, NbsNote> notesAtTick, final String name, final byte volume, final short panning, final boolean locked) {
        this.notesAtTick = notesAtTick;
        this.name = name;
        this.volume = volume;
        this.panning = panning;
        this.locked = locked;
    }

    public NbsLayer() {
    }

    @SuppressWarnings("UnstableApiUsage")
    public void write(final NbsHeader header, final LittleEndianDataOutputStream dos) throws IOException {
        writeString(dos, this.name);
        if (header.getNbsVersion() >= 4) {
            dos.writeBoolean(this.locked);
        }
        dos.writeByte(this.volume);
        if (header.getNbsVersion() >= 2) {
            dos.writeByte(this.panning);
        }
    }

    /**
     * @return A map of all notes in this layer, with the tick as the key.
     * @since v0
     */
    public Map<Integer, NbsNote> getNotesAtTick() {
        return this.notesAtTick;
    }

    /**
     * @param notesAtTick A map of all notes in this layer, with the tick as the key.
     * @since v0
     */
    public void setNotesAtTick(final Map<Integer, NbsNote> notesAtTick) {
        this.notesAtTick = notesAtTick;
    }

    /**
     * @return The name of the layer.
     * @since v0
     */
    public String getName() {
        return this.name;
    }

    /**
     * @param name The name of the layer.
     * @since v0
     */
    public void setName(final String name) {
        this.name = name;
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
     * @since v0
     */
    public void setVolume(final byte volume) {
        this.volume = volume;
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
     * @since v2
     */
    public void setPanning(final short panning) {
        this.panning = panning;
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
     * @since v4
     */
    public void setLocked(final boolean locked) {
        this.locked = locked;
    }

}
