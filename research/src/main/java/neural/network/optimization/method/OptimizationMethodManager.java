package neural.network.optimization.method;

public class OptimizationMethodManager {
    public static OptimizationMethod getOptimizationMethod(String optimizationMethodParameters) {
        String[] parameters = optimizationMethodParameters.trim().split(";");
        String name = parameters[0];
        double limitGradientToValueMax;
        double limitGradientToValueMin;
        switch (name.toLowerCase()) {
            case "1":
                limitGradientToValueMax = Double.parseDouble(parameters[1]);
                limitGradientToValueMin = Double.parseDouble(parameters[2]);
                return new СonstantStepGradientDescentMethod(limitGradientToValueMax, limitGradientToValueMin);
            case "2":
                limitGradientToValueMax = Double.parseDouble(parameters[1]);
                limitGradientToValueMin = Double.parseDouble(parameters[2]);
                return new VariableStepGradientDescentMethod(limitGradientToValueMax, limitGradientToValueMin);
            default:
                throw new ExceptionInInitializerError(
                    "1 - Сonstant step gradient descent method; "
                        + "2 - Variable step gradient descent method"
                );
        }
    }
}
