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
package net.raphimc.noteblocklib.format.nbs.model;

import com.google.common.io.LittleEndianDataInputStream;
import com.google.common.io.LittleEndianDataOutputStream;
import net.raphimc.noteblocklib.model.Header;
import net.raphimc.noteblocklib.model.Note;
import net.raphimc.noteblocklib.model.SongView;
import net.raphimc.noteblocklib.util.Instrument;

import java.io.IOException;
import java.util.List;

import static net.raphimc.noteblocklib.format.nbs.NbsParser.readString;
import static net.raphimc.noteblocklib.format.nbs.NbsParser.writeString;

public class NbsHeader implements Header {

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
    private int vanillaInstrumentCount;

    /**
     * @since v0
     */
    private short layerCount;

    /**
     * @since v0
     */
    private String title = "";

    /**
     * @since v0
     */
    private String author = "";

    /**
     * @since v0
     */
    private String originalAuthor = "";

    /**
     * @since v0
     */
    private String description = "";

    /**
     * @since v0
     */
    private short speed;

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
    private String sourceFileName = "";

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

    public NbsHeader(final LittleEndianDataInputStream dis) throws IOException {
        final short length = dis.readShort();
        if (length == 0) {
            this.version = dis.readByte();
            this.vanillaInstrumentCount = dis.readUnsignedByte();
            if (this.version >= 3) {
                this.length = dis.readShort();
            } else {
                this.length = -1;
            }
        } else {
            this.length = length;
            this.version = 0;
            this.vanillaInstrumentCount = 10;
        }

        if (this.version < 0 || this.version > 5) {
            throw new IllegalStateException("Unsupported NBS version: " + this.version);
        }

        this.layerCount = dis.readShort();
        this.title = readString(dis);
        this.author = readString(dis);
        this.originalAuthor = readString(dis);
        this.description = readString(dis);
        this.speed = dis.readShort();
        this.autoSave = dis.readBoolean();
        this.autoSaveInterval = dis.readByte();
        this.timeSignature = dis.readByte();
        this.minutesSpent = dis.readInt();
        this.leftClicks = dis.readInt();
        this.rightClicks = dis.readInt();
        this.noteBlocksAdded = dis.readInt();
        this.noteBlocksRemoved = dis.readInt();
        this.sourceFileName = readString(dis);

        if (this.version >= 4) {
            this.loop = dis.readBoolean();
            this.maxLoopCount = dis.readByte();
            this.loopStartTick = dis.readShort();
        }
    }

    public NbsHeader(final short length, final byte version, final int vanillaInstrumentCount, final short layerCount, final String title, final String author, final String originalAuthor, final String description, final short speed, final boolean autoSave, final byte autoSaveInterval, final byte timeSignature, final int minutesSpent, final int leftClicks, final int rightClicks, final int noteBlocksAdded, final int noteBlocksRemoved, final String sourceFileName, final boolean loop, final byte maxLoopCount, final short loopStartTick) {
        this(length, version, vanillaInstrumentCount, layerCount, title, author, originalAuthor, description, speed, autoSave, autoSaveInterval, timeSignature, minutesSpent, leftClicks, rightClicks, noteBlocksAdded, noteBlocksRemoved, sourceFileName);

        this.loop = loop;
        this.maxLoopCount = maxLoopCount;
        this.loopStartTick = loopStartTick;
    }

    public NbsHeader(final short length, final byte version, final int vanillaInstrumentCount, final short layerCount, final String title, final String author, final String originalAuthor, final String description, final short speed, final boolean autoSave, final byte autoSaveInterval, final byte timeSignature, final int minutesSpent, final int leftClicks, final int rightClicks, final int noteBlocksAdded, final int noteBlocksRemoved, final String sourceFileName) {
        this.length = length;
        this.version = version;
        this.vanillaInstrumentCount = vanillaInstrumentCount;
        this.layerCount = layerCount;
        this.title = title;
        this.author = author;
        this.originalAuthor = originalAuthor;
        this.description = description;
        this.speed = speed;
        this.autoSave = autoSave;
        this.autoSaveInterval = autoSaveInterval;
        this.timeSignature = timeSignature;
        this.minutesSpent = minutesSpent;
        this.leftClicks = leftClicks;
        this.rightClicks = rightClicks;
        this.noteBlocksAdded = noteBlocksAdded;
        this.noteBlocksRemoved = noteBlocksRemoved;
        this.sourceFileName = sourceFileName;
    }

