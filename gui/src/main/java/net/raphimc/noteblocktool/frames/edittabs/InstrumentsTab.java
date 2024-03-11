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

import net.raphimc.noteblocklib.model.Song;
import net.raphimc.noteblocklib.model.SongView;
import net.raphimc.noteblocklib.util.Instrument;
import net.raphimc.noteblocklib.util.SongUtil;
import net.raphimc.noteblocktool.elements.instruments.InstrumentsTable;
import net.raphimc.noteblocktool.frames.ListFrame;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class InstrumentsTab extends EditTab {

    private InstrumentsTable table;

    public InstrumentsTab(final List<ListFrame.LoadedSong> songs, final Consumer<ListFrame.LoadedSong> songRefreshConsumer) {
        super(songs, songRefreshConsumer);
    }

    @Override
    protected void initComponents(JPanel center) {
        this.removeAll();

        this.table = new InstrumentsTable();
        this.add(new JScrollPane(this.table));
        Set<Instrument> usedInstruments = SongUtil.getUsedVanillaInstruments(this.songs.get(0).getSong().getView());
        for (Instrument instrument : usedInstruments) this.table.addRow(instrument.name(), instrument);
    }

    @Override
    public void apply(Song<?, ?, ?> song, SongView<?> view) {
        Map<Byte, Instrument> replacements = new HashMap<>();
        for (int i = 0; i < this.table.getRowCount(); i++) {
            String original = (String) this.table.getValueAt(i, 0);
            Instrument replacement = (Instrument) this.table.getValueAt(i, 1);
            replacements.put(Instrument.valueOf(original).nbsId(), replacement);
        }
        SongUtil.applyToAllNotes(view, note -> {
            Instrument replacement = replacements.get(note.getInstrument());
            if (replacement != null) note.setInstrument(replacement.nbsId());
        });
    }

}
