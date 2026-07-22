package com.ankur.Commons.plt;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Plot {
    static String yTitle = "";

    public static void plot(double[] x, double[] y) {
        if (x.length != y.length) {
            throw new IllegalArgumentException("The lengths of arrays x and y are not the same");
        }

        XYSeries series = new XYSeries(yTitle);
        final int length = x.length;

        for (int i = 0; i < length; i++) {
            series.add(x[i], y[i]);
        }
        plot(series);
    }

    public static void plot(XYSeries series) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        // Create chart
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Plot of y = sin(x)",
                "x",
                "sin(x)",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        // Display chart
        ChartFrame frame = new ChartFrame("Sine Function", chart);
        frame.setSize(800, 600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    }
}
