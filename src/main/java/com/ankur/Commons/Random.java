package com.ankur.Commons;

public class Random {
    /**
     * randomInt function generates random integer in the range of [lowerBound, upperBound]
     *
     * @param lowerBound  The lower bound of number to guess
     * @param higherBound The higher bound of number to guess
     * @return Returns the random number
     */
    public static int randomInt(final int lowerBound, final int higherBound) {
        return (int) (Math.random() * ((higherBound - lowerBound) + 1)) + lowerBound;
    }

    public static double random(final double lowerBound, final int higherBound) {
        return Math.random() * ((higherBound - lowerBound) + 1) + lowerBound;
    }

    /**
     * Simulates a fair die throw of 6 sides.
     *
     * @return The value from the die throw
     */
    public static int diceThrow() {
        return randomInt(1, 6);
    }

    /**
     * Simulates a fair coin toss of 2 sides.
     * Here 0 is head and 1 is tail
     * @return The value from the coin toss
     */
    public static int coinThrow() {
        return randomInt(0, 1);
    }
}
