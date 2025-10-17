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
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static javax.sound.midi.ShortMessage.*;
import static net.raphimc.noteblocklib.format.midi.MidiDefinitions.*;

public class MidiIo {

    public static MidiSong readSong(final InputStream is, final String fileName) throws IOException, InvalidMidiDataException {
        return readSong(is, fileName, true);
    }

    public static MidiSong readSong(final InputStream is, final String fileName, final boolean skipOutOfRangeNotes) throws IOException, InvalidMidiDataException {
        return parseSong(MidiSystem.getSequence(is), fileName, skipOutOfRangeNotes);
    }

    public static MidiSong parseSong(final Sequence sequence, final String fileName) {
        return parseSong(sequence, fileName, true);
    }

    public static MidiSong parseSong(final Sequence sequence, final String fileName, final boolean skipOutOfRangeNotes) {
        final MidiSong song = new MidiSong(fileName);

        if (sequence.getTickLength() > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("MIDI sequence has too many ticks");
        }

        if (sequence.getDivisionType() == Sequence.PPQ) {
            song.getTempoEvents().set(0, (float) (1_000_000D / ((double) MidiDefinitions.DEFAULT_TEMPO_MPQ / sequence.getResolution())));
        } else {
            song.getTempoEvents().set(0, sequence.getResolution() * sequence.getDivisionType());
        }

        final byte[] channelInstruments = new byte[MidiDefinitions.CHANNEL_COUNT];
        final byte[] channelVolumes = new byte[MidiDefinitions.CHANNEL_COUNT];
        final byte[] channelPans = new byte[MidiDefinitions.CHANNEL_COUNT];
        Arrays.fill(channelVolumes, MidiDefinitions.MAX_VELOCITY);
        Arrays.fill(channelPans, MidiDefinitions.CENTER_PAN);

        for (int trackIdx = 0; trackIdx < sequence.getTracks().length; trackIdx++) {
            final Track track = sequence.getTracks()[trackIdx];
            for (int eventIdx = 0; eventIdx < track.size(); eventIdx++) {
                final MidiEvent event = track.get(eventIdx);
                final MidiMessage message = event.getMessage();

                if (message instanceof ShortMessage) {
                    final ShortMessage shortMessage = (ShortMessage) message;
                    switch (shortMessage.getCommand()) {
                        case NOTE_ON:
                            final byte instrument = channelInstruments[shortMessage.getChannel()];
                            final byte key = (byte) shortMessage.getData1();
                            final byte velocity = (byte) shortMessage.getData2();
                            final byte pan = channelPans[shortMessage.getChannel()];

                            final Note note = new Note();
                            if (shortMessage.getChannel() == PERCUSSION_CHANNEL) {
                                final PercussionMapping mapping = MidiMappings.PERCUSSION_MAPPINGS[key];
                                if (mapping == null) continue;

                                note.setInstrument(mapping.getInstrument());
                                note.setNbsKey(mapping.getNbsKey());
                            } else {
                                final InstrumentMapping mapping = MidiMappings.INSTRUMENT_MAPPINGS[instrument];
                                if (mapping == null) continue;

                                note.setInstrument(mapping.getInstrument());
                                note.setMidiKey(MathUtil.clamp(key + KEYS_PER_OCTAVE * mapping.getOctaveModifier(), LOWEST_KEY, HIGHEST_KEY));
                            }
                            note.setVolume(((float) velocity / MAX_VELOCITY) * (float) channelVolumes[shortMessage.getChannel()] / MAX_VELOCITY);
                            note.setPanning((float) (pan - CENTER_PAN) / CENTER_PAN);

                            if (skipOutOfRangeNotes && (note.getMidiKey() < NbsDefinitions.LOWEST_MIDI_KEY || note.getMidiKey() > NbsDefinitions.HIGHEST_MIDI_KEY)) {
                                continue;
                            }

                            song.getNotes().add((int) event.getTick(), note);
                            break;
                        case NOTE_OFF:
                            // Ignore note off events
                            break;
                        case PROGRAM_CHANGE:
                            channelInstruments[shortMessage.getChannel()] = (byte) shortMessage.getData1();
                            break;
                        case CONTROL_CHANGE:
                            switch (shortMessage.getData1()) {
                                case VOLUME_CONTROL_MSB:
                                    channelVolumes[shortMessage.getChannel()] = (byte) shortMessage.getData2();
                                    break;
                                case PAN_CONTROL_MSB:
                                    channelPans[shortMessage.getChannel()] = (byte) shortMessage.getData2();
                                    break;
                                case RESET_CONTROLS:
                                    channelVolumes[shortMessage.getChannel()] = MAX_VELOCITY;
                                    channelPans[shortMessage.getChannel()] = CENTER_PAN;
                                    break;
                            }
                            break;
                        case PITCH_BEND:
                            // Ignore pitch bend events
                            break;
                        case SYSTEM_RESET:
                            Arrays.fill(channelInstruments, (byte) 0);
                            Arrays.fill(channelVolumes, MAX_VELOCITY);
                            Arrays.fill(channelPans, CENTER_PAN);
                            break;
                    }
                } else if (message instanceof MetaMessage) {
                    final MetaMessage metaMessage = (MetaMessage) message;
                    final byte[] data = metaMessage.getData();
                    if (metaMessage.getType() == META_SET_TEMPO && data.length == 3 && sequence.getDivisionType() == Sequence.PPQ) {
                        final int newMpq = ((data[0] & 0xFF) << 16) | ((data[1] & 0xFF) << 8) | (data[2] & 0xFF);
                        final double microsPerTick = (double) newMpq / sequence.getResolution();
                        song.getTempoEvents().set((int) event.getTick(), (float) (1_000_000D / microsPerTick));
                    } else if (metaMessage.getType() == META_COPYRIGHT_NOTICE) {
                        song.setOriginalAuthor(new String(data, StandardCharsets.US_ASCII));
                    } else if (metaMessage.getType() == META_TRACK_NAME) {
                        song.getTrackNames().put(trackIdx, new String(data, StandardCharsets.US_ASCII));
                    }
                }
            }
        }

        final float maxTempo = song.getTempoEvents().getTempoRange()[1];
        if (maxTempo > SONG_TARGET_TEMPO) {
            SongResampler.changeTickSpeed(song, SONG_TARGET_TEMPO);
        }

        return song;
    }

}
