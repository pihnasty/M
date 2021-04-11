package a21_CMIS_TOU;

import main.ProjectManager;
import string.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AppA21_CMIS_TOU {
    public static void main(String[] args) {

        double k = 6;   //  *5
        int countTau =24000;   //24000
        double psi_m_0 =  0.0  ;//        4.0     2.889;

        double tauMin = 0.0;
        double tauMax = 120.0 * k;  // 120      interval of analysis


        double initialMassValueMin = 0.4;
        double initialMassValueMax = 1.4;

        double initialXbValueMin = 0.0;
        double initialXbValueMax = 29.50*k;   // 29.50

        int countPsi_b_value = 500;
        List<Double> regularSpeeds = Arrays.asList(0.176,
            0.246, 0.316, 0.386, 0.456, 0.526, 0.596, 0.667, 0.736, 0.806,
            0.878);

        Tariff tariff = Tariff.SouthAfricaEscomHigtdemand;
        Input input = Input.SIN_0_5;
        InitialDensity initialDensity = InitialDensity.CONST_0_DENSITY;

        double initialMassValue = 0.4;  // начальная масса ленты с материалом initial mass of belt with material  see (12)
        double initialXbValue = 0.0;






        int n = (int) psi_m_0*1000;
//==================================================================

        double dt = (tauMax - tauMin) / countTau;
        double psi_b_valueMin =tariff.getMin(dt);
        double psi_b_valueMax =tariff.getMax(dt);
        double deltaPsi_b_value = (psi_b_valueMax - psi_b_valueMin) / countPsi_b_value;


        double psi_b_OptimalValue
            = getPsi_b_optimalValue(tauMin, tauMax, initialMassValueMin, initialMassValueMax, initialXbValueMin
            , initialXbValueMax, regularSpeeds, tariff, input, initialMassValue, initialXbValue, psi_m_0
            , initialDensity, dt, psi_b_valueMin, psi_b_valueMax, deltaPsi_b_value);



        Speed speed;
        Delay delay;
        Density density;
        Mass mass;
        Xb xb;
        Psi_B psi_b;
        Psi_M psi_m;
        ObjectiveFunction objectiveFunction;
        Hamiltonian hamiltonian;


        double tau = tauMin;
        speed = new Speed(dt, regularSpeeds);
        delay = new Delay(dt);
        density = new Density(initialDensity, delay, input, speed);
        mass = new Mass(dt, tau, initialMassValue, input, speed, delay, density);
        xb = new Xb(dt, tau, initialXbValueMin, speed, mass);

        psi_b = new Psi_B(psi_b_OptimalValue);
        psi_m = new Psi_M(dt, tau, psi_m_0, psi_b, tariff, speed);

        objectiveFunction = new ObjectiveFunction(dt, tau, 0.0, tariff, speed, mass
            , ObjectiveFunction.ObjectiveFunctionCase.ZUM);
        hamiltonian = new Hamiltonian(dt, psi_b, tariff, speed, mass, psi_m, input, delay, density, objectiveFunction);

        for (tau = tauMin + dt; tau <= tauMax; tau += dt) {


            double optimalSpeedControl = hamiltonian.getOptimalSpeedControl(tau);
            speed.add(tau, optimalSpeedControl);
            delay.add(tau, speed);
            mass.add(tau);
            psi_m.add(tau);
            xb.add(tau);
            objectiveFunction.addQualityIntegrals(tau);

        }


        saveResult(n, tauMin, tauMax, dt, speed, input, delay, density, mass, psi_b, psi_m, tariff, xb, hamiltonian, objectiveFunction);
        System.out.println(
            "xb.getByCurrentTau()= " +xb.getByCurrentTau()
            + "\npsi_m.getByCurrentTau()= " + psi_m.getByCurrentTau()
            + "\nobjectiveFunction.getIntegralByCurrentTau()= "+ objectiveFunction.getIntegralByCurrentTau()
                + "\nRC= "+ objectiveFunction.getIntegralByCurrentTau()/xb.getByCurrentTau()
        );
    }

    private static double getPsi_b_optimalValue(double tauMin, double tauMax, double initialMassValueMin, double initialMassValueMax,
                                                double initialXbValueMin, double initialXbValueMax, List<Double> regularSpeeds,
                                                Tariff tariff, Input input, double initialMassValue, double initialXbValue,
                                                double psi_m_0, InitialDensity initialDensity, double dt, double psi_b_valueMin,
                                                double psi_b_valueMax, double deltaPsi_b_value) {
        Speed speed;
        Delay delay;
        Density density;
        Mass mass ;
        Xb xb;
        Psi_B psi_b;
        Psi_M psi_m;
        ObjectiveFunction objectiveFunction;

        double psi_b_OptimalValue = psi_b_valueMin;
        double psi_mEndValue = Double.MAX_VALUE;

        for (double psi_b_value = psi_b_valueMin; psi_b_value < psi_b_valueMax; psi_b_value += deltaPsi_b_value) {

            checkInitialMassValue(initialMassValueMin, initialMassValueMax, initialMassValue);
            checkInitialXbValue(initialXbValueMin, initialXbValueMax, initialXbValue);
            double tau = tauMin;
            speed = new Speed(dt, regularSpeeds);
            delay = new Delay(dt);
            density = new Density(initialDensity, delay, input, speed);
            mass = new Mass(dt, tau, initialMassValue, input, speed, delay, density);
            xb = new Xb(dt, tau, initialXbValueMin, speed, mass);
            objectiveFunction = new ObjectiveFunction(dt, tau, 0.0, tariff, speed, mass
                , ObjectiveFunction.ObjectiveFunctionCase.ZUM);

            psi_b = new Psi_B(psi_b_value);
            psi_m = new Psi_M(dt, tau, psi_m_0, psi_b, tariff, speed);

            for (tau = tauMin + dt; tau <= tauMax; tau += dt) {

                Hamiltonian hamiltonian = new Hamiltonian(dt, psi_b, tariff, speed, mass, psi_m, input, delay, density,objectiveFunction);
                double optimalSpeedControl = hamiltonian.getOptimalSpeedControl(tau);
                speed.add(tau, optimalSpeedControl);
                delay.add(tau, speed);
                mass.add(tau);
                psi_m.add(tau);
                xb.add(tau);
                objectiveFunction.addQualityIntegrals(tau);
            }

            double psi_mByCurrentTau = Math.abs(psi_m.getByCurrentTau());
            if ( psi_mEndValue > psi_mByCurrentTau  ) {
                psi_b_OptimalValue = psi_b_value;
                psi_mEndValue = psi_mByCurrentTau;
            }

        }
        return psi_b_OptimalValue;
    }

    private static void saveResult(int n, double tauMin, double tauMax, double dt,
                                   Speed speed, Input input, Delay delay, Density density,
                                   Mass mass, Psi_B psi_b, Psi_M psi_m, Tariff tariff, Xb xb,
                                   Hamiltonian hamiltonian, ObjectiveFunction objectiveFunction) {
        double tau;
        List<List<String>> tableTest = new ArrayList<>();
        List<String> header = new ArrayList<>();
        header.add("   0.tau     ");
        header.add("   1.speed   ");
        header.add("   2.input   ");
        header.add("   3.delay   ");
        header.add("   4.density ");
        header.add("   5.mass    ");
        header.add("   6.psi_b   ");
        header.add("   7.psi_m   ");
        header.add("   8.tariff  ");
        header.add("   9.xb      ");
        header.add("   10.hamiltonianOptimal ");
        header.add("   11.speed1 ");
        header.add("   12.outputDensity ");
        header.add("   13.qualityIntegral ");

        tableTest.add(header);


        for (tau = tauMin; tau < tauMax-2*dt; tau+=dt) {
            List<String> row = new ArrayList<>();
            row.add(getValue(tau, header, 0));
            row.add(getValue(speed.getByTau(tau), header, 1));
            row.add(getValue(input.getByTau(tau), header, 2));
            row.add(getValue(delay.getDeltaTau1(tau), header, 3));
            row.add(getValue(density.getByTau(tau), header, 4));
            row.add(getValue(mass.getByTau(tau), header, 5));
            row.add(getValue(psi_b.getPsi_bByTau(), header, 6));
            row.add(getValue(psi_m.getByTau(tau), header, 7));
            row.add(getValue(tariff.getByTau(tau), header, 8));
            row.add(getValue(xb.getByTau(tau), header, 9));
            row.add(getValue(hamiltonian.getByTau(tau), header, 10));
            row.add(getValue(hamiltonian.getSpeed1ByTau(tau), header, 11));
            row.add(getValue(hamiltonian.getOutputDensityByTau(tau), header, 12));
            row.add(getValue(objectiveFunction.getQualityIntegralsByTau(tau), header, 13));
            tableTest.add(row);
        }

        ProjectManager projectManager = ProjectManager.getInstance();
        projectManager.saveData(tableTest, "data "+tariff.getName()+" "+n+".csv", "D:\\A\\M\\article\\src\\main\\java\\a21_CMIS_TOU\\result");
    }

    private static void checkInitialMassValue(double initialMassValueMin, double initialMassValueMax, double initialMassValue) {
        if (!(initialMassValueMin <= initialMassValue
            && initialMassValue <= initialMassValueMax)) {
            throw new IllegalArgumentException(
                " check initialMassValue: initialMassValueMin < initialMassValue, initialMassValue < initialMassValueMax"
            );
        }
    }

    private static void checkInitialXbValue(double initialXbValueMin, double initialXbValueMax, double initialXbValue) {
        if (!(initialXbValueMin <= initialXbValue
            && initialXbValue <= initialXbValueMax)) {
            throw new IllegalArgumentException(
                " check initialMassValue: initialMassValueMin < initialMassValue, initialMassValue < initialMassValueMax"
            );
        }
    }

    public static  String getValue (Double value, List<String> headear, int columnNumber) {
        int additionSize = 2;
        return StringUtil.getDoubleFormatValue( value, headear.get(columnNumber).length() - additionSize);
    }
}
