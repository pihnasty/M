package main;

import neural.test.ControlSpeedAndInputBunkerTransportSystem;
import org.junit.Test;
import settings.Settings;
import string.StringUtil;

import java.util.ArrayList;
import java.util.List;


public class ProjectManagerDensityKogevnikovTest {

    @Test
    public void saveTableDataTest() {
        int additionSize = 2;
        double deltaTau = 0.1;
        double tauMax = 10.0;

        List<Double> taus = new ArrayList<>();

        for (double tau=0.0; tau<tauMax; tau+=deltaTau) {
            taus.add(tau);
        }




        ProjectManager projectManager = ProjectManager.getInstance();

        int variant= 16;

        ControlSpeedAndInputBunkerTransportSystem transportSystem = new ControlSpeedAndInputBunkerTransportSystem(variant, deltaTau);



        List<List<String>> tableTest = new ArrayList<>();

        List<String> headear = new ArrayList<>();
        headear.add("   0.NN   ");

        taus.forEach( tau ->
            headear.add(" "+tau+".tau"+" ")
        );

        tableTest.add(headear);


        taus.forEach(tau -> {
                List<String> row = new ArrayList<>();
                for (double ksi = 0.0; ksi <= 1.0; ksi = +0.01) {
                    row.add(StringUtil.getDoubleFormatValue(Q_0(tau, ksi), headear.get(1).length() - additionSize));
                }
                tableTest.add(row);
            }
        );


        projectManager.saveData(tableTest, Settings.Values.NEURAL_NETWORk_MODEL_SPEED_INPUT_TEST_DATA_CSV);

    }

    private double H (double ksi) {
        return ksi>=0.0 ? ksi : 0.0;
    }

    private double psi (double ksi) {
        return H(ksi-0.5)-H(ksi-0.6);
    }

    private double gamma (double tau) {
        return 1.0+Math.sin(tau);
    }

    private double getSpeed (double tau) {
        return 1.0;
    }

    private double Q_0 (double tau, double ksi) {
        double g = getSpeed(tau);
        double tauKsi = tau - tau/g;
        return (H(ksi)-H(-g*tauKsi))
            * gamma(g*tauKsi) /g
            + H(g*tauKsi)*psi(g*tauKsi);
    }


}