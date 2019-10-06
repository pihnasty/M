package neural.test.papameters;

import static neural.test.TransportSystem.VARIANT;

public class OutputPlan {
    double gamma0;
    double gamma1;
    double w;
    double fi;
    double m;

    public OutputPlan(double m) {
        this.m=m;
        switch (VARIANT) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 9:
            case 10:
                init1_10(m);
                break;
            case 11:
            case 12:
            case 13:
            case 14:            case 15:
                case 16:   // This condition for the article in Kiev
                init1_11(m);
                break;
            default:
                throw new NullPointerException("Требуется определить вариант инициализации в OutputPlan");
        }
    }

    private void  init1_10(double m) {
        this.gamma0 = (3 + m) / 24.0;
        this.gamma1 = gamma0;
        this.w = m * Math.PI;
        this.fi = - m * Math.PI / 4.0;
    }

    private void  init1_11(double m) {

    }

    public Double getValue(Double tau) {
        switch (VARIANT) {
            case 11:
            case 12:
            case 13:
            case 14:            case 15:
                case 16:  // This condition for the article in Kiev
                return 1.0+Math.sin(Math.PI*tau);
            default:
                return null;
        }
    }

    public double getName() {
        return m;
    }

}
