package com.ankur.tester;

import com.ankur.Commons.plt.Plot;
import org.jfree.data.xy.XYSeries;

public class SinPlot {

    public static void main(String[] args) {

        // Create a series for y = sin(x)
        XYSeries series = new XYSeries("sin(x)");

        double start = -4 * Math.PI;
        double end = 4 * Math.PI;
        double step = 0.01;

        for (double x = start; x <= end; x += step) {
            series.add(x, Math.sin(x));
        }

        // Dataset
        Plot.plot(series);
    }

}
