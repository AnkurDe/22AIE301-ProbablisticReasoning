package com.ankur.Commons.pltGNU;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * Manages temporary files used by the plotting library.
 */
public final class TempManager {

    private final PlotConfiguration configuration;

    public TempManager(PlotConfiguration configuration) {

        this.configuration = Objects.requireNonNull(configuration);

        ensureDirectoryExists();
    }

    /**
     * Creates the temp directory if it does not already exist.
     */
    private void ensureDirectoryExists() {

        try {

            Files.createDirectories(configuration.getTempDirectory());

        } catch (IOException e) {

            throw new RuntimeException(
                    "Unable to create temporary directory.",
                    e
            );
        }
    }

    /**
     * Deletes every file inside the temporary directory.
     */
    public void clean() {

        Path temp = configuration.getTempDirectory();

        if (!Files.exists(temp)) {
            return;
        }

        try (Stream<Path> stream = Files.walk(temp)) {

            stream.sorted(Comparator.reverseOrder())
                  .filter(path -> !path.equals(temp))
                  .forEach(path -> {

                      try {

                          Files.deleteIfExists(path);

                      } catch (IOException e) {

                          throw new RuntimeException(
                                  "Unable to delete: " + path,
                                  e
                          );
                      }

                  });

        } catch (IOException e) {

            throw new RuntimeException(
                    "Unable to clean temporary directory.",
                    e
            );
        }

        ensureDirectoryExists();
    }

    /**
     * Deletes a single file if it exists.
     */
    public void delete(Path file) {

        if (file == null) {
            return;
        }

        try {

            Files.deleteIfExists(file);

        } catch (IOException e) {

            throw new RuntimeException(
                    "Unable to delete file: " + file,
                    e
            );
        }
    }

    /**
     * Creates a temporary data file.
     *
     * Example:
     * data_5f8ad.dat
     */
    public Path createDataFile() {

        return configuration
                .getTempDirectory()
                .resolve("data_" + random() + ".dat");
    }

    /**
     * Creates a temporary script file.
     *
     * Example:
     * script_5f8ad.gnuplot
     */
    public Path createScriptFile() {

        return configuration
                .getTempDirectory()
                .resolve("script_" + random() + ".gnuplot");
    }

    /**
     * Creates a temporary matrix file.
     *
     * Used by heatmaps.
     */
    public Path createMatrixFile() {

        return configuration
                .getTempDirectory()
                .resolve("matrix_" + random() + ".dat");
    }

    /**
     * Creates a temporary 3D data file.
     */
    public Path create3DDataFile() {

        return configuration
                .getTempDirectory()
                .resolve("surface_" + random() + ".dat");
    }

    /**
     * Creates any temporary file.
     */
    public Path createFile(String prefix,
                           String extension) {

        if (!extension.startsWith(".")) {
            extension = "." + extension;
        }

        return configuration
                .getTempDirectory()
                .resolve(prefix + "_" + random() + extension);
    }

    /**
     * Returns the configured temporary directory.
     */
    public Path getTempDirectory() {

        return configuration.getTempDirectory();
    }

    /**
     * Generates a short unique identifier.
     */
    private String random() {

        return UUID.randomUUID()
                   .toString()
                   .replace("-", "")
                   .substring(0, 8);
    }

}