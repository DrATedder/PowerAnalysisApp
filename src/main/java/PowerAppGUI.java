import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import org.jfree.chart.ChartPanel;

public class PowerAppGUI extends JFrame {

    private JTextField alphaField, powerField, ratioField;
    private JLabel resultLabel;
    private JPanel chartPanel;

    public PowerAppGUI() {
        setTitle("Power Analysis App");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        inputPanel.add(new JLabel("Alpha (e.g., 0.05):"));
        alphaField = new JTextField("0.05");
        inputPanel.add(alphaField);

        inputPanel.add(new JLabel("Power (e.g., 0.8):"));
        powerField = new JTextField("0.8");
        inputPanel.add(powerField);

        inputPanel.add(new JLabel("Group Ratio (e.g., 1.0):"));
        ratioField = new JTextField("1.0");
        inputPanel.add(ratioField);

        JButton chooseButton = new JButton("Choose CSV");
        chooseButton.addActionListener(this::chooseFile);
        inputPanel.add(chooseButton);

        resultLabel = new JLabel("Sample size per group will appear here.");
        inputPanel.add(resultLabel);

        add(inputPanel, BorderLayout.NORTH);

        chartPanel = new JPanel();
        add(chartPanel, BorderLayout.CENTER);
    }

    private void chooseFile(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("CSV Files", "csv"));
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                List<GeneEffect> effects = loadEffectsFromFile(file);
                PowerCalculator calculator = new PowerCalculator(effects);
                double d = calculator.getEffectSizeStats().mean;
                double alpha = Double.parseDouble(alphaField.getText());
                double power = Double.parseDouble(powerField.getText());
                double ratio = Double.parseDouble(ratioField.getText());
                int sampleSize = (int)Math.ceil(calculator.estimateSampleSize(d, alpha, power, ratio));
                resultLabel.setText("Required sample size per group: " + sampleSize);

                PowerPlotter plotter = new PowerPlotter();
                JFreeChart chart = plotter.plotPowerCurve(d, alpha, power, ratio);
                chartPanel.removeAll();
                chartPanel.add(new ChartPanel(chart));
                chartPanel.validate();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error reading file or calculating: " + ex.getMessage());
            }
        }
    }

    private List<GeneEffect> loadEffectsFromFile(File file) throws IOException {
        List<GeneEffect> effects = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) continue;
            String[] parts = line.split(",");
            if (parts.length >= 2) {
                String gene = parts[0].trim();
                double effect = Double.parseDouble(parts[1].trim());
                effects.add(new GeneEffect(gene, effect));
            }
        }
        br.close();
        return effects;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PowerAppGUI().setVisible(true));
    }
}
