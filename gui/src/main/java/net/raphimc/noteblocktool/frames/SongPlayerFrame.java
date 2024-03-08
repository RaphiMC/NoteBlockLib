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
package net.raphimc.noteblocktool.frames;

import net.lenni0451.commons.swing.GBC;
import net.raphimc.noteblocklib.format.nbs.NbsDefinitions;
import net.raphimc.noteblocklib.format.nbs.NbsSong;
import net.raphimc.noteblocklib.format.nbs.model.NbsNote;
import net.raphimc.noteblocklib.model.Note;
import net.raphimc.noteblocklib.model.NoteWithPanning;
import net.raphimc.noteblocklib.model.NoteWithVolume;
import net.raphimc.noteblocklib.model.SongView;
import net.raphimc.noteblocklib.player.ISongPlayerCallback;
import net.raphimc.noteblocklib.player.SongPlayer;
import net.raphimc.noteblocklib.util.Instrument;
import net.raphimc.noteblocklib.util.MinecraftDefinitions;
import net.raphimc.noteblocklib.util.SongResampler;
import net.raphimc.noteblocktool.audio.JavaxSoundSystem;
import net.raphimc.noteblocktool.audio.OpenALSoundSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.util.Optional;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;

public class SongPlayerFrame extends JFrame implements ISongPlayerCallback {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");

    private final ListFrame.LoadedSong song;
    private final SongPlayer songPlayer;
    private final Timer updateTimer;
    private final JComboBox<String> soundSystemComboBox = new JComboBox<>(new String[]{SoundSystem.OPENAL.getName(), SoundSystem.JAVAX.getName()});
    private final JSpinner maxSoundsSpinner = new JSpinner(new SpinnerNumberModel(256, 64, 4096, 64));
    private final JSlider volumeSlider = new JSlider(0, 100, 100);
    private final JButton playStopButton = new JButton("Play");
    private final JButton pauseResumeButton = new JButton("Pause");
    private final JSlider progressSlider = new JSlider(0, 100, 0);
    private final JLabel soundCount = new JLabel("Sounds: 0/" + DECIMAL_FORMAT.format(this.maxSoundsSpinner.getValue()));
    private SoundSystem soundSystem = SoundSystem.OPENAL;
    private float volume = 1F;

