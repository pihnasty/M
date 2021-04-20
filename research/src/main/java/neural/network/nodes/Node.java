package neural.network.nodes;

public class Node {
    private final String factorName;
    private int id;
    private double value;
    private double error;

    public Node(int id, String factorName) {
        this.factorName = factorName.trim();
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
//        if(Double.toString(value).equals("NaN")) {
//            System.out.println();
//        }
        this.value = value;
    }

    public double getError() {
        return error;
    }

    public void setError(double error) {
        this.error = error;
    }

    public String getFactorName() {
        return factorName;
    }
}
