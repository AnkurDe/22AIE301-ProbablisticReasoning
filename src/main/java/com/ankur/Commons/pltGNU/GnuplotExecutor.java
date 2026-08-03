package com.ankur.Commons.pltGNU;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.Objects;

/**
 * Executes GNUPlot scripts.
 */
public final class GnuplotExecutor {

    private final PlotConfiguration configuration;

    public GnuplotExecutor(PlotConfiguration configuration) {
        this.configuration = Objects.requireNonNull(configuration);
    }

    /**
     * Executes the supplied gnuplot script.
     *
     * @param scriptFile script to execute
     */
    public void execute(Path scriptFile)
            throws IOException, InterruptedException {

        Objects.requireNonNull(scriptFile);

        if (!scriptFile.toFile().exists()) {
            throw new IllegalArgumentException(
                    "Script file does not exist: " + scriptFile);
        }

        ProcessBuilder builder = new ProcessBuilder(
                configuration.getGnuplotExecutable(),
                scriptFile.toAbsolutePath().toString()
        );

        builder.directory(
                configuration.getTempDirectory().toFile());

        builder.redirectErrorStream(true);

        Process process = builder.start();

        StringBuilder output = new StringBuilder();

        try (BufferedReader reader =
                     new BufferedReader(
                             new InputStreamReader(
                                     process.getInputStream()))) {

            String line;

            while ((line = reader.readLine()) != null) {

                output.append(line)
                      .append(System.lineSeparator());
            }
        }

        int exitCode = process.waitFor();

        if (exitCode != 0) {

            throw new IOException(
                    "GNUPlot exited with code "
                            + exitCode
                            + System.lineSeparator()
                            + output);
        }
    }

}