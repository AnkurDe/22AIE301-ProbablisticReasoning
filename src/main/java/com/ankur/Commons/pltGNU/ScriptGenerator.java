package com.ankur.Commons.pltGNU;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

/**
 * Generates GNUPlot script files.
 */
public final class ScriptGenerator {

    /**
     * Generates a complete gnuplot script.
     *
     * @param style plot style
     * @param dataFile temporary data file
     * @param outputImage output image
     * @param scriptFile script destination
     * @param options plot options
     */
    public void generate(
            PlotStyle style,
            Path dataFile,
            Path outputImage,
            Path scriptFile,
            PlotOptions options) throws IOException {

        Objects.requireNonNull(style);
        Objects.requireNonNull(dataFile);
        Objects.requireNonNull(outputImage);
        Objects.requireNonNull(scriptFile);
        Objects.requireNonNull(options);

        StringBuilder script = new StringBuilder();

        buildHeader(script, outputImage, options);

        buildAxes(script, options);

        buildGrid(script, options);

        buildLegend(script, options);

        buildPlot(script,
                style,
                dataFile,
                options);

        Files.writeString(scriptFile, script.toString());
    }

    /**
     * Writes terminal/output.
     */
    private void buildHeader(
            StringBuilder sb,
            Path output,
            PlotOptions options) {

        sb.append(
                options.getTerminal()
                        .buildTerminalCommand(
                                options.getWidth(),
                                options.getHeight()));

        sb.append(System.lineSeparator());

        if (options.getTerminal().isFileOutput()) {

            sb.append("set output '")
              .append(output.toAbsolutePath())
              .append("'")
              .append(System.lineSeparator());
        }

        if (!options.getTitle().isBlank()) {

            sb.append("set title '")
              .append(options.getTitle())
              .append("'")
              .append(System.lineSeparator());
        }

        sb.append(System.lineSeparator());
    }

    /**
     * Labels.
     */
    private void buildAxes(
            StringBuilder sb,
            PlotOptions options) {

        sb.append("set xlabel '")
          .append(options.getXLabel())
          .append("'")
          .append(System.lineSeparator());

        sb.append("set ylabel '")
          .append(options.getYLabel())
          .append("'")
          .append(System.lineSeparator());

        if (!options.getZLabel().isBlank()) {

            sb.append("set zlabel '")
              .append(options.getZLabel())
              .append("'")
              .append(System.lineSeparator());
        }

        sb.append(System.lineSeparator());
    }

    /**
     * Grid.
     */
    private void buildGrid(
            StringBuilder sb,
            PlotOptions options) {

        if (options.isGridEnabled()) {

            sb.append("set grid")
              .append(System.lineSeparator());

        } else {

            sb.append("unset grid")
              .append(System.lineSeparator());
        }

        sb.append(System.lineSeparator());
    }

    /**
     * Legend.
     */
    private void buildLegend(
            StringBuilder sb,
            PlotOptions options) {

        if (options.isLegendEnabled()) {

            sb.append("set key")
              .append(System.lineSeparator());

        } else {

            sb.append("unset key")
              .append(System.lineSeparator());
        }

        sb.append(System.lineSeparator());
    }

    /**
     * Plot command.
     */
    private void buildPlot(
            StringBuilder sb,
            PlotStyle style,
            Path dataFile,
            PlotOptions options) {

        String file = dataFile.toAbsolutePath().toString();

        switch (style) {

            case LINE ->

                    sb.append("plot '")
                      .append(file)
                      .append("' using 1:2 with lines")
                      .append(" lw ")
                      .append(options.getLineWidth())
                      .append(" lc rgb '")
                      .append(options.getLineColor())
                      .append("'");

            case LINES_POINTS ->

                    sb.append("plot '")
                      .append(file)
                      .append("' using 1:2 with linespoints")
                      .append(" lw ")
                      .append(options.getLineWidth())
                      .append(" pt 7 ps ")
                      .append(options.getPointSize());

            case POINTS ->

                    sb.append("plot '")
                      .append(file)
                      .append("' using 1:2 with points")
                      .append(" pt 7 ps ")
                      .append(options.getPointSize());

            case STEM ->

                    sb.append("plot '")
                      .append(file)
                      .append("' using 1:2 with impulses");

            case HISTOGRAM ->

                    sb.append("""
                            binwidth=1
                            bin(x,width)=width*floor(x/width)

                            plot '""")
                      .append(file)
                      .append("""
                            ' using (bin($1,binwidth)):(1.0) \
                            smooth frequency with boxes
                            """);

            case HEATMAP ->

                    sb.append("""
                            set view map
                            set pm3d map

                            splot '""")
                      .append(file)
                      .append("""
                            ' matrix with image
                            """);

            case SURFACE ->

                    sb.append("""
                            set hidden3d

                            splot '""")
                      .append(file)
                      .append("""
                            ' using 1:2:3 with lines
                            """);

            case SCATTER3D ->

                    sb.append("""
                            splot '""")
                      .append(file)
                      .append("""
                            ' using 1:2:3 with points pt 7
                            """);

            case MESH ->

                    sb.append("""
                            set dgrid3d

                            splot '""")
                      .append(file)
                      .append("""
                            ' using 1:2:3 with lines
                            """);

            case WIREFRAME ->

                    sb.append("""
                            set hidden3d

                            splot '""")
                      .append(file)
                      .append("""
                            ' using 1:2:3 with lines
                            """);

            case CONTOUR ->

                    sb.append("""
                            set contour
                            unset surface

                            splot '""")
                      .append(file)
                      .append("""
                            ' using 1:2:3
                            """);

            case PM3D ->

                    sb.append("""
                            set pm3d

                            splot '""")
                      .append(file)
                      .append("""
                            ' using 1:2:3
                            """);

            case BAR ->
                    throw new UnsupportedOperationException(
                            "Bar chart not implemented.");

            case AREA ->
                    throw new UnsupportedOperationException(
                            "Area chart not implemented.");

            case STEP ->
                    throw new UnsupportedOperationException(
                            "Step plot not implemented.");
        }

        sb.append(System.lineSeparator());
    }

}