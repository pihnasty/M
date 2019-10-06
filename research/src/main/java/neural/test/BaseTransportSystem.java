package neural.test;


import neural.test.papameters.*;

import java.util.*;
import java.util.stream.Collectors;

public abstract class BaseTransportSystem {
    protected List<Double> ksis;
    protected List<Speed> speeds = new ArrayList<>();

    protected List<Input> inputs = new ArrayList<>();

    protected List<Psi> psis = new ArrayList<>();

    protected List<ControlSpeed> controlSpeeds = new ArrayList<>();
    protected List<Control_fromInputBunker> control_fromInputBunkers = new ArrayList<>();
    protected List<OutputPlan> outputPlans = new ArrayList<>();
    protected List<InputBunker> inputBunkers =  new ArrayList<>();



    protected double startIntegrationValue;
    protected double finishIntegrationValue;

    protected List<Section> sections = new ArrayList<>();

    protected double M;

    static public int VARIANT;
    static public double deltaTau;

    public BaseTransportSystem(int variant, double _deltaTau) {
        VARIANT = variant;
        deltaTau = _deltaTau;
        initTransportSystem ();
    }

    protected abstract void initTransportSystem ();

    public void  executeSection (Section section, double tau) {
        if(Objects.isNull(section)) {
            return;
        }
        List<Section> sections =section.getChildren();
        if(!Objects.isNull(sections)) {
            sections.forEach(child -> executeSection(child,tau));
        }
        section.execute(tau);
        // System.out.println(section.getName());
    }

    protected abstract void initSections();

    public Section getSectionByName(Double name) {
        List<Section> filteredSection = sections.stream().filter(section-> name.equals(section.getName())).collect(Collectors.toList());
        if ( filteredSection.isEmpty()) {
            System.out.println("filteredSection isEmpty");
        }
        return filteredSection.get(0);
    }

    public List<Section> getRootSection() {
        return sections.stream().filter(section -> Objects.isNull(section.getParents())).collect(Collectors.toList());
    }

    public List<Section> getSections() {
        return sections;
    }

    public List<Input> getInputs() {
        return inputs;
    }

    public List<Control_fromInputBunker> getControl_fromInputBunkers() {
        return control_fromInputBunkers;
    }

    public List<InputBunker> getInputBunkers() {
        return inputBunkers;
    }

    public List<ControlSpeed> getControlSpeeds() {
        return controlSpeeds;
    }

    public List<OutputPlan> getOutputPlans() {
        return outputPlans;
    }
}
