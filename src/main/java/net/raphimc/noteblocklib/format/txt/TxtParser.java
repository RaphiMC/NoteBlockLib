package net.raphimc.noteblocklib.format.txt;

import com.google.common.io.ByteStreams;
import net.raphimc.noteblocklib.format.txt.data.TxtData;
import net.raphimc.noteblocklib.format.txt.header.TxtV1Header;
import net.raphimc.noteblocklib.format.txt.header.TxtV2Header;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Scanner;

public class TxtParser {

    public static TxtSong parseFile(final File file) throws IOException {
        return parse(file, new ByteArrayInputStream(Files.readAllBytes(file.toPath())));
    }

    public static TxtSong parseStream(final InputStream is) throws IOException {
        return parse(null, new ByteArrayInputStream(ByteStreams.toByteArray(is)));
    }

    public static TxtSong parse(final File sourceFile, final InputStream is) {
        final Scanner scanner = new Scanner(is);

        final TxtV1Header header = scanner.hasNext("#{3}\\d+") ? new TxtV2Header(scanner.skip("#{3}")) : new TxtV1Header();
        final TxtData data = new TxtData(scanner.useDelimiter("[:\r\n]+"));

        return new TxtSong(sourceFile, header, data);
    }

}
