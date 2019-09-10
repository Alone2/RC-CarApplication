package com.example.rc_carapplication;

public enum MoveCarEnum {
    SYNC(4, 1200),

    FORWARD(11, 400),
    BACKWARDS(39, 400),
    FORWARDRIGHT(33, 400),
    FORWARDLEFT(27, 400),
    BACKWARDSRIGHT(45, 400),
    BACKWARDSLEFT(51, 400),
    RIGHT(64, 400),
    LEFT(59, 400);

    private final int repeats;
    private final int burst;

    MoveCarEnum(int repeats, int burst) {
        this.repeats = repeats;
        this.burst = burst;
    }

    public int getValue() {
        return repeats;
    }

    public int getBurst() {
        return burst;
    }
}
