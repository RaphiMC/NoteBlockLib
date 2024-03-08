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
package net.raphimc.noteblocklib.gui.elements;

import net.raphimc.noteblocklib.gui.ListFrame;

import javax.swing.*;

public class DragTable extends JTable {

    public DragTable(final String... columns) {
        super(new DragTableModel(columns));

        this.getTableHeader().setReorderingAllowed(false);
    }

    public void addRow(final ListFrame.LoadedSong song) {
        //"Path", "Name", "Length", "Author", "Notes", "Speed", "Playable Version"
        ((DragTableModel) this.getModel()).addRow(new Object[]{
                song,
                song.getSong().getView().getTitle(),
                song.getLength(),
                song.getAuthor(),
                song.getNoteCount(),
                song.getSong().getView().getSpeed(),
                "TODO"
        });
    }

}
