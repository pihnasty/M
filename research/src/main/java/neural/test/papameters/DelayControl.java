package neural.test.papameters;

import neural.test.BaseTransportSystem;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;

public class DelayControl {
    private Function<Double, Double> function;
    private Map<Double, Double> cash_G_FunctionValue;
    private Map<Double, Double> cashFunctionValue;
    private Map<Double, Double> cashValue;
    double deltaTau;

    private double controlSpeedValue;


    public DelayControl(double startIntegrationValue, Function<Double, Double> function) {
        this.function = function;
        cash_G_FunctionValue = new TreeMap<>();
        cashValue = new TreeMap<>();
        cashFunctionValue = new TreeMap<>();
        deltaTau= BaseTransportSystem.deltaTau;
        cashValue.put(startIntegrationValue, 0.0);
        cash_G_FunctionValue.put(0.0, startIntegrationValue);
        Double integralValue = startIntegrationValue;
        for (double tau=0; tau<210.0; tau+=deltaTau/2) {
            Double functionValue = function.apply(tau);
            cashValue.put(integralValue, tau);
            cash_G_FunctionValue.put(tau, integralValue);
            integralValue+=functionValue*deltaTau/2;
        }
        System.out.println("");
    }

    public double getValue(double functionValue) {
        double fistFunctionValue = ((TreeMap<Double, Double>) cash_G_FunctionValue).firstKey();

        if (fistFunctionValue > functionValue) {
            return -1.0;
        }

        return cash_G_FunctionValue.get(((TreeMap<Double, Double>) cash_G_FunctionValue).tailMap(functionValue).firstKey());
    }

    public double getFunctionValue(double value) {
        double fistValue = ((TreeMap<Double, Double>) cashValue).firstKey();

        if (fistValue > value) {
            return -1.0;
        }
        return cashValue.get(((TreeMap<Double, Double>) cashValue).tailMap(value).firstKey());
    }


    public double getDelayValue(double tau, double ksi) {
        //double tauMap = ((TreeMap<Double, Double>) cashValue).tailMap(tau).firstKey();
        if(((TreeMap<Double, Double>) cashValue).tailMap(tau).size()==0) {
            System.out.println(tau);
            double tauMapKey = ((TreeMap<Double, Double>) cashValue).tailMap(tau-1.1*deltaTau).firstKey();
            double integration = cashValue.get(tauMapKey) + controlSpeedValue*deltaTau;
            cashValue.put(tau, integration);
            cash_G_FunctionValue.put(integration, tau);
        }
//        if (cashValue.keySet().) {
//            double currentFunctionValue = cash_G_FunctionValue.get(tau)+ cashFunctionValue.get(tau)*deltaTau;
//            cashValue.put(tau,currentFunctionValue );
//            cash_G_FunctionValue.put(currentFunctionValue,tau);
//        }
        double functionValue = getFunctionValue(tau);
        double tau_ksi = getValue(functionValue - ksi);
        return tau_ksi > 0 ? tau - tau_ksi : -1.0;
    }


    public static void main(String[] args) {

        DelayControl delay = new DelayControl(0,  t -> 2.0 * t);


        //  delay.getValue( 6.0);


        System.out.println("delay.getValue(  ) = " + delay.getFunctionValue(10.0));

        System.out.println("delay.getValue(  ) = " + delay.getValue( 168.0));

    //    System.out.println("        delay.getDelay(1.0, 1.0) =" + delay.getDelay(0.9999999, 1.0));

    }

    public void setControlSpeedValue(double controlSpeedValue) {
        this.controlSpeedValue = controlSpeedValue;
    }
}
