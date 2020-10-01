package a20_ICTM_KhAI;


import main.ProjectManager;
import org.junit.Test;
import string.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Amplitude_B_n {

    @Test
    public void saveTableDataTest() {
        int n = 2;
        List<Double> a_gamma = Arrays.asList(0.01, 0.1, 0.2, 0.3, 0.4);

        ProjectManager  projectManager = ProjectManager.getInstance();
        double deltaTau = 0.1;
        double tauMax = 10;
        
        

        List<List<String>> tableTest = new ArrayList<>();
        List<String> headear = new ArrayList<>();
        headear.add("   0.tau   ");
        a_gamma.stream().forEach(
            value -> headear.add(
                StringUtil.getDoubleFormatValue(value, 10)
            )
        );
        tableTest.add(headear);

        for (Double tau_Vg = 0.0; tau_Vg <tauMax;tau_Vg+=deltaTau) {

            List<String> row = new ArrayList<>();
            row.add(getValue(tau_Vg, headear, 0));


            int i = 1;
            for (Double value: a_gamma) {
                row.add(           getValue(Bn_to_Bmax(tau_Vg, n, value), headear, i)     );
                i++;
            }
            tableTest.add(row);
        }


        projectManager.saveData(tableTest, "data.csv", "article\\src\\main\\java\\a20_ICTM_KhAI\\result"+n);

    }

    public Double Bn ( double tau_Vg, int n, double a_gamma) {
        double pi_n = Math.PI * n;
        double Qc =1.0;
        return Math.exp(-pi_n * pi_n * a_gamma * a_gamma * tau_Vg) / (pi_n * pi_n * pi_n) / Math.sqrt(4.0 -pi_n * pi_n * a_gamma * a_gamma * (1+Qc)/Qc);

    }

    public Double Bn_to_Bmax ( double tau_Vg, int n, double a_gamma) {
        double pi_n = Math.PI * n;
        double Qc =1.0;
        return Math.exp(-pi_n * pi_n * a_gamma * a_gamma * tau_Vg);

    }

    public String getValue (Double value, List<String> headear, int columnNumber) {
        int additionSize = 2;
        return StringUtil.getDoubleFormatValue( value, headear.get(columnNumber).length() - additionSize);
    }



}