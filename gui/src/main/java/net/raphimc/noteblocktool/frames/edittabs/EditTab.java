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

import net.lenni0451.commons.swing.layouts.VerticalLayout;
import net.raphimc.noteblocklib.model.Song;
import net.raphimc.noteblocklib.model.SongView;
import net.raphimc.noteblocktool.elements.FastScrollPane;
import net.raphimc.noteblocktool.elements.ScrollPaneSizedPanel;
import net.raphimc.noteblocktool.frames.ListFrame;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public abstract class EditTab extends JPanel {

    protected final List<ListFrame.LoadedSong> songs;
    private final JPanel center;

    public EditTab(final List<ListFrame.LoadedSong> songs) {
        this.songs = songs;

        this.setLayout(new BorderLayout());
        JScrollPane scrollPane = new FastScrollPane();
        this.center = new ScrollPaneSizedPanel(scrollPane);
        this.center.setLayout(new VerticalLayout(5, 5));
        scrollPane.setViewportView(this.center);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    public void init() {
        this.initComponents(this.center);
    }

    protected abstract void initComponents(final JPanel center);

    public abstract void apply(final Song<?, ?, ?> song, final SongView<?> view);

    protected final JLabel html(final String... lines) {
        return new JLabel("<html>" + String.join("<br>", lines) + "</html>");
    }

}
