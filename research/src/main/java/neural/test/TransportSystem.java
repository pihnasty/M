package neural.test;


import java.util.*;
import java.util.stream.Collectors;

public class TransportSystem {
    private List<Double> ksis;
    private List<Speed> speeds;
    private List<Input> inputs;
    private List<Psi> psis;

    double startIntegrationValue;
    double finishIntegrationValue;

    private List<Section> sections;

    double M;

    public TransportSystem() {
        M=8;
        initTransportSystem ();
    }

    private void initTransportSystem () {
        ksis = Arrays.asList(1.0,0.5,0.7,0.8
                            ,1.5,1.0,1.5,0.6);

        startIntegrationValue=0.0;
        finishIntegrationValue=1.0;
        inputs = new ArrayList<>();
        speeds = new ArrayList<>();
        psis = new ArrayList<>();

        sections = new ArrayList<>();

        for (double m=1.0; m<=M; m++) {
             inputs.add(new Input(m));
             speeds.add(new Speed(m));
             psis.add(new Psi(m));
             int name = (int)(m-1);
             sections.add(new Section(
                 name+1.0
                 ,ksis.get(name)
                 ,startIntegrationValue
                 ,finishIntegrationValue
                 ,t->inputs.get(name).getValue(t)
                 ,t->speeds.get(name).getValue(t)
                 ,t->psis.get(name).getValue(t)
             ));
        }

        getSectionByName(1.0).setParents(Arrays.asList(getSectionByName(3.0)));
        getSectionByName(2.0).setParents(Arrays.asList(getSectionByName(3.0)));

        getSectionByName(3.0).setParents(Arrays.asList(getSectionByName(6.0)));
        getSectionByName(4.0).setParents(Arrays.asList(getSectionByName(6.0)));
        getSectionByName(5.0).setParents(Arrays.asList(getSectionByName(6.0)));

        getSectionByName(6.0).setParents(Arrays.asList(getSectionByName(7.0),getSectionByName(8.0)));


        getSectionByName(6.0).setOutputDistribution(
            new HashMap<Section,Double>() {
                {
                    put(getSectionByName(7.0),2.0);
                    put(getSectionByName(8.0),3.0);
                }
            }
        );

        getSectionByName(3.0).setChildren(Arrays.asList(getSectionByName(1.0),getSectionByName(2.0)));
        getSectionByName(6.0).setChildren(Arrays.asList(getSectionByName(3.0),getSectionByName(4.0),getSectionByName(5.0)));
        getSectionByName(7.0).setChildren(Arrays.asList(getSectionByName(6.0)));
        getSectionByName(8.0).setChildren(Arrays.asList(getSectionByName(6.0)));

    }


    public void  executeSection (Section section, double tau) {
        if(Objects.isNull(section)) {
            return;
        }
        List<Section> sections =section.getChildren();
        if(!Objects.isNull(sections)) {
            sections.forEach(child -> executeSection(child,tau));
        }
        section.execute(tau);
        System.out.println(section.getName());
    }


    private Section getSectionByName(Double name) {
        List<Section> filteredSection = sections.stream().filter(section-> name.equals(section.getName())).collect(Collectors.toList());
        if ( filteredSection.isEmpty()) {
            System.out.println("filteredSection isEmpty");
        }
        return filteredSection.get(0);
    }

    public List<Section> getRootSection() {
        return sections.stream().filter(section -> Objects.isNull(section.getParents())).collect(Collectors.toList());
    }



    public static void main(String[] args) {
        double tau = 0.0;
        TransportSystem transportSystem = new TransportSystem();

        transportSystem.getRootSection().stream().forEach(
            section -> transportSystem.executeSection(transportSystem.getSectionByName(section.getName()),tau)
        );



        System.out.println();

    }

}
