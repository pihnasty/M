package neural.test.papameters;

import java.util.Objects;
import java.util.function.Function;

import static neural.test.TransportSystem.VARIANT;

public class ControlSpeed {

    private Function<Double, Double> outputPlan;
    private double m;
    private Double controlSpeedValue;
    private Double controlSpeedValueMax;
    private Double controlSpeedValueMin;
    private Section section;
    private InputBunker inputBunker;
    private Double controlSpeedConstraintDensityValue;

    public ControlSpeed(double m
        , Function<Double, Double> outputPlan
        , InputBunker inputBunker) {
        this.m=m;
        this.outputPlan = outputPlan;
        this.inputBunker = inputBunker;



        switch (VARIANT) {
            case 11:
                controlSpeedValueMin = 0.1;
                controlSpeedValueMax = 2.0;
                break;
            case 12:
            case 13:
                controlSpeedValueMin = 0.1;
                controlSpeedValueMax = 2.0;
                break;
                case 14:
                controlSpeedValueMin = 0.5;
                controlSpeedValueMax = 2.0;
                break;
            case 15:
            case 16:              // This condition for the article in Kiev
                controlSpeedValueMin = 0.0001;
                controlSpeedValueMax = 2.0;
                break;
            default:
                break;
        }
    }


    public Double getValue(Double tau) {
        switch (VARIANT) {
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:            case 16:
                controlSpeedValue = getControlSpeed_case11(tau);
                checkConstraintSpeed();
                checkCapacity_11 ();
                section.getDelay().setControlSpeedValue(controlSpeedValue);
                return controlSpeedValue;
            default:
                return null;
        }
    }

    private void checkConstraintSpeed() {
        if(controlSpeedValue>controlSpeedValueMax) {
            controlSpeedValue=controlSpeedValueMax;
        }
        if(controlSpeedValue<controlSpeedValueMin) {
            controlSpeedValue=controlSpeedValueMin;
        }
    }

    private void checkCapacity_11() {
        if(Objects.nonNull(controlSpeedConstraintDensityValue)
        && controlSpeedValue < controlSpeedConstraintDensityValue) {
            controlSpeedValue = controlSpeedConstraintDensityValue;
        }
    }

    private Double getControlSpeed_case11(Double tau) {
        Double controlSpeedValue;
        Double density_tau_delayValue = section.getDensity_tau_delayValue(tau);
        Double outputPlanValue = outputPlan.apply(tau);
        if (density_tau_delayValue == 0.0) {
            controlSpeedValue = (outputPlanValue == 0.0)
                ? controlSpeedValueMin : controlSpeedValueMax;
        } else {
            controlSpeedValue = outputPlanValue / density_tau_delayValue;
        }
        return controlSpeedValue;
    }

    public double getName() {
        return m;
    }

    public Double getControlSpeedValueMax() {
        return controlSpeedValueMax;
    }

    public Double getControlSpeedValueMin() {
        return controlSpeedValueMin;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public void setControlSpeedConstraintDensityValue(Double controlSpeedConstraintDensityValue) {
        this.controlSpeedConstraintDensityValue = controlSpeedConstraintDensityValue;
    }
}
