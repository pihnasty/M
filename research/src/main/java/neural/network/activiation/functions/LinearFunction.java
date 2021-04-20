package neural.network.activiation.functions;

import java.util.function.Function;

public class LinearFunction implements ActiviationFunction {
    private Function<Double, Double> function;
    private double a;
    private double b;

    public LinearFunction(double a, double b) {
        this.a = a;
        this.b = b;
        function = x -> {
            double y = b * x;
            if (y > a) {
                y = a;
            }
            if (y < -a) {
                y = -a;
            }
            return y;
        };
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
                derivativeFunction = x -> {
                    double y = b * x;
                    double derivative = b;

                    if (y > a) {
                        derivative = 0.0;
                    }
                    if (y < -a) {
                        derivative = 0.0;
                    }
                    return derivative;
                };
                break;
            case "F(S)":
                derivativeFunction = y -> {
                    double derivative = b;

                    if (y > a) {
                        derivative = 0.0;
                    }
                    if (y < -a) {
                        derivative = 0.0;
                    }
                    return derivative;
                };
                break;
            default:
                derivativeFunction = errorFunction;
                break;
        }
        return derivativeFunction;
    }

}
