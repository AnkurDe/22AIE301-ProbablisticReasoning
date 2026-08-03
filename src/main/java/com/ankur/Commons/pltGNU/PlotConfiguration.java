package com.ankur.Commons.pltGNU;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * Global configuration for the GNUPlot library.
 *
 * This class controls:
 *  - Working directories
 *  - Gnuplot executable
 *  - Cleanup policy
 *  - Output directory
 */
public final class PlotConfiguration {

    /**
     * Default singleton configuration.
     */
    private static final PlotConfiguration DEFAULT =
            PlotConfiguration.builder().build();

    private final Path tempDirectory;
    private final Path outputDirectory;

    private final String gnuplotExecutable;

    private final boolean autoCleanup;

    private final boolean overwriteOutput;

    private PlotConfiguration(Builder builder) {

        this.tempDirectory = builder.tempDirectory;
        this.outputDirectory = builder.outputDirectory;
        this.gnuplotExecutable = builder.gnuplotExecutable;
        this.autoCleanup = builder.autoCleanup;
        this.overwriteOutput = builder.overwriteOutput;

        createDirectories();
    }

    /**
     * Creates directories if they do not exist.
     */
    private void createDirectories() {

        try {

            Files.createDirectories(tempDirectory);
            Files.createDirectories(outputDirectory);

        } catch (Exception e) {

            throw new RuntimeException(
                    "Unable to create plotting directories.",
                    e
            );
        }
    }

    /**
     * Returns the default configuration.
     */
    public static PlotConfiguration defaults() {
        return DEFAULT;
    }

    /**
     * Returns a new builder.
     */
    public static Builder builder() {
        return new Builder();
    }

    public Path getTempDirectory() {
        return tempDirectory;
    }

    public Path getOutputDirectory() {
        return outputDirectory;
    }

    public String getGnuplotExecutable() {
        return gnuplotExecutable;
    }

    public boolean isAutoCleanupEnabled() {
        return autoCleanup;
    }

    public boolean isOverwriteOutputEnabled() {
        return overwriteOutput;
    }

    /**
     * Builder
     */
    public static final class Builder {

        /**
         * Default location:
         *
         * ~/.gnuplot-java/
         */
        private Path baseDirectory =
                Paths.get(System.getProperty("user.home"),
                        ".gnuplot-java");

        private Path tempDirectory =
                baseDirectory.resolve("temp");

        private Path outputDirectory =
                baseDirectory.resolve("plots");

        private String gnuplotExecutable = "gnuplot";

        private boolean autoCleanup = true;

        private boolean overwriteOutput = true;

        private Builder() {
        }

        /**
         * Sets a base working directory.
         */
        public Builder baseDirectory(Path directory) {

            Objects.requireNonNull(directory);

            this.baseDirectory = directory;

            this.tempDirectory =
                    directory.resolve("temp");

            this.outputDirectory =
                    directory.resolve("plots");

            return this;
        }

        /**
         * Sets custom temp directory.
         */
        public Builder tempDirectory(Path directory) {

            this.tempDirectory =
                    Objects.requireNonNull(directory);

            return this;
        }

        /**
         * Sets custom output directory.
         */
        public Builder outputDirectory(Path directory) {

            this.outputDirectory =
                    Objects.requireNonNull(directory);

            return this;
        }

        /**
         * Sets gnuplot executable.
         *
         * Linux:
         * gnuplot
         *
         * Windows:
         * C:\\Program Files\\gnuplot\\bin\\gnuplot.exe
         */
        public Builder executable(String executable) {

            this.gnuplotExecutable =
                    Objects.requireNonNull(executable);

            return this;
        }

        public Builder autoCleanup(boolean value) {

            this.autoCleanup = value;

            return this;
        }

        public Builder overwriteOutput(boolean value) {

            this.overwriteOutput = value;

            return this;
        }

        public PlotConfiguration build() {

            return new PlotConfiguration(this);
        }

    }

}