package a19_east_european_journal_of_physics;

public class W0_dKsi {
    public Double getValue(Double tau, Double ksi) {
        double v1 = CommonConstants.v_1;
        double W01_dKsi = new W01_dKsi().getValue(tau, ksi);
        double z0 = new Z0().getValue(ksi+v1*tau, ksi-v1*tau);
        return   W01_dKsi * CommonConstants.w_01_Is +  z0*CommonConstants.z0_Is;

    }
}
