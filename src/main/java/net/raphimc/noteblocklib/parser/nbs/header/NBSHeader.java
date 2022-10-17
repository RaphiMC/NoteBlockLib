package net.raphimc.noteblocklib.parser.nbs.header;

import com.google.common.io.LittleEndianDataInputStream;
import net.raphimc.noteblocklib.parser.Header;

import java.io.IOException;

public class NBSHeader extends Header {

    private short length;
    private byte nbsVersion;
    private byte vanillaInstrumentCount;

    public NBSHeader(final LittleEndianDataInputStream dis) throws IOException {
        super();

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
        super();

        this.length = length;
        this.nbsVersion = nbsVersion;
        this.vanillaInstrumentCount = vanillaInstrumentCount;
    }

    public void setLength(final short length) {
        this.length = length;
    }

    /**
     * Can be -1 if the nbsVersion did not support this field
     *
     * @return The length of the song, measured in ticks. Divide this by the tempo to get the length of the song in seconds.
     */
    public short getLength() {
        return this.length;
    }

    public void setNbsVersion(final byte nbsVersion) {
        this.nbsVersion = nbsVersion;
    }

    /**
     * @return The version of the new NBS format.
     */
    public byte getNbsVersion() {
        return this.nbsVersion;
    }

    public void setVanillaInstrumentCount(final byte vanillaInstrumentCount) {
        this.vanillaInstrumentCount = vanillaInstrumentCount;
    }

    /**
     * @return Amount of default instruments when the song was saved. This is needed to determine at what index custom instruments start.
     */
    public byte getVanillaInstrumentCount() {
        return this.vanillaInstrumentCount;
    }

}
