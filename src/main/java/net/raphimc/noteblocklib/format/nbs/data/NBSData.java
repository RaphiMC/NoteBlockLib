package net.raphimc.noteblocklib.format.nbs.data;

import com.google.common.io.LittleEndianDataInputStream;
import net.raphimc.noteblocklib.format.nbs.data.layer.NBSLayer;
import net.raphimc.noteblocklib.format.nbs.data.layer.NBSv0Layer;
import net.raphimc.noteblocklib.format.nbs.data.layer.NBSv2Layer;
import net.raphimc.noteblocklib.format.nbs.data.layer.NBSv4Layer;
import net.raphimc.noteblocklib.format.nbs.header.NBSv0Header;
import net.raphimc.noteblocklib.format.nbs.note.NBSNote;
import net.raphimc.noteblocklib.format.nbs.note.NBSv0Note;
import net.raphimc.noteblocklib.format.nbs.note.NBSv4Note;
import net.raphimc.noteblocklib.model.Data;
import net.raphimc.noteblocklib.util.Instrument;

import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.raphimc.noteblocklib.format.nbs.NBSParser.readString;

public class NBSData implements Data {

    private Map<Integer, NBSLayer> layers;
    private List<NBSv0CustomInstrument> customInstruments;

    @SuppressWarnings("UnstableApiUsage")
    public NBSData(final NBSv0Header header, final LittleEndianDataInputStream dis) throws IOException {
        this.layers = new HashMap<>();
        this.customInstruments = new ArrayList<>();

        final int customInstrumentDiff = Instrument.values().length - header.getVanillaInstrumentCount();
        header.setVanillaInstrumentCount((byte) Instrument.values().length);

        int tick = -1;
        while (true) {
            final short jumpTicks = dis.readShort();
            if (jumpTicks == 0) break;
            tick += jumpTicks;

            int layer = -1;
            while (true) {
                final short jumpLayers = dis.readShort();
                if (jumpLayers == 0) break;
                layer += jumpLayers;

                final NBSv0Note note = header.getNbsVersion() >= 4 ? new NBSv4Note(dis) : new NBSv0Note(dis);
                if (note.getInstrument() >= header.getVanillaInstrumentCount() && customInstrumentDiff > 0) {
                    note.setInstrument((byte) (note.getInstrument() + customInstrumentDiff));
                }
                this.layers.computeIfAbsent(layer, k -> new NBSLayer(new HashMap<>())).getNotesAtTick().put(tick, note);
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

        try {
            final int customInstrumentsAmount = dis.readUnsignedByte();
            for (int i = 0; i < customInstrumentsAmount; i++) {
                this.customInstruments.add(new NBSv0CustomInstrument(dis));
            }
        } catch (EOFException ignored) {
        }
    }

    public NBSData(final Map<Integer, NBSLayer> layers, final List<NBSv0CustomInstrument> customInstruments) {
        this.layers = layers;
        this.customInstruments = customInstruments;
    }

    public Map<Integer, NBSLayer> getLayers() {
        return this.layers;
    }

    public void setLayers(final Map<Integer, NBSLayer> layers) {
        this.layers = layers;
    }

    public List<NBSv0CustomInstrument> getCustomInstruments() {
        return this.customInstruments;
    }

    public void setCustomInstruments(final List<NBSv0CustomInstrument> customInstruments) {
        this.customInstruments = customInstruments;
    }

}
