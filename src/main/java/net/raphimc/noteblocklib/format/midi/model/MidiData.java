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
package net.raphimc.noteblocklib.format.midi.model;

import net.raphimc.noteblocklib.format.midi.mapping.InstrumentMapping;
import net.raphimc.noteblocklib.format.midi.mapping.MidiMappings;
import net.raphimc.noteblocklib.format.midi.mapping.PercussionMapping;
import net.raphimc.noteblocklib.model.NotemapData;

import javax.sound.midi.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static javax.sound.midi.ShortMessage.*;
import static net.raphimc.noteblocklib.format.midi.MidiDefinitions.*;
import static net.raphimc.noteblocklib.format.nbs.NbsDefinitions.*;

public class MidiData extends NotemapData<MidiNote> {

    public MidiData(final MidiHeader header, final InputStream is) throws InvalidMidiDataException, IOException {
        if (header.getMidiFileFormat().getType() != 0 && header.getMidiFileFormat().getType() != 1) {
            throw new IllegalArgumentException("Midi file type must be 0 or 1");
        }
        if (header.getMidiFileFormat().getDivisionType() != Sequence.PPQ) {
            throw new IllegalArgumentException("Midi file division type must be PPQ");
        }
        final Sequence sequence = MidiSystem.getSequence(is);

        final List<TempoEvent> tempoEvents = new ArrayList<>();
        for (int trackIdx = 0; trackIdx < sequence.getTracks().length; trackIdx++) {
            final Track track = sequence.getTracks()[trackIdx];
            for (int eventIdx = 0; eventIdx < track.size(); eventIdx++) {
                final MidiEvent event = track.get(eventIdx);
                final MidiMessage message = event.getMessage();
                if (message instanceof MetaMessage) {
                    final MetaMessage metaMessage = (MetaMessage) message;
                    if (metaMessage.getType() == SET_TEMPO && metaMessage.getData().length == 3) {
                        final int newMpq = ((metaMessage.getData()[0] & 0xFF) << 16) | ((metaMessage.getData()[1] & 0xFF) << 8) | (metaMessage.getData()[2] & 0xFF);
                        tempoEvents.add(new TempoEvent(event.getTick(), (double) newMpq / sequence.getResolution()));
                    }
                }

            }
        }
        if (tempoEvents.isEmpty() || tempoEvents.get(0).getTick() != 0) {
            tempoEvents.add(0, new TempoEvent(0L, (double) DEFAULT_TEMPO_MPQ / sequence.getResolution()));
        }
        tempoEvents.sort(Comparator.comparingLong(TempoEvent::getTick));

        final byte[] channelInstruments = new byte[CHANNELS];
        final byte[] channelVolumes = new byte[CHANNELS];
        final byte[] channelPans = new byte[CHANNELS];
        Arrays.fill(channelVolumes, MAX_VELOCITY);
        Arrays.fill(channelPans, CENTER_PAN);

        for (int trackIdx = 0; trackIdx < sequence.getTracks().length; trackIdx++) {
            final Track track = sequence.getTracks()[trackIdx];

            double microTime = 0;
            long lastTick = 0;
            double microsPerTick = tempoEvents.get(0).getMicrosPerTick();
            int tempoEventIdx = 1;
            for (int eventIdx = 0; eventIdx < track.size(); eventIdx++) {
                final MidiEvent event = track.get(eventIdx);
                final MidiMessage message = event.getMessage();

                while (tempoEventIdx < tempoEvents.size() && event.getTick() > tempoEvents.get(tempoEventIdx).getTick()) {
                    final TempoEvent tempoEvent = tempoEvents.get(tempoEventIdx++);
                    microTime += (tempoEvent.getTick() - lastTick) * microsPerTick;
                    lastTick = tempoEvent.getTick();
                    microsPerTick = tempoEvent.getMicrosPerTick();
                }
                microTime += (event.getTick() - lastTick) * microsPerTick;
                lastTick = event.getTick();

                if (message instanceof ShortMessage) {
                    final ShortMessage shortMessage = (ShortMessage) message;
                    if (shortMessage.getCommand() == NOTE_ON) {
                        final byte instrument = channelInstruments[shortMessage.getChannel()];
                        final byte key = (byte) shortMessage.getData1();
                        final byte velocity = (byte) shortMessage.getData2();
                        final byte effectiveVelocity = (byte) ((float) velocity * channelVolumes[shortMessage.getChannel()] / MAX_VELOCITY);
                        final byte pan = channelPans[shortMessage.getChannel()];

                        final MidiNote note;
                        if (shortMessage.getChannel() == PERCUSSION_CHANNEL) {
                            final PercussionMapping mapping = MidiMappings.PERCUSSION_MAPPINGS.get(key);
                            if (mapping == null) continue;

                            note = new MidiNote(event.getTick(), mapping.getInstrument().nbsId(), mapping.getKey(), effectiveVelocity, pan);
                        } else {
                            final InstrumentMapping mapping = MidiMappings.INSTRUMENT_MAPPINGS.get(instrument);
                            if (mapping == null) continue;

                            final int transposedKey = key - NBS_KEY_OFFSET + KEYS_PER_OCTAVE * mapping.getOctaveModifier();
                            final byte clampedKey = (byte) Math.max(NBS_LOWEST_KEY, Math.min(transposedKey, NBS_HIGHEST_KEY));
                            note = new MidiNote(event.getTick(), mapping.getInstrument().nbsId(), clampedKey, effectiveVelocity, pan);
                        }

                        this.notes.computeIfAbsent((int) Math.round(microTime * SONG_TICKS_PER_SECOND / 1_000_000D), k -> new ArrayList<>()).add(note);
                    } else if (shortMessage.getCommand() == PROGRAM_CHANGE) {
                        channelInstruments[shortMessage.getChannel()] = (byte) shortMessage.getData1();
                    } else if (shortMessage.getCommand() == CONTROL_CHANGE) {
                        if (shortMessage.getData1() == VOLUME_CONTROL_MSB) {
                            channelVolumes[shortMessage.getChannel()] = (byte) shortMessage.getData2();
                        } else if (shortMessage.getData1() == PAN_CONTROL_MSB) {
                            channelPans[shortMessage.getChannel()] = (byte) shortMessage.getData2();
                        } else if (shortMessage.getData1() == RESET_CONTROLS) {
                            channelVolumes[shortMessage.getChannel()] = MAX_VELOCITY;
                            channelPans[shortMessage.getChannel()] = CENTER_PAN;
                        }
                    } else if (shortMessage.getCommand() == SYSTEM_RESET) {
                        Arrays.fill(channelInstruments, (byte) 0);
                        Arrays.fill(channelVolumes, MAX_VELOCITY);
                        Arrays.fill(channelPans, CENTER_PAN);
                    }
                }
            }
        }
    }

    public MidiData(final Map<Integer, List<MidiNote>> notes) {
        super(notes);
    }

    private static class TempoEvent {
        private final long tick;
        private final double microsPerTick;

        public TempoEvent(final long tick, final double microsPerTick) {
            this.tick = tick;
            this.microsPerTick = microsPerTick;
        }

        public long getTick() {
            return this.tick;
        }

        public double getMicrosPerTick() {
            return this.microsPerTick;
        }
    }

}
