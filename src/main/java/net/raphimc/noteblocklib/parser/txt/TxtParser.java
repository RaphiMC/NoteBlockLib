package net.raphimc.noteblocklib.parser.txt;

import net.raphimc.noteblocklib.parser.Header;
import net.raphimc.noteblocklib.parser.txt.data.TxtData;
import net.raphimc.noteblocklib.parser.txt.header.TxtV1Header;
import net.raphimc.noteblocklib.parser.txt.header.TxtV2Header;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.file.Files;
import java.util.Scanner;

public class TxtParser {

    public static TxtSong parseFile(final File file) {
        try {
            return parse(file, new ByteArrayInputStream(Files.readAllBytes(file.toPath())));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static TxtSong parseStream(final InputStream is) {
        try {
            return parse(null, new ByteArrayInputStream(IOUtils.toByteArray(is)));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static TxtSong parse(final File sourceFile, final InputStream is) {
        try {
            final Scanner scanner = new Scanner(is);

            final Header header = scanner.hasNext("#{3}\\d+") ? new TxtV2Header(scanner.skip("#{3}")) : new TxtV1Header();
            final TxtData data = new TxtData(scanner.useDelimiter("[:\r\n]+"));

            return new TxtSong(sourceFile, header, data);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

}
