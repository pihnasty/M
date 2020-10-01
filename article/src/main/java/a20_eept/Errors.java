package a20_eept;


import main.ProjectManager;
import org.junit.Test;
import string.StringUtil;

import java.util.ArrayList;
import java.util.List;


public class Errors {

    @Test
    public void saveTableDataTest() {
        int additionSize = 2;


        ProjectManager projectManager = ProjectManager.getInstance();
        double M =500.0;
        double deltaKsi = 1/M;
        double tauMax = 0.1;             //            0.08   =   0.5 * ( 1.0/Math.PI/2.0);
        double ksiMax =1.0;

        List<List<String>> tableTest = new ArrayList<>();
        List<String> headear = new ArrayList<>();
        headear.add("   0.M                   ");
        headear.add("   1.error               ");
        headear.add("   2.n_m_SystemDynamic   ");
        headear.add("   3.n_m_PDE             ");

        tableTest.add(headear);

        for (double ksi = 0.0; ksi < ksiMax+deltaKsi/2.0; ksi += deltaKsi) {

            List<String> row = new ArrayList<>();

            double n_m_SystemDynamic = n_m_SystemDynamic(ksi, tauMax, M);
            double n_m_PDE = n_m_PDE(ksi, tauMax, M);

            double err = (n_m_SystemDynamic - n_m_PDE);   //      /n_m_SystemDynamic(ksi, tauMax, M);
            row.add(StringUtil.getDoubleFormatValue(ksi, headear.get(0).length() - additionSize));
            row.add(StringUtil.getDoubleFormatValue(err, headear.get(1).length() - additionSize));
            row.add(StringUtil.getDoubleFormatValue(n_m_SystemDynamic, headear.get(2).length() - additionSize));
            row.add(StringUtil.getDoubleFormatValue(n_m_PDE, headear.get(3).length() - additionSize));

            tableTest.add(row);
        }


        projectManager.saveData(tableTest, (int) M +".csv", "article\\src\\main\\java\\a20_eept\\result");

    }

    private double nS(double ksi) {
        return  1.0 + 1.0/2.0*Math.sin(2*Math.PI*ksi);
    }

    private double n_m_SystemDynamic(double ksi, double tau, double M) {
        return  nS(ksi) +M/2.0 * (
                Math.cos(2.0*Math.PI*(ksi-1.0/M) )
                    - Math.cos(2.0*Math.PI*ksi)
            )*tau;
    }

    private double n_m_PDE(double ksi, double tau, double M) {
        return  1.0 + Math.sin(2.0*Math.PI*ksi)*(1.0/2.0 +Math.PI * tau);
    }

}