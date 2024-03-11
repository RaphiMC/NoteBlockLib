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
import net.raphimc.noteblocklib.format.nbs.NbsSong;
import net.raphimc.noteblocklib.format.nbs.model.NbsNote;
import net.raphimc.noteblocklib.model.Song;
import net.raphimc.noteblocklib.model.SongView;
import net.raphimc.noteblocklib.util.SongResampler;
import net.raphimc.noteblocktool.elements.formatter.DoubleFormatterFactory;
import net.raphimc.noteblocktool.frames.ListFrame;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.function.Consumer;

public class ResamplingTab extends EditTab {

    private JCheckBox changeSpeedEnabled;
    private JSpinner changeSpeedSpinner;
    private JCheckBox precomputeNbsTempoChanges;

    public ResamplingTab(final List<ListFrame.LoadedSong> songs, final Consumer<ListFrame.LoadedSong> songRefreshConsumer) {
        super(songs, songRefreshConsumer);
    }

    @Override
    protected void initComponents(JPanel center) {
        JPanel resampling = new JPanel();
        resampling.setLayout(new GridBagLayout());
        resampling.setBorder(BorderFactory.createTitledBorder("Change speed"));
        center.add(resampling);
        GBC.create(resampling).grid(0, 0).insets(5, 5, 0, 5).width(2).anchor(GBC.LINE_START).add(new JCheckBox("Enabled"), checkBox -> {
            this.changeSpeedEnabled = checkBox;
        });
        GBC.create(resampling).grid(0, 1).insets(5, 5, 5, 5).anchor(GBC.LINE_START).add(html("New speed:"));
        GBC.create(resampling).grid(1, 1).insets(5, 5, 5, 5).weightx(1).fill(GBC.HORIZONTAL).add(new JSpinner(new SpinnerNumberModel(20D, 5D, 100D, 1D)), spinner -> {
            this.changeSpeedSpinner = spinner;
            ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().setFormatterFactory(new DoubleFormatterFactory(" TPS"));
        });

        JPanel nbsTempoChanger = new JPanel();
        nbsTempoChanger.setLayout(new GridBagLayout());
        nbsTempoChanger.setBorder(BorderFactory.createTitledBorder("NBS tempo changer"));
        center.add(nbsTempoChanger);
        GBC.create(nbsTempoChanger).grid(0, 0).insets(5, 5, 0, 5).anchor(GBC.LINE_START).add(new JCheckBox("Precompute NBS tempo changes"), checkBox -> {
            this.precomputeNbsTempoChanges = checkBox;
        });
        GBC.create(nbsTempoChanger).grid(0, 1).insets(5, 5, 5, 5).weightx(1).fill(GBC.HORIZONTAL).add(html("Applies the undocumented tempo changers from Note Block Studio.This allows the song to be played in players which aren't handling those."));
    }

    @Override
    public void apply(final Song<?, ?, ?> song, final SongView<?> view) {
        if ((this.precomputeNbsTempoChanges.isSelected() || this.changeSpeedEnabled.isSelected()) && song instanceof NbsSong) {
            SongResampler.applyNbsTempoChangers((NbsSong) song, (SongView<NbsNote>) view);
        }
        if (this.changeSpeedEnabled.isSelected()) {
            SongResampler.changeTickSpeed(view, ((Double) this.changeSpeedSpinner.getValue()).floatValue());
        }
    }

}
