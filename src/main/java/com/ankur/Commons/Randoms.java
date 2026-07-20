package com.ankur.Commons;

public class Randoms {
    public static int random(final int lowerBound, final int higherBound) {
        return (int) (Math.random() * ((higherBound - lowerBound) + 1)) + lowerBound;
    }

    public static int genDice() {
        return random(1, 6);
    }
}
