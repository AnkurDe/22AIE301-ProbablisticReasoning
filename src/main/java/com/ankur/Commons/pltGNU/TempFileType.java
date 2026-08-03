package com.ankur.Commons.pltGNU;

/**
 * Types of temporary files used by the plotting library.
 */
public enum TempFileType {

    /**
     * Standard 2D data file.
     */
    DATA("data", "dat"),

    /**
     * Matrix data for heatmaps.
     */
    MATRIX("matrix", "dat"),

    /**
     * 3D xyz point data.
     */
    SURFACE("surface", "dat"),

    /**
     * Gnuplot script.
     */
    SCRIPT("script", "gnuplot"),

    /**
     * Histogram data.
     */
    HISTOGRAM("histogram", "dat"),

    /**
     * Bar chart data.
     */
    BAR("bar", "dat");

    private final String prefix;
    private final String extension;

    TempFileType(String prefix,
                 String extension) {
        this.prefix = prefix;
        this.extension = extension;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getExtension() {
        return extension;
    }

}