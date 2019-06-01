package neural.test;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;

public class Delay {
    private double startIntegrationValue;
    private double finishIntegrationValue;
    private Function<Double, Double> function;
    private double deltaValue;

    private Map<Double, Double> cashFunctionValue;
    private Map<Double, Double> cashValue;


    public Delay(double startIntegrationValue, double finishIntegrationValue, Function<Double, Double> function, double deltaValue) {
        this.startIntegrationValue = startIntegrationValue;
        this.finishIntegrationValue = finishIntegrationValue;
        this.function = function;
        this.deltaValue = deltaValue;
        cashFunctionValue = new TreeMap<>();
        cashValue = new TreeMap<>();
        initCashFunctionValue(0.0, startIntegrationValue, finishIntegrationValue, function, deltaValue);
        initCashValue(0.0, startIntegrationValue, finishIntegrationValue, function, deltaValue);
    }

    private void initCashFunctionValue(double integration, double startIntegrationValue, double finishIntegrationValue, Function<Double, Double> function, double deltaValue) {
        for (double value = startIntegrationValue; value < finishIntegrationValue; value += deltaValue) {
            cashFunctionValue.put(integration, value);
            integration += deltaValue * function.apply(value);
        }
    }

    private void initCashValue(double integration, double startIntegrationValue, double finishIntegrationValue, Function<Double, Double> function, double deltaValue) {
        for (double value = startIntegrationValue; value < finishIntegrationValue; value += deltaValue) {
            cashValue.put(value, integration);
            integration += deltaValue * function.apply(value);
        }
    }


    public double getValue(double functionValue) {
        double lastFunctionValue = ((TreeMap<Double, Double>) cashFunctionValue).lastKey();
        double fistFunctionValue = ((TreeMap<Double, Double>) cashFunctionValue).firstKey();

        if (fistFunctionValue > functionValue) {
            return -1.0;
        }

        while (lastFunctionValue < functionValue) {
            double lastValue = cashFunctionValue.get(lastFunctionValue);
            initCashFunctionValue(lastFunctionValue, lastValue, 2.0 * lastValue, function, deltaValue);
            lastFunctionValue = ((TreeMap<Double, Double>) cashFunctionValue).lastKey();
        }
        return cashFunctionValue.get(((TreeMap<Double, Double>) cashFunctionValue).tailMap(functionValue).firstKey());
    }

    public double getFunctionValue(double value) {
        double lastValue = ((TreeMap<Double, Double>) cashValue).lastKey();
        double fistValue = ((TreeMap<Double, Double>) cashValue).firstKey();

        if (fistValue > value) {
            return -1.0;
        }

        while (lastValue < value) {
            double lastFunctionValue = cashValue.get(lastValue);
            initCashValue(lastFunctionValue, lastValue, 2.0 * lastValue, function, deltaValue);
            lastValue = ((TreeMap<Double, Double>) cashValue).lastKey();
        }
        return cashValue.get(((TreeMap<Double, Double>) cashValue).tailMap(value).firstKey());
    }


    public double getDelay(double tau, double ksi) {
        double tau_ksi = getValue(getFunctionValue(tau) - ksi);
        return tau_ksi > 0 ? tau - tau_ksi : -1.0;
    }


    public static void main(String[] args) {

        Delay delay = new Delay(0, 0.01, t -> 2.0 * t, 0.001);


        //  delay.getValue( 6.0);


        System.out.println("delay.getValue(  ) = " + delay.getFunctionValue(10.0));

        //System.out.println("delay.getValue(  ) = " + delay.getValue( 144.0));

        System.out.println("        delay.getDelay(1.0, 1.0) =" + delay.getDelay(0.9999999, 1.0));

    }
}
