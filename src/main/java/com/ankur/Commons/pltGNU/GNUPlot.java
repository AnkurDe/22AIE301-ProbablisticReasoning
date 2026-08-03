//package com.ankur.Commons.pltGNU;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.util.Objects;
//
//public final class GNUPlot {
//
//    private final PlotConfiguration configuration;
//    private final TempManager tempManager;
////    private final DataWriter dataWriter;
//    private final ScriptGenerator scriptGenerator;
//    private final GnuplotExecutor executor;
//
//    /**
//     * Creates a GNUPlot instance using the supplied configuration.
//     */
//    public GNUPlot(PlotConfiguration configuration) {
//
//        this.configuration = Objects.requireNonNull(configuration);
//
//        this.tempManager = new TempManager(configuration);
//        this.dataWriter = new DataWriter();
//        this.scriptGenerator = new ScriptGenerator();
//        this.executor = new GnuplotExecutor(configuration);
//    }
//
//    /**
//     * Uses default configuration.
//     */
//    public GNUPlot() {
//
//        this(PlotConfiguration.builder().build());
//
//    }
//
//
//    public Path plot(
//            double[] x,
//            double[] y,
//            String outputName)
//            throws IOException, InterruptedException {
//
//        return plot(x, y, outputName,
//                PlotOptions.builder().build());
//
//    }
//
//    public Path plot(
//            double[] x,
//            double[] y,
//            String outputName,
//            PlotOptions options)
//            throws IOException, InterruptedException {
//
//        return generate2D(
//                PlotStyle.LINE,
//                x,
//                y,
//                outputName,
//                options);
//
//    }
//
//    public Path scatter(
//            double[] x,
//            double[] y,
//            String outputName,
//            PlotOptions options)
//            throws IOException, InterruptedException {
//
//        return generate2D(
//                PlotStyle.POINTS,
//                x,
//                y,
//                outputName,
//                options);
//
//    }
//
//    public Path stem(
//            double[] x,
//            double[] y,
//            String outputName,
//            PlotOptions options)
//            throws IOException, InterruptedException {
//
//        return generate2D(
//                PlotStyle.STEM,
//                x,
//                y,
//                outputName,
//                options);
//
//    }
//
//    public Path hist(
//            double[] values,
//            String outputName,
//            PlotOptions options)
//            throws IOException, InterruptedException {
//
//        validate(values);
//
//        tempManager.clean();
//
//        Path dataFile =
//                tempManager.createTempFile(
//                        TempFileType.DATA);
//
//        Path scriptFile =
//                tempManager.createTempFile(
//                        TempFileType.SCRIPT);
//
//        Path outputFile =
//                configuration.getOutputDirectory()
//                        .resolve(outputName);
//
//        dataWriter.writeValues(values, dataFile);
//
//        scriptGenerator.generate(
//                PlotStyle.HISTOGRAM,
//                dataFile,
//                outputFile,
//                scriptFile,
//                options);
//
//        executor.execute(scriptFile);
//
//        cleanup(dataFile, scriptFile);
//
//        return outputFile;
//    }
//
//    public Path heatmap(
//            double[][] matrix,
//            String outputName,
//            PlotOptions options)
//            throws IOException, InterruptedException {
//
//        Objects.requireNonNull(matrix);
//
//        tempManager.clean();
//
//        Path dataFile =
//                tempManager.createTempFile(
//                        TempFileType.MATRIX);
//
//        Path scriptFile =
//                tempManager.createTempFile(
//                        TempFileType.SCRIPT);
//
//        Path outputFile =
//                configuration.getOutputDirectory()
//                        .resolve(outputName);
//
//        dataWriter.writeMatrix(matrix, dataFile);
//
//        scriptGenerator.generate(
//                PlotStyle.HEATMAP,
//                dataFile,
//                outputFile,
//                scriptFile,
//                options);
//
//        executor.execute(scriptFile);
//
//        cleanup(dataFile, scriptFile);
//
//        return outputFile;
//    }
//
//    /*==========================================================
//                          3D PLOTS
//     ==========================================================*/
//
//    public Path surface3D(
//            double[] x,
//            double[] y,
//            double[] z,
//            String outputName,
//            PlotOptions options)
//            throws IOException, InterruptedException {
//
//        return generate3D(
//                PlotStyle.SURFACE,
//                x,
//                y,
//                z,
//                outputName,
//                options);
//
//    }
//
//    public Path scatter3D(
//            double[] x,
//            double[] y,
//            double[] z,
//            String outputName,
//            PlotOptions options)
//            throws IOException, InterruptedException {
//
//        return generate3D(
//                PlotStyle.SCATTER3D,
//                x,
//                y,
//                z,
//                outputName,
//                options);
//
//    }
//
//    public Path mesh3D(
//            double[] x,
//            double[] y,
//            double[] z,
//            String outputName,
//            PlotOptions options)
//            throws IOException, InterruptedException {
//
//        return generate3D(
//                PlotStyle.MESH,
//                x,
//                y,
//                z,
//                outputName,
//                options);
//
//    }
//
//    /*==========================================================
//                     INTERNAL PIPELINE
//     ==========================================================*/
//
//    private Path generate2D(
//            PlotStyle style,
//            double[] x,
//            double[] y,
//            String outputName,
//            PlotOptions options)
//            throws IOException, InterruptedException {
//
//        validate(x, y);
//
//        tempManager.clean();
//
//        Path dataFile =
//                tempManager.createTempFile(
//                        TempFileType.DATA);
//
//        Path scriptFile =
//                tempManager.createTempFile(
//                        TempFileType.SCRIPT);
//
//        Path outputFile =
//                configuration.getOutputDirectory()
//                        .resolve(outputName);
//
//        dataWriter.writeXY(x, y, dataFile);
//
//        scriptGenerator.generate(
//                style,
//                dataFile,
//                outputFile,
//                scriptFile,
//                options);
//
//        executor.execute(scriptFile);
//
//        cleanup(dataFile, scriptFile);
//
//        return outputFile;
//    }
//
//    private Path generate3D(
//            PlotStyle style,
//            double[] x,
//            double[] y,
//            double[] z,
//            String outputName,
//            PlotOptions options)
//            throws IOException, InterruptedException {
//
//        validate(x, y, z);
//
//        tempManager.clean();
//
//        Path dataFile =
//                tempManager.createTempFile(
//                        TempFileType.SURFACE);
//
//        Path scriptFile =
//                tempManager.createTempFile(
//                        TempFileType.SCRIPT);
//
//        Path outputFile =
//                configuration.getOutputDirectory()
//                        .resolve(outputName);
//
//        dataWriter.writeXYZ(
//                x,
//                y,
//                z,
//                dataFile);
//
//        scriptGenerator.generate(
//                style,
//                dataFile,
//                outputFile,
//                scriptFile,
//                options);
//
//        executor.execute(scriptFile);
//
//        cleanup(dataFile, scriptFile);
//
//        return outputFile;
//    }
//
//    /*==========================================================
//                         VALIDATION
//     ==========================================================*/
//
//    private void validate(double[] values) {
//
//        Objects.requireNonNull(values);
//
//        if (values.length == 0) {
//
//            throw new IllegalArgumentException(
//                    "Array is empty");
//
//        }
//
//    }
//
//    private void validate(
//            double[] x,
//            double[] y) {
//
//        Objects.requireNonNull(x);
//        Objects.requireNonNull(y);
//
//        if (x.length != y.length) {
//
//            throw new IllegalArgumentException(
//                    "x and y lengths differ");
//
//        }
//
//        if (x.length == 0) {
//
//            throw new IllegalArgumentException(
//                    "Arrays are empty");
//
//        }
//
//    }
//
//    private void validate(
//            double[] x,
//            double[] y,
//            double[] z) {
//
//        validate(x, y);
//
//        Objects.requireNonNull(z);
//
//        if (z.length != x.length) {
//
//            throw new IllegalArgumentException(
//                    "z length differs");
//
//        }
//
//    }
//
//    /*==========================================================
//                         CLEANUP
//     ==========================================================*/
//
//    private void cleanup(Path... files)
//            throws IOException {
//
//        if (!configuration.isAutoCleanup()) {
//
//            return;
//
//        }
//
//        for (Path file : files) {
//
//            Files.deleteIfExists(file);
//
//        }
//
//    }
//
//}