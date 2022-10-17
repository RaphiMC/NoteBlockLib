package net.raphimc.noteblocklib.midi;

import net.raphimc.noteblocklib.util.Instrument;

import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class MidiConverter {

    private static final boolean V2 = true;

    public static String readMidi(final File file) throws InvalidMidiDataException, IOException {
        final Map<Long, String> notes = new TreeMap<>();
        final ArrayList<String> relativeNotes = new ArrayList<>();

        final Sequence sequence = MidiSystem.getSequence(file);
        final double milliLength = (double) (sequence.getMicrosecondLength() / 1000L);
        final double tickLength = (double) sequence.getTickLength();
        for (Track t : sequence.getTracks()) {
            int data = 0;
            long lastTick = 0L;
            int lastChannel = -1;

            for (int i = 0; i < t.size(); i++) {
                final MidiEvent event = t.get(i);
                final MidiMessage message = event.getMessage();
                if (message instanceof ShortMessage) {
                    ShortMessage shortMessage = (ShortMessage) message;
                    if (shortMessage.getCommand() == ShortMessage.NOTE_ON) {
                        int key = shortMessage.getData1();
                        int inst = !V2 ? MidiInstruments.instrumentMapping.get(data).mcInstrument() : (shortMessage.getChannel() != 9 ? MidiInstruments.instrumentMapping.get(data).mcInstrument() : MidiInstruments.percussionMapping.get(key).mcInstrument());
                        long tick = (long) ((double) event.getTick() * milliLength / tickLength);
                        if (inst > -1) {
                            int fixKey = key - 21;
                            int maxFixKey = fixKey - 33;
                            if (lastTick != tick || lastChannel != shortMessage.getChannel()) {
                                if (shortMessage.getChannel() != 9) {
                                    if (MidiInstruments.instrumentMapping.get(data).octaveModifier() != 0) {
                                        maxFixKey = maxFixKey + 12 * MidiInstruments.instrumentMapping.get(data).octaveModifier();
                                    }
                                    while (maxFixKey < 0) maxFixKey += 12;
                                    while (maxFixKey > 24) maxFixKey -= 12;
                                } else {
                                    if (!V2) inst = MidiInstruments.percussionMapping.get(key).mcInstrument();
                                    maxFixKey = MidiInstruments.percussionMapping.get(key).mcKey();
                                }
                                final String currentNotes = notes.get(tick);
                                notes.put(tick, currentNotes == null ? inst + "::#" + maxFixKey : currentNotes + "_" + inst + "::#" + maxFixKey);
                            }
                        }

                        lastTick = tick;
                        lastChannel = shortMessage.getChannel();
                    } else if (shortMessage.getCommand() == ShortMessage.PROGRAM_CHANGE) {
                        data = shortMessage.getData1();
                    }
                }
            }
        }

        long lastTime = 0L;
        for (long time : notes.keySet()) {
            relativeNotes.add((time - lastTime) + "!" + notes.get(time));
            lastTime = time;
        }

        final List<String> output = new ArrayList<>();
        output.add("###80");
        int time = 0;
        for (String scrPart : relativeNotes) {
            final int delay = Integer.parseInt(scrPart.split("!")[0]);
            time += delay;
            scrPart = scrPart.split("!")[1];
            final String[] parts = scrPart.split("_");
            for (String part : parts) {
                final int inst = Instrument.fromNbsId(Byte.parseByte(part.split("::#")[0])).mcId();
                final String pitch = part.split("::#")[1];
                output.add((int) (time / 12.5F) + ":" + pitch + ":" + inst);
            }
        }

        return String.join("\n", output);
    }

}
