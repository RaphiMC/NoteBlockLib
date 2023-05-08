package net.raphimc.noteblocklib.format.txt.header;

import java.util.Scanner;

public class TxtV2Header extends TxtV1Header {

    private float speed;

    public TxtV2Header(final Scanner scanner) {
        this.speed = Float.parseFloat(scanner.next("\\d+(|\\.\\d+)"));
    }

    public TxtV2Header(final float speed) {
        this.speed = speed;
    }

    public float getSpeed() {
        return this.speed;
    }

    public void setSpeed(final float speed) {
        this.speed = speed;
    }

}
