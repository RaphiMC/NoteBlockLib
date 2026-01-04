/*
 * This file is part of NoteBlockLib - https://github.com/RaphiMC/NoteBlockLib
 * Copyright (C) 2022-2026 RK_01/RaphiMC and contributors
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
package net.raphimc.noteblocklib.format.mcsp2.model;

import net.raphimc.noteblocklib.format.SongFormat;
import net.raphimc.noteblocklib.model.song.Song;

import java.util.HashMap;
import java.util.Map;

public class McSp2Song extends Song {

    private int tempo;
    private int autoSaveInterval;
    private int leftClicks;
    private int rightClicks;
    private int noteBlocksAdded;
    private int noteBlocksRemoved;
    private int minutesSpent;
    private final Map<Integer, McSp2Layer> layers = new HashMap<>();

    public McSp2Song() {
        this(null);
    }

    public McSp2Song(final String fileName) {
        super(SongFormat.MCSP, fileName);
        this.tempo = 10;
    }

    /**
     * @return The tempo of the song. Measured in ticks per second.
     */
    public int getTempo() {
        return this.tempo;
    }

    /**
     * @param tempo The tempo of the song. Measured in ticks per second.
     * @return this
     */
    public McSp2Song setTempo(final int tempo) {
        this.tempo = tempo;
        return this;
    }

    /**
     * @return The amount of minutes between each auto-save (0 indicates that auto-save is disabled) (0-60).
     */
    public int getAutoSaveInterval() {
        return this.autoSaveInterval;
    }

    /**
     * @param autoSaveInterval The amount of minutes between each auto-save (0 indicates that auto-save is disabled) (0-60).
     * @return this
     */
    public McSp2Song setAutoSaveInterval(final int autoSaveInterval) {
        this.autoSaveInterval = autoSaveInterval;
        return this;
    }

    /**
     * @return Amount of times the user has left-clicked.
     */
    public int getLeftClicks() {
        return this.leftClicks;
    }

    /**
     * @param leftClicks Amount of times the user has left-clicked.
     * @return this
     */
    public McSp2Song setLeftClicks(final int leftClicks) {
        this.leftClicks = leftClicks;
        return this;
    }

    /**
     * @return Amount of times the user has right-clicked.
     */
    public int getRightClicks() {
        return this.rightClicks;
    }

    /**
     * @param rightClicks Amount of times the user has right-clicked.
     * @return this
     */
    public McSp2Song setRightClicks(final int rightClicks) {
        this.rightClicks = rightClicks;
        return this;
    }

    /**
     * @return Amount of times the user has added a note block.
     */
    public int getNoteBlocksAdded() {
        return this.noteBlocksAdded;
    }

    /**
     * @param noteBlocksAdded Amount of times the user has added a note block.
     * @return this
     */
    public McSp2Song setNoteBlocksAdded(final int noteBlocksAdded) {
        this.noteBlocksAdded = noteBlocksAdded;
        return this;
    }

    /**
     * @return Amount of times the user has removed a note block.
     */
    public int getNoteBlocksRemoved() {
        return this.noteBlocksRemoved;
    }

    /**
     * @param noteBlocksRemoved Amount of times the user has removed a note block.
     * @return this
     */
    public McSp2Song setNoteBlocksRemoved(final int noteBlocksRemoved) {
        this.noteBlocksRemoved = noteBlocksRemoved;
        return this;
    }

    /**
     * @return Amount of minutes spent on the project.
     */
    public int getMinutesSpent() {
        return this.minutesSpent;
    }

    /**
     * @param minutesSpent Amount of minutes spent on the project.
     * @return this
     */
    public McSp2Song setMinutesSpent(final int minutesSpent) {
        this.minutesSpent = minutesSpent;
        return this;
    }

    /**
     * @return The layers of this song
     */
    public Map<Integer, McSp2Layer> getLayers() {
        return this.layers;
    }

    @Override
    public McSp2Song copy() {
        final McSp2Song copySong = new McSp2Song(this.getFileName());
        copySong.copyGeneralData(this);
        copySong.setTempo(this.getTempo());
        copySong.setAutoSaveInterval(this.getAutoSaveInterval());
        copySong.setMinutesSpent(this.getMinutesSpent());
        copySong.setLeftClicks(this.getLeftClicks());
        copySong.setRightClicks(this.getRightClicks());
        copySong.setNoteBlocksAdded(this.getNoteBlocksAdded());
        copySong.setNoteBlocksRemoved(this.getNoteBlocksRemoved());
        final Map<Integer, McSp2Layer> layers = this.getLayers();
        final Map<Integer, McSp2Layer> copyLayers = copySong.getLayers();
        for (final Map.Entry<Integer, McSp2Layer> entry : layers.entrySet()) {
            copyLayers.put(entry.getKey(), entry.getValue().copy());
        }
        return copySong;
    }

}
