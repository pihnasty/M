package neural.test.parameters;

import static neural.test.BaseTransportSystem.VARIANT;

public class Psi {
    double gamma0;
    double gamma1;
    double w;
    double fi;
    double m;

    public Psi(double m) {
        this.m=m;
        this.gamma0 = (3 + m) / 24.0;
        this.gamma1 = gamma0;
        this.w = m * Math.PI;
        this.fi =  m * Math.PI / 4.0;
    }

    public Double getValue(Double ksi) {
        switch (VARIANT) {
            case 1:
                return gamma0;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 9:
            case 10:
                return gamma0 + gamma1 * Math.sin(w * ksi + fi);
            case 11:
            case 12:
            case 13:            case 14:            case 15:
                return 1.0;
            case 16:
                return (1 +   Math.sin(2*Math.PI * ksi))/2.0;   // This condition for the article in Kiev
            default:
                return null;
        }
    }

    public double getName() {
        return m;
    }
}
