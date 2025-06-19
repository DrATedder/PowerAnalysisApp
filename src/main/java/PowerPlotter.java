import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.ui.Layer;
import org.jfree.chart.ui.TextAnchor;
import org.jfree.chart.ui.RectangleAnchor;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Map;
import org.jfree.chart.ChartUtils;

public class PowerPlotter {

    public static void showPowerPlot(Map<Integer, Double> powerCurve, double desiredPower) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (Map.Entry<Integer, Double> entry : powerCurve.entrySet()) {
            dataset.addValue(entry.getValue(), "Power", entry.getKey());
        }

        JFreeChart chart = ChartFactory.createLineChart(
                "Power vs. Sample Size",
                "Sample Size per Group",
                "Power",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        // Add desired power line
        ValueMarker marker = new ValueMarker(desiredPower);
        marker.setPaint(Color.RED);
        marker.setStroke(new BasicStroke(2.0f));
        marker.setLabel("Desired Power");
        marker.setLabelAnchor(RectangleAnchor.TOP_LEFT);
        marker.setLabelTextAnchor(TextAnchor.BOTTOM_LEFT);

        CategoryPlot plot = chart.getCategoryPlot();
        plot.addRangeMarker(marker, Layer.FOREGROUND);

        // GUI Window
        JFrame frame = new JFrame("Power Analysis Plot");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        ChartPanel chartPanel = new ChartPanel(chart);
        frame.add(chartPanel, BorderLayout.CENTER);

        JButton saveButton = new JButton("Save Plot as PNG");
        saveButton.addActionListener((ActionEvent e) -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    ChartUtils.saveChartAsPNG(file, chart, 800, 600);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error saving plot: " + ex.getMessage());
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
    }
}