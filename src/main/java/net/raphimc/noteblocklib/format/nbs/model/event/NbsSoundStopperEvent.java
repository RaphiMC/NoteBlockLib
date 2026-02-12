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
package net.raphimc.noteblocklib.format.nbs.model.event;

import net.raphimc.noteblocklib.model.note.Note;

import java.util.Objects;

/**
 * Event, which stops all sounds originating from the specified layers.
 */
public class NbsSoundStopperEvent implements NbsEvent {

    private short startLayer;
    private short endLayer;

    public NbsSoundStopperEvent(final short startLayer, final short endLayer) {
        this.setStartLayer(startLayer);
        this.setEndLayer(endLayer);
    }

    /**
     * @return The start layer (1 indexed, inclusive) of the layer range whose sounds will be stopped. 0 = stop all layers
     */
    public short getStartLayer() {
        return this.startLayer;
    }

    /**
     * @param startLayer The start layer (1 indexed, inclusive) of the layer range whose sounds will be stopped. 0 = stop all layers
     */
    public void setStartLayer(final short startLayer) {
        this.startLayer = startLayer;
    }

    /**
     * @return The end layer (1 indexed, inclusive) of the layer range whose sounds will be stopped.
     */
    public short getEndLayer() {
        return this.endLayer;
    }

    /**
     * @param endLayer The end layer (1 indexed, inclusive) of the layer range whose sounds will be stopped.
     */
    public void setEndLayer(final short endLayer) {
        this.endLayer = endLayer;
    }

    /**
     * Checks if the specified note should be stopped by this event.
     *
     * @param note The note to check.
     * @return True if the note should be stopped by this event, false otherwise.
     */
    public boolean shouldStop(final Note note) {
        if (this.startLayer == 0) {
            return true;
        }
        return note.getGroupId() >= (this.startLayer - 1) && note.getGroupId() <= (this.endLayer - 1);
    }

    @Override
    public NbsSoundStopperEvent copy() {
        return new NbsSoundStopperEvent(this.startLayer, this.endLayer);
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        final NbsSoundStopperEvent that = (NbsSoundStopperEvent) o;
        return startLayer == that.startLayer && endLayer == that.endLayer;
    }

    @Override
    public int hashCode() {
        return Objects.hash(startLayer, endLayer);
    }

}
