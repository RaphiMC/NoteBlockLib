package net.raphimc.noteblocklib.parser.future;

import com.google.common.io.LittleEndianDataInputStream;
import net.raphimc.noteblocklib.parser.future.data.FutureData;
import net.raphimc.noteblocklib.parser.future.header.FutureHeader;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.file.Files;

public class FutureParser {

    public static FutureSong parseFile(final File file) {
        try {
            return parse(file, new ByteArrayInputStream(Files.readAllBytes(file.toPath())));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static FutureSong parseStream(final InputStream is) {
        try {
            return parse(null, new ByteArrayInputStream(IOUtils.toByteArray(is)));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static FutureSong parse(final File sourceFile, final InputStream is) {
        try {
            final FutureHeader header = new FutureHeader(is);
            final FutureData data = new FutureData(header, new LittleEndianDataInputStream(is));

            return new FutureSong(sourceFile, header, data);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

}
