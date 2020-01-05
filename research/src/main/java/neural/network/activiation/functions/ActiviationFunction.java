package neural.network.activiation.functions;

import java.util.function.Function;

public interface ActiviationFunction {

    Function<Double, Double> errorFunction = x -> { throw new ExceptionInInitializerError("The method of calculating the derivative of the activation function is not defined.");};

    Function<Double, Double> getFunction();
    Function<Double, Double> getDerivativeFunction(String mode);

}