    public SongPlayerFrame(final ListFrame.LoadedSong song) {
        this.song = song;
        this.songPlayer = new SongPlayer(this.getSongView(), this);
        this.updateTimer = new Timer(50, e -> this.tick());
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

            int gridy = 0;
            GBC.create(northPanel).grid(0, gridy).insets(5, 5, 0, 5).anchor(GBC.LINE_START).add(new JLabel("Sound System:"));
            GBC.create(northPanel).grid(1, gridy++).insets(5, 0, 0, 5).weightx(1).fill(GBC.HORIZONTAL).add(this.soundSystemComboBox);

            GBC.create(northPanel).grid(0, gridy).insets(5, 5, 0, 5).anchor(GBC.LINE_START).add(new JLabel("Max Sounds:"));
            GBC.create(northPanel).grid(1, gridy++).insets(5, 0, 0, 5).weightx(1).fill(GBC.HORIZONTAL).add(this.maxSoundsSpinner);

            GBC.create(northPanel).grid(0, gridy).insets(5, 5, 5, 5).anchor(GBC.LINE_START).add(new JLabel("Volume:"));
            GBC.create(northPanel).grid(1, gridy++).insets(5, 0, 5, 5).weightx(1).fill(GBC.HORIZONTAL).add(this.volumeSlider, () -> {
                this.volumeSlider.setPaintLabels(true);
                this.volumeSlider.setPaintTicks(true);
                this.volumeSlider.setMajorTickSpacing(25);
                this.volumeSlider.setMinorTickSpacing(5);
                this.volumeSlider.addChangeListener(e -> {
                    this.volume = this.volumeSlider.getValue() / 100F;
                    if (this.soundSystem.equals(SoundSystem.OPENAL)) OpenALSoundSystem.setMasterVolume(this.volume);
                });
            });
        }
        { //Center Panel
            final JPanel centerPanel = new JPanel();
            centerPanel.setLayout(new GridBagLayout());
            root.add(centerPanel, BorderLayout.CENTER);

            int gridy = 0;
            GBC.create(centerPanel).grid(0, gridy).insets(5, 5, 0, 5).anchor(GBC.LINE_START).add(new JLabel("Title:"));
            GBC.create(centerPanel).grid(1, gridy++).insets(5, 0, 0, 5).weightx(1).fill(GBC.HORIZONTAL).add(new JLabel(this.song.getSong().getView().getTitle()));

            Optional<String> author = this.song.getAuthor();
            if (author.isPresent()) {
                GBC.create(centerPanel).grid(0, gridy).insets(5, 5, 0, 5).anchor(GBC.LINE_START).add(new JLabel("Author:"));
                GBC.create(centerPanel).grid(1, gridy++).insets(5, 0, 0, 5).weightx(1).fill(GBC.HORIZONTAL).add(new JLabel(author.get()));
            }

            Optional<String> originalAuthor = this.song.getOriginalAuthor();
            if (originalAuthor.isPresent()) {
                GBC.create(centerPanel).grid(0, gridy).insets(5, 5, 0, 5).anchor(GBC.LINE_START).add(new JLabel("Original Author:"));
                GBC.create(centerPanel).grid(1, gridy++).insets(5, 0, 0, 5).weightx(1).fill(GBC.HORIZONTAL).add(new JLabel(originalAuthor.get()));
            }

            Optional<String> description = this.song.getDescription();
            if (description.isPresent()) {
                GBC.create(centerPanel).grid(0, gridy).insets(5, 5, 0, 5).anchor(GBC.LINE_START).add(new JLabel("Description:"));
                GBC.create(centerPanel).grid(1, gridy++).insets(5, 0, 0, 5).weightx(1).fill(GBC.HORIZONTAL).add(new JLabel(description.get()));
            }

            GBC.create(centerPanel).grid(0, gridy).insets(5, 5, 0, 5).anchor(GBC.LINE_START).add(new JLabel("Length:"));
            GBC.create(centerPanel).grid(1, gridy++).insets(5, 0, 0, 5).weightx(1).fill(GBC.HORIZONTAL).add(new JLabel(this.song.getLength()));

            GBC.create(centerPanel).grid(0, gridy).insets(5, 5, 0, 5).anchor(GBC.LINE_START).add(new JLabel("Note count:"));
            GBC.create(centerPanel).grid(1, gridy++).insets(5, 0, 0, 5).weightx(1).fill(GBC.HORIZONTAL).add(new JLabel(DECIMAL_FORMAT.format(this.song.getNoteCount())));

            GBC.create(centerPanel).grid(0, gridy).insets(5, 5, 0, 5).anchor(GBC.LINE_START).add(new JLabel("Speed:"));
            GBC.create(centerPanel).grid(1, gridy++).insets(5, 0, 0, 5).weightx(1).fill(GBC.HORIZONTAL).add(new JLabel(DECIMAL_FORMAT.format(this.song.getSong().getView().getSpeed())));

            GBC.fillVerticalSpace(centerPanel);
        }
        { //South Panel
            final JPanel southPanel = new JPanel();
            southPanel.setLayout(new GridBagLayout());
            root.add(southPanel, BorderLayout.SOUTH);

            GBC.create(southPanel).grid(0, 0).insets(5, 5, 0, 5).weightx(1).fill(GBC.HORIZONTAL).add(this.progressSlider, () -> {
                this.progressSlider.addChangeListener(e -> {
                    //Skip updates if the value is set directly
                    if (!this.progressSlider.getValueIsAdjusting()) return;
                    if (!this.songPlayer.isRunning()) {
                        this.songPlayer.play();
                        this.songPlayer.setPaused(true);
                    }
                    this.songPlayer.setTick(this.progressSlider.getValue());
                });
            });

            final JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new GridLayout(1, 3, 5, 0));
            buttonPanel.add(this.playStopButton);
            this.playStopButton.addActionListener(e -> {
                if (this.songPlayer.isRunning()) {
                    this.songPlayer.stop();
                    this.songPlayer.setTick(0);
                    this.soundSystem.stopSounds();
                } else {
                    SoundSystem selectedSoundSystem = SoundSystem.values()[this.soundSystemComboBox.getSelectedIndex()];
                    if (!this.soundSystem.equals(selectedSoundSystem)) {
                        this.soundSystem.destroy();
                        this.soundSystem = selectedSoundSystem;
                    }
                    this.soundSystem.init((int) this.maxSoundsSpinner.getValue());
                    if (this.soundSystem.equals(SoundSystem.OPENAL)) OpenALSoundSystem.setMasterVolume(this.volume);
                    this.songPlayer.play();
                }
            });
            buttonPanel.add(this.pauseResumeButton);
            this.pauseResumeButton.addActionListener(e -> this.songPlayer.setPaused(!this.songPlayer.isPaused()));
            GBC.create(southPanel).grid(0, 1).insets(5, 5, 5, 5).weightx(1).width(2).fill(GBC.HORIZONTAL).add(buttonPanel);

