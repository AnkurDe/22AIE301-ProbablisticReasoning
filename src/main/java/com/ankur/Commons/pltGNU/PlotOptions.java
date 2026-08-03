package com.ankur.Commons.pltGNU;

import java.awt.Color;
import java.util.Objects;

/**
 * Immutable configuration object containing all plot options.
 */
public final class PlotOptions {

    private final String title;
    private final String xLabel;
    private final String yLabel;
    private final String zLabel;

    private final int width;
    private final int height;

    private final boolean grid;
    private final boolean legend;

    private final String lineColor;
    private final int lineWidth;
    private final int pointSize;

    private final String backgroundColor;

    private final PlotTerminal terminal;

    private PlotOptions(Builder builder) {

        this.title = builder.title;
        this.xLabel = builder.xLabel;
        this.yLabel = builder.yLabel;
        this.zLabel = builder.zLabel;

        this.width = builder.width;
        this.height = builder.height;

        this.grid = builder.grid;
        this.legend = builder.legend;

        this.lineColor = builder.lineColor;
        this.lineWidth = builder.lineWidth;
        this.pointSize = builder.pointSize;

        this.backgroundColor = builder.backgroundColor;

        this.terminal = builder.terminal;
    }

    /**
     * Returns a Builder initialized with default values.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Returns a PlotOptions instance using all default values.
     */
    public static PlotOptions defaults() {
        return builder().build();
    }

    public String getTitle() {
        return title;
    }

    public String getXLabel() {
        return xLabel;
    }

    public String getYLabel() {
        return yLabel;
    }

    public String getZLabel() {
        return zLabel;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isGridEnabled() {
        return grid;
    }

    public boolean isLegendEnabled() {
        return legend;
    }

    public String getLineColor() {
        return lineColor;
    }

    public int getLineWidth() {
        return lineWidth;
    }

    public int getPointSize() {
        return pointSize;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public PlotTerminal getTerminal() {
        return terminal;
    }

    /**
     * Builder class.
     */
    public static final class Builder {

        private String title = "";

        private String xLabel = "X";
        private String yLabel = "Y";
        private String zLabel = "Z";

        private int width = 1600;
        private int height = 900;

        private boolean grid = true;
        private boolean legend = true;

        private String lineColor = "blue";
        private int lineWidth = 2;
        private int pointSize = 1;

        private String backgroundColor = "white";

        private PlotTerminal terminal = PlotTerminal.PNG;

        private Builder() {
        }

        public Builder title(String title) {
            this.title = Objects.requireNonNull(title);
            return this;
        }

        public Builder xLabel(String label) {
            this.xLabel = Objects.requireNonNull(label);
            return this;
        }

        public Builder yLabel(String label) {
            this.yLabel = Objects.requireNonNull(label);
            return this;
        }

        public Builder zLabel(String label) {
            this.zLabel = Objects.requireNonNull(label);
            return this;
        }

        public Builder width(int width) {

            if (width <= 0)
                throw new IllegalArgumentException("Width must be positive.");

            this.width = width;
            return this;
        }

        public Builder height(int height) {

            if (height <= 0)
                throw new IllegalArgumentException("Height must be positive.");

            this.height = height;
            return this;
        }

        public Builder size(int width, int height) {

            return width(width).height(height);
        }

        public Builder grid(boolean grid) {
            this.grid = grid;
            return this;
        }

        public Builder legend(boolean legend) {
            this.legend = legend;
            return this;
        }

        public Builder lineWidth(int width) {

            if (width <= 0)
                throw new IllegalArgumentException("Line width must be positive.");

            this.lineWidth = width;
            return this;
        }

        public Builder pointSize(int size) {

            if (size <= 0)
                throw new IllegalArgumentException("Point size must be positive.");

            this.pointSize = size;
            return this;
        }

        public Builder lineColor(String color) {

            this.lineColor = Objects.requireNonNull(color);

            return this;
        }

        /**
         * Convenience overload.
         */
        public Builder lineColor(Color color) {

            this.lineColor = String.format(
                    "#%02X%02X%02X",
                    color.getRed(),
                    color.getGreen(),
                    color.getBlue()
            );

            return this;
        }

        public Builder backgroundColor(String color) {

            this.backgroundColor = Objects.requireNonNull(color);

            return this;
        }

        public Builder terminal(PlotTerminal terminal) {

            this.terminal = Objects.requireNonNull(terminal);

            return this;
        }

        public PlotOptions build() {

            return new PlotOptions(this);
        }

    }

}