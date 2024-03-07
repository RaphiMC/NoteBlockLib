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
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import net.raphimc.noteblocklib.util.Instrument;
import org.lwjgl.openal.*;
import org.lwjgl.system.MemoryUtil;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class SoundSystem {

    private static final ScheduledExecutorService SCHEDULER = Executors.newSingleThreadScheduledExecutor(new ThreadFactoryBuilder().setNameFormat("Sound System").setDaemon(true).build());
    private static final Map<Instrument, Integer> INSTRUMENT_BUFFERS = new EnumMap<>(Instrument.class);
    private static final List<Integer> PLAYING_SOURCES = new CopyOnWriteArrayList<>();
    private static long DEVICE = 0L;
    private static long CONTEXT = 0L;
    private static ScheduledFuture<?> TICK_TASK;

    public static void init() {
        DEVICE = ALC10.alcOpenDevice((ByteBuffer) null);
        if (DEVICE <= 0L) {
            throw new RuntimeException("Could not open device");
        }
        checkError("Could not open device");
        final ALCCapabilities alcCapabilities = ALC.createCapabilities(DEVICE);
        checkError("Could not create alcCapabilities");

        if (!alcCapabilities.OpenALC11) {
            throw new RuntimeException("OpenAL 1.1 is not supported");
        } else if (!alcCapabilities.ALC_SOFT_output_limiter) {
            throw new RuntimeException("ALC_SOFT_output_limiter is not supported");
        }

        // TODO: HRTF
        // TODO: Loopback support (for audio export)

        CONTEXT = ALC10.alcCreateContext(DEVICE, new int[]{ALC11.ALC_MONO_SOURCES, 4096, SOFTOutputLimiter.ALC_OUTPUT_LIMITER_SOFT, 1, 0});
        checkError("Could not create context");
        if (!ALC10.alcMakeContextCurrent(CONTEXT)) {
            throw new RuntimeException("Could not make context current");
        }

        AL.createCapabilities(alcCapabilities);
        checkError("Could not create alCapabilities");

        AL10.alListener3f(AL10.AL_POSITION, 0F, 0F, 0F);
        checkError("Could not set listener position");
        AL10.alListener3f(AL10.AL_VELOCITY, 0F, 0F, 0F);
        checkError("Could not set listener velocity");
        AL10.alListenerfv(AL10.AL_ORIENTATION, new float[]{0F, 0F, -1F, 0F, 1F, 0F});
        checkError("Could not set listener orientation");

        INSTRUMENT_BUFFERS.put(Instrument.HARP, loadWav(SoundSystem.class.getResourceAsStream("/noteblock_sounds/harp.wav")));
        INSTRUMENT_BUFFERS.put(Instrument.BASS, loadWav(SoundSystem.class.getResourceAsStream("/noteblock_sounds/bass.wav")));
        INSTRUMENT_BUFFERS.put(Instrument.BASS_DRUM, loadWav(SoundSystem.class.getResourceAsStream("/noteblock_sounds/bd.wav")));
        INSTRUMENT_BUFFERS.put(Instrument.SNARE, loadWav(SoundSystem.class.getResourceAsStream("/noteblock_sounds/snare.wav")));
        INSTRUMENT_BUFFERS.put(Instrument.HAT, loadWav(SoundSystem.class.getResourceAsStream("/noteblock_sounds/hat.wav")));
        INSTRUMENT_BUFFERS.put(Instrument.GUITAR, loadWav(SoundSystem.class.getResourceAsStream("/noteblock_sounds/guitar.wav")));
        INSTRUMENT_BUFFERS.put(Instrument.FLUTE, loadWav(SoundSystem.class.getResourceAsStream("/noteblock_sounds/flute.wav")));
        INSTRUMENT_BUFFERS.put(Instrument.BELL, loadWav(SoundSystem.class.getResourceAsStream("/noteblock_sounds/bell.wav")));
        INSTRUMENT_BUFFERS.put(Instrument.CHIME, loadWav(SoundSystem.class.getResourceAsStream("/noteblock_sounds/icechime.wav")));
        INSTRUMENT_BUFFERS.put(Instrument.XYLOPHONE, loadWav(SoundSystem.class.getResourceAsStream("/noteblock_sounds/xylobone.wav")));
        INSTRUMENT_BUFFERS.put(Instrument.IRON_XYLOPHONE, loadWav(SoundSystem.class.getResourceAsStream("/noteblock_sounds/iron_xylophone.wav")));
        INSTRUMENT_BUFFERS.put(Instrument.COW_BELL, loadWav(SoundSystem.class.getResourceAsStream("/noteblock_sounds/cow_bell.wav")));
        INSTRUMENT_BUFFERS.put(Instrument.DIDGERIDOO, loadWav(SoundSystem.class.getResourceAsStream("/noteblock_sounds/didgeridoo.wav")));
        INSTRUMENT_BUFFERS.put(Instrument.BIT, loadWav(SoundSystem.class.getResourceAsStream("/noteblock_sounds/bit.wav")));
        INSTRUMENT_BUFFERS.put(Instrument.BANJO, loadWav(SoundSystem.class.getResourceAsStream("/noteblock_sounds/banjo.wav")));
        INSTRUMENT_BUFFERS.put(Instrument.PLING, loadWav(SoundSystem.class.getResourceAsStream("/noteblock_sounds/pling.wav")));

        TICK_TASK = SCHEDULER.scheduleAtFixedRate(SoundSystem::tick, 0, 100, TimeUnit.MILLISECONDS);

        System.out.println("Initialized OpenAL on " + ALC10.alcGetString(DEVICE, ALC11.ALC_ALL_DEVICES_SPECIFIER));
    }

    public static int createSource(final Instrument instrument, final float pitch, final float volume) {
        final int id = AL10.alGenSources();
        checkError("Could not generate audio source");
        if (id > 0) {
            AL10.alSourcei(id, AL10.AL_BUFFER, INSTRUMENT_BUFFERS.get(instrument));
            checkError("Could not set audio source buffer");
            AL10.alSourcef(id, AL10.AL_PITCH, pitch);
            checkError("Could not set audio source pitch");
            AL10.alSourcef(id, AL10.AL_GAIN, volume);
            checkError("Could not set audio source volume");
            AL10.alSourcei(id, AL10.AL_LOOPING, AL10.AL_FALSE);
            checkError("Could not set audio source looping");
        }
        return id;
    }

    public static void playSource(final int source) {
        if (source > 0) {
            AL10.alSourcePlay(source);
            checkError("Could not play audio source");
            PLAYING_SOURCES.add(source);
        }
    }

    public static void stopAllSources() {
        for (Integer source : PLAYING_SOURCES) {
            AL10.alSourceStop(source);
            checkError("Could not stop audio source");
        }
        PLAYING_SOURCES.clear();
    }

    public static void destroy() {
        if (TICK_TASK != null) {
            TICK_TASK.cancel(true);
            TICK_TASK = null;
        }
        INSTRUMENT_BUFFERS.values().forEach(AL10::alDeleteBuffers);
        INSTRUMENT_BUFFERS.clear();
        PLAYING_SOURCES.forEach(AL10::alDeleteSources);
        PLAYING_SOURCES.clear();
        if (CONTEXT != 0L) {
            ALC10.alcMakeContextCurrent(0);
            ALC10.alcDestroyContext(CONTEXT);
            CONTEXT = 0L;
        }
        ALC10.alcCloseDevice(DEVICE);
        DEVICE = 0L;
    }

    public static int getPlayingSources() {
        return PLAYING_SOURCES.size();
    }

    public static int getMaxSources() {
        return ALC10.alcGetInteger(DEVICE, ALC11.ALC_MONO_SOURCES);
    }

    private static void tick() {
        PLAYING_SOURCES.removeIf(source -> {
            final int state = AL10.alGetSourcei(source, AL10.AL_SOURCE_STATE);
            checkError("Could not get audio source state");
            if (state != AL10.AL_PLAYING) {
                AL10.alDeleteSources(source);
                checkError("Could not delete audio source");
                return true;
            }

            return false;
        });
    }

    private static int loadWav(final InputStream inputStream) {
        final int id = AL10.alGenBuffers();
        checkError("Could not generate audio buffer");
        try {
            final AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(inputStream);
            final AudioFormat audioFormat = audioInputStream.getFormat();
            if (audioFormat.getChannels() != 1) {
                throw new RuntimeException("Only mono audio is supported");
            }
            if (audioFormat.getSampleSizeInBits() != 16) {
                throw new RuntimeException("Only 16-bit audio is supported");
            }

            final byte[] audioBytes = ByteStreams.toByteArray(audioInputStream);
            final ByteBuffer audioBuffer = MemoryUtil.memAlloc(audioBytes.length).put(audioBytes);
            audioBuffer.flip();
            AL10.alBufferData(id, AL10.AL_FORMAT_MONO16, audioBuffer, (int) audioFormat.getSampleRate());
            checkError("Could not set audio buffer data");
            MemoryUtil.memFree(audioBuffer);
        } catch (Throwable e) {
            throw new RuntimeException("Could not load audio buffer", e);
        }

        return id;
    }

    private static void checkError(final String message) {
        final int error = ALC10.alcGetError(DEVICE);
        if (error != ALC10.ALC_NO_ERROR) {
            throw new RuntimeException("OpenAL error: " + message + " (" + error + ")");
        }
    }

}
