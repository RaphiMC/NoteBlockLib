package net.raphimc.noteblocklib.format.txt;

import net.raphimc.noteblocklib.format.txt.data.TxtData;
import net.raphimc.noteblocklib.format.txt.header.TxtV1Header;
import net.raphimc.noteblocklib.format.txt.header.TxtV2Header;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Scanner;

public class TxtParser {

    public static TxtSong parse(final byte[] bytes, final File sourceFile) {
        final Scanner scanner = new Scanner(new ByteArrayInputStream(bytes));

        final TxtV1Header header = scanner.hasNext("#{3}\\d+") ? new TxtV2Header(scanner.skip("#{3}")) : new TxtV1Header();
        final TxtData data = new TxtData(scanner.useDelimiter("[:\r\n]+"));

        return new TxtSong(sourceFile, header, data);
    }

}
