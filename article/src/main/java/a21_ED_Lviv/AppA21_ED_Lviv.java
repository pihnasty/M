package a21_ED_Lviv;


import main.ProjectManager;
import org.junit.Test;
import string.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class AppA21_ED_Lviv {

    private double deltaTau11;
    private double deltaTau12;
    private double deltaTau13;
    private double flowRestriction;
    private double controlGammaValueMax1;
    private double controlGammaValueMax2;
    private double controlGammaValueMax3;
    private double deltaTau;
    private double tauLoad;
    private double n0max;

    private void init (int n ) {

        deltaTau = 0.01;
        n0max = 2.0;


        deltaTau11 = 1.0/3.0;
        deltaTau12 = 2.0/3.0;
        deltaTau13 = 3.0/3.0;

        flowRestriction = 1.0;


        switch (n) {
            case 1:
                controlGammaValueMax1 = flowRestriction;
                controlGammaValueMax2 = flowRestriction;
                controlGammaValueMax3 = flowRestriction;
                break;
            case 2:
                controlGammaValueMax1 = flowRestriction /2.0;
                controlGammaValueMax2 = flowRestriction /2.0;
                controlGammaValueMax3 = flowRestriction /2.0;
                break;
            case 3:
                controlGammaValueMax1 = flowRestriction /3.0;
                controlGammaValueMax2 = flowRestriction /3.0;
                controlGammaValueMax3 = flowRestriction /3.0;
                break;
            case 4:
                controlGammaValueMax1 = flowRestriction /4.0;
                controlGammaValueMax2 = flowRestriction /4.0;
                controlGammaValueMax3 = flowRestriction /4.0;
                break;
            default:
        }

        if (controlGammaValueMax1 >= flowRestriction) {
            tauLoad = n0max / flowRestriction + deltaTau11;
        }

        if (flowRestriction > controlGammaValueMax1 && controlGammaValueMax1 >= flowRestriction / 2.0) {
            tauLoad = (n0max - controlGammaValueMax1* (deltaTau12-deltaTau11) ) / flowRestriction + deltaTau12;
        }

        if (flowRestriction / 2.0 > controlGammaValueMax1 && controlGammaValueMax1 >= flowRestriction / 3.0) {
            tauLoad = (n0max - controlGammaValueMax1* (deltaTau13-deltaTau11) - controlGammaValueMax2* (deltaTau13-deltaTau12)) / flowRestriction + deltaTau13;
        }

        if (flowRestriction / 3.0 > controlGammaValueMax1) {
            tauLoad = (n0max - controlGammaValueMax1* (deltaTau13-deltaTau11) - controlGammaValueMax2* (deltaTau13-deltaTau12))
                / (controlGammaValueMax1 + controlGammaValueMax2 + controlGammaValueMax3) + deltaTau13;
        }


    }

    public void saveTableDataTest(int variant) {
        int n = variant;
        init(n);
        List<String> headerNames = Arrays.asList( "   0.tau   ","   n0   ","   u1   ","   u2   ","   u3   "," u1alias"," u2alias"," u3alias");

        ProjectManager  projectManager = ProjectManager.getInstance();

        ControlGamma controlGamma1 = new ControlGamma(deltaTau,tauLoad,deltaTau11, controlGammaValueMax1,flowRestriction, null);
        ControlGamma controlGamma2 = new ControlGamma(deltaTau,tauLoad,deltaTau12, controlGammaValueMax2,flowRestriction, controlGamma1 );
        ControlGamma controlGamma3 = new ControlGamma(deltaTau,tauLoad,deltaTau13, controlGammaValueMax3,flowRestriction, controlGamma2 );

        try {
            controlGamma1.join();
            controlGamma2.join();
            controlGamma3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        List<List<String>> tableTest = new ArrayList<>();
        List<String> headear = new ArrayList<>();
        headerNames.forEach(value -> headear.add(StringUtil.getStringFormat(value, 10)));

        tableTest.add(headear);

        List<Double> n0s = calculateN0(controlGamma1.getControlGammas(), controlGamma2.getControlGammas(), controlGamma3.getControlGammas());


        for (int i=0; i<n0s.size(); i++) {

            List<String> row = new ArrayList<>();
            row.add(getValue(controlGamma1.getTaus().get(i), headear, 0));
            row.add(getValue(n0s.get(i), headear, 1));

            row.add(getValue(controlGamma1.getControlGammas().get(i), headear, 2));
            row.add(getValue(controlGamma2.getControlGammas().get(i), headear, 3));
            row.add(getValue(controlGamma3.getControlGammas().get(i), headear, 4));

            row.add(getValue(controlGamma1.getControlGammas().get(i) > 0 ? 1.0 : 0.0, headear, 5));
            row.add(getValue(controlGamma2.getControlGammas().get(i) > 0 ? 2.0 : 0.0, headear, 6));
            row.add(getValue(controlGamma3.getControlGammas().get(i) > 0 ? 3.0 : 0.0, headear, 7));

            tableTest.add(row);
        }


        projectManager.saveData(tableTest, "data" + n + ".csv", "article\\src\\main\\java\\a21_ED_Lviv\\result");

    }

    @Test
    public void saveTableDataAllVariantTest() {
        saveTableDataTest(1);
        saveTableDataTest(2);
        saveTableDataTest(3);
        saveTableDataTest(4);
    }

    private List<Double> calculateN0(List<Double> u1s, List<Double> u2s, List<Double> u3s) {
        List<Double> n0s = new ArrayList<>();
        double n0 = 0.0;
        int size = u1s.size();
        for (int i = 1; i < size; i++) {
            n0 += (usWithDelay(u1s, deltaTau11, i) + usWithDelay(u2s, deltaTau12, i) + usWithDelay(u3s, deltaTau13, i)) * deltaTau;
            n0s.add(n0);
        }
        return n0s;
    }

    private double usWithDelay(List<Double> us, double deltaTau1, int currentNumber) {
        int delayNumber = (int) (currentNumber - deltaTau1 / deltaTau);
        double u = 0  ;
        try{
            u = delayNumber < 0 ? 0.0 : us.get(delayNumber);
        }catch (Exception e) {
            System.out.println();
        }
        return u;
    }


    public String getValue(Double value, List<String> header, int columnNumber) {
        int additionSize = 2;
        return StringUtil.getDoubleFormatValue(value, header.get(columnNumber).length() - additionSize);
    }

}