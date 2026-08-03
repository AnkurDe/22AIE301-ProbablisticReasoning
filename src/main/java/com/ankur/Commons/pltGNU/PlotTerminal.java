package com.ankur.Commons.pltGNU;

/**
 * Represents the supported Gnuplot output terminals.
 *
 * Each terminal defines:
 * <ul>
 *     <li>Gnuplot terminal name</li>
 *     <li>Default output file extension</li>
 *     <li>Whether width and height can be specified</li>
 * </ul>
 */
public enum PlotTerminal {

    /**
     * PNG image using Cairo.
     */
    PNG("pngcairo", "png", true),

    /**
     * JPEG image.
     */
    JPEG("jpeg", "jpg", true),

    /**
     * SVG vector image.
     */
    SVG("svg", "svg", true),

    /**
     * PDF output using Cairo.
     */
    PDF("pdfcairo", "pdf", true),

    /**
     * Encapsulated PostScript.
     */
    EPS("postscript eps enhanced color", "eps", false),

    /**
     * GIF image.
     */
    GIF("gif", "gif", true),

    /**
     * Animated GIF.
     */
    GIF_ANIMATED("gif animate", "gif", true),

    /**
     * ASCII text plot.
     */
    DUMB("dumb", "txt", false),

    /**
     * Interactive Qt window.
     */
    QT("qt", "", true),

    /**
     * Interactive X11 window (Linux).
     */
    X11("x11", "", true);

    /**
     * Gnuplot terminal name.
     */
    private final String terminalName;

    /**
     * Default file extension.
     */
    private final String extension;

    /**
     * Whether this terminal supports specifying image size.
     */
    private final boolean supportsSize;

    PlotTerminal(String terminalName,
                 String extension,
                 boolean supportsSize) {
        this.terminalName = terminalName;
        this.extension = extension;
        this.supportsSize = supportsSize;
    }

    /**
     * Returns the Gnuplot terminal name.
     */
    public String getTerminalName() {
        return terminalName;
    }

    /**
     * Returns the default output file extension.
     */
    public String getExtension() {
        return extension;
    }

    /**
     * Returns whether this terminal supports width/height.
     */
    public boolean supportsSize() {
        return supportsSize;
    }

    /**
     * Returns true if this terminal generates an output file.
     */
    public boolean isFileOutput() {
        return !extension.isBlank();
    }

    /**
     * Returns true if this terminal opens an interactive window.
     */
    public boolean isInteractive() {
        return extension.isBlank();
    }

    /**
     * Creates the complete Gnuplot terminal command.
     *
     * Example:
     * <pre>
     * set terminal pngcairo size 1600,900
     * </pre>
     *
     * or
     *
     * <pre>
     * set terminal pdfcairo size 1200,800
     * </pre>
     *
     * or
     *
     * <pre>
     * set terminal postscript eps enhanced color
     * </pre>
     */
    public String buildTerminalCommand(int width, int height) {

        if (supportsSize) {
            return String.format(
                    "set terminal %s size %d,%d",
                    terminalName,
                    width,
                    height
            );
        }

        return "set terminal " + terminalName;
    }

    /**
     * Finds a terminal from a file extension.
     *
     * Example:
     * png -> PNG
     * pdf -> PDF
     */
    public static PlotTerminal fromExtension(String extension) {

        if (extension == null || extension.isBlank()) {
            throw new IllegalArgumentException("Extension cannot be null or blank.");
        }

        String normalized = extension.toLowerCase();

        if (normalized.startsWith(".")) {
            normalized = normalized.substring(1);
        }

        for (PlotTerminal terminal : values()) {
            if (terminal.extension.equalsIgnoreCase(normalized)) {
                return terminal;
            }
        }

        throw new IllegalArgumentException(
                "Unsupported file extension: " + extension
        );
    }

    @Override
    public String toString() {
        return terminalName;
    }

}