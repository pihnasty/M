package neural.network.activiation.functions;

public class FunctionManager {
    public static ActiviationFunction getFunction(String functionParameters) {
        String[] parameters = functionParameters.trim().split(";");
        String functionName = parameters[0];
        double a;
        double b;
        switch (functionName.toLowerCase()) {
            case "1":
                a = Double.parseDouble(parameters[1]);
                b = Double.parseDouble(parameters[2]);
                return new LogisticFunction(a, b);
            case "2":
                a = Double.parseDouble(parameters[1]);
                b = Double.parseDouble(parameters[2]);
                return new LinearFunction(a, b);
            default:
                throw new ExceptionInInitializerError("1 - LogisticFunction; 2 - LinearFunction  y=a+bx");
        }
    }
}
