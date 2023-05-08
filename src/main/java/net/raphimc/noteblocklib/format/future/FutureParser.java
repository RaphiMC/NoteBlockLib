package net.raphimc.noteblocklib.format.future;

import com.google.common.io.ByteStreams;
import com.google.common.io.LittleEndianDataInputStream;
import net.raphimc.noteblocklib.format.future.data.FutureData;
import net.raphimc.noteblocklib.format.future.header.FutureHeader;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

@SuppressWarnings("UnstableApiUsage")
public class FutureParser {

    public static FutureSong parseFile(final File file) throws IOException {
        return parse(file, new ByteArrayInputStream(Files.readAllBytes(file.toPath())));
    }

    public static FutureSong parseStream(final InputStream is) throws IOException {
        return parse(null, new ByteArrayInputStream(ByteStreams.toByteArray(is)));
    }

    public static FutureSong parse(final File sourceFile, final InputStream is) throws IOException {
        final LittleEndianDataInputStream dis = new LittleEndianDataInputStream(is);

        final FutureHeader header = new FutureHeader(dis);
        final FutureData data = new FutureData(header, dis);

        return new FutureSong(sourceFile, header, data);
    }

}
