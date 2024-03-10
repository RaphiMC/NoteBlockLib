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
package net.raphimc.noteblocktool.elements;

import net.raphimc.noteblocktool.frames.ListFrame;

import javax.swing.*;

public class DragTable extends JTable {

    public DragTable() {
        super(new DragTableModel("Path", "Title", "Author", "Length", "Notes", "Speed"));

        this.getTableHeader().setReorderingAllowed(false);
        this.getColumnModel().getColumn(1).setPreferredWidth(250);
        this.getColumnModel().getColumn(3).setPreferredWidth(25);
        this.getColumnModel().getColumn(4).setPreferredWidth(25);
        this.getColumnModel().getColumn(5).setPreferredWidth(25);
    }

    public void addRow(final ListFrame.LoadedSong song) {
        ((DragTableModel) this.getModel()).addRow(new Object[]{
                song,
                song.getSong().getView().getTitle(),
                song.getAuthor().orElse("Unknown"),
                song.getLength(),
                song.getNoteCount(),
                song.getSong().getView().getSpeed()
        });
    }

    public void refreshRow(final ListFrame.LoadedSong song) {
        for (int i = 0; i < this.getModel().getRowCount(); i++) {
            if (this.getModel().getValueAt(i, 0) == song) {
                this.getModel().setValueAt(song.getSong().getView().getTitle(), i, 1);
                this.getModel().setValueAt(song.getAuthor().orElse("Unknown"), i, 2);
                this.getModel().setValueAt(song.getLength(), i, 3);
                this.getModel().setValueAt(song.getNoteCount(), i, 4);
                this.getModel().setValueAt(song.getSong().getView().getSpeed(), i, 5);
                break;
            }
        }
    }

}
