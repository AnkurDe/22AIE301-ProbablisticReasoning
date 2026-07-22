package com.ankur.tester;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class T1 extends JFrame {

    public T1(String title) {
        super(title);

        // 1. Create the dataset
        DefaultCategoryDataset dataset = createDataset();

        // 2. Create the chart
        JFreeChart chart = ChartFactory.createLineChart(
                "Monthly Sales Performance", // Chart title
                "Month",                    // X-Axis Label
                "Sales (in USD)",           // Y-Axis Label
                dataset,                    // Dataset
                PlotOrientation.VERTICAL,   // Plot orientation
                true,                       // Include legend
                true,                       // Use tooltips
                false                       // Configure URLs
        );

        // 3. Create a panel to hold the chart and add it to the frame
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        setContentPane(chartPanel);
    }

    private DefaultCategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Series 1: Product A (Value, Row Key/Series, Column Key/Category)
        dataset.addValue(1500, "Product A", "January");
        dataset.addValue(2100, "Product A", "February");
        dataset.addValue(1800, "Product A", "March");
        dataset.addValue(2500, "Product A", "April");

        // Series 2: Product B
        dataset.addValue(1200, "Product B", "January");
        dataset.addValue(1600, "Product B", "February");
        dataset.addValue(2200, "Product B", "March");
        dataset.addValue(2900, "Product B", "April");

        return dataset;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            T1 example = new T1("Java Plotting Example");
            example.setExtendedState(JFrame.MAXIMIZED_BOTH);
            example.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            example.pack();
            example.setVisible(true);
        });
    }
}
