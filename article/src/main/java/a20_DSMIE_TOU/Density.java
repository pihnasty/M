package a20_DSMIE_TOU;

public class Density {

    private final InitialDensity initialDensity;
    private final Delay delay;
    private final Input input;
    private final Speed speed;

    public Density(InitialDensity initialDensity, Delay delay, Input input, Speed speed) {
        this.initialDensity = initialDensity;
        this.delay = delay;
        this.input = input;
        this.speed = speed;
    }

    public double getByTau(double tau) {
        double valueDensity = input.getByTau(tau) / speed.getByTau(tau);
        return valueDensity;
    }

    public double getOutputDensityByTau(double tau) {
        double valueDensity = initialDensity.getInitialDensityByKsi(1.0-delay.getGs(tau));
        return valueDensity;
    }
}
