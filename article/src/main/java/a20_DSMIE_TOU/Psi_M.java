package a20_DSMIE_TOU;

import java.util.ArrayList;
import java.util.List;

public class Psi_M {
    private final Psi_B psi_b;
    private final Tariff tariff;
    private final Speed speed;
    List<Double> taus;
    List<Double> psi_Ms;
    private final double dt;

    public Psi_M(double dt, double tau, double initialValue, Psi_B psi_b, Tariff tariff, Speed speed) {
        taus = new ArrayList<>();
        psi_Ms = new ArrayList<>();
        this.dt = dt;
        this.psi_b = psi_b;
        this.tariff = tariff;
        this.speed = speed;
        taus.add(tau);
        psi_Ms.add(initialValue);
    }

    public void add(double tau ) {
        double tauBefore = tau - dt;
        double valuePsi_MsBefore = psi_Ms.get(psi_Ms.size() - 1);
        double valuePsi_Ms
            = valuePsi_MsBefore + dt*(tariff.getByTau(tauBefore)-psi_b.getPsi_bByTau())*speed.getByTau(tauBefore);

        taus.add(tau);
        psi_Ms.add(valuePsi_Ms);
    }

    public double getByTau (double tau) {
        int index = (int) (tau / dt);
        double psi_MsValue;
        try {
            psi_MsValue = psi_Ms.get(index);
        } catch (Exception e) {
            throw e;
        }
        return psi_MsValue;
    }
    public double getByCurrentTau () {
        return psi_Ms.get(taus.size()-1);
    }


}
