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

import net.raphimc.noteblocktool.frames.edittabs.MetadataTab;
import net.raphimc.noteblocktool.frames.edittabs.NotesTab;
import net.raphimc.noteblocktool.frames.edittabs.ResamplingTab;

import javax.swing.*;
import java.util.List;
import java.util.function.Consumer;

public class EditFrame extends JFrame {

    private final List<ListFrame.LoadedSong> songs;
    private final Consumer<ListFrame.LoadedSong> songRefreshConsumer;

    public EditFrame(final List<ListFrame.LoadedSong> songs, final Consumer<ListFrame.LoadedSong> songRefreshConsumer) {
        this.songs = songs;
        this.songRefreshConsumer = songRefreshConsumer;

        this.setTitle("NoteBlockTool Editor");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(500, 400);
        this.setLocationRelativeTo(null);

        this.initComponents();

        this.setMinimumSize(this.getSize());
        this.setVisible(true);
    }

    private void initComponents() {
        JTabbedPane root = new JTabbedPane();
        this.setContentPane(root);

        root.addTab("Notes", new NotesTab(this.songs, this.songRefreshConsumer));
        root.addTab("Resampling", new ResamplingTab(this.songs, this.songRefreshConsumer));
        root.addTab("Metadata", new MetadataTab(this.songs, this.songRefreshConsumer));
        if (this.songs.size() != 1) {
            root.setEnabledAt(2, false);
            root.setToolTipTextAt(2, "This tab is only available when editing a single song");
        }
    }

}
