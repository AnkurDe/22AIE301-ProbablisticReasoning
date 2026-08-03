package com.ankur.Commons.pltGNU;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Objects;

/**
 * Writes Java data structures into files that can be read by Gnuplot.
 */
public final class DataWriter {

    /**
     * Number formatting.
     */
    private static final Locale LOCALE = Locale.US;

    /**
     * Decimal precision.
     */
    private static final String FORMAT = "%.10f";

    /**
     * Writes x-y data.
     * <p>
     * Format:
     * x y
     * </p>
     *
     */
    public Path write(double[] x,
                      double[] y,
                      Path outputFile) throws IOException {

        Objects.requireNonNull(x);
        Objects.requireNonNull(y);
        Objects.requireNonNull(outputFile);

        if (x.length != y.length) {
            throw new IllegalArgumentException(
                    "x and y arrays must have the same length."
            );
        }

        try (BufferedWriter writer =
                     Files.newBufferedWriter(outputFile)) {

            for (int i = 0; i < x.length; i++) {

                writer.write(String.format(
                        LOCALE,
                        FORMAT + " " + FORMAT,
                        x[i],
                        y[i]
                ));

                writer.newLine();
            }
        }

        return outputFile;
    }

    /**
     * Writes only Y values.
     * <p>
     * Used for histograms.
     */
    public Path write(double[] values,
                      Path outputFile) throws IOException {

        Objects.requireNonNull(values);
        Objects.requireNonNull(outputFile);

        try (BufferedWriter writer =
                     Files.newBufferedWriter(outputFile)) {

            for (double value : values) {

                writer.write(String.format(
                        LOCALE,
                        FORMAT,
                        value
                ));

                writer.newLine();
            }
        }

        return outputFile;
    }

    /**
     * Writes x-y-z data.
     * <p>
     * Format:
     * <p>
     * x y z
     */
    public Path write(double[] x,
                      double[] y,
                      double[] z,
                      Path outputFile) throws IOException {

        Objects.requireNonNull(x);
        Objects.requireNonNull(y);
        Objects.requireNonNull(z);
        Objects.requireNonNull(outputFile);

        if (x.length != y.length ||
                y.length != z.length) {

            throw new IllegalArgumentException(
                    "All arrays must have the same length."
            );
        }

        try (BufferedWriter writer =
                     Files.newBufferedWriter(outputFile)) {

            for (int i = 0; i < x.length; i++) {

                writer.write(String.format(
                        LOCALE,
                        FORMAT + " " + FORMAT + " " + FORMAT,
                        x[i],
                        y[i],
                        z[i]
                ));

                writer.newLine();
            }
        }

        return outputFile;
    }

    /**
     * Writes a matrix.
     * <p>
     * Each row becomes one row in the data file.
     */
    public Path write(double[][] matrix,
                      Path outputFile) throws IOException {

        Objects.requireNonNull(matrix);
        Objects.requireNonNull(outputFile);

        try (BufferedWriter writer =
                     Files.newBufferedWriter(outputFile)) {

            for (double[] row : matrix) {

                for (int col = 0; col < row.length; col++) {

                    if (col > 0) {
                        writer.write(' ');
                    }

                    writer.write(
                            String.format(
                                    LOCALE,
                                    FORMAT,
                                    row[col]
                            )
                    );
                }

                writer.newLine();
            }
        }

        return outputFile;
    }

}