    public NbsHeader() {
    }

    public <N extends Note> NbsHeader(final SongView<N> songView) {
        this.version = 4;
        this.vanillaInstrumentCount = Instrument.values().length;
        this.title = songView.getTitle();
        this.length = (short) songView.getLength();
        this.speed = (short) (songView.getSpeed() * 100F);
        this.author = "NoteBlockLib";
        this.description = "Created with NoteBlockLib";
        this.layerCount = (short) songView.getNotes().values().stream().mapToInt(List::size).max().orElse(0);
    }

    public void write(final LittleEndianDataOutputStream dos) throws IOException {
        if (this.version == 0) {
            dos.writeShort(this.length);
        } else {
            dos.writeShort(0);
            dos.writeByte(this.version);
            dos.writeByte(this.vanillaInstrumentCount);
            if (this.version >= 3) {
                dos.writeShort(this.length);
            }
        }

        dos.writeShort(this.layerCount);
        writeString(dos, this.title);
        writeString(dos, this.author);
        writeString(dos, this.originalAuthor);
        writeString(dos, this.description);
        dos.writeShort(this.speed);
        dos.writeBoolean(this.autoSave);
        dos.writeByte(this.autoSaveInterval);
        dos.writeByte(this.timeSignature);
        dos.writeInt(this.minutesSpent);
        dos.writeInt(this.leftClicks);
        dos.writeInt(this.rightClicks);
        dos.writeInt(this.noteBlocksAdded);
        dos.writeInt(this.noteBlocksRemoved);
        writeString(dos, this.sourceFileName);

        if (this.version >= 4) {
            dos.writeBoolean(this.loop);
            dos.writeByte(this.maxLoopCount);
            dos.writeShort(this.loopStartTick);
        }
    }

    /**
     * Can be -1 if the nbsVersion did not support this field
     *
     * @return The length of the song, measured in ticks. Divide this by the tempo to get the length of the song in seconds.
     * @since v0 and {@literal >}= v3
     */
    public short getLength() {
        return this.length;
    }

    /**
     * @param length The length of the song, measured in ticks. Divide this by the tempo to get the length of the song in seconds.
     * @since v0 and {@literal >}= v3
     */
    public void setLength(final short length) {
        this.length = length;
    }

    /**
     * @return The version of the NBS format.
     * @since v0
     */
    public byte getVersion() {
        return this.version;
    }

    /**
     * @param version The version of the NBS format.
     * @since v0
     */
    public void setVersion(final byte version) {
        this.version = version;
    }

    /**
     * @return The version of the NBS format.
     * @since v0
     */
    @Deprecated
    public byte getNbsVersion() {
        return this.version;
    }

    /**
     * @param version The version of the NBS format.
     * @since v0
     */
    @Deprecated
    public void setNbsVersion(final byte version) {
        this.version = version;
    }

    /**
     * @return Amount of default instruments when the song was saved. This is needed to determine at what index custom instruments start.
     * @since v0
     */
    public int getVanillaInstrumentCount() {
        return this.vanillaInstrumentCount;
    }

