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
import net.raphimc.noteblocklib.format.mcsp.McSpSong;
import net.raphimc.noteblocklib.format.mcsp.model.McSpHeader;
import net.raphimc.noteblocklib.format.nbs.NbsSong;
import net.raphimc.noteblocklib.format.nbs.model.NbsHeader;
import net.raphimc.noteblocklib.model.Song;
import net.raphimc.noteblocklib.model.SongView;
import net.raphimc.noteblocktool.elements.InvisiblePanel;
import net.raphimc.noteblocktool.frames.ListFrame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class MetadataTab extends EditTab {

    private final List<Runnable> saves = new ArrayList<>();
    private int gridy;

    public MetadataTab(final List<ListFrame.LoadedSong> songs) {
        super(songs);
    }

    @Override
    protected void initComponents(JPanel center) {
        center.setLayout(new GridBagLayout());

        Song<?, ?, ?> song = this.songs.get(0).getSong();
        if (song instanceof NbsSong) {
            NbsHeader header = ((NbsSong) song).getHeader();
            this.addString(center, "Title", header::getTitle, title -> {
                if (!header.getTitle().isEmpty() || !title.isEmpty()) {
                    header.setTitle(title);
                    song.getView().setTitle(title);
                }
            });
            this.addString(center, "Author", header::getAuthor, header::setAuthor);
            this.addString(center, "Original author", header::getOriginalAuthor, header::setOriginalAuthor);
            this.addString(center, "Description", header::getDescription, header::setDescription);
            this.addBoolean(center, "Auto Save", header::isAutoSave, header::setAutoSave);
            this.addNumber(center, "AutoSave Interval", header::getAutoSaveInterval, num -> header.setAutoSaveInterval(num.byteValue()));
            this.addNumber(center, "Time Signature", header::getTimeSignature, num -> header.setTimeSignature(num.byteValue()));
            this.addNumber(center, "Minutes Spent", header::getMinutesSpent, num -> header.setMinutesSpent(num.intValue()));
            this.addNumber(center, "Left Clicks", header::getLeftClicks, num -> header.setLeftClicks(num.intValue()));
            this.addNumber(center, "Right Clicks", header::getRightClicks, num -> header.setRightClicks(num.intValue()));
            this.addNumber(center, "Note Blocks Added", header::getNoteBlocksAdded, num -> header.setNoteBlocksAdded(num.intValue()));
            this.addNumber(center, "Note Blocks Removed", header::getNoteBlocksRemoved, num -> header.setNoteBlocksRemoved(num.intValue()));
            this.addString(center, "Source File Name", header::getSourceFileName, header::setSourceFileName);
            this.addBoolean(center, "Loop", header::isLoop, header::setLoop);
            this.addNumber(center, "Max Loop Count", header::getMaxLoopCount, num -> header.setMaxLoopCount(num.byteValue()));
            this.addNumber(center, "Loop Start Tick", header::getLoopStartTick, num -> header.setLoopStartTick(num.shortValue()));
        } else if (song instanceof McSpSong) {
            McSpHeader header = ((McSpSong) song).getHeader();
            this.addString(center, "Title", header::getTitle, title -> {
                if (!header.getTitle().isEmpty() || !title.isEmpty()) {
                    header.setTitle(title);
                    song.getView().setTitle(title);
                }
            });
            this.addString(center, "Author", header::getAuthor, header::setAuthor);
            this.addString(center, "Original Author", header::getOriginalAuthor, header::setOriginalAuthor);
            this.addNumber(center, "Auto Save Interval", header::getAutoSaveInterval, num -> header.setAutoSaveInterval(num.intValue()));
            this.addNumber(center, "Left Clicks", header::getLeftClicks, num -> header.setLeftClicks(num.intValue()));
            this.addNumber(center, "Right Clicks", header::getRightClicks, num -> header.setRightClicks(num.intValue()));
            this.addNumber(center, "Note Blocks Added", header::getNoteBlocksAdded, num -> header.setNoteBlocksAdded(num.intValue()));
            this.addNumber(center, "Note Blocks Removed", header::getNoteBlocksRemoved, num -> header.setNoteBlocksRemoved(num.intValue()));
            this.addNumber(center, "Minutes Spent", header::getMinutesSpent, num -> header.setMinutesSpent(num.intValue()));
        } else {
            GBC.create(center).grid(0, this.gridy).insets(5, 5, 0, 5).anchor(GBC.CENTER).add(new JLabel("No metadata available"));
        }
        GBC.create(center).grid(0, this.gridy++).insets(0).add(new InvisiblePanel(1, 5));
        GBC.fillVerticalSpace(center);
    }

    private void addBoolean(final JPanel parent, final String name, final Supplier<Boolean> getter, final Consumer<Boolean> setter) {
        GBC.create(parent).grid(0, this.gridy).insets(5, 5, 0, 5).anchor(GBC.LINE_START).add(new JLabel(name + ":"));
        GBC.create(parent).grid(1, this.gridy++).insets(5, 5, 0, 5).anchor(GBC.LINE_START).add(new JCheckBox("", getter.get()), checkBox -> {
            this.saves.add(() -> setter.accept(checkBox.isSelected()));
        });
    }

    private void addNumber(final JPanel parent, final String name, final Supplier<Number> getter, final Consumer<Number> setter) {
        GBC.create(parent).grid(0, this.gridy).insets(5, 5, 0, 5).anchor(GBC.LINE_START).add(new JLabel(name + ":"));
        GBC.create(parent).grid(1, this.gridy++).insets(5, 5, 0, 5).weightx(1).fill(GBC.HORIZONTAL).add(new JSpinner(new SpinnerNumberModel(getter.get(), null, null, 1)), spinner -> {
            this.saves.add(() -> setter.accept((Number) spinner.getValue()));
        });
    }

    private void addString(final JPanel parent, final String name, final Supplier<String> getter, final Consumer<String> setter) {
        GBC.create(parent).grid(0, this.gridy).insets(5, 5, 0, 5).anchor(GBC.LINE_START).add(new JLabel(name + ":"));
        GBC.create(parent).grid(1, this.gridy++).insets(5, 5, 0, 5).weightx(1).fill(GBC.HORIZONTAL).add(new JTextField(getter.get()), textField -> {
            this.saves.add(() -> setter.accept(textField.getText()));
        });
    }

    @Override
    public void apply(final Song<?, ?, ?> song, final SongView<?> view) {
        this.saves.forEach(Runnable::run);
    }

}
