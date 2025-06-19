import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import org.apache.commons.csv.*;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class Main {
    private static JTextField alphaField, powerField, ratioField;
    private static JTextArea outputArea;
    private static File selectedFile;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Power Analysis App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(5, 2));

        JButton fileButton = new JButton("Select CSV File");
        JLabel fileLabel = new JLabel("No file selected");

        fileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
                fileLabel.setText(selectedFile.getName());
            }
        });

        alphaField = new JTextField("0.05");
        powerField = new JTextField("0.8");
        ratioField = new JTextField("1.0");

        inputPanel.add(fileButton);
        inputPanel.add(fileLabel);
        inputPanel.add(new JLabel("Significance level (alpha):"));
        inputPanel.add(alphaField);
        inputPanel.add(new JLabel("Desired power:"));
        inputPanel.add(powerField);
        inputPanel.add(new JLabel("Group ratio (control/treatment):"));
        inputPanel.add(ratioField);

        JButton runButton = new JButton("Run Analysis");
        inputPanel.add(runButton);

        frame.add(inputPanel, BorderLayout.NORTH);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        frame.add(new JScrollPane(outputArea), BorderLayout.CENTER);

        runButton.addActionListener(e -> runAnalysis());

        frame.setVisible(true);
    }

    private static void runAnalysis() {
        if (selectedFile == null) {
            JOptionPane.showMessageDialog(null, "Please select a CSV file first.");
            return;
        }

        try {
            double alpha = Double.parseDouble(alphaField.getText());
            double power = Double.parseDouble(powerField.getText());
            double ratio = Double.parseDouble(ratioField.getText());

            List<GeneEffect> genes = new ArrayList<>();
            Reader in = new FileReader(selectedFile);
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);

            for (CSVRecord record : records) {
                String probeId = record.get("ProbeID");
                double d = Double.parseDouble(record.get("Cohens_d"));
                genes.add(new GeneEffect(probeId, d));
            }

            PowerCalculator calc = new PowerCalculator(genes);
            DescriptiveStatistics stats = calc.getEffectSizeStats();
            double medianD = stats.getPercentile(50);

            double adjustedAlpha = alpha / genes.size();
            int requiredN = calc.requiredSampleSize(medianD, adjustedAlpha, power);

            outputArea.setText(String.format(
                "Median Cohen's d: %.3f\nAdjusted alpha: %.8f\nRequired sample size per group: %d\n",
                medianD, adjustedAlpha, requiredN
            ));

            Map<Integer, Double> powerCurve = calc.estimatePowerCurve(medianD, adjustedAlpha);
            PowerPlotter.showPowerPlot(powerCurve, power);  // desired power line

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }
    }
}