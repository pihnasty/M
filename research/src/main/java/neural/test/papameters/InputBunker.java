package neural.test.papameters;

import java.util.Objects;
import java.util.function.Function;

import static neural.test.BaseTransportSystem.VARIANT;

public class InputBunker {
    private double name;
    private Function<Double, Double > input;
    private Function<Double, Double > control_fromInputBunker;
    private Double capacity;
    private Double tau;
    private Double capacityMax;
    private Double capacityMin;
    private boolean isCapacityMax;
    private boolean isCapacityMin;

    public InputBunker (
        double name
        , Function<Double, Double> input
        , Function<Double, Double> control_fromInputBunker) {

        this.name=name;
        this.input = input;
        this.control_fromInputBunker = control_fromInputBunker;


        switch (VARIANT) {
            case 11:
                capacity = 0.4;
                capacityMin = 0.0;
                capacityMax = 2.0/Math.PI - 0.23;
                break;
            case 12:
                capacity = 2.0/Math.PI+0.2;
                capacityMin = 0.0;
                capacityMax = 2.0/Math.PI+0.2;
                break;
            case 13:
                capacity = 0.2;
                capacityMin = 0.0;
                capacityMax = 2.0/Math.PI+0.2;
                break;
            case 14:
                capacity = 2.0/Math.PI+0.2;
                capacityMin = 0.0;
                capacityMax = 2.0/Math.PI+0.2;
                break;
            case 15:
            case 16:    // This condition for the article in Kiev
                capacity = 0.73;  //  2.0/Math.PI+0.2;
                capacityMin = 0.0;
                capacityMax = 2.0/Math.PI+0.2;
                break;
            default:
                break;
        }
    }

    public void execute(double tau) {
        if (Objects.nonNull(this.tau)) {
            capacity = capacity + (input.apply(tau) - control_fromInputBunker.apply(tau))*(tau-this.tau);
            checkConstraintCapasity();
        }
        this.tau = tau;
    }

    public Double getCapacity() {
        return capacity;
    }

    private void checkConstraintCapasity() {
        if(capacity>=capacityMax) {
            isCapacityMax = true;
            isCapacityMin =false;
        } else {
            isCapacityMax = false;
        }
        if(capacity<=capacityMin) {
            isCapacityMin = true;
            isCapacityMax = false;
        } else {
            isCapacityMin =false;
        }
    }

    public boolean isCapacityMax () {
        return isCapacityMax;
    }

    public boolean isCapacityMin () {
        return isCapacityMin;
    }

    public double getInputValue(double tau) {
        return input.apply(tau);
    }

    public double getName() {
        return name;
    }

    public Double getCapacityMax() {
        return capacityMax;
    }

    public Double getCapacityMin() {
        return capacityMin;
    }
}
