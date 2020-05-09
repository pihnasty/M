package neural.testold;

import static neural.testold.TransportSystemO.VARIANT;

public class InputO {
    double gamma0;
    double gamma1;
    double w;
    double fi;
    double m;

    public InputO(double m) {
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
                return gamma0 + gamma1 * Math.sin(w * tau + fi);
            case 10:
                return gamma0 + gamma1 * Math.sin(w * tau + fi);
            default:
                return 1.0*gamma0;
        }
    }

    public double getName() {
        return m;
    }

}
