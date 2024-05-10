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
package net.raphimc.noteblocklib.util;

import net.raphimc.noteblocklib.format.nbs.model.NbsCustomInstrument;
import net.raphimc.noteblocklib.format.nbs.model.NbsNote;
import net.raphimc.noteblocklib.model.Note;
import net.raphimc.noteblocklib.model.NoteWithVolume;
import net.raphimc.noteblocklib.model.SongView;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SongUtil {

    /**
     * Applies the given consumer to all notes in the song view.<br>
     * This method will also modify the notes of the original song as the view references the original song notes.<br>
     * Use cases for this method can be for example to transpose all notes in a song to be within the minecraft octave range.
     *
     * @param songView     The song view
     * @param noteConsumer The note consumer
     * @param <N>          The note type
     */
    public static <N extends Note> void applyToAllNotes(final SongView<N> songView, final Consumer<N> noteConsumer) {
        songView.getNotes().values().stream().flatMap(Collection::stream).forEach(noteConsumer);
    }

    /**
     * Applies the given predicate to all notes in the song view.<br>
     * The predicate can return true to break the iteration.
     *
     * @param songView      The song view
     * @param notePredicate The note predicate
     * @param <N>           The note type
     */
    public static <N extends Note> void iterateAllNotes(final SongView<N> songView, final Predicate<N> notePredicate) {
        for (N note : songView.getNotes().values().stream().flatMap(Collection::stream).collect(Collectors.toList())) {
            if (notePredicate.test(note)) {
                break;
            }
        }
    }

    /**
     * Removes all notes which match the given predicate.
     *
     * @param songView      The song view
     * @param notePredicate The predicate
     * @param <N>           The note type
     */
    public static <N extends Note> void removeNotesIf(final SongView<N> songView, final Predicate<N> notePredicate) {
        for (List<N> list : songView.getNotes().values()) {
            list.removeIf(notePredicate);
        }
    }

    /**
     * Removes duplicate notes which are on the same tick.<br>
     * Useful when handling large MIDI files with a lot of duplicate notes.
     *
     * @param songView The song view
     * @param <N>      The note type
     */
    public static <N extends Note> void removeDoubleNotes(final SongView<N> songView) {
        for (List<N> list : songView.getNotes().values()) {
            final Set<N> set = new HashSet<>(list);
            list.clear();
            list.addAll(set);
        }
    }

    /**
     * Removes all notes which have a volume of 0.
     *
     * @param songView The song view
     * @param <N>      The note type
     */
    public static <N extends Note> void removeSilentNotes(final SongView<N> songView) {
        removeSilentNotes(songView, 0F);
    }

    /**
     * Removes all notes which have a volume lower than or equal the given threshold.
     *
     * @param songView  The song view
     * @param threshold The threshold
     * @param <N>       The note type
     */
    public static <N extends Note> void removeSilentNotes(final SongView<N> songView, final float threshold) {
        removeNotesIf(songView, note -> {
            if (note instanceof NoteWithVolume) {
                return ((NoteWithVolume) note).getVolume() <= threshold;
            } else {
                return false;
            }
        });
    }

    /**
     * Returns a list of all used vanilla instruments in a song.
     *
     * @param songView The song view
     * @param <N>      The note type
     * @return The used instruments
     */
    public static <N extends Note> Set<Instrument> getUsedVanillaInstruments(final SongView<N> songView) {
        final Set<Instrument> usedInstruments = EnumSet.noneOf(Instrument.class);
        iterateAllNotes(songView, note -> {
            if (note.getInstrument() != null) {
                usedInstruments.add(note.getInstrument());
            }
            return false;
        });

        return usedInstruments;
    }

    /**
     * Returns a list of all used custom instruments in a song.
     *
     * @param songView The song view
     * @param <N>      The note type
     * @return The used custom instruments
     */
    public static <N extends Note> Set<NbsCustomInstrument> getUsedCustomInstruments(final SongView<N> songView) {
        final Set<NbsCustomInstrument> usedInstruments = new HashSet<>();
        iterateAllNotes(songView, note -> {
            if (note instanceof NbsNote && ((NbsNote) note).getCustomInstrument() != null) {
                usedInstruments.add(((NbsNote) note).getCustomInstrument());
            }
            return false;
        });

        return usedInstruments;
    }

    /**
     * Returns the total amount of notes in a song.
     *
     * @param songView The song view
     * @param <N>      The note type
     * @return The note count
     */
    public static <N extends Note> long getNoteCount(final SongView<N> songView) {
        return songView.getNotes().values().stream().mapToLong(List::size).sum();
    }

}
