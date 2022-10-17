package net.raphimc.noteblocklib.parser.future.header;

import net.raphimc.noteblocklib.parser.Header;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

public class FutureHeader extends Header {

    private boolean useMagicValue = true;

    public FutureHeader(final InputStream is) throws IOException {
        super();

        is.mark(is.available());
        final byte[] data = IOUtils.toByteArray(is);
        for (byte b : data) {
            if (b == 64) {
                this.useMagicValue = false;
                break;
            }
        }
        is.reset();
    }

    public FutureHeader(final boolean useMagicValue) {
        super();

        this.useMagicValue = useMagicValue;
    }

    public void setUseMagicValue(final boolean useMagicValue) {
        this.useMagicValue = useMagicValue;
    }

    public boolean isUseMagicValue() {
        return this.useMagicValue;
    }

}
