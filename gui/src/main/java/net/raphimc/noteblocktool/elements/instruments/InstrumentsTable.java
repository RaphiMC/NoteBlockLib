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
package net.raphimc.noteblocktool.elements.instruments;

import net.raphimc.noteblocklib.util.Instrument;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class InstrumentsTable extends JTable {

    public InstrumentsTable(final boolean addEmptyEntry) {
        super(new InstrumentsModel("Original", "Replacement"));
        this.getTableHeader().setReorderingAllowed(false);

        JComboBox<Instrument> instruments = new JComboBox<>();
        if (addEmptyEntry) instruments.addItem(null);
        for (Instrument instrument : Instrument.values()) instruments.addItem(instrument);
        this.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(instruments));
    }

    public void addRow(final String original, final Instrument instrument) {
        ((DefaultTableModel) this.getModel()).addRow(new Object[]{
                original,
                instrument
        });
    }

    @Override
    public Class<?> getColumnClass(int column) {
        if (column == 1) return JComboBox.class;
        else return super.getColumnClass(column);
    }

}
