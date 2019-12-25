package a19_east_european_journal_of_physics;

public class Z0 {
    public double getValue(double betta1, double betta2) {
        double z1 = new Z().getValue(betta1)*CommonConstants.z1_Is;
        double z2 = new Z().getValue(betta2)*CommonConstants.z2_Is;
        double v1 = CommonConstants.v_1;
        return (z1-z2)/2.0/v1;
    }
}
