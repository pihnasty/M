package neural.network;

public class Node {
    private int id;
    private double value;
    private double error;

    public Node(int id) {
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
