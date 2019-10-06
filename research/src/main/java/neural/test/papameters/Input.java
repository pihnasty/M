package neural.test.papameters;

import static neural.test.TransportSystem.VARIANT;

public class Input {
    double gamma0;
    double gamma1;
    double w;
    double fi;
    double m;

    public Input(double m) {
        this.m=m;
        this.gamma0 = (3 + m) / 24.0;
        this.gamma1 = gamma0;
        this.w = m * Math.PI;
        this.fi = - m * Math.PI / 4.0;
    }

    public Double getValue(Double tau) {
        switch (VARIANT) {
            case 1:
                return gamma0;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                return gamma0 + gamma1 * Math.sin(w * tau + fi);
            case 7:
                return 0.5;
            case 9:
            case 10:
                return gamma0 + gamma1 * Math.sin(w * tau + fi);
            case 11:
            case 12:
            case 13:
            case 14:            case 15:            case 16:
                return 1.0;
            default:
                return 1.0*gamma0;
        }
    }

    public double getName() {
        return m;
    }

}
