package net.raphimc.noteblocklib.format.midi.data;

import net.raphimc.noteblocklib.format.midi.header.MidiHeader;
import net.raphimc.noteblocklib.format.midi.mapping.InstrumentMapping;
import net.raphimc.noteblocklib.format.midi.mapping.MidiMappings;
import net.raphimc.noteblocklib.format.midi.mapping.PercussionMapping;
import net.raphimc.noteblocklib.format.midi.note.MidiNote;
import net.raphimc.noteblocklib.model.Data;

import javax.sound.midi.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static javax.sound.midi.ShortMessage.NOTE_ON;
import static javax.sound.midi.ShortMessage.PROGRAM_CHANGE;
import static net.raphimc.noteblocklib.format.midi.MidiDefinitions.*;
import static net.raphimc.noteblocklib.format.nbs.NBSDefinitions.*;

public class MidiData implements Data {

    private Map<Integer, List<MidiNote>> notes;

    public MidiData(final MidiHeader header, final InputStream is) throws InvalidMidiDataException, IOException {
        if (header.getMidiFileFormat().getType() != 0 && header.getMidiFileFormat().getType() != 1) {
            throw new IllegalArgumentException("Midi file type must be 0 or 1");
        }
        if (header.getMidiFileFormat().getDivisionType() != Sequence.PPQ) {
            throw new IllegalArgumentException("Midi file division type must be PPQ");
        }

        final Sequence sequence = MidiSystem.getSequence(is);
        this.notes = new HashMap<>();

        final List<TempoEvent> tempoEvents = new ArrayList<>();
        for (int trackIdx = 0; trackIdx < sequence.getTracks().length; trackIdx++) {
            final Track track = sequence.getTracks()[trackIdx];
            for (int eventIdx = 0; eventIdx < track.size(); eventIdx++) {
                final MidiEvent event = track.get(eventIdx);
                final MidiMessage message = event.getMessage();
                if (message instanceof MetaMessage) {
                    final MetaMessage metaMessage = (MetaMessage) message;
                    if (metaMessage.getType() == SET_TEMPO) {
                        tempoEvents.add(new TempoEvent(event.getTick(), (double) this.getTempoMPQ(metaMessage) / sequence.getResolution()));
                    }
                }

            }
        }
        if (tempoEvents.isEmpty() || tempoEvents.get(0).getTick() != 0) {
            tempoEvents.add(0, new TempoEvent(0L, (double) DEFAULT_TEMPO_MPQ / sequence.getResolution()));
        }
        tempoEvents.sort(Comparator.comparingLong(TempoEvent::getTick));

        final byte[] channelInstruments = new byte[CHANNELS];

        for (int trackIdx = 0; trackIdx < sequence.getTracks().length; trackIdx++) {
            final Track track = sequence.getTracks()[trackIdx];
            double microTime = 0;
            long lastTick = 0;
            double tempo = tempoEvents.get(0).getTempo();
            int tempoEventIdx = 1;

            for (int eventIdx = 0; eventIdx < track.size(); eventIdx++) {
                final MidiEvent event = track.get(eventIdx);
                final MidiMessage message = event.getMessage();

                while (tempoEventIdx < tempoEvents.size() && event.getTick() > tempoEvents.get(tempoEventIdx).getTick()) {
                    final TempoEvent tempoEvent = tempoEvents.get(tempoEventIdx++);
                    microTime += (tempoEvent.getTick() - lastTick) * tempo;
                    lastTick = tempoEvent.getTick();
                    tempo = tempoEvent.getTempo();
                }
                microTime += (event.getTick() - lastTick) * tempo;
                lastTick = event.getTick();

                if (message instanceof ShortMessage) {
                    final ShortMessage shortMessage = (ShortMessage) message;
                    final byte instrument = channelInstruments[shortMessage.getChannel()];

                    if (shortMessage.getCommand() == PROGRAM_CHANGE) {
                        channelInstruments[shortMessage.getChannel()] = (byte) shortMessage.getData1();
                    } else if (shortMessage.getCommand() == NOTE_ON) {
                        final byte key = (byte) shortMessage.getData1();
                        final byte velocity = (byte) shortMessage.getData2();

                        final MidiNote note;
                        if (shortMessage.getChannel() == PERCUSSION_CHANNEL) {
                            final PercussionMapping mapping = MidiMappings.PERCUSSION_MAPPINGS.get(key);
                            if (mapping == null) continue;

                            note = new MidiNote(mapping.getInstrument().nbsId(), mapping.getKey(), velocity);
                        } else {
                            final InstrumentMapping mapping = MidiMappings.INSTRUMENT_MAPPINGS.get(instrument);
                            if (mapping == null) continue;

                            final int transposedKey = key - NBS_KEY_OFFSET + KEYS_PER_OCTAVE * mapping.getOctaveModifier();
                            final byte clampedKey = (byte) Math.max(NBS_LOWEST_KEY, Math.min(transposedKey, NBS_HIGHEST_KEY));
                            note = new MidiNote(mapping.getInstrument().nbsId(), clampedKey, velocity);
                        }

                        final int calculatedTick = (int) Math.round(microTime / 1_000_000D * SONG_TICKS_PER_SECOND);
                        this.notes.computeIfAbsent(calculatedTick, k -> new ArrayList<>()).add(note);
                    }
                }
            }
        }
    }

    public MidiData(final Map<Integer, List<MidiNote>> notes) {
        this.notes = notes;
    }

    public Map<Integer, List<MidiNote>> getNotes() {
        return this.notes;
    }

    public void setNotes(final Map<Integer, List<MidiNote>> notes) {
        this.notes = notes;
    }

    private int getTempoMPQ(final MetaMessage metaMessage) {
        if (metaMessage.getData().length != 3) {
            return -1;
        }

        return ((metaMessage.getData()[0] & 0xFF) << 16) | ((metaMessage.getData()[1] & 0xFF) << 8) | (metaMessage.getData()[2] & 0xFF);
    }

    private static class TempoEvent {
        private final long tick;
        private final double tempo;

        public TempoEvent(final long tick, final double tempo) {
            this.tick = tick;
            this.tempo = tempo;
        }

        public long getTick() {
            return this.tick;
        }

        public double getTempo() {
            return this.tempo;
        }
    }

}
