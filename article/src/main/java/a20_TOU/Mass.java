package a20_TOU;

import java.util.ArrayList;
import java.util.List;

public class Mass {
    private final Input input;
    private final Speed speed;
    private final Delay delay;
    private final Density density;
    List<Double> taus;
    List<Double> masses;
    private final double dt;

    public Mass(double dt, double initialTau, double initialValue
        , Input input, Speed speed, Delay delay, Density density) {
        taus = new ArrayList<>();
        masses = new ArrayList<>();
        this.dt = dt;
        this.input = input;
        this.speed = speed;
        this.delay = delay;
        this.density = density;

        taus.add(initialTau);
        masses.add(initialValue);
    }

    public void add(double tau) {
        double tauBefore = tau - dt;
        double tau1 = delay.getTau1(tauBefore);
        double valueMassBefore = masses.get(masses.size() - 1);

        double outputDensity= tau1>0 ? input.getByTau(tau1) / speed.getByTau(tau1) : density.getOutputDensityByTau(tau);
        double deltaValueInput = input.getByTau(tauBefore) - outputDensity * speed.getByTau(tauBefore);
        double currentValueMass = valueMassBefore + deltaValueInput * dt;

        taus.add(tau);
        masses.add(currentValueMass);
    }

    public double getByTau (double tau) {
        int index = (int) (tau / dt);
        return masses.get(index);
    }
    public double getByCurrentTau () {
        return masses.get(taus.size()-1);
    }

}
