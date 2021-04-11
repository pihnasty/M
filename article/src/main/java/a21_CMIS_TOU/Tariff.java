package a21_CMIS_TOU;

public enum Tariff {
    Ukraine2zone ("Ukraine 2-th zone accounting (Nightsave)"),
    Ukraine3zone ("Ukraine 3-th zone accounting"),
    SouthAfricaEscomLowdemand ("South Africa (Escom),low demang season (sep-may)"),
    SouthAfricaEscomHigtdemand ("South Africa (Escom),higt demand season (jun-aug)"),
    SouthAfricaEscomNightSave ("South Africa (Escom),night save"),
    GreatBritainTOU ("Great Britain–TOU"),
    Fixed ("Great Britain–TOU");

    private String name;
    private double periodTime = 24;

    Tariff(String name) {
        this.name =  name;
    }

    public double getByTau(double tau) {
        double tariffTime = (tau * 24 /periodTime) %24;
        double tariffValue = -1.0;
        switch (this) {
            case Ukraine2zone:
                if (0.0 <= tariffTime && tariffTime < 7.0) {
                    tariffValue = 0.35;
                }
                if (7.0 <= tariffTime && tariffTime < 23.0) {
                    tariffValue = 1.8;
                }
                if (23.0 <= tariffTime && tariffTime < 24.0) {
                    tariffValue = 0.35;
                }
                break;
            case Ukraine3zone:
                if (0.0 <= tariffTime && tariffTime < 6.0) {
                    tariffValue = 0.25;
                }
                if (6.0 <= tariffTime && tariffTime < 8.0) {
                    tariffValue = 1.02;
                }
                if (8.0 <= tariffTime && tariffTime < 10.0) {
                    tariffValue = 1.8;
                }
                if (10.0 <= tariffTime && tariffTime < 18.0) {
                    tariffValue = 1.02;
                }
                if (18.0 <= tariffTime && tariffTime < 22.0) {
                    tariffValue = 1.8;
                }
                if (22.0 <= tariffTime && tariffTime < 23.0) {
                    tariffValue = 1.02;
                }
                if (23.0 <= tariffTime && tariffTime < 24.0) {
                    tariffValue = 0.25;
                }
                break;
            case SouthAfricaEscomLowdemand:
                if (0.0 <= tariffTime && tariffTime < 6.0) {
                    tariffValue = 0.64;
                }
                if (6.0 <= tariffTime && tariffTime < 7.0) {
                    tariffValue = 1.0;
                }
                if (7.0 <= tariffTime && tariffTime < 10.0) {
                    tariffValue = 1.45;
                }
                if (10.0 <= tariffTime && tariffTime < 18.0) {
                    tariffValue = 1.0;
                }
                if (18.0 <= tariffTime && tariffTime < 20.0) {
                    tariffValue = 1.45;
                }
                if (20.0 <= tariffTime && tariffTime < 22.0) {
                    tariffValue = 1.0;
                }
                if (22.0 <= tariffTime && tariffTime < 24.0) {
                    tariffValue = 0.64;
                }
                break;
            case SouthAfricaEscomHigtdemand:
                if (0.0 <= tariffTime && tariffTime < 6.0) {
                    tariffValue = 0.74;
                }
                if (6.0 <= tariffTime && tariffTime < 9.0) {
                    tariffValue = 4.43;
                }
                if (9.0 <= tariffTime && tariffTime < 17.0) {
                    tariffValue = 1.35;
                }
                if (17.0 <= tariffTime && tariffTime < 19.0) {
                    tariffValue = 4.43;
                }
                if (19.0 <= tariffTime && tariffTime < 22.0) {
                    tariffValue = 1.35;
                }
                if (22.0 <= tariffTime && tariffTime < 24.0) {
                    tariffValue = 0.74;
                }
                break;
            case SouthAfricaEscomNightSave:
                if (0.0 <= tariffTime && tariffTime < 6.0) {
                    tariffValue = 0.85;
                }
                if (6.0 <= tariffTime && tariffTime < 22.0) {
                    tariffValue = 1.09;
                }
                if (22.0 <= tariffTime && tariffTime < 24.0) {
                    tariffValue = 0.85;
                }
                break;
            case GreatBritainTOU:
                if (0.0 <= tariffTime && tariffTime < 6.0) {
                    tariffValue = 0.77;
                }
                if (6.0 <= tariffTime && tariffTime < 16.5) {
                    tariffValue = 1.07;
                }
                if (16.5 <= tariffTime && tariffTime < 19.5) {
                    tariffValue = 1.34;
                }
                if (19.5 <= tariffTime && tariffTime < 23.0) {
                    tariffValue = 1.07;
                }
                if (23.0 <= tariffTime && tariffTime < 24.0) {
                    tariffValue = 0.77;
                }
                break;
            case Fixed:
                tariffValue = 1.0;
                break;
            default:
                break;
        }
        return tariffValue;
    }

    public String getName() {
        return name;
    }

    public double getPeriodTime() {
        return periodTime;
    }

    public void setPeriodTime(double periodTime) {
        this.periodTime = periodTime;
    }

    public double getMax (double dt) {
        double maxValue= 0.0;
        double tau = 0.0;
        double value;
        for (; tau < periodTime; tau +=dt) {
            value = getByTau(tau);
           if ( maxValue < value) {
               maxValue = value;
           }
        }
        return maxValue;
    }

    public double getMin (double dt) {
        double minValue= Double.MAX_VALUE;
        double tau = 0.0;
        double value;
        for (; tau < periodTime; tau += dt) {
            value = getByTau(tau);
            if ( minValue > value) {
                minValue = value;
            }
        }
        return minValue;
    }

    @Override
    public String toString() {
        return "Tariff{" +
            "name='" + name + '\'' +
            '}';
    }

}
