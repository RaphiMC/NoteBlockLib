package net.raphimc.noteblocklib.format.nbs;

import com.google.common.io.ByteStreams;
import com.google.common.io.LittleEndianDataInputStream;
import net.raphimc.noteblocklib.format.nbs.data.NBSData;
import net.raphimc.noteblocklib.format.nbs.header.NBSHeader;
import net.raphimc.noteblocklib.format.nbs.header.NBSv0Header;
import net.raphimc.noteblocklib.format.nbs.header.NBSv4Header;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

@SuppressWarnings("UnstableApiUsage")
public class NBSParser {

    public static NBSSong parseFile(final File file) throws IOException {
        return parse(file, new ByteArrayInputStream(Files.readAllBytes(file.toPath())));
    }

    public static NBSSong parseStream(final InputStream is) throws IOException {
        return parse(null, new ByteArrayInputStream(ByteStreams.toByteArray(is)));
    }

    public static NBSSong parse(final File sourceFile, final InputStream is) throws IOException {
        final LittleEndianDataInputStream dis = new LittleEndianDataInputStream(is);
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
