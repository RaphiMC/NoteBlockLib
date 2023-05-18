/*
 * This file is part of NoteBlockLib - https://github.com/RaphiMC/NoteBlockLib
 * Copyright (C) 2023 RK_01/RaphiMC and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.raphimc.noteblocklib.util;

import net.raphimc.noteblocklib.model.Note;
import net.raphimc.noteblocklib.model.SongView;

import java.util.Collection;
import java.util.function.Consumer;

public class SongUtil {

    /**
     * Applies the given consumer to all notes in the song view.
     * This method will also modify the notes of the original song as the view references the original song notes.
     * Use cases for this method can be for example to transpose all notes in a song to be within the minecraft octave range.
     *
     * @param songView     The song view
     * @param noteConsumer The note consumer
     * @param <N>          The note type
     */
    public static <N extends Note> void applyToAllNotes(final SongView<N> songView, final Consumer<N> noteConsumer) {
        songView.getNotes().values().stream().flatMap(Collection::stream).forEach(noteConsumer);
    }

}
