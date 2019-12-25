package a19_east_european_journal_of_physics;

public class Z {
    public Double getValue(Double bettaN) {
        Double v1 =CommonConstants.v_1;
        Double v_g =CommonConstants.v_g;
        Double v_f1 =CommonConstants.v_f1;
        Double v_dTemp = - (v_g*v_g)/(v1*v1)*v_f1;
        double betta = getBetta(bettaN);
        return v_dTemp * ( betta*betta/2-betta);
    }

    public double getBetta(double betta) {
        double tempBetta = betta % 2;
        tempBetta = (1.0 < tempBetta && tempBetta <= 2.0) ? tempBetta - 2.0 : tempBetta;
        tempBetta = (-2.0 <= tempBetta && tempBetta < -1.0) ? tempBetta + 2.0 : tempBetta;
        tempBetta = tempBetta < 0 ? -tempBetta : tempBetta;
        return tempBetta;
    }

}
