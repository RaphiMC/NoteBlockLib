package net.raphimc.noteblocklib.parser.nbs.data;

import com.google.common.io.LittleEndianDataInputStream;

import java.io.IOException;

import static net.raphimc.noteblocklib.parser.nbs.NBSParser.readString;

public class NBSv0CustomInstrument {

    private String name;
    private String soundFileName;
    private byte pitch;
    private boolean pressKey;

    public NBSv0CustomInstrument(final LittleEndianDataInputStream dis) throws IOException {
        this.name = readString(dis);
        this.soundFileName = readString(dis);
        this.pitch = dis.readByte();
        this.pressKey = dis.readBoolean();
    }

    public NBSv0CustomInstrument(final String name, final String soundFileName, final byte pitch, final boolean pressKey) {
        this.name = name;
        this.soundFileName = soundFileName;
        this.pitch = pitch;
        this.pressKey = pressKey;
    }

    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @return The name of the instrument.
     */
    public String getName() {
        return this.name;
    }

    public void setSoundFileName(final String soundFileName) {
        this.soundFileName = soundFileName;
    }

    /**
     * @return The sound file of the instrument (just the file name, not the path).
     */
    public String getSoundFileName() {
        return this.soundFileName;
    }

    public void setPitch(final byte pitch) {
        this.pitch = pitch;
    }

    /**
     * @return The pitch of the sound file. Just like the note blocks, this ranges from 0-87. Default is 45 (F#4).
     */
    public byte getPitch() {
        return this.pitch;
    }

    public void setPressKey(final boolean pressKey) {
        this.pressKey = pressKey;
    }

    /**
     * @return Whether the piano should automatically press keys with this instrument when the marker passes them.
     */
    public boolean isPressKey() {
        return this.pressKey;
    }

}
