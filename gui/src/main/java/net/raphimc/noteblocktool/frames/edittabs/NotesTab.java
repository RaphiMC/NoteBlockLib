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
package net.raphimc.noteblocktool.frames.edittabs;

import net.lenni0451.commons.swing.GBC;
import net.lenni0451.commons.swing.layouts.VerticalLayout;
import net.raphimc.noteblocklib.model.SongView;
import net.raphimc.noteblocklib.util.SongUtil;
import net.raphimc.noteblocktool.elements.FastScrollPane;
import net.raphimc.noteblocktool.elements.IntFormatterFactory;
import net.raphimc.noteblocktool.elements.ScrollPaneSizedPanel;
import net.raphimc.noteblocktool.frames.ListFrame;
import net.raphimc.noteblocktool.frames.SongPlayerFrame;
import net.raphimc.noteblocktool.util.PitchCorrection;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.function.Consumer;

public class NotesTab extends JPanel {

    private final List<ListFrame.LoadedSong> songs;
    private final Consumer<ListFrame.LoadedSong> songRefreshConsumer;
    private JComboBox<PitchCorrection> pitchCorrection;
    private JSpinner volumeSpinner;

    public NotesTab(final List<ListFrame.LoadedSong> songs, final Consumer<ListFrame.LoadedSong> songRefreshConsumer) {
        this.songs = songs;
        this.songRefreshConsumer = songRefreshConsumer;

        this.setLayout(new BorderLayout());
        this.initComponents();
    }

    private void initComponents() {
        { //Center Panel
            JScrollPane scrollPane = new FastScrollPane();
            JPanel center = new ScrollPaneSizedPanel(scrollPane);
            center.setLayout(new VerticalLayout(5, 5));
            scrollPane.setViewportView(center);
            this.add(scrollPane, BorderLayout.CENTER);

            JPanel pitchCorrection = new JPanel();
            pitchCorrection.setLayout(new GridBagLayout());
            pitchCorrection.setBorder(BorderFactory.createTitledBorder("Pitch Correction"));
            center.add(pitchCorrection);
            GBC.create(pitchCorrection).grid(0, 0).insets(5, 5, 0, 5).weightx(1).fill(GBC.HORIZONTAL).add(new JComboBox<>(PitchCorrection.values()), comboBox -> {
                this.pitchCorrection = comboBox;
            });
            GBC.create(pitchCorrection).grid(0, 1).insets(5, 5, 5, 5).width(2).weightx(1).fill(GBC.HORIZONTAL).add(html(
                    "<b>NONE:</b> Don't change the key of the note.",
                    "<b>INSTRUMENT_SHIFT:</b> \"Transposes\" the key of the note by shifting the instrument to a higher or lower sounding one. This often sounds the best of the three methods as it keeps the musical key the same and only changes the instrument.",
                    "<b>TRANSPOSE:</b> Transposes the key of the note to fall within minecraft octave range. Any key below 33 will be transposed up an octave, and any key above 57 will be transposed down an octave.",
                    "<b>CLAMP:</b> Clamps the key of the note to fall within minecraft octave range. Any key below 33 will be set to 33, and any key above 57 will be set to 57."
            ));

            JPanel volume = new JPanel();
            volume.setLayout(new GridBagLayout());
            volume.setBorder(BorderFactory.createTitledBorder("Volume"));
            center.add(volume);
            GBC.create(volume).grid(0, 0).insets(5, 5, 5, 5).anchor(GBC.LINE_START).add(html("Remove notes quieter than:"));
            GBC.create(volume).grid(1, 0).insets(5, 5, 5, 5).weightx(1).fill(GBC.HORIZONTAL).add(new JSpinner(new SpinnerNumberModel(0, 0, 100, 1)), spinner -> {
                this.volumeSpinner = spinner;
                ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().setFormatterFactory(new IntFormatterFactory("%"));
            });
        }
        { //South Panel
            JPanel south = new JPanel();
            south.setLayout(new FlowLayout(FlowLayout.RIGHT));
            this.add(south, BorderLayout.SOUTH);

            JButton apply = new JButton("Save");
            apply.addActionListener(e -> {
                for (ListFrame.LoadedSong song : this.songs) {
                    SongUtil.applyToAllNotes(song.getSong().getView(), note -> ((PitchCorrection) this.pitchCorrection.getSelectedItem()).correctNote(note));
                    SongUtil.removeSilentNotes(song.getSong().getView(), (int) this.volumeSpinner.getValue());
                }
                JOptionPane.showMessageDialog(this, "Saved all changes", "Saved", JOptionPane.INFORMATION_MESSAGE);
                for (ListFrame.LoadedSong song : this.songs) this.songRefreshConsumer.accept(song);
            });
            south.add(apply);
            JButton preview = new JButton("Preview");
            preview.addActionListener(e -> {
                ListFrame.LoadedSong song = this.songs.get(0);
                SongView<?> view = song.getSong().getView().clone();
                SongUtil.applyToAllNotes(view, note -> ((PitchCorrection) this.pitchCorrection.getSelectedItem()).correctNote(note));
                SongUtil.removeSilentNotes(view, (int) this.volumeSpinner.getValue());
                SongPlayerFrame.open(song, view);
            });
            if (this.songs.size() != 1) {
                preview.setEnabled(false);
                preview.setToolTipText("Preview is only available for one song at a time");
            }
            south.add(preview);
        }
    }

    private JLabel html(final String... lines) {
        return new JLabel("<html>" + String.join("<br>", lines) + "</html>");
    }

}
