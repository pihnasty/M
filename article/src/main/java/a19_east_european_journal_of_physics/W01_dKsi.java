package a19_east_european_journal_of_physics;

public class W01_dKsi {
    public Double getValue(Double tau, Double ksi) {
        Double v1 =CommonConstants.v_1;
        Double v2 =CommonConstants.v_2.apply(tau);
        Double v2_v1_SQR = (v2*v2)/(v1*v1);
        Double alfa1 = CommonConstants.alfa1;
        return v2_v1_SQR*ksi + (alfa1 -v2_v1_SQR);
    }
}
