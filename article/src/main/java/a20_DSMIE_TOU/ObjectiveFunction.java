package a20_DSMIE_TOU;

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

    public void add(double tau) {
        double tauBefore = tau - dt;
        double valueBefore = qualityIntegrals.get(qualityIntegrals.size() - 1);
        double currentValue;
            switch (objectiveFunctionCase) {
            case ZUM:
                currentValue = valueBefore
                    + speed.getByTau(tauBefore)
                    * mass.getByTau(tauBefore)
                    * tariff.getByTau(tauBefore)
                    * dt;
                break;
            default:
                currentValue = Double.MAX_VALUE;
                break;
        }

        taus.add(tau);
        qualityIntegrals.add(currentValue);
    }

    public double getByTau (double tau) {
        int index = (int) (tau / dt);
        return qualityIntegrals.get(index);
    }
    public double getByCurrentTau () {
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
