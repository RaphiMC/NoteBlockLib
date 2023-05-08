package net.raphimc.noteblocklib.format.nbs.header;

import com.google.common.io.LittleEndianDataInputStream;
import net.raphimc.noteblocklib.model.Header;

import java.io.IOException;

public class NBSHeader implements Header {

    private short length;
    private byte nbsVersion;
    private byte vanillaInstrumentCount;

    @SuppressWarnings("UnstableApiUsage")
    public NBSHeader(final LittleEndianDataInputStream dis) throws IOException {
        final short length = dis.readShort();
        if (length == 0) {
            this.nbsVersion = dis.readByte();
            this.vanillaInstrumentCount = dis.readByte();
            if (this.nbsVersion >= 3) {
                this.length = dis.readShort();
            } else {
                this.length = -1;
            }
        } else {
            this.length = length;
            this.nbsVersion = 0;
            this.vanillaInstrumentCount = 10;
        }
    }

    public NBSHeader(final short length, final byte nbsVersion, final byte vanillaInstrumentCount) {
        this.length = length;
        this.nbsVersion = nbsVersion;
        this.vanillaInstrumentCount = vanillaInstrumentCount;
    }

    /**
     * Can be -1 if the nbsVersion did not support this field
     *
     * @return The length of the song, measured in ticks. Divide this by the tempo to get the length of the song in seconds.
     */
    public short getLength() {
        return this.length;
    }

    public void setLength(final short length) {
        this.length = length;
    }

    /**
     * @return The version of the new NBS format.
     */
    public byte getNbsVersion() {
        return this.nbsVersion;
    }

    public void setNbsVersion(final byte nbsVersion) {
        this.nbsVersion = nbsVersion;
    }

    /**
     * @return Amount of default instruments when the song was saved. This is needed to determine at what index custom instruments start.
     */
    public byte getVanillaInstrumentCount() {
        return this.vanillaInstrumentCount;
    }

    public void setVanillaInstrumentCount(final byte vanillaInstrumentCount) {
        this.vanillaInstrumentCount = vanillaInstrumentCount;
    }

}
