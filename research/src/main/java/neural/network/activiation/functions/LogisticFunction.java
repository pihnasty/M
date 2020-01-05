package neural.network.activiation.functions;

import java.util.function.Function;

public class LogisticFunction implements ActiviationFunction {
    private Function<Double, Double> function = x -> 1.0 / (1.0 + Math.exp(-x));

    @Override
    public Function<Double, Double> getFunction() {
        return function;
    }

    @Override
    public Function<Double, Double> getDerivativeFunction(String mode) {
        Function<Double, Double> derivativeFunction;
        switch (mode.toUpperCase()) {
            case "S" :  derivativeFunction = x -> function.apply(x) * (1.0 - function.apply(x));
                break;
            case "F(S)" :  derivativeFunction = x -> x * (1.0 - x);
                break;
                default: derivativeFunction = errorFunction;
                break;
        }
        return derivativeFunction;
    }

}
