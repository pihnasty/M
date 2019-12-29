package neural.network.activiation.functions;

import java.util.function.Function;

public interface ActiviationFunction {

    Function<Double, Double> getFunction();
    Function<Double, Double> getDerivativeFunction();

}
