package com.ankur.Commons.np.Arr;

import java.util.Arrays;
import java.util.stream.IntStream;

public class General {
    public static double[] add(double[] A, double[] B) {
        if (A.length != B.length) {
            throw new IllegalArgumentException("Incorrect vector sizes");
        }
        return IntStream.range(0, A.length).mapToDouble(i -> A[i] + B[i]).toArray();
    }

    public static double[] subtract(double[] A, double[] B) {
        if (A.length != B.length) {
            throw new IllegalArgumentException("Incorrect vector sizes");
        }
        return IntStream.range(0, A.length).mapToDouble(i -> A[i] - B[i]).toArray();
    }

    public static double dot(double[] A, double[] B) {
        if (A.length != B.length)
            throw new IllegalArgumentException("Matrices are incompatible for dot product because they are unequal length");

        return IntStream.range(0, A.length).mapToDouble(i -> A[i] * B[i]).sum();
    }

    public static double norm(double[] vector) {
        return Math.sqrt(dot(vector, vector));
    }

    public static double norm(double[] vector, int p) {
        double temp = Arrays.stream(vector).map(v -> Math.pow(v, p)).sum();
        return Math.pow(temp, 1.0 / p);
    }

    public static double[] sin(double[] x) {
        return Arrays.stream(x).map(Math::sin).toArray();
    }

    public static double[] cos(double[] x) {
        return Arrays.stream(x).map(Math::cos).toArray();
    }

    public static double[] cosh(double[] x) {
        return Arrays.stream(x).map(Math::cosh).toArray();
    }

    public static double[] sqrt(double[] x) {
        return Arrays.stream(x).map(Math::sqrt).toArray();
    }

    public static double[] cbrt(double[] x) {
        return Arrays.stream(x).map(Math::cbrt).toArray();
    }

    public static double[] zeros(int n) {
        return new double[n];
    }
}