            final JPanel statusBar = new JPanel();
            statusBar.setBorder(BorderFactory.createEtchedBorder());
            statusBar.setLayout(new GridLayout(1, 1));
            statusBar.add(this.soundCount);
            GBC.create(southPanel).grid(0, 100).weightx(1).fill(GBC.HORIZONTAL).add(statusBar);
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
                SongPlayerFrame.this.soundSystem.stopSounds();
            }
        });
    }

    private void tick() {
        if (this.songPlayer.isRunning()) {
            this.soundSystemComboBox.setEnabled(false);
            this.maxSoundsSpinner.setEnabled(false);
            this.playStopButton.setText("Stop");
            this.pauseResumeButton.setEnabled(true);
            if (this.songPlayer.isPaused()) this.pauseResumeButton.setText("Resume");
            else this.pauseResumeButton.setText("Pause");

            int tickCount = this.songPlayer.getSongView().getLength();
            if (this.progressSlider.getMaximum() != tickCount) this.progressSlider.setMaximum(tickCount);
            this.progressSlider.setValue(this.songPlayer.getTick());
        } else {
            this.soundSystemComboBox.setEnabled(true);
            this.maxSoundsSpinner.setEnabled(true);
            this.playStopButton.setText("Play");
            this.pauseResumeButton.setText("Pause");
            this.pauseResumeButton.setEnabled(false);
            this.progressSlider.setValue(0);
        }
        this.soundCount.setText("Sounds: " + DECIMAL_FORMAT.format(this.soundSystem.getSoundCount()) + "/" + DECIMAL_FORMAT.format(this.maxSoundsSpinner.getValue()));
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
        OPENAL("OpenAL", OpenALSoundSystem::init, OpenALSoundSystem::getPlayingSources, OpenALSoundSystem::stopAllSources, OpenALSoundSystem::destroy),
        JAVAX("Javax", JavaxSoundSystem::init, JavaxSoundSystem::getPlayingSounds, JavaxSoundSystem::stopAllSounds, JavaxSoundSystem::destroy);

        private final String name;
        private final IntConsumer init;
        private final IntSupplier soundCount;
        private final Runnable stopSounds;
        private final Runnable destroy;
        private boolean initialized;

        SoundSystem(final String name, final IntConsumer init, final IntSupplier soundCount, final Runnable stopSounds, final Runnable destroy) {
            this.name = name;
            this.init = init;
            this.soundCount = soundCount;
            this.stopSounds = stopSounds;
            this.destroy = destroy;
        }

        public String getName() {
            return this.name;
        }

        public void init(final int maxSounds) {
            if (this.initialized) return;
            this.init.accept(maxSounds);
            this.initialized = true;
        }

        public int getSoundCount() {
            if (!this.initialized) return 0;
            return this.soundCount.getAsInt();
        }

        public void stopSounds() {
            if (!this.initialized) return;
            this.stopSounds.run();
        }

        public void destroy() {
            if (!this.initialized) return;
            this.destroy.run();
            this.initialized = false;
        }
    }

}
