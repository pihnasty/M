package neural.network.optimization.method;

public class VariableStepGradientDescentMethod implements OptimizationMethod {

    private double limitGradientToValueMax;
    private double limitGradientToValueMin;

    public VariableStepGradientDescentMethod(double limitGradientToValueMax, double limitGradientToValueMin) {
        this.limitGradientToValueMax =limitGradientToValueMax;
        this.limitGradientToValueMin =limitGradientToValueMin;

    }

    @Override
    public double getOptimalSpeed(double alpha, double wlistValue, double gradientWlistValue) {
        double newAlphaValue = alpha;
        double temp = Math.abs(gradientWlistValue/wlistValue);
        if (temp < limitGradientToValueMax && temp > limitGradientToValueMin) {
            newAlphaValue = alpha / temp;
        }
        if (temp > 2.0 && temp < 1.0 / limitGradientToValueMin) {   // (temp > 1.0 / deltaAlpha && temp < limitMax)
            newAlphaValue = alpha * temp;
        }
         return newAlphaValue;
    }
}
