package net.raphimc.noteblocklib.parser.nbs.data;

import com.google.common.io.LittleEndianDataInputStream;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.raphimc.noteblocklib.parser.Data;
import net.raphimc.noteblocklib.parser.nbs.data.layer.*;
import net.raphimc.noteblocklib.parser.nbs.header.NBSv0Header;
import net.raphimc.noteblocklib.parser.nbs.note.*;
import net.raphimc.noteblocklib.util.Instrument;

import java.io.EOFException;
import java.io.IOException;

import static net.raphimc.noteblocklib.parser.nbs.NBSParser.readString;

public class NBSData extends Data {

    private Int2ObjectMap<NBSLayer> layers;
    private NBSv0CustomInstrument[] customInstruments;

    public NBSData(final NBSv0Header header, final LittleEndianDataInputStream dis) throws IOException {
        super();

        this.layers = new Int2ObjectOpenHashMap<>();
        final int customInstrumentDiff = Instrument.values().length - header.getVanillaInstrumentCount();
        header.setVanillaInstrumentCount((byte) Instrument.values().length);

        int tick = -1;
        while (true) {
            final short jumpTicks = dis.readShort();
            if (jumpTicks == 0) {
                break;
            }
            tick += jumpTicks;
            int layer = -1;
            while (true) {
                short jumpLayers = dis.readShort();
                if (jumpLayers == 0) {
                    break;
                }
                layer += jumpLayers;
                final NBSv0Note note = header.getNbsVersion() >= 4 ? new NBSv4Note(dis) : new NBSv0Note(dis);
                if (note.getInstrument() >= header.getVanillaInstrumentCount() && customInstrumentDiff > 0) {
                    note.setInstrument((byte) (note.getInstrument() + customInstrumentDiff));
                }
                this.layers.computeIfAbsent(layer, k -> new NBSLayer(new Int2ObjectOpenHashMap<>())).getNotesAtTick().put(tick, note);
                note.setLayer(this.layers.get(layer));
            }
        }

        try {
            for (int i = 0; i < header.getLayerCount(); i++) {
                final String name = readString(dis);
                final boolean locked = header.getNbsVersion() >= 4 && dis.readBoolean();
                final byte volume = dis.readByte();
                final short panning = (short) (header.getNbsVersion() >= 2 ? dis.readUnsignedByte() : 100);

                final NBSLayer layer = this.layers.get(i);
                if (layer == null) continue;
                if (header.getNbsVersion() >= 4) {
                    this.layers.put(i, new NBSv4Layer(layer.getNotesAtTick(), name, volume, panning, locked));
                } else if (header.getNbsVersion() >= 2) {
                    this.layers.put(i, new NBSv2Layer(layer.getNotesAtTick(), name, volume, panning));
                } else {
                    this.layers.put(i, new NBSv0Layer(layer.getNotesAtTick(), name, volume));
                }
                for (NBSNote note : this.layers.get(i).getNotesAtTick().values()) {
                    note.setLayer(this.layers.get(i));
                }
            }
        } catch (EOFException ignored) {
        }
        NBSv0CustomInstrument[] customInstruments;
        try {
            final int customInstrumentsAmount = dis.readUnsignedByte();
            customInstruments = new NBSv0CustomInstrument[customInstrumentsAmount];
            for (int i = 0; i < customInstrumentsAmount; i++) {
                customInstruments[i] = new NBSv0CustomInstrument(dis);
            }
        } catch (EOFException ignored) {
            customInstruments = new NBSv0CustomInstrument[0];
        }
        this.customInstruments = customInstruments;
    }

    public NBSData(final Int2ObjectMap<NBSLayer> layers, final NBSv0CustomInstrument[] customInstruments) {
        super();

        this.layers = layers;
        this.customInstruments = customInstruments;
    }

    public void setLayers(final Int2ObjectMap<NBSLayer> layers) {
        this.layers = layers;
    }

    public Int2ObjectMap<NBSLayer> getLayers() {
        return this.layers;
    }

    public void setCustomInstruments(final NBSv0CustomInstrument[] customInstruments) {
        this.customInstruments = customInstruments;
    }

    public NBSv0CustomInstrument[] getCustomInstruments() {
        return this.customInstruments;
    }

}
