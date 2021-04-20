package neural.network.activiation.functions;

import java.util.function.Function;

public class LogisticFunction implements ActiviationFunction {
    private Function<Double, Double> function;
    private double a;
    private double b;

    public LogisticFunction(double a, double b) {
        this.a =a;
        this.b =b;
        function = x -> a / (1.0 + Math.exp(-b * x));
    }

    @Override
    public Function<Double, Double> getFunction() {
        return function;
    }

    @Override
    public Function<Double, Double> getDerivativeFunction(String mode) {
        Function<Double, Double> derivativeFunction;
        switch (mode.toUpperCase()) {
            case "S":
                derivativeFunction = x -> b * function.apply(x) * (1.0 - function.apply(x) / a);
                break;
            case "F(S)":
                derivativeFunction = y -> b * y * (1.0 - y / a);
                break;
            default:
                derivativeFunction = errorFunction;
                break;
        }
        return derivativeFunction;
    }

}
