package a20_TOU;

import java.util.ArrayList;
import java.util.List;

public class Speed {
    private final List<Double> speeds;
    private final List<Double> taus;

    private final List<Double> discreteSpeedModes;
    private final double dt;
    private double minValue;
    private double maxValue;

    public Speed(double dt, double minValue, double maxValue ) {
        this.speeds = new ArrayList<>();
        this.taus = new ArrayList<>();
        discreteSpeedModes = null;
        this.dt = dt;
        this.minValue =minValue;
        this.maxValue =maxValue;
    }

    public Speed(double dt, List<Double> discreteSpeedModes) {
        this.speeds = new ArrayList<>();
        this.taus = new ArrayList<>();
        if (discreteSpeedModes.isEmpty()) {
            throw new IllegalArgumentException("discreteSpeedModes is empty");
        }
        this.discreteSpeedModes = discreteSpeedModes;
        this.dt = dt;
    }

    public void add(double tau, double speedValue) {
        taus.add(tau);
        speeds.add(speedValue);
    }

    public double getByTau (double tau) {
        int index = (int) (tau / dt);
        double value = 0.0;
            try{
                value = speeds.get(index);
            } catch (Exception e) {
                throw e;
            }
        return value;
    }
    public double getByCurrentTau () {
        return speeds.get(taus.size()-1);
    }

    public List<Double> getDiscreteSpeedModes() {
        return discreteSpeedModes;
    }

    public double getMinValue() {
        return minValue;
    }

    public double getMaxValue() {
        return maxValue;
    }
}
