package neural.test.parameters;

import java.util.function.Function;

import static neural.test.TransportSystem.VARIANT;

public class Control_fromInputBunker {
    private Function<Double, Double> controlSpeedFunction;
    private double control_fromInputBunkerValue;
    private double control_fromInputBunkerValueMin;
    private double control_fromInputBunkerValueMax;
    private double name;
    private Double densityValueMax;
    private InputBunker inputBunker;
    private ControlSpeed controlSpeed;

    public Control_fromInputBunker(
        double name
        , Function<Double, Double> controlSpeedFunction
        , InputBunker inputBunker
    ) {
        this.name = name;
        this.controlSpeedFunction = controlSpeedFunction;
        this.inputBunker = inputBunker;
        switch (VARIANT) {
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:    // This condition for the article in Kiev
                control_fromInputBunkerValueMin = 0.0;
                control_fromInputBunkerValueMax = 2.0;
                densityValueMax = 1.0;
                break;
            default:
                try {
                    throw
                        new Exception("Требуется определить вариант инициализации в Control_fromInputBunker");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    public Double getValue(Double tau) {
        switch (VARIANT) {
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
                control_fromInputBunkerValue = controlSpeedFunction.apply(tau)*densityValueMax;
                checkConstraintControl_fromInputBunkerValue();
                checkConstraintCapasity(tau);
                return control_fromInputBunkerValue;
            case 16:     // This condition for the article in Kiev
                return 1.0;
            default:
                throw new NullPointerException("Требуется определить вариант инициализации в Control_fromInputBunker");
        }
    }

    public double getName() {
        return name;
    }

    private void checkConstraintControl_fromInputBunkerValue() {
        if(control_fromInputBunkerValue>control_fromInputBunkerValueMax) {
            control_fromInputBunkerValue=control_fromInputBunkerValueMax;
        }
        if(control_fromInputBunkerValue<control_fromInputBunkerValueMin) {
            control_fromInputBunkerValue=control_fromInputBunkerValueMin;
        }

    }

    private void checkConstraintCapasity(double tau) {
        controlSpeed.setControlSpeedConstraintDensityValue(null);
        if (inputBunker.isCapacityMin()) {
            if (control_fromInputBunkerValue > inputBunker.getInputValue(tau) ) {
                control_fromInputBunkerValue = inputBunker.getInputValue(tau);
           //     controlSpeed.setControlSpeedConstraintDensityValue(null);
            }
        }
        if (inputBunker.isCapacityMax()) {
            if (control_fromInputBunkerValue < inputBunker.getInputValue(tau)) {
                control_fromInputBunkerValue = inputBunker.getInputValue(tau);
            }
        }

        if (control_fromInputBunkerValue > controlSpeedFunction.apply(tau)*densityValueMax) {

            if (inputBunker.isCapacityMin()) {
                control_fromInputBunkerValue = controlSpeedFunction.apply(tau)*densityValueMax;
            }

            if (inputBunker.isCapacityMax()) {
                controlSpeed.setControlSpeedConstraintDensityValue(control_fromInputBunkerValue/densityValueMax);
            } else {
                controlSpeed.setControlSpeedConstraintDensityValue(null);
            }

        }
    }

    public double getControl_fromInputBunkerValueMin() {
        return control_fromInputBunkerValueMin;
    }

    public double getControl_fromInputBunkerValueMax() {
        return control_fromInputBunkerValueMax;
    }

    public Double getDensityValueMax() {
        return densityValueMax;
    }

    public void setControlSpeed(ControlSpeed controlSpeed) {
        this.controlSpeed = controlSpeed;
    }
}
