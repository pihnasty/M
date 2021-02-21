package a21_InretPartner_TOU;

public enum InitialDensity {
    CONST_0p5_DENSITY ("density = 0.5"),
    CONST_0p8523_DENSITY ("density = 0.8523"),
    SIN_2PTAU_DENSITY ("gamma1 = 0.5+0.5sin(2PI*ksi)");

    private String name;
    InitialDensity(String name) {
        this.name =  name;
    }

    public double getInitialDensityByKsi(double ksi) {
        double psi;
        switch (this) {
            case CONST_0p5_DENSITY:
                psi = 0.5;
                break;
            case CONST_0p8523_DENSITY:
                psi = 0.8523;
                break;
            case SIN_2PTAU_DENSITY:
                psi = 0.5 + 0.5 * Math.sin(2.0 * Math.PI * ksi);
                break;
            default:
                psi = -1.0;
                break;
        }
        return psi;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Input{" +
            "name='" + name + '\'' +
            '}';
    }
}