    /**
     * @param vanillaInstrumentCount Amount of default instruments when the song was saved. This is needed to determine at what index custom instruments start.
     * @since v0
     */
    public void setVanillaInstrumentCount(final int vanillaInstrumentCount) {
        this.vanillaInstrumentCount = vanillaInstrumentCount;
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
     * @since v0
     */
    public void setLayerCount(final short layerCount) {
        this.layerCount = layerCount;
    }

    /**
     * @return The name of the song.
     * @since v0
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * @param title The name of the song.
     * @since v0
     */
    public void setTitle(final String title) {
        this.title = title;
    }

    /**
     * @return The author of the song.
     * @since v0
     */
    public String getAuthor() {
        return this.author;
    }

    /**
     * @param author The author of the song.
     * @since v0
     */
    public void setAuthor(final String author) {
        this.author = author;
    }

    /**
     * @return The original author of the song.
     * @since v0
     */
    public String getOriginalAuthor() {
        return this.originalAuthor;
    }

    /**
     * @param originalAuthor The original author of the song.
     * @since v0
     */
    public void setOriginalAuthor(final String originalAuthor) {
        this.originalAuthor = originalAuthor;
    }

    /**
     * @return The description of the song.
     * @since v0
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * @param description The description of the song.
     * @since v0
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * @return The tempo of the song multiplied by 100 (for example, 1225 instead of 12.25). Measured in ticks per second.
     * @since v0
     */
    public short getSpeed() {
        return this.speed;
    }

    /**
     * @param speed The tempo of the song multiplied by 100 (for example, 1225 instead of 12.25). Measured in ticks per second.
     * @since v0
     */
    public void setSpeed(final short speed) {
        this.speed = speed;
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
     * @since v0
     */
    public void setAutoSave(final boolean autoSave) {
        this.autoSave = autoSave;
    }

    /**
     * @return The amount of minutes between each auto-save (if it has been enabled) (1-60). As of NBS version 4 this value is still saved to the file, but no longer used in the program.
     * @since v0
     */
    public byte getAutoSaveInterval() {
        return this.autoSaveInterval;
    }

    /**
     * @param autoSaveInterval The amount of minutes between each auto-save (if it has been enabled) (1-60). As of NBS version 4 this value is still saved to the file, but no longer used in the program.
     * @since v0
     */
    public void setAutoSaveInterval(final byte autoSaveInterval) {
        this.autoSaveInterval = autoSaveInterval;
    }

    /**
     * @return The time signature of the song. If this is 3, then the signature is 3/4. Default is 4. This value ranges from 2-8.
     * @since v0
     */
    public byte getTimeSignature() {
        return this.timeSignature;
    }

    /**
     * @param timeSignature The time signature of the song. If this is 3, then the signature is 3/4. Default is 4. This value ranges from 2-8.
     * @since v0
     */
    public void setTimeSignature(final byte timeSignature) {
        this.timeSignature = timeSignature;
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
     * @since v0
     */
    public void setMinutesSpent(final int minutesSpent) {
        this.minutesSpent = minutesSpent;
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
     * @since v0
     */
    public void setLeftClicks(final int leftClicks) {
        this.leftClicks = leftClicks;
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
     * @since v0
     */
    public void setRightClicks(final int rightClicks) {
        this.rightClicks = rightClicks;
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
     * @since v0
     */
    public void setNoteBlocksAdded(final int noteBlocksAdded) {
        this.noteBlocksAdded = noteBlocksAdded;
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
     * @since v0
     */
    public void setNoteBlocksRemoved(final int noteBlocksRemoved) {
        this.noteBlocksRemoved = noteBlocksRemoved;
    }

    /**
     * @return If the song has been imported from a .mid or .schematic file, that file name is stored here (only the name of the file, not the path).
     * @since v0
     */
    public String getSourceFileName() {
        return this.sourceFileName;
    }

    /**
     * @param sourceFileName If the song has been imported from a .mid or .schematic file, that file name is stored here (only the name of the file, not the path).
     * @since v0
     */
    public void setSourceFileName(final String sourceFileName) {
        this.sourceFileName = sourceFileName;
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
     * @since v4
     */
    public void setLoop(final boolean loop) {
        this.loop = loop;
    }

    /**
     * @return 0 = infinite. Other values mean the amount of times the song loops.
     * @since v4
     */
    public byte getMaxLoopCount() {
        return this.maxLoopCount;
    }

    /**
     * @param maxLoopCount 0 = infinite. Other values mean the amount of times the song loops.
     * @since v4
     */
    public void setMaxLoopCount(final byte maxLoopCount) {
        this.maxLoopCount = maxLoopCount;
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
     * @since v4
     */
    public void setLoopStartTick(final short loopStartTick) {
        this.loopStartTick = loopStartTick;
    }

}
