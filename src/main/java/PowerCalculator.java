import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import java.util.*;

public class PowerCalculator {
    private List<GeneEffect> geneList;

    public PowerCalculator(List<GeneEffect> genes) {
        this.geneList = genes;
    }

    public DescriptiveStatistics getEffectSizeStats() {
        DescriptiveStatistics stats = new DescriptiveStatistics();
        for (GeneEffect g : geneList) {
            stats.addValue(Math.abs(g.cohensD));
        }
        return stats;
    }

    public double computePower(double effectSize, int sampleSizePerGroup, double alpha) {
        double nTotal = sampleSizePerGroup * 2;
        double tCrit = new org.apache.commons.math3.distribution.TDistribution(nTotal - 2)
                .inverseCumulativeProbability(1 - alpha / 2);
        double noncentrality = effectSize * Math.sqrt(sampleSizePerGroup / 2.0);
        org.apache.commons.math3.distribution.NormalDistribution normal = new org.apache.commons.math3.distribution.NormalDistribution();
        return 1 - normal.cumulativeProbability(tCrit - noncentrality);
    }

    public Map<Integer, Double> estimatePowerCurve(double effectSize, double alpha) {
        Map<Integer, Double> powerMap = new LinkedHashMap<>();
        for (int n = 5; n <= 100; n += 5) {
            double power = computePower(effectSize, n, alpha);
            powerMap.put(n, power);
        }
        return powerMap;
    }

    public int requiredSampleSize(double effectSize, double alpha, double targetPower) {
        for (int n = 5; n <= 1000; n++) {
            double power = computePower(effectSize, n, alpha);
            if (power >= targetPower)
                return n;
        }
        return -1;
    }
}