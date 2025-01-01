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

import com.google.common.io.LittleEndianDataInputStream;
import com.google.common.io.LittleEndianDataOutputStream;
import net.raphimc.noteblocklib.model.Note;
import net.raphimc.noteblocklib.model.NoteWithPanning;
import net.raphimc.noteblocklib.model.NoteWithVolume;
import net.raphimc.noteblocklib.util.Instrument;

import java.io.IOException;
import java.util.Objects;

public class NbsNote extends Note implements NoteWithVolume, NoteWithPanning {

    /**
     * @since v0
     */
    private NbsLayer layer;

    /**
     * @since v0
     */
    private NbsCustomInstrument customInstrument;

    /**
     * @since v4
     */
    private byte velocity = 100;

    /**
     * @since v4
     */
    private short panning = 100;

    /**
     * @since v4
     */
    private short pitch = 0;

    // For internal use
    private int rawInstrumentId;

    public NbsNote(final NbsHeader header, final NbsLayer layer, final LittleEndianDataInputStream dis) throws IOException {
        super(null, (byte) 0);

        this.rawInstrumentId = dis.readUnsignedByte();
        if (this.rawInstrumentId < header.getVanillaInstrumentCount()) {
            this.instrument = Instrument.fromNbsId((byte) this.rawInstrumentId);
        }
        this.key = dis.readByte();

        if (header.getVersion() >= 4) {
            this.velocity = dis.readByte();
            this.panning = (short) dis.readUnsignedByte();
            this.pitch = dis.readShort();
        }

        this.layer = layer;
    }

    public NbsNote(final NbsLayer layer, final Instrument instrument, final byte key, final byte velocity, final short panning, final short pitch, final NbsCustomInstrument customInstrument) {
        this(layer, instrument, key, velocity, panning, pitch);
        if (instrument != null && customInstrument != null) {
            throw new IllegalArgumentException("Cannot set both instrument and custom instrument");
        }

        this.customInstrument = customInstrument;
    }

    public NbsNote(final NbsLayer layer, final Instrument instrument, final byte key, final byte velocity, final short panning, final short pitch) {
        this(layer, instrument, key);

        this.velocity = velocity;
        this.panning = panning;
        this.pitch = pitch;
    }

    public NbsNote(final NbsLayer layer, final Instrument instrument, final byte key) {
        super(instrument, key);

        this.layer = layer;
    }

    public NbsNote(final Instrument instrument, final byte key) {
        super(instrument, key);
    }

    public void write(final NbsHeader header, final NbsData data, final LittleEndianDataOutputStream dos) throws IOException {
        if (this.customInstrument != null) {
            if (!data.getCustomInstruments().contains(this.customInstrument)) {
                throw new IllegalArgumentException("Custom instrument not found in NBS data custom instruments list");
            }
            dos.writeByte((byte) (header.getVanillaInstrumentCount() + data.getCustomInstruments().indexOf(this.customInstrument)));
        } else {
            dos.writeByte(this.instrument.nbsId());
        }
        dos.writeByte(this.key);

        if (header.getVersion() >= 4) {
            dos.writeByte(this.velocity);
            dos.writeByte(this.panning);
            dos.writeShort(this.pitch);
        }
    }

    // For internal use
    void resolveCustomInstrument(final NbsHeader header, final NbsData data) {
        if (this.rawInstrumentId >= header.getVanillaInstrumentCount()) {
            this.customInstrument = data.getCustomInstruments().get(this.rawInstrumentId - header.getVanillaInstrumentCount());
        }
    }

    @Override
    public void setInstrument(final Instrument instrument) {
        super.setInstrument(instrument);
        this.customInstrument = null;
    }

    /**
     * This value is excluded from equals and hashcode.
     *
     * @return The NBS layer this note is in.
     * @since v0
     */
    public NbsLayer getLayer() {
        return this.layer;
    }

    /**
     * This value is excluded from equals and hashcode.
     *
     * @param layer The NBS layer this note is in. The layer has to be added to the layer list of the {@link NbsData} class.
     * @since v0
     */
    public void setLayer(final NbsLayer layer) {
        this.layer = layer;
    }

    /**
     * @return The custom instrument of the note if set or else null.
     * @since v0
     */
    public NbsCustomInstrument getCustomInstrument() {
        return this.customInstrument;
    }

    /**
     * @param customInstrument The custom instrument of the note. If null, the note will use the {@link #instrument} value instead.
     *                         The custom instrument has to be added to the custom instrument list of the {@link NbsData} class.
     * @since v0
     */
    public void setCustomInstrument(final NbsCustomInstrument customInstrument) {
        this.customInstrument = customInstrument;
        this.instrument = null;
    }

    /**
     * @return The velocity/volume of the note, from 0% to 100%. Factors in the layer's volume.
     * @since v4
     */
    @Override
    public float getVolume() {
        final float layerVolume = this.layer != null ? this.layer.getVolume() : 100F;
        final float noteVolume = this.velocity;
        return (layerVolume * noteVolume) / 100F;
    }

    /**
     * @param volume The velocity/volume of the note, from 0% to 100%. Does not change the layer's volume.
     * @since v4
     */
    @Override
    public void setVolume(final float volume) {
        this.velocity = (byte) volume;
    }

    public byte getRawVelocity() {
        return this.velocity;
    }

    /**
     * @return The stereo position of the note block. (-100 is 2 blocks right, 0 is center, 100 is 2 blocks left). Factors in the layer's panning.
     * @since v4
     */
    @Override
    public float getPanning() {
        final float layerPanning = this.layer != null ? (this.layer.getPanning() - 100) : 0F;
        final float notePanning = this.panning - 100;
        return (layerPanning + notePanning) / 2F;
    }

    /**
     * @param panning The stereo position of the note block. (-100 is 2 blocks right, 0 is center, 100 is 2 blocks left). Does not change the layer's panning.
     * @since v4
     */
    @Override
    public void setPanning(final float panning) {
        this.panning = (short) (panning + 100);
    }

    public short getRawPanning() {
        return this.panning;
    }

    /**
     * 100 = 1 key<br>
     * 1200 = 1 octave
     *
     * @return The fine pitch of the note block, from -32,768 to 32,767 cents (but the max in Note Block Studio is limited to -1200 and +1200). 0 is no fine-tuning. ±100 cents is a single semitone difference.
     * @since v4
     */
    public short getPitch() {
        return this.pitch;
    }

    /**
     * @param pitch The fine pitch of the note block, from -32,768 to 32,767 cents (but the max in Note Block Studio is limited to -1200 and +1200). 0 is no fine-tuning. ±100 cents is a single semitone difference.
     * @since v4
     */
    public void setPitch(final short pitch) {
        this.pitch = pitch;
    }

    @Override
    public NbsNote clone() {
        return new NbsNote(this.layer, this.instrument, this.key, this.velocity, this.panning, this.pitch, this.customInstrument);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        NbsNote nbsNote = (NbsNote) o;
        return velocity == nbsNote.velocity && panning == nbsNote.panning && pitch == nbsNote.pitch && Objects.equals(customInstrument, nbsNote.customInstrument);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), customInstrument, velocity, panning, pitch);
    }

}
