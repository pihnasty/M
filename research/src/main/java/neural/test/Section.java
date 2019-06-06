package neural.test;

import java.util.*;
import java.util.function.Function;

public class Section {

    private List<Section> parents;

    private Map<Section,Double>  outputDistribution;
    private Double sumDisrtibution;
    private List<Section> children;
    private boolean isLeaf =true;

    private double ksi;
    private double startIntegrationValue;
    private double finishIntegrationValue;

    private Function<Double, Double > speed;
    private Function<Double, Double > input;
    private Function<Double, Double > psi;

    private double speedValue;
    private double inputValue;
    private double densityValue;
    private double psiValue;
    private double outputValue;
    double delayValue;

    private Delay delay;
    private TreeMap<Double, Double> densities;
    private double name;


    public Section(
        double name,
        double ksi
        , double startIntegrationValue
        , double finishIntegrationValue
        , Function<Double, Double> input
        , Function<Double, Double> speed
        , Function<Double, Double> psi) {

        this.name=name;
        this.startIntegrationValue=startIntegrationValue;
        this.finishIntegrationValue=finishIntegrationValue;
        this.ksi = ksi;

        this.speed = speed;
        this.input = input;
        this.psi = psi;
        this.delay = new Delay(startIntegrationValue, finishIntegrationValue, speed, 0.001);
        this.densities = new TreeMap<>();

    }

    public void execute(double tau) {
        if(isLeaf) {
            inputValue =input.apply(tau);
        } else {
            inputValue=children.stream().map( child ->child.getOutputToParent(this)).reduce( Double::sum).get();
        }

        speedValue =speed.apply(tau);
        psiValue =psi.apply(tau);
        double G_minus_ksi = delay.getFunctionValue(tau)-ksi;
        if (G_minus_ksi>0 ) {
            delayValue =  delay.getDelay(tau,ksi);
            double density_Tau_DelayValue = densities.get(( densities).tailMap(tau-delayValue).firstKey());
            outputValue=density_Tau_DelayValue*speedValue;
        } else {
            outputValue=psi.apply(-G_minus_ksi)*speedValue;
        }
        densityValue = inputValue/speedValue;
        densities.put(tau, densityValue);
    }

    public double getSpeedValue() {
        return speedValue;
    }

    public double getInputValue() {
        return inputValue;
    }

    public double getPsiValue() {
        return psiValue;
    }

    public double getOutputValue() {
        return outputValue;
    }

    public List<Section> getParents() {
        return parents;
    }

    public List<Section> getChildren() {
        return children;
    }

    public void setParents(List<Section> parents) {
        this.parents = parents;
    }

    public void setChildren(List<Section> children) {
        this.children = children;
        isLeaf = Objects.isNull(children);
    }

    public Map<Section, Double> getOutputDistribution() {
        return outputDistribution;
    }

    public void setOutputDistribution(Map<Section, Double> outputDistribution) {
        sumDisrtibution= outputDistribution.values().stream().reduce( Double::sum).get();
        this.outputDistribution = outputDistribution;
    }

    public double getOutputToParent (Section section) {
        return Objects.isNull(outputDistribution) ? outputValue : outputDistribution.get(section)*outputValue/sumDisrtibution;
    }

    public double getName() {
        return name;
    }

    public double getKsi() {
        return ksi;
    }

    public double getDelayValue() {
        return delayValue;
    }

    public double getDensityValue() {
        return densityValue;
    }
}
