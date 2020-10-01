package neural.testold;

public class PsiO {
    double gamma0;
    double gamma1;
    double w;
    double fi;
    double m;

    public PsiO(double m) {
        this.m=m;
        this.gamma0 = (3 + m) / 24.0;
        this.gamma1 = gamma0;
        this.w = m * Math.PI;
        this.fi =  m * Math.PI / 4.0;
    }

    public Double getValue(Double ksi) {
        return gamma0 + gamma1 * Math.sin(w * ksi + fi);
    }

    public double getName() {
        return m;
    }
}