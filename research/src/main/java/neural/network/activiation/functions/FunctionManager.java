package neural.network.activiation.functions;

public class FunctionManager {
    public static ActiviationFunction getFunction(String functionParameters) {
        String[] parameters = functionParameters.trim().split(";");
        String functionName = parameters[0];
        switch (functionName.toLowerCase()) {
            case "1":
                double a = Double.parseDouble(parameters[1]);
                double b = Double.parseDouble(parameters[2]);
                return new LogisticFunction(a, b);
            default:
                throw new ExceptionInInitializerError("1 - LogisticFunction");
        }
    }
}
