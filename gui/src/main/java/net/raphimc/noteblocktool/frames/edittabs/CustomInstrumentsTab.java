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

import net.raphimc.noteblocklib.format.nbs.model.NbsCustomInstrument;
import net.raphimc.noteblocklib.format.nbs.model.NbsData;
import net.raphimc.noteblocklib.model.Song;
import net.raphimc.noteblocklib.model.SongView;
import net.raphimc.noteblocklib.util.Instrument;
import net.raphimc.noteblocklib.util.SongUtil;
import net.raphimc.noteblocktool.elements.instruments.InstrumentsTable;
import net.raphimc.noteblocktool.frames.ListFrame;

import javax.swing.*;
import java.util.*;

public class CustomInstrumentsTab extends EditTab {

    private InstrumentsTable table;
    private Set<Integer> usedInstruments;

    public CustomInstrumentsTab(final List<ListFrame.LoadedSong> songs) {
        super(songs);
    }

    @Override
    protected void initComponents(JPanel center) {
        this.removeAll();

        this.table = new InstrumentsTable(true);
        this.add(new JScrollPane(this.table));
        this.usedInstruments = SongUtil.getUsedCustomInstruments(this.songs.get(0).getSong().getView());
        NbsData data = (NbsData) this.songs.get(0).getSong().getData();
        for (Integer instrument : this.usedInstruments) {
            NbsCustomInstrument customInstrument = data.getCustomInstruments().get(instrument - Instrument.values().length);
            this.table.addRow(customInstrument.getName() + " (" + customInstrument.getSoundFileName() + ")", null);
        }
    }

    @Override
    public void apply(Song<?, ?, ?> song, SongView<?> view) {
        Map<Integer, Instrument> replacements = new HashMap<>();
        int i = 0;
        for (Integer instrument : this.usedInstruments) {
            Instrument replacement = (Instrument) this.table.getValueAt(i, 1);
            replacements.put(instrument, replacement);
            i++;
        }
        SongUtil.applyToAllNotes(view, note -> {
            Instrument replacement = replacements.get((int) note.getInstrument());
            if (replacement != null) note.setInstrument(replacement.nbsId());
        });
        if (song != null && SongUtil.getUsedCustomInstruments(view).isEmpty()) {
            ((NbsData) song.getData()).setCustomInstruments(new ArrayList<>());
        }
    }

}
