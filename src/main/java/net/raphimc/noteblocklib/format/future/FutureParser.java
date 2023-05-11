package net.raphimc.noteblocklib.format.future;

import com.google.common.io.LittleEndianDataInputStream;
import net.raphimc.noteblocklib.format.future.data.FutureData;
import net.raphimc.noteblocklib.format.future.header.FutureHeader;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

@SuppressWarnings("UnstableApiUsage")
public class FutureParser {

    public static FutureSong parse(final byte[] bytes, final File sourceFile) throws IOException {
        final LittleEndianDataInputStream dis = new LittleEndianDataInputStream(new ByteArrayInputStream(bytes));

        final FutureHeader header = new FutureHeader(dis);
        final FutureData data = new FutureData(header, dis);

        return new FutureSong(sourceFile, header, data);
    }

}
