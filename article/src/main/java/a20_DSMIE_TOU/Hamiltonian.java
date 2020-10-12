package a20_DSMIE_TOU;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Hamiltonian {

    private final List<Double> hamiltonianOptimalValues;
    private final List<Double> taus;
    private final List<Double> speed1s;
    private final List<Double> outputDensities;
    private final Tariff tariff;
    private final Speed speed;
    private final Mass mass;
    private final Psi_M psi_m;
    private final Input input;
    private final Delay delay;
    private final Psi_B psi_b;
    private final Density density;
    private final double dt;

    public Hamiltonian(double dt, Psi_B psi_b, Tariff tariff, Speed speed, Mass mass, Psi_M psi_m, Input input, Delay delay, Density density) {
        this.tariff = tariff;
        this.speed = speed;
        this.mass = mass;
        this.psi_m = psi_m;
        this.input = input;
        this.delay = delay;
        this.psi_b = psi_b;
        this.density = density;
        this.dt = dt;
        hamiltonianOptimalValues = new ArrayList<>();
        taus = new ArrayList<>();
        speed1s = new ArrayList<>();
        outputDensities  = new ArrayList<>();
    }

    private double execute(double tau, double speedValue) {
        double tauPrev = tau - dt;
        double tau1 = delay.getTau1(tauPrev);

        double outputDensity= tau1>0 ? input.getByTau(tau1) / speed.getByTau(tau1) : density.getOutputDensityByTau(tauPrev);

        return (psi_b.getPsi_bByTau() - tariff.getByTau(tau)) * speedValue * mass.getByTau(tauPrev)
            + psi_m.getByTau(tauPrev) * (
             input.getByTau(tauPrev) - outputDensity * speedValue
            );
    }

    public double getOptimalSpeedControl(double tau) {
        List<Double> discreteSpeedModes = speed.getDiscreteSpeedModes();
        double optimalHamiltoniaValue = Double.MIN_VALUE;
        double optimalSpeedControl = -1.0;
        if(Objects.nonNull(discreteSpeedModes)) {
            optimalSpeedControl = discreteSpeedModes.get(0);
            optimalHamiltoniaValue = execute(tau, optimalSpeedControl);

            for (int i=1; i< discreteSpeedModes.size(); i++) {
                double speedControl = discreteSpeedModes.get(i);
                double hamiltoniaValue = execute(tau, speedControl);
                if (optimalHamiltoniaValue < hamiltoniaValue) {
                    optimalSpeedControl = speedControl;
                    optimalHamiltoniaValue = hamiltoniaValue;
                }
            }

        }
        if(optimalSpeedControl<0) {
            throw new IllegalArgumentException("optimalSpeedControl<0");
        }
        optimalSpeedControl = getCheckAndCorrectOptimalSpeedControlConstraintDensity(tau, discreteSpeedModes, optimalSpeedControl);

        add(tau,optimalHamiltoniaValue );
        return optimalSpeedControl;
    }

    private double getCheckAndCorrectOptimalSpeedControlConstraintDensity(double tau, List<Double> discreteSpeedModes, double optimalSpeedControl) {
        // Constraint of the density.
        double constraintOfDensitySpeed = input.getByTau(tau)/1.0;   // The max density is 1.0

        if (constraintOfDensitySpeed > optimalSpeedControl) {
            if(Objects.nonNull(speed.getDiscreteSpeedModes()) && !speed.getDiscreteSpeedModes().isEmpty()) {
                List<Double> discreteSpeedModesSpeedSorted = new ArrayList<>(discreteSpeedModes);
                discreteSpeedModesSpeedSorted.sort(Double::compareTo);
                for(int i=0; i<discreteSpeedModesSpeedSorted.size(); i++) {
                    if(discreteSpeedModesSpeedSorted.get(i)>=constraintOfDensitySpeed) {
                        optimalSpeedControl = discreteSpeedModesSpeedSorted.get(i);
                        break;
                    }
                }
                if (optimalSpeedControl > discreteSpeedModesSpeedSorted.get(discreteSpeedModesSpeedSorted.size()-1)) {
                    throw new IllegalArgumentException("optimalSpeedControl   >  speed.getMaxValue()");
                }

            } else {
                optimalSpeedControl = constraintOfDensitySpeed;
                if (optimalSpeedControl > speed.getMaxValue()) {
                    throw new IllegalArgumentException("optimalSpeedControl   >  speed.getMaxValue()");
                }
            }

        }
        return optimalSpeedControl;
    }

    public void add (double tau, double hamiltonianOptimalValue) {
        hamiltonianOptimalValues.add(hamiltonianOptimalValue);
        taus.add(tau);
        addSpeed1(tau);
        addOutputDensity(tau);
    }

    private void addSpeed1(double tau) {
        double tau1 = delay.getTau1(tau-dt);
        double speed1= tau1>0 ? speed.getByTau(tau1) : -1.0;
        speed1s.add(speed1);
    }

    private void addOutputDensity (double tau) {
        double tauPrev = tau - dt;
        double tau1 = delay.getTau1(tauPrev);
        double outputDensity= tau1>0 ? input.getByTau(tau1) / speed.getByTau(tau1) : density.getOutputDensityByTau(tauPrev);
        outputDensities.add(outputDensity);
    }

    public double getByTau (double tau) {
        int index = (int) (tau / dt);
        return hamiltonianOptimalValues.get(index);
    }

    public double getSpeed1ByTau (double tau) {
        int index = (int) (tau / dt);
        return speed1s.get(index);
    }
    public double getOutputDensityByTau (double tau) {
        int index = (int) (tau / dt);
        return outputDensities.get(index);
    }
}
