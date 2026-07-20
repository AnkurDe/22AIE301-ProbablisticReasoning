package com.ankur.Lab_1.Q1;

import java.util.Arrays;

/**
 * Assume a fair coin, and a simple pair of fair dice are being thrown.
 * a) Generate the probability distribution for all the possible states of the coin, and the sum of the two
 * dice being thrown.
 */

class CoinDice {
    private final int runs;

    CoinDice(int runs) {
        this.runs = runs;
    }

    private static int random(final int lowerBound, final int higherBound) {
        return (int) (Math.random() * ((higherBound - lowerBound) + 1)) + lowerBound;
    }

    private static int genDice() {
        return random(1, 6);
    }

    private static int genCoin() {
        return random(0, 1);
    }

    String run() {
        final int coinFaces = 2;
        int[][] distribution = new int[coinFaces][13];

        for (int i = 0; i < this.runs; i++) {
            int dice1 = genDice();
            int dice2 = genDice();
            int sum = dice1 + dice2;
            int coin = genCoin();
            distribution[coin][sum]++;
        }

        StringBuilder sb = new StringBuilder();

        for (int[] eachCoin : distribution) {
            sb.append(Arrays.toString(eachCoin)).append(",\n");
        }

        return sb.toString();
    }
}

public class Q1 {
    static void main() {
        CoinDice coin = new CoinDice(1000000);
        IO.println(coin.run());
    }
}
