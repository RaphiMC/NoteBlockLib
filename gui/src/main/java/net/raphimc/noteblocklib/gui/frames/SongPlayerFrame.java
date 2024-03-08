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
package net.raphimc.noteblocklib.gui.frames;

import net.lenni0451.commons.swing.GBC;
import net.raphimc.noteblocklib.format.nbs.NbsDefinitions;
import net.raphimc.noteblocklib.format.nbs.NbsSong;
import net.raphimc.noteblocklib.format.nbs.model.NbsNote;
import net.raphimc.noteblocklib.gui.audio.JavaxSoundSystem;
import net.raphimc.noteblocklib.gui.audio.OpenALSoundSystem;
import net.raphimc.noteblocklib.model.Note;
import net.raphimc.noteblocklib.model.NoteWithPanning;
import net.raphimc.noteblocklib.model.NoteWithVolume;
import net.raphimc.noteblocklib.model.SongView;
import net.raphimc.noteblocklib.player.ISongPlayerCallback;
import net.raphimc.noteblocklib.player.SongPlayer;
import net.raphimc.noteblocklib.util.Instrument;
import net.raphimc.noteblocklib.util.MinecraftDefinitions;
import net.raphimc.noteblocklib.util.SongResampler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SongPlayerFrame extends JFrame implements ISongPlayerCallback {

    private final ListFrame.LoadedSong song;
    private final SongPlayer songPlayer;
    private final Timer updateTimer;
    private final JComboBox<String> soundSystemComboBox = new JComboBox<>(new String[]{SoundSystem.OPENAL.getName(), SoundSystem.JAVAX.getName()});
    private final JButton playStopButton = new JButton("Play");
    private final JButton pauseResumeButton = new JButton("Pause");
    private final JSlider volumeSlider = new JSlider(0, 100, 100);
    private SoundSystem soundSystem = SoundSystem.OPENAL;
    private boolean openALSupported = true;
    private float volume = 1F;

    public SongPlayerFrame(final ListFrame.LoadedSong song) {
        this.song = song;
        this.songPlayer = new SongPlayer(this.getSongView(), this);
        this.updateTimer = new Timer(50, e -> {
            if (this.songPlayer.isRunning()) {
                this.playStopButton.setText("Stop");
                this.pauseResumeButton.setEnabled(true);
                if (this.songPlayer.isPaused()) this.pauseResumeButton.setText("Resume");
                else this.pauseResumeButton.setText("Pause");
            } else {
                this.playStopButton.setText("Play");
                this.pauseResumeButton.setText("Pause");
                this.pauseResumeButton.setEnabled(false);
            }
        });
        this.updateTimer.start();

        this.setTitle("NoteBlockLib Song Player - " + this.song.getSong().getView().getTitle());
        this.setSize(500, 400);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setLocationRelativeTo(null);

        this.initComponents();
        this.initFrameHandler();

        this.playStopButton.doClick();

        this.setMinimumSize(this.getSize());
        this.setVisible(true);
    }

    private SongView<?> getSongView() {
        final SongView<?> songView = this.song.getSong().getView();
        if (this.song.getSong() instanceof NbsSong) {
            SongResampler.applyNbsTempoChangers(((NbsSong) this.song.getSong()));
            this.song.getSong().refreshView();
        }
        return songView;
    }

    private void initComponents() {
        final JPanel root = new JPanel();
        root.setLayout(new BorderLayout());
        this.setContentPane(root);

        { //North Panel
            final JPanel northPanel = new JPanel();
            northPanel.setLayout(new GridBagLayout());
            root.add(northPanel, BorderLayout.NORTH);

            GBC.create(northPanel).grid(0, 0).insets(5, 5, 0, 5).anchor(GBC.LINE_START).add(new JLabel("Sound System:"));
            GBC.create(northPanel).grid(1, 0).insets(5, 0, 0, 5).weightx(1).fill(GBC.HORIZONTAL).add(this.soundSystemComboBox, () -> {
                try {
                    this.soundSystem.init();
                } catch (Throwable t) {
                    this.soundSystem = SoundSystem.JAVAX;
                    this.soundSystem.init();
                    this.soundSystemComboBox.setSelectedIndex(1);
                    this.openALSupported = false;
                }
                this.soundSystemComboBox.addActionListener(e -> {
                    if (this.openALSupported) {
                        this.soundSystem = SoundSystem.values()[this.soundSystemComboBox.getSelectedIndex()];
                        this.soundSystem.init();
                    } else {
                        JOptionPane.showMessageDialog(this, "OpenAL is not supported on this system", "Error", JOptionPane.ERROR_MESSAGE);
                        this.soundSystemComboBox.setSelectedIndex(1);
                    }
                });
            });
            GBC.create(northPanel).grid(0, 1).insets(5, 5, 5, 5).anchor(GBC.LINE_START).add(new JLabel("Volume:"));
            GBC.create(northPanel).grid(1, 1).insets(5, 0, 5, 5).weightx(1).fill(GBC.HORIZONTAL).add(this.volumeSlider, () -> {
                if (this.openALSupported) OpenALSoundSystem.setMasterVolume(this.volume);
                this.volumeSlider.setPaintLabels(true);
                this.volumeSlider.setPaintTicks(true);
                this.volumeSlider.setMajorTickSpacing(25);
                this.volumeSlider.setMinorTickSpacing(5);
                this.volumeSlider.addChangeListener(e -> {
                    if (this.openALSupported) {
                        this.volume = this.volumeSlider.getValue() / 100F;
                        OpenALSoundSystem.setMasterVolume(this.volume);
                    }
                });
            });
        }
        { //South Panel
            final JPanel southPanel = new JPanel();
            southPanel.setLayout(new GridBagLayout());
            root.add(southPanel, BorderLayout.SOUTH);

            final JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new GridLayout(1, 3, 5, 0));
            buttonPanel.add(this.playStopButton);
            this.playStopButton.addActionListener(e -> {
                if (this.songPlayer.isRunning()) {
                    this.songPlayer.stop();
                    this.songPlayer.setTick(0);
                    this.soundSystem.stopSounds();
                } else {
                    this.songPlayer.play();
                }
            });
            buttonPanel.add(this.pauseResumeButton);
            this.pauseResumeButton.addActionListener(e -> this.songPlayer.setPaused(!this.songPlayer.isPaused()));
            GBC.create(southPanel).grid(0, 0).insets(5, 5, 5, 5).weightx(1).width(2).fill(GBC.HORIZONTAL).add(buttonPanel);
        }
    }

    private void initFrameHandler() {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                SongPlayerFrame.this.dispose();
            }

            @Override
            public void windowClosed(WindowEvent e) {
                SongPlayerFrame.this.songPlayer.stop();
                SongPlayerFrame.this.updateTimer.stop();
            }
        });
    }

    @Override
    public void playNote(Note note) {
        if (note.getInstrument() >= Instrument.values().length) return;

        final float volume;
        if (note instanceof NoteWithVolume) {
            final NoteWithVolume noteWithVolume = (NoteWithVolume) note;
            volume = noteWithVolume.getVolume();
        } else {
            volume = 100F;
        }
        if (volume <= 0) return;

        final float panning;
        if (note instanceof NoteWithPanning) {
            final NoteWithPanning noteWithPanning = (NoteWithPanning) note;
            panning = noteWithPanning.getPanning();
        } else {
            panning = 0F;
        }

        final float pitch;
        if (note instanceof NbsNote) {
            final NbsNote nbsNote = (NbsNote) note;
            pitch = MinecraftDefinitions.nbsPitchToMcPitch(NbsDefinitions.getPitch(nbsNote));
        } else {
            pitch = MinecraftDefinitions.mcKeyToMcPitch(MinecraftDefinitions.nbsKeyToMcKey(note.getKey()));
        }

        final Instrument instrument = Instrument.fromNbsId(note.getInstrument());
        final float playerVolume = volume / 100F;
        final float playerPanning = panning / 100F;
        if (this.soundSystem.equals(SoundSystem.OPENAL)) {
            OpenALSoundSystem.playNote(instrument, playerVolume, pitch, playerPanning);
        } else if (this.soundSystem.equals(SoundSystem.JAVAX)) {
            JavaxSoundSystem.playNote(instrument, playerVolume * this.volume, pitch);
        }
    }


    private enum SoundSystem {
        OPENAL("Open AL", OpenALSoundSystem::init),
        JAVAX("Javax", JavaxSoundSystem::init);

        private final String name;
        private final Runnable init;
        private boolean initialized;

        SoundSystem(final String name, final Runnable init) {
            this.name = name;
            this.init = init;
        }

        public String getName() {
            return this.name;
        }

        public void init() {
            if (this.initialized) return;
            this.init.run();
            this.initialized = true;
        }

        public void stopSounds() {
            if (this.equals(SoundSystem.OPENAL)) OpenALSoundSystem.stopAllSources();
            else if (this.equals(SoundSystem.JAVAX)) JavaxSoundSystem.stopAllSounds();
        }
    }

}
