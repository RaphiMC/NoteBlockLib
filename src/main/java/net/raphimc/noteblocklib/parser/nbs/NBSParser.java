package net.raphimc.noteblocklib.parser.nbs;

import com.google.common.io.LittleEndianDataInputStream;
import net.raphimc.noteblocklib.parser.nbs.data.NBSData;
import net.raphimc.noteblocklib.parser.nbs.header.*;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.file.Files;

public class NBSParser {

    public static NBSSong parseFile(final File file) {
        try {
            return parse(file, new ByteArrayInputStream(Files.readAllBytes(file.toPath())));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static NBSSong parseStream(final InputStream is) {
        try {
            return parse(null, new ByteArrayInputStream(IOUtils.toByteArray(is)));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static NBSSong parse(final File sourceFile, final InputStream is) {
        try {
            final LittleEndianDataInputStream dis = new LittleEndianDataInputStream(is);
            int nbsVersion;
            {
                dis.mark(6);
                nbsVersion = new NBSHeader(dis).getNbsVersion();
                dis.reset();
            }

            final NBSv0Header header = nbsVersion >= 4 ? new NBSv4Header(dis) : new NBSv0Header(dis);
            final NBSData data = new NBSData(header, dis);
            return new NBSSong(sourceFile, header, data);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
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
