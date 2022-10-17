package net.raphimc.noteblocklib.midi;

import com.google.common.base.Preconditions;

import java.util.Objects;

public class MidiInstrument {

    private final int mcInstrument;
    private final int octaveModifier;
    private final boolean isLongSound;

    public MidiInstrument(final int mcInstrument, final int octaveModifier, final boolean isLongSound) {
        Preconditions.checkArgument(mcInstrument >= -1);
        Preconditions.checkArgument(mcInstrument <= 15);
        this.mcInstrument = mcInstrument;
        this.octaveModifier = octaveModifier;
        this.isLongSound = isLongSound;
    }

    public int mcInstrument() {
        return this.mcInstrument;
    }

    public int octaveModifier() {
        return this.octaveModifier;
    }

    public boolean isLongSound() {
        return this.isLongSound;
    }

    @Override
    public String toString() {
        return "MidiInstrument{" +
                "mcInstrument=" + mcInstrument +
                ", octaveModifier=" + octaveModifier +
                ", isLongSound=" + isLongSound +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MidiInstrument that = (MidiInstrument) o;
        return mcInstrument == that.mcInstrument && octaveModifier == that.octaveModifier && isLongSound == that.isLongSound;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mcInstrument, octaveModifier, isLongSound);
    }

}
