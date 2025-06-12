import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.util.Map;

public class PowerPlotter {

    public static void showPowerPlot(Map<Integer, Double> powerCurve, double effectSize) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (Map.Entry<Integer, Double> entry : powerCurve.entrySet()) {
            dataset.addValue(entry.getValue(), "Power", entry.getKey());
        }

        JFreeChart chart = ChartFactory.createLineChart(
                "Power vs. Sample Size (Effect Size d = " + String.format("%.2f", effectSize) + ")",
                "Sample Size per Group",
                "Power",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        JFrame frame = new JFrame("Power Analysis");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ChartPanel(chart));
        frame.pack();
        frame.setVisible(true);
    }
}