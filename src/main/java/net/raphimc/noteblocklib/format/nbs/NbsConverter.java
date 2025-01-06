/*
 * This file is part of NoteBlockLib - https://github.com/RaphiMC/NoteBlockLib
 * Copyright (C) 2022-2025 RK_01/RaphiMC and contributors
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
package net.raphimc.noteblocklib.format.nbs;

import net.raphimc.noteblocklib.data.MinecraftInstrument;
import net.raphimc.noteblocklib.format.mcsp2.model.McSp2Song;
import net.raphimc.noteblocklib.format.nbs.model.NbsCustomInstrument;
import net.raphimc.noteblocklib.format.nbs.model.NbsLayer;
import net.raphimc.noteblocklib.format.nbs.model.NbsNote;
import net.raphimc.noteblocklib.format.nbs.model.NbsSong;
import net.raphimc.noteblocklib.model.Note;
import net.raphimc.noteblocklib.model.Song;

import java.util.List;

public class NbsConverter {

    /**
     * Creates a new NBS song from the general data of the given song (Also copies some format specific fields if applicable).
     *
     * @param song The song
     * @return The new NBS song
     */
    public static NbsSong createSong(final Song song) {
        final NbsSong newSong = new NbsSong();
        newSong.copyGeneralData(song);
        newSong.setLength((short) song.getNotes().getLengthInTicks());
        newSong.setTempo((short) Math.round(song.getTempoEvents().get(0) * 100F));

        for (int tick : song.getNotes().getTicks()) {
            final List<Note> notes = song.getNotes().get(tick);
            for (int i = 0; i < notes.size(); i++) {
                final Note note = notes.get(i);
                final NbsLayer nbsLayer = newSong.getLayers().computeIfAbsent(i, k -> new NbsLayer());

                final NbsNote nbsNote = new NbsNote();
                if (note.getInstrument() instanceof MinecraftInstrument) {
                    nbsNote.setInstrument(((MinecraftInstrument) note.getInstrument()).nbsId());
                } else if (note.getInstrument() instanceof NbsCustomInstrument) {
                    final NbsCustomInstrument customInstrument = (NbsCustomInstrument) note.getInstrument();
                    if (!newSong.getCustomInstruments().contains(customInstrument)) {
                        newSong.getCustomInstruments().add(customInstrument);
                    }
                    nbsNote.setInstrument((short) (newSong.getVanillaInstrumentCount() + newSong.getCustomInstruments().indexOf(customInstrument)));
                }
                nbsNote.setKey((byte) Math.max(NbsDefinitions.NBS_LOWEST_KEY, Math.min(NbsDefinitions.NBS_HIGHEST_KEY, note.getNbsKey())));
                nbsNote.setVelocity((byte) Math.round(note.getVolume() * 100F));
                nbsNote.setPanning((short) (Math.round(note.getPanning() * 100F) + NbsDefinitions.CENTER_PANNING));
                nbsNote.setPitch((short) Math.round(note.getFractionalKeyPart() * 100F));

                nbsLayer.getNotes().put(tick, nbsNote);
            }
        }
        newSong.getCustomInstruments().replaceAll(NbsCustomInstrument::copy);

        if (song.getTempoEvents().getTicks().size() > 1) {
            final NbsCustomInstrument tempoChangerInstrument = new NbsCustomInstrument();
            tempoChangerInstrument.setName(NbsDefinitions.TEMPO_CHANGER_CUSTOM_INSTRUMENT_NAME);
            final short instrumentId = (short) (newSong.getVanillaInstrumentCount() + newSong.getCustomInstruments().size());
            newSong.getCustomInstruments().add(tempoChangerInstrument);

            final NbsLayer tempoChangerLayer = new NbsLayer();
            tempoChangerLayer.setName(NbsDefinitions.TEMPO_CHANGER_CUSTOM_INSTRUMENT_NAME);
            tempoChangerLayer.setVolume((byte) 0);
            newSong.getLayers().put(newSong.getLayers().size(), tempoChangerLayer);

            for (int tempoEventTick : song.getTempoEvents().getTicks()) {
                final float tps = song.getTempoEvents().get(tempoEventTick);
                final NbsNote tempoChangerNote = new NbsNote();
                tempoChangerNote.setInstrument(instrumentId);
                tempoChangerNote.setKey((byte) NbsDefinitions.F_SHARP_4_NBS_KEY);
                tempoChangerNote.setPitch((short) Math.round(tps * 15F));
                tempoChangerLayer.getNotes().put(tempoEventTick, tempoChangerNote);
            }
        }

        newSong.setLayerCount((short) newSong.getLayers().size());
        newSong.setSourceFileName(song.getFileName());

        if (song instanceof NbsSong) {
            final NbsSong nbsSong = (NbsSong) song;
            newSong.setAutoSave(nbsSong.isAutoSave());
            newSong.setAutoSaveInterval(nbsSong.getAutoSaveInterval());
            newSong.setTimeSignature(nbsSong.getTimeSignature());
            newSong.setMinutesSpent(nbsSong.getMinutesSpent());
            newSong.setLeftClicks(nbsSong.getLeftClicks());
            newSong.setRightClicks(nbsSong.getRightClicks());
            newSong.setNoteBlocksAdded(nbsSong.getNoteBlocksAdded());
            newSong.setNoteBlocksRemoved(nbsSong.getNoteBlocksRemoved());
            newSong.setSourceFileName(nbsSong.getSourceFileName());
            newSong.setLoop(nbsSong.isLoop());
            newSong.setMaxLoopCount(nbsSong.getMaxLoopCount());
            newSong.setLoopStartTick(nbsSong.getLoopStartTick());
        } else if (song instanceof McSp2Song) {
            final McSp2Song mcSp2Song = (McSp2Song) song;
            newSong.setAutoSave(mcSp2Song.getAutoSaveInterval() != 0);
            newSong.setAutoSaveInterval((byte) mcSp2Song.getAutoSaveInterval());
            newSong.setMinutesSpent(mcSp2Song.getMinutesSpent());
            newSong.setLeftClicks(mcSp2Song.getLeftClicks());
            newSong.setRightClicks(mcSp2Song.getRightClicks());
            newSong.setNoteBlocksAdded(mcSp2Song.getNoteBlocksAdded());
            newSong.setNoteBlocksRemoved(mcSp2Song.getNoteBlocksRemoved());
        }

        return newSong;
    }

}
