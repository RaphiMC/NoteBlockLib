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
package net.raphimc.noteblocklib.format.futureclient;

import com.google.common.io.ByteStreams;
import com.google.common.io.LittleEndianDataInputStream;
import net.raphimc.noteblocklib.data.MinecraftInstrument;
import net.raphimc.noteblocklib.format.futureclient.model.FutureClientNote;
import net.raphimc.noteblocklib.format.futureclient.model.FutureClientSong;
import net.raphimc.noteblocklib.model.Note;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FutureClientIo {

    private static final int BUFFER_SIZE = 1024 * 1024;

    public static FutureClientSong readSong(final InputStream is, final String fileName) throws IOException {
        final LittleEndianDataInputStream dis = new LittleEndianDataInputStream(new BufferedInputStream(is, BUFFER_SIZE));
        final FutureClientSong song = new FutureClientSong(fileName);
        final Map<Integer, List<FutureClientNote>> notes = song.getFutureClientNotes();

        boolean use64 = false;
        dis.mark(dis.available());
        final byte[] data = ByteStreams.toByteArray(dis);
        for (byte b : data) {
            if (b == 64) {
                use64 = true;
                break;
            }
        }
        dis.reset();

        int tick = 0;
        while (dis.available() > 0) {
            final byte instrument = dis.readByte();
            if (instrument == (use64 ? 64 : 5)) {
                tick += dis.readUnsignedShort();
            } else {
                final FutureClientNote note = new FutureClientNote();
                note.setInstrument(instrument);
                note.setKey(dis.readByte());
                notes.computeIfAbsent(tick, k -> new ArrayList<>()).add(note);
            }
        }

        { // Fill generalized song structure with data
            song.getTempoEvents().set(0, FutureClientDefinitions.TEMPO);
            for (Map.Entry<Integer, List<FutureClientNote>> entry : notes.entrySet()) {
                for (FutureClientNote futureClientNote : entry.getValue()) {
                    final Note note = new Note();
                    note.setInstrument(MinecraftInstrument.fromMcId(futureClientNote.getInstrument()));
                    note.setMcKey(futureClientNote.getKey());
                    song.getNotes().add(entry.getKey(), note);
                }
            }
        }

        return song;
    }

}
