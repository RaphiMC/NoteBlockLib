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
package net.raphimc.noteblocktool.elements.drag;

import net.raphimc.noteblocklib.util.Instrument;
import net.raphimc.noteblocklib.util.MinecraftDefinitions;
import net.raphimc.noteblocklib.util.SongUtil;
import net.raphimc.noteblocktool.frames.ListFrame;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class DragTable extends JTable {

    public DragTable() {
        super(new DragTableModel("Path", "Title", "Author", "Length", "Notes", "Speed", "Minecraft compatible"));

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
                song.getSong().getView().getSpeed(),
                this.isSchematicCompatible(song)
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
                this.getModel().setValueAt(this.isSchematicCompatible(song), i, 6);
                break;
            }
        }
    }

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        if (column == 6) {
            List<String> reasons = (List<String>) this.getModel().getValueAt(row, column);
            if (reasons.isEmpty()) return new JLabel("Yes");
            else return new JLabel("No");
        }
        return super.prepareRenderer(renderer, row, column);
    }

    @Override
    public String getToolTipText(MouseEvent event) {
        int row = this.rowAtPoint(event.getPoint());
        int column = this.columnAtPoint(event.getPoint());
        if (row < 0) return null;
        if (column == 0) return this.getModel().getValueAt(row, column).toString();
        if (column == 6) {
            List<String> reasons = (List<String>) this.getModel().getValueAt(row, column);
            if (!reasons.isEmpty()) return String.join("\n", reasons);
        }
        return null;
    }

    private List<String> isSchematicCompatible(final ListFrame.LoadedSong song) {
        List<String> unsupportedReasons = new ArrayList<>();

        float speed = song.getSong().getView().getSpeed();
        if (speed != 2.5F && speed != 5F && speed != 10F) unsupportedReasons.add("The speed must be 2.5, 5 or 10 TPS");

        SongUtil.iterateAllNotes(song.getSong().getView(), note -> {
            if (note.getKey() < MinecraftDefinitions.MC_LOWEST_KEY || note.getKey() > MinecraftDefinitions.MC_HIGHEST_KEY) {
                unsupportedReasons.add("The song contains notes that are out of the Minecraft octave range");
                return true;
            }
            return false;
        });

        SongUtil.iterateAllNotes(song.getSong().getView(), note -> {
            if (note.getInstrument() >= Instrument.values().length) {
                unsupportedReasons.add("The song contains notes with custom instruments");
                return true;
            }
            return false;
        });

        return unsupportedReasons;
    }

}
