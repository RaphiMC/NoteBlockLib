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
package net.raphimc.noteblocklib.format.nbs.model;

import net.raphimc.noteblocklib.data.MinecraftInstrument;
import net.raphimc.noteblocklib.format.SongFormat;
import net.raphimc.noteblocklib.model.Song;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NbsSong extends Song {

    /**
     * @since v0 and {@literal >}= v3
     */
    private short length;

    /**
     * @since v0
     */
    private byte version;

    /**
     * @since v0
     */
    private byte vanillaInstrumentCount;

    /**
     * @since v0
     */
    private short layerCount;

    /**
     * @since v0
     */
    private short tempo;

    /**
     * @since v0
     */
    private boolean autoSave;

    /**
     * @since v0
     */
    private byte autoSaveInterval;

    /**
     * @since v0
     */
    private byte timeSignature;

    /**
     * @since v0
     */
    private int minutesSpent;

    /**
     * @since v0
     */
    private int leftClicks;

    /**
     * @since v0
     */
    private int rightClicks;

    /**
     * @since v0
     */
    private int noteBlocksAdded;

    /**
     * @since v0
     */
    private int noteBlocksRemoved;

    /**
     * @since v0
     */
    private String sourceFileName;

    /**
     * @since v4
     */
    private boolean loop;

    /**
     * @since v4
     */
    private byte maxLoopCount;

    /**
     * @since v4
     */
    private short loopStartTick;

    /**
     * @since v0
     */
    private final Map<Integer, NbsLayer> layers = new HashMap<>();

    /**
     * @since v0
     */
    private final List<NbsCustomInstrument> customInstruments = new ArrayList<>();

    public NbsSong() {
        this(null);
    }

    public NbsSong(final String fileName) {
        super(SongFormat.NBS, fileName);
        this.version = 5;
        this.vanillaInstrumentCount = (byte) MinecraftInstrument.values().length;
    }

    /**
     * @return The length of the song, measured in ticks. Divide this by the tempo to get the length of the song in seconds. Can be -1 if the nbsVersion did not support this field
     * @since v0 and {@literal >}= v3
     */
    public short getLength() {
        return this.length;
    }

    /**
     * @param length The length of the song, measured in ticks. Divide this by the tempo to get the length of the song in seconds.
     * @return this
     * @since v0 and {@literal >}= v3
     */
    public NbsSong setLength(final short length) {
        this.length = length;
        return this;
    }

    /**
     * @return The version of the NBS format.
     * @since v0
     */
    public int getVersion() {
        return this.version & 0xFF;
    }

    /**
     * @param version The version of the NBS format.
     * @return this
     * @since v0
     */
    public NbsSong setVersion(final int version) {
        if (version < 0 || version > 255) {
            throw new IllegalArgumentException("NBS version must be between 0 and 255");
        }
        this.version = (byte) version;
        return this;
    }

    /**
     * @return Amount of default instruments when the song was saved. This is needed to determine at what index custom instruments start.
     * @since v0
     */
    public int getVanillaInstrumentCount() {
        return this.vanillaInstrumentCount & 0xFF;
    }

    /**
     * @param vanillaInstrumentCount Amount of default instruments when the song was saved. This is needed to determine at what index custom instruments start.
     * @return this
     * @since v0
     */
    public NbsSong setVanillaInstrumentCount(final int vanillaInstrumentCount) {
        if (vanillaInstrumentCount < 0 || vanillaInstrumentCount > 255) {
            throw new IllegalArgumentException("Vanilla instrument count must be between 0 and 255");
        }
        this.vanillaInstrumentCount = (byte) vanillaInstrumentCount;
        return this;
    }

    /**
     * @return The last layer with at least one note block in it, or the last layer that has had its name, volume or stereo changed.
     * @since v0
     */
    public short getLayerCount() {
        return this.layerCount;
    }

    /**
     * @param layerCount The last layer with at least one note block in it, or the last layer that has had its name, volume or stereo changed.
     * @return this
     * @since v0
     */
    public NbsSong setLayerCount(final short layerCount) {
        this.layerCount = layerCount;
        return this;
    }

    /**
     * @return The tempo of the song multiplied by 100 (for example, 1225 instead of 12.25). Measured in ticks per second.
     * @since v0
     */
    public short getTempo() {
        return this.tempo;
    }

    /**
     * @param tempo The tempo of the song multiplied by 100 (for example, 1225 instead of 12.25). Measured in ticks per second.
     * @return this
     * @since v0
     */
    public NbsSong setTempo(final short tempo) {
        this.tempo = tempo;
        return this;
    }

    /**
     * @return Whether auto-saving has been enabled (0 or 1). As of NBS version 4 this value is still saved to the file, but no longer used in the program.
     * @since v0
     */
    public boolean isAutoSave() {
        return this.autoSave;
    }

    /**
     * @param autoSave Whether auto-saving has been enabled (0 or 1). As of NBS version 4 this value is still saved to the file, but no longer used in the program.
     * @return this
     * @since v0
     */
    public NbsSong setAutoSave(final boolean autoSave) {
        this.autoSave = autoSave;
        return this;
    }

    /**
     * @return The amount of minutes between each auto-save (if it has been enabled) (1-60). As of NBS version 4 this value is still saved to the file, but no longer used in the program.
     * @since v0
     */
    public int getAutoSaveInterval() {
        return this.autoSaveInterval & 0xFF;
    }

    /**
     * @param autoSaveInterval The amount of minutes between each auto-save (if it has been enabled) (1-60). As of NBS version 4 this value is still saved to the file, but no longer used in the program.
     * @return this
     * @since v0
     */
    public NbsSong setAutoSaveInterval(final int autoSaveInterval) {
        if (autoSaveInterval < 0 || autoSaveInterval > 255) {
            throw new IllegalArgumentException("Auto-save interval must be between 0 and 255");
        }
        this.autoSaveInterval = (byte) autoSaveInterval;
        return this;
    }

    /**
     * @return The time signature of the song. If this is 3, then the signature is 3/4. Default is 4. This value ranges from 2-8.
     * @since v0
     */
    public int getTimeSignature() {
        return this.timeSignature & 0xFF;
    }

    /**
     * @param timeSignature The time signature of the song. If this is 3, then the signature is 3/4. Default is 4. This value ranges from 2-8.
     * @return this
     * @since v0
     */
    public NbsSong setTimeSignature(final int timeSignature) {
        if (timeSignature < 0 || timeSignature > 255) {
            throw new IllegalArgumentException("Time signature must be between 0 and 255");
        }
        this.timeSignature = (byte) timeSignature;
        return this;
    }

    /**
     * @return Amount of minutes spent on the project.
     * @since v0
     */
    public int getMinutesSpent() {
        return this.minutesSpent;
    }

    /**
     * @param minutesSpent Amount of minutes spent on the project.
     * @return this
     * @since v0
     */
    public NbsSong setMinutesSpent(final int minutesSpent) {
        this.minutesSpent = minutesSpent;
        return this;
    }

    /**
     * @return Amount of times the user has left-clicked.
     * @since v0
     */
    public int getLeftClicks() {
        return this.leftClicks;
    }

    /**
     * @param leftClicks Amount of times the user has left-clicked.
     * @return this
     * @since v0
     */
    public NbsSong setLeftClicks(final int leftClicks) {
        this.leftClicks = leftClicks;
        return this;
    }

    /**
     * @return Amount of times the user has right-clicked.
     * @since v0
     */
    public int getRightClicks() {
        return this.rightClicks;
    }

    /**
     * @param rightClicks Amount of times the user has right-clicked.
     * @return this
     * @since v0
     */
    public NbsSong setRightClicks(final int rightClicks) {
        this.rightClicks = rightClicks;
        return this;
    }

    /**
     * @return Amount of times the user has added a note block.
     * @since v0
     */
    public int getNoteBlocksAdded() {
        return this.noteBlocksAdded;
    }

    /**
     * @param noteBlocksAdded Amount of times the user has added a note block.
     * @return this
     * @since v0
     */
    public NbsSong setNoteBlocksAdded(final int noteBlocksAdded) {
        this.noteBlocksAdded = noteBlocksAdded;
        return this;
    }

    /**
     * @return Amount of times the user has removed a note block.
     * @since v0
     */
    public int getNoteBlocksRemoved() {
        return this.noteBlocksRemoved;
    }

    /**
     * @param noteBlocksRemoved Amount of times the user has removed a note block.
     * @return this
     * @since v0
     */
    public NbsSong setNoteBlocksRemoved(final int noteBlocksRemoved) {
        this.noteBlocksRemoved = noteBlocksRemoved;
        return this;
    }

    /**
     * @return If the song has been imported from a .mid or .schematic file, that file name is stored here (only the name of the file, not the path).
     * @since v0
     */
    public String getSourceFileName() {
        return this.sourceFileName;
    }

    /**
     * @param fallback The fallback value if the source file name is not set.
     * @return If the song has been imported from a .mid or .schematic file, that file name is stored here (only the name of the file, not the path).
     * @since v0
     */
    public String getSourceFileNameOr(final String fallback) {
        return this.sourceFileName == null ? fallback : this.sourceFileName;
    }

    /**
     * @param sourceFileName If the song has been imported from a .mid or .schematic file, that file name is stored here (only the name of the file, not the path).
     * @return this
     * @since v0
     */
    public NbsSong setSourceFileName(final String sourceFileName) {
        if (sourceFileName != null && !sourceFileName.isEmpty()) {
            this.sourceFileName = sourceFileName;
        } else {
            this.sourceFileName = null;
        }
        return this;
    }

    /**
     * @return Whether looping is on or off.
     * @since v4
     */
    public boolean isLoop() {
        return this.loop;
    }

    /**
     * @param loop Whether looping is on or off.
     * @return this
     * @since v4
     */
    public NbsSong setLoop(final boolean loop) {
        this.loop = loop;
        return this;
    }

    /**
     * @return 0 = infinite. Other values mean the amount of times the song loops.
     * @since v4
     */
    public int getMaxLoopCount() {
        return this.maxLoopCount & 0xFF;
    }

    /**
     * @param maxLoopCount 0 = infinite. Other values mean the amount of times the song loops.
     * @return this
     * @since v4
     */
    public NbsSong setMaxLoopCount(final int maxLoopCount) {
        if (maxLoopCount < 0 || maxLoopCount > 255) {
            throw new IllegalArgumentException("Max loop count must be between 0 and 255");
        }
        this.maxLoopCount = (byte) maxLoopCount;
        return this;
    }

    /**
     * @return Determines which part of the song (in ticks) it loops back to.
     * @since v4
     */
    public short getLoopStartTick() {
        return this.loopStartTick;
    }

    /**
     * @param loopStartTick Determines which part of the song (in ticks) it loops back to.
     * @return this
     * @since v4
     */
    public NbsSong setLoopStartTick(final short loopStartTick) {
        this.loopStartTick = loopStartTick;
        return this;
    }

    /**
     * @return The layers of this song
     * @since v0
     */
    public Map<Integer, NbsLayer> getLayers() {
        return this.layers;
    }

    /**
     * @return The custom instruments of this song
     * @since v0
     */
    public List<NbsCustomInstrument> getCustomInstruments() {
        return this.customInstruments;
    }

    @Override
    public NbsSong copy() {
        final NbsSong copySong = new NbsSong(this.getFileName());
        copySong.copyGeneralData(this);
        copySong.setVersion(this.getVersion());
        copySong.setVanillaInstrumentCount(this.getVanillaInstrumentCount());
        copySong.setLayerCount(this.getLayerCount());
        copySong.setTempo(this.getTempo());
        copySong.setAutoSave(this.isAutoSave());
        copySong.setAutoSaveInterval(this.getAutoSaveInterval());
        copySong.setTimeSignature(this.getTimeSignature());
        copySong.setMinutesSpent(this.getMinutesSpent());
        copySong.setLeftClicks(this.getLeftClicks());
        copySong.setRightClicks(this.getRightClicks());
        copySong.setNoteBlocksAdded(this.getNoteBlocksAdded());
        copySong.setNoteBlocksRemoved(this.getNoteBlocksRemoved());
        copySong.setSourceFileName(this.getSourceFileName());
        copySong.setLoop(this.isLoop());
        copySong.setMaxLoopCount(this.getMaxLoopCount());
        copySong.setLoopStartTick(this.getLoopStartTick());
        final Map<Integer, NbsLayer> layers = this.getLayers();
        final Map<Integer, NbsLayer> copyLayers = copySong.getLayers();
        for (final Map.Entry<Integer, NbsLayer> entry : layers.entrySet()) {
            copyLayers.put(entry.getKey(), entry.getValue().copy());
        }
        final List<NbsCustomInstrument> customInstruments = this.getCustomInstruments();
        final List<NbsCustomInstrument> copyCustomInstruments = copySong.getCustomInstruments();
        for (final NbsCustomInstrument customInstrument : customInstruments) {
            copyCustomInstruments.add(customInstrument.copy());
        }
        return copySong;
    }

}
