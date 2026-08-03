//package com.ankur.tester;
//
//
//import com.ankur.Commons.pltGNU.GNUPlot;
//import com.ankur.Commons.pltGNU.PlotConfiguration;
//import com.ankur.Commons.pltGNU.PlotOptions;
//import com.ankur.Commons.pltGNU.PlotTerminal;
//
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//public class PlotTest {
//
//    public static void main(String[] args) {
//
//        try {
//
//            /*
//             * Configure library
//             */
//            PlotConfiguration configuration =
//                    PlotConfiguration.builder()
//                            .baseDirectory(Paths.get("src/main/resources"))
//                            .executable("gnuplot")        // Linux
//                            //.executable("C:\\Program Files\\gnuplot\\bin\\gnuplot.exe") // Windows
//                            .autoCleanup(true)
//                            .build();
//
//            /*
//             * Create plotter
//             */
//            GNUPlot plotter = new GNUPlot(configuration);
//
//            /*
//             * Plot options
//             */
//            PlotOptions options = PlotOptions.builder()
//                    .title("Trigonometric Functions")
//                    .xLabel("X")
//                    .yLabel("Y")
//                    .terminal(PlotTerminal.PNG)
//                    .grid(true)
//                    .lineWidth(3)
//                    .lineColor("#1565C0")
//                    .size(1920, 1080)
//                    .build();
//
//            /*
//             * Generate sample data
//             */
//            int n = 360;
//
//            double[] x = new double[n];
//            double[] sin = new double[n];
//            double[] cos = new double[n];
//
//            for (int i = 0; i < n; i++) {
//
//                double angle = Math.toRadians(i);
//
//                x[i] = i;
//                sin[i] = Math.sin(angle);
//                cos[i] = Math.cos(angle);
//            }
//
//            /*
//             * Line plot
//             */
//            Path output = plotter.plot(
//                    x,
//                    sin,
//                    "sin.png",
//                    options
//            );
//
//            System.out.println("Line plot created:");
//            System.out.println(output);
//
//            /*
//             * Scatter plot
//             */
//            output = plotter.scatter(
//                    x,
//                    cos,
//                    "cos_scatter.png",
//                    options
//            );
//
//            System.out.println("Scatter plot created:");
//            System.out.println(output);
//
//            /*
//             * Stem plot
//             */
//            output = plotter.stem(
//                    x,
//                    sin,
//                    "stem.png",
//                    options
//            );
//
//            System.out.println("Stem plot created:");
//            System.out.println(output);
//
//            /*
//             * Histogram
//             */
//            double[] histogram = new double[1000];
//
//            for (int i = 0; i < histogram.length; i++) {
//
//                histogram[i] = Math.random() * 100.0;
//
//            }
//
//            output = plotter.hist(
//                    histogram,
//                    "histogram.png",
//                    options
//            );
//
//            System.out.println("Histogram created:");
//            System.out.println(output);
//
//            /*
//             * Heatmap
//             */
//            int rows = 50;
//            int cols = 50;
//
//            double[][] matrix = new double[rows][cols];
//
//            for (int r = 0; r < rows; r++) {
//
//                for (int c = 0; c < cols; c++) {
//
//                    matrix[r][c] =
//                            Math.sin(r * 0.15)
//                                    * Math.cos(c * 0.15);
//
//                }
//            }
//
//            output = plotter.heatmap(
//                    matrix,
//                    "heatmap.png",
//                    options
//            );
//
//            System.out.println("Heatmap created:");
//            System.out.println(output);
//
//            /*
//             * 3D Surface
//             */
//            int size = 100;
//
//            double[] xs = new double[size * size];
//            double[] ys = new double[size * size];
//            double[] zs = new double[size * size];
//
//            int index = 0;
//
//            for (int i = 0; i < size; i++) {
//
//                for (int j = 0; j < size; j++) {
//
//                    double xx = i / 10.0;
//                    double yy = j / 10.0;
//
//                    xs[index] = xx;
//                    ys[index] = yy;
//
//                    zs[index] =
//                            Math.sin(xx)
//                                    * Math.cos(yy);
//
//                    index++;
//
//                }
//
//            }
//
//            output = plotter.surface3D(
//                    xs,
//                    ys,
//                    zs,
//                    "surface.png",
//                    options
//            );
//
//            System.out.println("Surface plot created:");
//            System.out.println(output);
//
//            /*
//             * 3D Scatter
//             */
//            output = plotter.scatter3D(
//                    xs,
//                    ys,
//                    zs,
//                    "scatter3d.png",
//                    options
//            );
//
//            System.out.println("Scatter3D created:");
//            System.out.println(output);
//
//            /*
//             * Mesh
//             */
//            output = plotter.mesh3D(
//                    xs,
//                    ys,
//                    zs,
//                    "mesh.png",
//                    options
//            );
//
//            System.out.println("Mesh plot created:");
//            System.out.println(output);
//
//            System.out.println();
//            System.out.println("All tests completed successfully.");
//
//        }
//        catch (Exception e) {
//
//            e.printStackTrace();
//
//        }
//
//    }
//
//}