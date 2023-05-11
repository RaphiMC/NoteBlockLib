package net.raphimc.noteblocklib.format.nbs;

import com.google.common.io.LittleEndianDataInputStream;
import net.raphimc.noteblocklib.format.nbs.data.NBSData;
import net.raphimc.noteblocklib.format.nbs.header.NBSHeader;
import net.raphimc.noteblocklib.format.nbs.header.NBSv0Header;
import net.raphimc.noteblocklib.format.nbs.header.NBSv4Header;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

@SuppressWarnings("UnstableApiUsage")
public class NBSParser {

    public static NBSSong parse(final byte[] bytes, final File sourceFile) throws IOException {
        final LittleEndianDataInputStream dis = new LittleEndianDataInputStream(new ByteArrayInputStream(bytes));
        dis.mark(6);
        final int nbsVersion = new NBSHeader(dis).getNbsVersion();
        dis.reset();

        final NBSv0Header header = nbsVersion >= 4 ? new NBSv4Header(dis) : new NBSv0Header(dis);
        final NBSData data = new NBSData(header, dis);

        return new NBSSong(sourceFile, header, data);
    }

    public static String readString(final LittleEndianDataInputStream dis) throws IOException {
        int length = dis.readInt();
        final StringBuilder builder = new StringBuilder(length);
        while (length > 0) {
            char c = (char) dis.readByte();
            if (c == (char) 0x0D) {
                c = ' ';
            }
            builder.append(c);
            length--;
        }
        return builder.toString();
    }

}
