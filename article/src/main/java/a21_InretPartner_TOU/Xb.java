package a21_InretPartner_TOU;

import java.util.ArrayList;
import java.util.List;

public class Xb {

    private final Speed speed;
    List<Double> taus;
    List<Double> xbs;
    private final double dt;
    private final Mass mass;

    public Xb(double dt, double initialTau, double initialValue
        , Speed speed, Mass mass) {
        taus = new ArrayList<>();
        xbs = new ArrayList<>();
        this.dt = dt;
        this.speed = speed;
        this.mass = mass;

        taus.add(initialTau);
        xbs.add(initialValue);
    }

    public void add(double tau) {
        double tauBefore = tau - dt;
        double valueBefore = xbs.get(xbs.size() - 1);
        double currentValue = valueBefore + speed.getByTau(tauBefore)*mass.getByTau(tauBefore) * dt;
        taus.add(tau);
        xbs.add(currentValue);
    }

    public double getByTau (double tau) {
        int index = (int) (tau / dt);
        return xbs.get(index);
    }
    public double getByCurrentTau () {
        return xbs.get(taus.size()-1);
    }

}
