package neural.test;

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
        return g0 + g1 * Math.sin(w * tau + fi);
    }

    public double getName() {
        return m;
    }
}
