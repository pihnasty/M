package a21_CMIS_TOU;

public enum Input {
    CONST_0p5_INPUT ("gamma1 = 0.5"),
    CONST_0p15_INPUT ("gamma1 = 0.15"),
    SIN_2PTAU_INPUT ("gamma1 = 1.0+sin(2PI*tau)"),
    SIN_0_15 ("0.15+ 0.15*Math.sin(2.0 * Math.PI * tau/24.0)"),
    SIN_0_5_15 ("0.15+(0.87-0.17)/2 + 0.15*Math.sin(2.0 * Math.PI * tau/24.0)"),
    SIN_0_5_fiPi_2 (" gamma1 = 0.15+(0.87-0.17)/2 + 0.3*Math.sin((2.0 * Math.PI +Math.PI /2.0)* tau/24.0)"),
    SIN_0_5 ("0.15+(0.87-0.17)/2 + 0.3*Math.sin(2.0 * Math.PI * tau/24.0)");

    private String name;
    Input (String name) {
        this.name =  name;
    }

    public double getByTau(double tau) {
        double gamma1;
        switch (this) {
            case CONST_0p5_INPUT:
                gamma1 = 0.5;
                break;
            case CONST_0p15_INPUT:
                gamma1 = 0.15;
                break;
            case SIN_2PTAU_INPUT:
                gamma1 = 1.0 + Math.sin(2.0 * Math.PI * tau);
                break;
            case SIN_0_5:
                gamma1 = 0.15+(0.87-0.17)/2 + 0.3*Math.sin(2.0 * Math.PI * tau/24.0);
                break;
            case SIN_0_5_fiPi_2:
                gamma1 = 0.15+(0.87-0.17)/2 + 0.3*Math.sin((2.0 * Math.PI +Math.PI /2.0)* tau/24.0);
                break;
            case SIN_0_5_15:
                gamma1 = 0.15+(0.87-0.17)/2 + 0.15*Math.sin(2.0 * Math.PI * tau/24.0);
                break;
            case SIN_0_15:
                gamma1 = 0.15+ 0.15*Math.sin(2.0 * Math.PI * tau/24.0);
                break;
            default:
                gamma1 = -1.0;
                break;
        }
        return gamma1;
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
