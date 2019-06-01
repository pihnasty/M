package neural.test;

public class Psi {
    double gamma0;
    double gamma1;
    double w;
    double fi;
    double m;

    public Psi(double m) {
        this.m=m;
        this.gamma0 = (3 + m) / 24.0;
        this.gamma0 = gamma1;
        this.w = m * Math.PI;
        this.fi =  m * Math.PI / 4.0;
    }

    public Double getValue(Double tau) {
        return gamma0 + gamma1 * Math.sin(w * tau + fi);
    }

    public double getName() {
        return m;
    }
}
