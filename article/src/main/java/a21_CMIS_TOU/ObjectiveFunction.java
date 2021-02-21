package a21_CMIS_TOU;

import java.util.ArrayList;
import java.util.List;

public class ObjectiveFunction {
    private final double dt;
    private final double tau;
    private final double initialValue;
    private final Tariff tariff;
    private final Speed speed;
    private final Mass mass;
    private final ObjectiveFunctionCase objectiveFunctionCase;
    List<Double> taus;
    List<Double> qualityIntegrals;

    public ObjectiveFunction (double dt, double tau, double initialValue, Tariff tariff, Speed speed
        , Mass mass, ObjectiveFunctionCase objectiveFunctionCase) {
        taus = new ArrayList<>();
        qualityIntegrals = new ArrayList<>();
        this.dt = dt;
        this.tau=tau;
        this.initialValue=initialValue;
        this.tariff = tariff;
        this.speed = speed;
        this.mass = mass;
        this.objectiveFunctionCase = objectiveFunctionCase;

        taus.add(tau);
        qualityIntegrals.add(initialValue);

    }

    public void addQualityIntegrals(double tau) {
        double tauBefore = tau - dt;
        double valueBefore = qualityIntegrals.get(qualityIntegrals.size() - 1);
        double currentValue = valueBefore + getByTau(tauBefore) * dt;
        taus.add(tau);
        qualityIntegrals.add(currentValue);
    }

    public double getByTau (double tau) {
        double valueObjectiveFunction;
        switch (objectiveFunctionCase) {
            case ZUM:
                valueObjectiveFunction =  speed.getByTau(tau) * mass.getByTau(tau) * tariff.getByTau(tau);
                break;
            default:
                valueObjectiveFunction = Double.MAX_VALUE;
                break;
        }

        return valueObjectiveFunction;
    }

    public double getByTauSpeed (double tau, double speedValue) {
        double valueObjectiveFunction;
        switch (objectiveFunctionCase) {
            case ZUM:
                valueObjectiveFunction =  speedValue * mass.getByTau(tau) * tariff.getByTau(tau+dt);
                break;
            default:
                valueObjectiveFunction = Double.MAX_VALUE;
                break;
        }

        return valueObjectiveFunction;
    }

    public double getIntegralByCurrentTau () {
        return qualityIntegrals.get(taus.size()-1);
    }

    public double getQualityIntegralsByTau (double tau) {
        int index = (int) (tau / dt);
        return qualityIntegrals.get(index);
    }

    public double getQualityIntegralsByCurrentTau () {
        return qualityIntegrals.get(taus.size()-1);
    }

    public enum ObjectiveFunctionCase {
        ZUM ("f(t)=z(t)u(t)m(t)");

        private String name;
        ObjectiveFunctionCase(String name) {
            this.name =  name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "Input{" +
                "name='" + name + '\'' +
                '}';
        }
    }
}
