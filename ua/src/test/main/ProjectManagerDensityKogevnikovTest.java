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
        double deltaTau = 0.2;
        double tauMax = 1.0;

        List<Double> taus = new ArrayList<>();
        List<Double> ksis = new ArrayList<>();

        for (double tau=0.0; tau<tauMax; tau+=deltaTau) {
            taus.add(tau);
        }

        for (double ksi = 0.0; ksi <= 1.0; ksi += 0.001) {
            ksis.add(ksi);
        }


        ProjectManager projectManager = ProjectManager.getInstance();



        List<List<String>> tableTest = new ArrayList<>();

        List<String> headear = new ArrayList<>();
        headear.add("   0.NN   ");

        taus.forEach( tau ->
            headear.add(" "
                +StringUtil.getDoubleFormatValue(tau,1)
                +".tau"+" ")
        );

        tableTest.add(headear);


        ksis.forEach(ksi -> {
                List<String> row = new ArrayList<>();
                row.add(StringUtil.getDoubleFormatValue(ksi, headear.get(1).length() - additionSize));
                taus.forEach(tau -> {
                        row.add(StringUtil.getDoubleFormatValue(Q_0(tau, ksi), headear.get(1).length() - additionSize));
                    }
                );
                tableTest.add(row);
            }
        );


        projectManager.saveData(tableTest, Settings.Values.NEURAL_NETWORk_MODEL_SPEED_INPUT_TEST_DATA_CSV);

    }

    private double H (double ksi) {
        return ksi>=0.0 ? 1.0 : 0.0;
    }

    private double psi (double ksi) {
        return H(ksi-0.2)-H(ksi-0.3);
    }

    private double gamma (double tau) {
        return 1.0+Math.sin(2.0*Math.PI*tau);
    }

    private double getSpeed (double tau) {
        return 1.0;
    }

    private double Q_0 (double tau, double ksi) {
        double g = getSpeed(tau);
        double tauKsi = tau - ksi/g;
        return (H(ksi)-H(-g*tauKsi))
            * gamma(g*tauKsi) /g
            + H(-g*tauKsi)*psi(-g*tauKsi);
    }


}