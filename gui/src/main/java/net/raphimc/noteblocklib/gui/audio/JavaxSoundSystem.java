/*
 * This file is part of NoteBlockLib - https://github.com/RaphiMC/NoteBlockLib
 * Copyright (C) 2022-2024 RK_01/RaphiMC and contributors
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
package net.raphimc.noteblocklib.gui.audio;

import com.google.common.io.ByteStreams;
import com.google.common.io.LittleEndianDataOutputStream;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import net.raphimc.noteblocklib.util.Instrument;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class JavaxSoundSystem {

    private static final Map<Instrument, Sound> SOUNDS = new HashMap<>();
    private static final ScheduledExecutorService SCHEDULER = Executors.newSingleThreadScheduledExecutor(new ThreadFactoryBuilder().setNameFormat("Javax Sound System").setDaemon(true).build());
    private static final List<Clip> PLAYING_SOUNDS = new CopyOnWriteArrayList<>();
    private static ScheduledFuture<?> TICK_TASK;

    public static void init() {
        try {
            SOUNDS.put(Instrument.HARP, readSound(OpenALSoundSystem.class.getResourceAsStream("/noteblock_sounds/harp.wav")));
            SOUNDS.put(Instrument.BASS, readSound(OpenALSoundSystem.class.getResourceAsStream("/noteblock_sounds/bass.wav")));
            SOUNDS.put(Instrument.BASS_DRUM, readSound(OpenALSoundSystem.class.getResourceAsStream("/noteblock_sounds/bd.wav")));
            SOUNDS.put(Instrument.SNARE, readSound(OpenALSoundSystem.class.getResourceAsStream("/noteblock_sounds/snare.wav")));
            SOUNDS.put(Instrument.HAT, readSound(OpenALSoundSystem.class.getResourceAsStream("/noteblock_sounds/hat.wav")));
            SOUNDS.put(Instrument.GUITAR, readSound(OpenALSoundSystem.class.getResourceAsStream("/noteblock_sounds/guitar.wav")));
            SOUNDS.put(Instrument.FLUTE, readSound(OpenALSoundSystem.class.getResourceAsStream("/noteblock_sounds/flute.wav")));
            SOUNDS.put(Instrument.BELL, readSound(OpenALSoundSystem.class.getResourceAsStream("/noteblock_sounds/bell.wav")));
            SOUNDS.put(Instrument.CHIME, readSound(OpenALSoundSystem.class.getResourceAsStream("/noteblock_sounds/icechime.wav")));
            SOUNDS.put(Instrument.XYLOPHONE, readSound(OpenALSoundSystem.class.getResourceAsStream("/noteblock_sounds/xylobone.wav")));
            SOUNDS.put(Instrument.IRON_XYLOPHONE, readSound(OpenALSoundSystem.class.getResourceAsStream("/noteblock_sounds/iron_xylophone.wav")));
            SOUNDS.put(Instrument.COW_BELL, readSound(OpenALSoundSystem.class.getResourceAsStream("/noteblock_sounds/cow_bell.wav")));
            SOUNDS.put(Instrument.DIDGERIDOO, readSound(OpenALSoundSystem.class.getResourceAsStream("/noteblock_sounds/didgeridoo.wav")));
            SOUNDS.put(Instrument.BIT, readSound(OpenALSoundSystem.class.getResourceAsStream("/noteblock_sounds/bit.wav")));
            SOUNDS.put(Instrument.BANJO, readSound(OpenALSoundSystem.class.getResourceAsStream("/noteblock_sounds/banjo.wav")));
            SOUNDS.put(Instrument.PLING, readSound(OpenALSoundSystem.class.getResourceAsStream("/noteblock_sounds/pling.wav")));
        } catch (Throwable e) {
            throw new RuntimeException("Could not load audio buffer", e);
        }

        TICK_TASK = SCHEDULER.scheduleAtFixedRate(JavaxSoundSystem::tick, 0, 100, TimeUnit.MILLISECONDS);
    }

    public static void playNote(final Instrument instrument, final float volume, final float pitch) {
        try {
            Sound sound = SOUNDS.get(instrument);
            int[] samples = mutate(sound.getSamples(), volume, pitch);
            Sound newSound = new Sound(sound.getAudioFormat(), samples);
            AudioInputStream audioStream = writeSound(newSound);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
            PLAYING_SOUNDS.add(clip);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public static void stopAllSounds() {
        for (Clip clip : PLAYING_SOUNDS) {
            clip.stop();
            clip.close();
        }
        PLAYING_SOUNDS.clear();
    }

    public static void destroy() {
        if (TICK_TASK != null) {
            TICK_TASK.cancel(true);
            TICK_TASK = null;
        }
        stopAllSounds();
        SOUNDS.clear();
    }

    private static Sound readSound(final InputStream is) throws UnsupportedAudioFileException, IOException {
        AudioInputStream in = AudioSystem.getAudioInputStream(is);
        AudioFormat audioFormat = in.getFormat();
        byte[] audioBytes = ByteStreams.toByteArray(in);

        int sampleSize = audioFormat.getSampleSizeInBits() / 8;
        int[] samples = new int[audioBytes.length / sampleSize];
        for (int i = 0; i < samples.length; i++) {
            byte[] sampleBytes = new byte[sampleSize];
            System.arraycopy(audioBytes, i * sampleSize, sampleBytes, 0, sampleSize);
            samples[i] = ByteBuffer.wrap(sampleBytes).order(ByteOrder.LITTLE_ENDIAN).getShort();
        }

        return new Sound(audioFormat, samples);
    }

    private static int[] mutate(final int[] samples, final float volume, final float pitchChangeFactor) {
        int[] newSamples = new int[(int) (samples.length / pitchChangeFactor)];
        for (int i = 0; i < newSamples.length; i++) {
            //Long to prevent clipping of the index
            long index = (long) i * samples.length / newSamples.length;
            newSamples[i] = (int) (samples[(int) index] * volume);
        }
        return newSamples;
    }

    private static AudioInputStream writeSound(final Sound sound) throws IOException {
        int sampleSize = sound.getAudioFormat().getSampleSizeInBits() / 8;
        final ByteArrayOutputStream baos = new ByteArrayOutputStream(sound.getSamples().length * sampleSize);
        final LittleEndianDataOutputStream dos = new LittleEndianDataOutputStream(baos);
        for (int sample : sound.getSamples()) {
            dos.writeShort((short) sample);
        }
        return new AudioInputStream(new ByteArrayInputStream(baos.toByteArray()), sound.getAudioFormat(), baos.size() / sound.getAudioFormat().getFrameSize());
    }

    private static void tick() {
        PLAYING_SOUNDS.removeIf(clip -> {
            if (clip.isRunning()) {
                return false;
            } else {
                clip.close();
                return true;
            }
        });
    }


    private static final class Sound {
        private AudioFormat audioFormat;
        private int[] samples;

        private Sound(AudioFormat audioFormat, int[] samples) {
            this.audioFormat = audioFormat;
            this.samples = samples;
        }

        public AudioFormat getAudioFormat() {
            return this.audioFormat;
        }

        public void setAudioFormat(final AudioFormat audioFormat) {
            this.audioFormat = audioFormat;
        }

        public int[] getSamples() {
            return this.samples;
        }

        public void setSamples(final int[] samples) {
            this.samples = samples;
        }
    }

}