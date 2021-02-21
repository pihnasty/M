package a21_CMIS_TOU;

import math.MathP;

import java.util.ArrayList;
import java.util.List;

public class Delay {
    private final List<Double> taus;
    private final List<Double> tau1s;
    private final List<Double> deltaTau1s;
    private final List<Double> Gs;

    private final double dt;
    public Delay (double dt) {
        taus = new ArrayList<>();
        tau1s = new ArrayList<>();
        deltaTau1s = new ArrayList<>();
        Gs = new ArrayList<>();
        this.dt = dt;
        Gs.add(0.0);
        tau1s.add(-1.0);
        deltaTau1s.add(-1.0);
    }

    public void add(double tau, Speed speed) {
        taus.add(tau);
        double GsLast = Gs.get(Gs.size() - 1) + speed.getByTau(tau-dt) * dt;
        Gs.add(GsLast);
        double GsLastMinus_1 = GsLast -1.0;
        if (GsLast < 1.0) {
            tau1s.add(-1.0);
            deltaTau1s.add(-1.0);
        } else {
            int index = MathP.binarySearch(Gs, GsLastMinus_1, 0, Gs.size()-1);
            tau1s.add(taus.get(index));
            deltaTau1s.add(tau - taus.get(index));
        }

    }

    public double getTau1 (double tau) {
        int index = (int) (tau / dt);
        double tau1 =0 ;
        try {
            tau1 = tau1s.get(index);
        } catch (Exception e) {
            System.out.println();
            throw new IllegalArgumentException(e.getMessage()+" "  + e.toString());
        }

        return tau1;
    }

    public double getDeltaTau1 (double tau) {
        int index = (int) (tau / dt);
        return deltaTau1s.get(index);
    }

    public double getGs (double tau) {
        int index = (int) (tau / dt);
        double GsValue = 0.0;
        try {
            GsValue = Gs.get(index);
        } catch (Exception e) {
            throw e;
        }
        return GsValue;
    }

}
