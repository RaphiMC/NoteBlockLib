package net.raphimc.noteblocklib.util;

public enum Instrument {

    HARP(0, 0),
    BASS(1, 4),
    BASS_DRUM(2, 1),
    SNARE(3, 2),
    HAT(4, 3),
    GUITAR(5, 7),
    FLUTE(6, 5),
    BELL(7, 6),
    CHIME(8, 8),
    XYLOPHONE(9, 9),
    IRON_XYLOPHONE(10, 10),
    COW_BELL(11, 11),
    DIDGERIDOO(12, 12),
    BIT(13, 13),
    BANJO(14, 14),
    PLING(15, 15),
    ;

    private final byte nbsId;
    private final byte mcId;

    Instrument(final int nbsId, final int mcId) {
        this.nbsId = (byte) nbsId;
        this.mcId = (byte) mcId;
    }

    public byte nbsId() {
        return this.nbsId;
    }

    public byte mcId() {
        return this.mcId;
    }

    public static Instrument fromNbsId(final byte nbsId) {
        for (final Instrument instrument : Instrument.values()) {
            if (instrument.nbsId == nbsId) {
                return instrument;
            }
        }
        return null;
    }

    public static Instrument fromMcId(final byte mcId) {
        for (final Instrument instrument : Instrument.values()) {
            if (instrument.mcId == mcId) {
                return instrument;
            }
        }
        return null;
    }

}
