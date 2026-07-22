package com.ankur.Lab_1.Q2;

import java.util.Arrays;

import static com.ankur.Commons.Random.diceThrow;

/**
 * Assume that a person stands at position 0 at time 0. He throws a fair sided die. If he gets an even
 * number, he will take that many steps forward. In other words, if he gets 2, he takes two steps forward.
 * If he gets 4, he takes four steps forward, and if he gets 6, he takes six steps forward. On the other hand,
 * if he gets an odd number, he takes the corresponding number of steps backwards. In other words, 1
 * results in one step back, 3 in 3 steps back, and 5 in 5 steps back. Generate the probability distribution
 * for all the states after 10 dice throws.
 */

class WalkingMan {
    private final int runs, noSteps;
    double[] arr;
    int minVal, maxVal;

    WalkingMan(int runs, int steps) {
        this.runs = runs;
        this.noSteps = steps;

    }

//    private static int random(final int lowerBound, final int higherBound) {
//        return (int) (Math.random() * ((higherBound - lowerBound) + 1)) + lowerBound;
//    }

//    private static int genDice() {
//        return random(1, 6);
//    }

    public void run() {
        this.minVal = -5 * noSteps;
        this.maxVal = 6 * noSteps;
        this.arr = new double[Math.abs(minVal) + maxVal + 1];

        for (int i = 0; i < this.runs; i++) {
            int pos = 0;
            for (int j = 0; j < this.noSteps; j++) {
                int step = diceThrow();

                // Logic for even odd detection
                // Checking for odd by logic number & 1 == 1 means odd
                if ((step & 1) == 1) {
                    // Odd
                    pos -= step;
                } else {
                    pos += step;
                }
            }
            arr[pos + Math.abs(minVal)]++;
        }

        arr = Arrays.stream(arr)
                .parallel()
                .map(element -> element / runs)
                .toArray();
    }

    public double prob(int pos) {
        return arr[Math.abs(minVal)-pos];
    }

    @Override
    public String toString() {
        return Arrays.toString(this.arr);
    }
}

public class Q2 {
    static void main() {
        WalkingMan walkingMan = new WalkingMan(60_000_000, 10);
        walkingMan.run();
        System.out.println(walkingMan);
        System.out.printf("positions \n0: %f, \n5: %f,\n10: %f,", walkingMan.prob(0), walkingMan.prob(5), walkingMan.prob(10));
    }
}
