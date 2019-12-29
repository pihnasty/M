package neural.network.activiation.functions;

public class FunctionManager {
    public static ActiviationFunction getFunction(String functionName) {

        switch (functionName.toLowerCase()) {
            case "1" : return new LogisticFunction();
            default: throw new ExceptionInInitializerError("1 - LogisticFunction");
        }
    }
}
