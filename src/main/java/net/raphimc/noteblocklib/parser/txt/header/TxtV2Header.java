package net.raphimc.noteblocklib.parser.txt.header;

import net.raphimc.noteblocklib.parser.Header;

import java.util.Scanner;

public class TxtV2Header extends Header {

    private float speed;

    public TxtV2Header(final Scanner scanner) {
        super();

        this.speed = Float.parseFloat(scanner.next("\\d+(|\\.\\d+)"));
    }

    public TxtV2Header(final float speed) {
        super();

        this.speed = speed;
    }

    public void setSpeed(final float speed) {
        this.speed = speed;
    }

    public float getSpeed() {
        return this.speed;
    }

}
