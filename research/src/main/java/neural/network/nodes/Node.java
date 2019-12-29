package neural.network.nodes;

public class Node {
    private final String factorName;
    private int id;
    private double value;
    private double error;

    public Node(int id, String factorName) {
        this.factorName = factorName;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getError() {
        return error;
    }

    public void setError(double error) {
        this.error = error;
    }
}