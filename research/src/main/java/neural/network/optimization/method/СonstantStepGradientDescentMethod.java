package neural.network.optimization.method;

public class СonstantStepGradientDescentMethod implements OptimizationMethod {

    private double limitGradientToValueMax;
    private double limitGradientToValueMin;

    public СonstantStepGradientDescentMethod(double limitGradientToValueMax, double limitGradientToValueMin) {
        this.limitGradientToValueMax = limitGradientToValueMax;
        this.limitGradientToValueMin = limitGradientToValueMin;

    }

    @Override
    public double getOptimalSpeed(double alpha, double wlistValue, double gradientWlistValue) {
        return alpha;
    }
}
