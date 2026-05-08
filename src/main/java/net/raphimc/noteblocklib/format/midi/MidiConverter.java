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
package net.raphimc.noteblocklib.format.midi;

import net.raphimc.noteblocklib.format.midi.mapping.InstrumentMapping;
import net.raphimc.noteblocklib.format.midi.mapping.MidiMappings;
import net.raphimc.noteblocklib.format.midi.mapping.PercussionMapping;
import net.raphimc.noteblocklib.format.midi.model.MidiSong;
import net.raphimc.noteblocklib.format.nbs.NbsDefinitions;
import net.raphimc.noteblocklib.model.note.Note;
import net.raphimc.noteblocklib.util.MathUtil;
import net.raphimc.noteblocklib.util.SongResampler;

import javax.sound.midi.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static javax.sound.midi.ShortMessage.*;
import static net.raphimc.noteblocklib.format.midi.MidiDefinitions.*;

public class MidiConverter {

    /**
     * Creates a new MIDI song from a MIDI sequence.
     *
     * @param sequence The MIDI sequence
     * @param fileName The name of the file the MIDI sequence was read from or null
     * @return The new MIDI song
     */
    public static MidiSong createSong(final Sequence sequence, final String fileName) {
        return createSong(sequence, fileName, true);
    }

    /**
     * Creates a new MIDI song from a MIDI sequence.
     *
     * @param sequence               The MIDI sequence
     * @param fileName               The name of the file the MIDI sequence was read from or null.
     * @param skipOutOfNbsRangeNotes Whether to skip notes that are out of the NBS key range
     * @return The new MIDI song
     */
    public static MidiSong createSong(final Sequence sequence, final String fileName, final boolean skipOutOfNbsRangeNotes) {
        if (sequence.getTickLength() > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("MIDI sequence has too many ticks");
        }

        final MidiSong song = new MidiSong(fileName);
        if (sequence.getDivisionType() == Sequence.PPQ) {
            song.getTempoEvents().set(0, (float) (1_000_000D / ((double) DEFAULT_TEMPO_MPQ / sequence.getResolution())));
        } else {
            song.getTempoEvents().set(0, sequence.getResolution() * sequence.getDivisionType());
        }

        final byte[] channelInstruments = new byte[CHANNEL_COUNT];
        final byte[] channelVolumes = new byte[CHANNEL_COUNT];
        final byte[] channelPans = new byte[CHANNEL_COUNT];
        final byte[] channelExpressions = new byte[CHANNEL_COUNT];
        Arrays.fill(channelVolumes, DEFAULT_VOLUME);
        Arrays.fill(channelPans, CENTER_PAN);
        Arrays.fill(channelExpressions, Byte.MAX_VALUE);

        for (int trackIdx = 0; trackIdx < sequence.getTracks().length; trackIdx++) {
            final Track track = sequence.getTracks()[trackIdx];
            for (int eventIdx = 0; eventIdx < track.size(); eventIdx++) {
                final MidiEvent event = track.get(eventIdx);
                final MidiMessage message = event.getMessage();

                if (message instanceof ShortMessage) {
                    final ShortMessage shortMessage = (ShortMessage) message;
                    switch (shortMessage.getCommand()) {
                        case NOTE_ON:
                            final byte key = (byte) MathUtil.clamp(shortMessage.getData1(), LOWEST_KEY, HIGHEST_KEY);
                            final byte velocity = (byte) MathUtil.clamp(shortMessage.getData2(), 0, MAX_VELOCITY);
                            final byte instrument = channelInstruments[shortMessage.getChannel()];
                            final byte volume = channelVolumes[shortMessage.getChannel()];
                            final byte pan = channelPans[shortMessage.getChannel()];
                            final byte expression = channelExpressions[shortMessage.getChannel()];

                            final Note note = new Note();
                            if (shortMessage.getChannel() == PERCUSSION_CHANNEL) {
                                final PercussionMapping mapping = MidiMappings.PERCUSSION_MAPPINGS[key];
                                if (mapping == null) {
                                    continue;
                                }

                                note.setInstrument(mapping.getInstrument());
                                note.setNbsKey(mapping.getNbsKey());
                            } else {
                                final InstrumentMapping mapping = MidiMappings.INSTRUMENT_MAPPINGS[instrument];
                                if (mapping == null) {
                                    continue;
                                }

                                note.setInstrument(mapping.getInstrument());
                                note.setMidiKey(MathUtil.clamp(key + KEYS_PER_OCTAVE * mapping.getOctaveModifier(), LOWEST_KEY, HIGHEST_KEY));
                            }
                            if (skipOutOfNbsRangeNotes && (note.getMidiKey() < NbsDefinitions.LOWEST_MIDI_KEY || note.getMidiKey() > NbsDefinitions.HIGHEST_MIDI_KEY)) {
                                continue;
                            }
                            note.setVolume(((float) velocity / MAX_VELOCITY) * ((float) volume / MAX_VELOCITY) * ((float) expression / MAX_VELOCITY));
                            if (pan < CENTER_PAN) { // 0-63 (64 values) -> left
                                note.setPanning((float) (pan - CENTER_PAN) / CENTER_PAN);
                            } else if (pan > CENTER_PAN) { // 65-127 (63 values) -> right
                                note.setPanning((float) (pan - CENTER_PAN) / (Byte.MAX_VALUE - CENTER_PAN));
                            }
                            song.getNotes().add((int) event.getTick(), note);
                            break;
                        case NOTE_OFF:
                            // Ignore note off events
                            break;
                        case PROGRAM_CHANGE:
                            channelInstruments[shortMessage.getChannel()] = (byte) Math.max((byte) shortMessage.getData1(), 0);
                            break;
                        case CONTROL_CHANGE:
                            switch (shortMessage.getData1()) {
                                case CONTROL_CHANNEL_VOLUME_MSB:
                                    channelVolumes[shortMessage.getChannel()] = (byte) MathUtil.clamp(shortMessage.getData2(), 0, MAX_VELOCITY);
                                    break;
                                case CONTROL_PAN_MSB:
                                    channelPans[shortMessage.getChannel()] = (byte) MathUtil.clamp(shortMessage.getData2(), 0, Byte.MAX_VALUE);
                                    break;
                                case CONTROL_EXPRESSION_CONTROLLER_MSB:
                                    channelExpressions[shortMessage.getChannel()] = (byte) MathUtil.clamp(shortMessage.getData2(), 0, Byte.MAX_VALUE);
                                    break;
                                case CONTROL_RESET_ALL_CONTROLLERS:
                                    // Most MIDI synths don't reset volume and pan
                                    channelExpressions[shortMessage.getChannel()] = Byte.MAX_VALUE;
                                    break;
                            }
                            break;
                        case PITCH_BEND:
                            // Ignore pitch bend events
                            break;
                        case CHANNEL_PRESSURE:
                            // Ignore channel pressure events
                            break;
                        case POLY_PRESSURE:
                            // Ignore poly pressure events
                            break;
                        default:
                            throw new IllegalStateException("Unsupported MIDI command: " + shortMessage.getCommand());
                    }
                } else if (message instanceof MetaMessage) {
                    final MetaMessage metaMessage = (MetaMessage) message;
                    final byte[] data = metaMessage.getData();
                    switch (metaMessage.getType()) {
                        case META_SET_TEMPO:
                            if (data.length == 3 && sequence.getDivisionType() == Sequence.PPQ) {
                                final int newMpq = ((data[0] & 0xFF) << 16) | ((data[1] & 0xFF) << 8) | (data[2] & 0xFF);
                                final double microsPerTick = (double) newMpq / sequence.getResolution();
                                song.getTempoEvents().set((int) event.getTick(), (float) (1_000_000D / microsPerTick));
                            }
                            break;
                        case META_TEXT:
                            final String text = Arrays.stream(new String(data, StandardCharsets.US_ASCII).split("\n"))
                                    .map(String::trim)
                                    .filter(line -> !line.isEmpty())
                                    .map(line -> "Text: " + line)
                                    .collect(Collectors.joining("\n"));
                            if (!text.isEmpty()) {
                                if (song.getDescription() == null) {
                                    song.setDescription(text);
                                } else {
                                    song.setDescription(song.getDescription() + "\n" + text);
                                }
                            }
                            break;
                        case META_COPYRIGHT_NOTICE:
                            final String copyright = Arrays.stream(new String(data, StandardCharsets.US_ASCII).split("\n"))
                                    .map(String::trim)
                                    .filter(line -> !line.isEmpty())
                                    .map(line -> "Copyright: " + line)
                                    .collect(Collectors.joining("\n"));
                            if (!copyright.isEmpty()) {
                                if (song.getDescription() == null) {
                                    song.setDescription(copyright);
                                } else {
                                    song.setDescription(song.getDescription() + "\n" + copyright);
                                }
                            }
                            break;
                        case META_TRACK_NAME:
                            final String trackName = Arrays.stream(new String(data, StandardCharsets.US_ASCII).split("\n"))
                                    .map(String::trim)
                                    .filter(line -> !line.isEmpty())
                                    .map(line -> "Track Name: " + line)
                                    .collect(Collectors.joining("\n"));
                            if (!trackName.isEmpty()) {
                                if (song.getDescription() == null) {
                                    song.setDescription(trackName);
                                } else {
                                    song.setDescription(song.getDescription() + "\n" + trackName);
                                }
                            }
                            break;
                    }
                } else if (message instanceof SysexMessage) {
                    final SysexMessage sysexMessage = (SysexMessage) message;
                    if (sysexMessage.getStatus() == SysexMessage.SYSTEM_EXCLUSIVE) {
                        final byte[] data = sysexMessage.getData();
                        if (data.length == 4 && (data[0] & 0xFF) == SYSEX_UNIVERSAL_NON_REALTIME_MESSAGE && (data[1] & 0xFF) == SYSEX_DEVICE_ALL && (data[2] & 0xFF) == SYSEX_SUB_ID_GENERAL_MIDI) {
                            final int subId2 = data[3] & 0xFF;
                            if (subId2 == SYSEX_GENERAL_MIDI_GM1_SYSTEM_ON || subId2 == SYSEX_GENERAL_MIDI_GM2_SYSTEM_ON) {
                                Arrays.fill(channelInstruments, (byte) 0);
                                Arrays.fill(channelVolumes, DEFAULT_VOLUME);
                                Arrays.fill(channelPans, CENTER_PAN);
                                Arrays.fill(channelExpressions, Byte.MAX_VALUE);
                            }
                        }
                    }
                } else {
                    throw new IllegalStateException("Unsupported MIDI message type: " + message.getClass().getName());
                }
            }
        }

        if (song.getTempoEvents().getTempoRange()[1] > SONG_TARGET_TEMPO) {
            final double[] times = SongResampler.getNotesByTime(song).keySet().stream().mapToDouble(Double::doubleValue).sorted().toArray();
            final double[] timeSpaces = IntStream.range(1, times.length).mapToDouble(i -> times[i] - times[i - 1]).sorted().toArray();
            if (timeSpaces.length > 0) {
                final float minTimeSpace = (float) timeSpaces[0];
                final float p05TimeSpace = (float) timeSpaces[timeSpaces.length / 20];
                final float p10TimeSpace = (float) timeSpaces[timeSpaces.length / 10];
                final float[] candidateTempos = new float[]{1000F / minTimeSpace, 1000F / p05TimeSpace, 1000F / p10TimeSpace};
                for (float candidateTempo : candidateTempos) {
                    if (candidateTempo <= SONG_TARGET_TEMPO) {
                        SongResampler.changeTickSpeed(song, candidateTempo);
                        break;
                    }
                }
            }
            if (song.getTempoEvents().getTempoRange()[1] > SONG_TARGET_TEMPO) {
                SongResampler.changeTickSpeed(song, SONG_TARGET_TEMPO);
            }
        }

        return song;
    }

}
