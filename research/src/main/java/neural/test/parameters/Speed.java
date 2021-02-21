package neural.test.parameters;

import static neural.test.TransportSystem.VARIANT;

public class Speed {
    double g0;
    double g1;
    double w;
    double fi;
    double m;

    public Speed(double m) {
        this.m=m;
        this.g0 = (3 + m) / 8.0;
        this.g1 = g0;
        this.w = m * Math.PI;
        this.fi = m * Math.PI / 4.0;
    }

    public Double getValue(Double tau) {
        switch (VARIANT) {
            case 1:
            case 3:
                return g0;
            case 2:
                return g0 + g1 * Math.sin(w * tau + fi);
            case 4:
                return g0 + 0.5*g1 * Math.sin(w * tau + fi);
            case 5:
                return g0 + 0.25*g1 * Math.sin(w * tau + fi);
            case 6:
                return g0 + 0.1*g1 * Math.sin(w * tau + fi);
            case 7:
                return Math.sin(2.0 * Math.PI * tau) > 0 ? 0.5 : 1.5;
            case 8:
                return  tau % 1.0 > 0.2 ? 0.25 : 4.0;
            case 9:
                return 0.2+g0 + g1 * Math.cos(w * tau + fi);
            case 10:
                this.fi = m * Math.PI / 3.0;
                return g0 + 0.5*g1 * Math.sin(w * tau + fi);
            default:
                return 2*g0;
        }
    }

    public double getName() {
        return m;
    }
}
