import java.io.*;
import java.util.*;
import org.apache.commons.csv.*;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;


public class Main {
    public static void main(String[] args) throws IOException {
        String filePath = "gene_effects.csv";
        List<GeneEffect> genes = new ArrayList<>();

        Reader in = new FileReader(filePath);
        Iterable<CSVRecord> records = CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .parse(in);

        for (CSVRecord record : records) {
            String probeId = record.get("ProbeID");
            double d = Double.parseDouble(record.get("Cohens_d"));
            genes.add(new GeneEffect(probeId, d));
        }

        PowerCalculator calc = new PowerCalculator(genes);
        DescriptiveStatistics stats = calc.getEffectSizeStats();

        double medianD = stats.getPercentile(50);
        double alpha = 0.05 / genes.size();
        double targetPower = 0.8;

        System.out.printf("Median |d|: %.3f%n", medianD);
        System.out.printf("Adjusted alpha: %.8f%n", alpha);

        int requiredN = calc.requiredSampleSize(medianD, alpha, targetPower);
        System.out.printf("Estimated sample size per group for 80%% power at median |d|: %d%n", requiredN);

        Map<Integer, Double> powerCurve = calc.estimatePowerCurve(medianD, alpha);

        PowerPlotter.showPowerPlot(powerCurve, medianD);
    }
}
