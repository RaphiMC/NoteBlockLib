package net.raphimc.noteblocklib.midi;

import com.google.common.base.Preconditions;

import java.util.Objects;

public class MidiPercussion {

    public final int mcInstrument;
    public final int mcKey;

    public MidiPercussion(final int mcInstrument, final int mcKey) {
        Preconditions.checkArgument(mcInstrument >= -1);
        Preconditions.checkArgument(mcInstrument <= 15);
        this.mcInstrument = mcInstrument;
        this.mcKey = mcKey;
    }

    public int mcInstrument() {
        return this.mcInstrument;
    }

    public int mcKey() {
        return this.mcKey;
    }

    @Override
    public String toString() {
        return "MidiPercussion{" +
                "mcInstrument=" + mcInstrument +
                ", mcKey=" + mcKey +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MidiPercussion that = (MidiPercussion) o;
        return mcInstrument == that.mcInstrument && mcKey == that.mcKey;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mcInstrument, mcKey);
    }

}
