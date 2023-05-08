package net.raphimc.noteblocklib.format.future.header;

import com.google.common.io.ByteStreams;
import net.raphimc.noteblocklib.model.Header;

import java.io.IOException;
import java.io.InputStream;

public class FutureHeader implements Header {

    private boolean useMagicValue = true;

    public FutureHeader(final InputStream is) throws IOException {
        is.mark(is.available());
        final byte[] data = ByteStreams.toByteArray(is);
        for (byte b : data) {
            if (b == 64) {
                this.useMagicValue = false;
                break;
            }
        }
        is.reset();
    }

    public FutureHeader(final boolean useMagicValue) {
        this.useMagicValue = useMagicValue;
    }

    public boolean useMagicValue() {
        return this.useMagicValue;
    }

    public void setUseMagicValue(final boolean useMagicValue) {
        this.useMagicValue = useMagicValue;
    }

}
