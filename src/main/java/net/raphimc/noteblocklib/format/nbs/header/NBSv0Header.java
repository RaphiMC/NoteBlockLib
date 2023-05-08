package net.raphimc.noteblocklib.format.nbs.header;

import com.google.common.io.LittleEndianDataInputStream;

import java.io.IOException;

import static net.raphimc.noteblocklib.format.nbs.NBSParser.readString;

public class NBSv0Header extends NBSHeader {

    private short layerCount;
    private String title;
    private String author;
    private String originalAuthor;
    private String description;
    private short speed;
    private boolean autoSave;
    private byte autoSaveInterval;
    private byte timeSignature;
    private int minutesSpent;
    private int leftClicks;
    private int rightClicks;
    private int noteBlocksAdded;
    private int noteBlocksRemoved;
    private String sourceFileName;

    @SuppressWarnings("UnstableApiUsage")
    public NBSv0Header(final LittleEndianDataInputStream dis) throws IOException {
        super(dis);

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
    }

    public NBSv0Header(final short length, final byte nbsVersion, final byte vanillaInstrumentCount, final short layerCount, final String title, final String author, final String originalAuthor, final String description, final short speed, final boolean autoSave, final byte autoSaveInterval, final byte timeSignature, final int minutesSpent, final int leftClicks, final int rightClicks, final int noteBlocksAdded, final int noteBlocksRemoved, final String sourceFileName) {
        super(length, nbsVersion, vanillaInstrumentCount);

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

    /**
     * @return The last layer with at least one note block in it, or the last layer that has had its name, volume or stereo changed.
     */
    public short getLayerCount() {
        return this.layerCount;
    }

    public void setLayerCount(final short layerCount) {
        this.layerCount = layerCount;
    }

    /**
     * @return The name of the song.
     */
    public String getTitle() {
        return this.title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    /**
     * @return The author of the song.
     */
    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(final String author) {
        this.author = author;
    }

    /**
     * @return The original author of the song.
     */
    public String getOriginalAuthor() {
        return this.originalAuthor;
    }

    public void setOriginalAuthor(final String originalAuthor) {
        this.originalAuthor = originalAuthor;
    }

    /**
     * @return The description of the song.
     */
    public String getDescription() {
        return this.description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * @return The tempo of the song multiplied by 100 (for example, 1225 instead of 12.25). Measured in ticks per second.
     */
    public short getSpeed() {
        return this.speed;
    }

    public void setSpeed(final short speed) {
        this.speed = speed;
    }

    /**
     * @return Whether auto-saving has been enabled (0 or 1). As of NBS version 4 this value is still saved to the file, but no longer used in the program.
     */
    public boolean isAutoSave() {
        return this.autoSave;
    }

    public void setAutoSave(final boolean autoSave) {
        this.autoSave = autoSave;
    }

    /**
     * @return The amount of minutes between each auto-save (if it has been enabled) (1-60). As of NBS version 4 this value is still saved to the file, but no longer used in the program.
     */
    public byte getAutoSaveInterval() {
        return this.autoSaveInterval;
    }

    public void setAutoSaveInterval(final byte autoSaveInterval) {
        this.autoSaveInterval = autoSaveInterval;
    }

    /**
     * @return The time signature of the song. If this is 3, then the signature is 3/4. Default is 4. This value ranges from 2-8.
     */
    public byte getTimeSignature() {
        return this.timeSignature;
    }

    public void setTimeSignature(final byte timeSignature) {
        this.timeSignature = timeSignature;
    }

    /**
     * @return Amount of minutes spent on the project.
     */
    public int getMinutesSpent() {
        return this.minutesSpent;
    }

    public void setMinutesSpent(final int minutesSpent) {
        this.minutesSpent = minutesSpent;
    }

    /**
     * @return Amount of times the user has left-clicked.
     */
    public int getLeftClicks() {
        return this.leftClicks;
    }

    public void setLeftClicks(final int leftClicks) {
        this.leftClicks = leftClicks;
    }

    /**
     * @return Amount of times the user has right-clicked.
     */
    public int getRightClicks() {
        return this.rightClicks;
    }

    public void setRightClicks(final int rightClicks) {
        this.rightClicks = rightClicks;
    }

    /**
     * @return Amount of times the user has added a note block.
     */
    public int getNoteBlocksAdded() {
        return this.noteBlocksAdded;
    }

    public void setNoteBlocksAdded(final int noteBlocksAdded) {
        this.noteBlocksAdded = noteBlocksAdded;
    }

    /**
     * @return Amount of times the user has removed a note block.
     */
    public int getNoteBlocksRemoved() {
        return this.noteBlocksRemoved;
    }

    public void setNoteBlocksRemoved(final int noteBlocksRemoved) {
        this.noteBlocksRemoved = noteBlocksRemoved;
    }

    /**
     * @return If the song has been imported from a .mid or .schematic file, that file name is stored here (only the name of the file, not the path).
     */
    public String getSourceFileName() {
        return this.sourceFileName;
    }

    public void setSourceFileName(final String sourceFileName) {
        this.sourceFileName = sourceFileName;
    }

}
