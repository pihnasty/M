package neural.testold;


import java.util.*;
import java.util.stream.Collectors;

public class TransportSystemO {
    private List<Double> ksis;
    private List<SpeedO> speeds;
    private List<InputO> inputs;
    private List<PsiO> psis;

    double startIntegrationValue;
    double finishIntegrationValue;

    private List<SectionO> sections;

    double M;

    static public int VARIANT;

    public TransportSystemO(int variant) {
        VARIANT = variant;
        M = 8;
        initTransportSystem();
    }

    private void initTransportSystem() {
        ksis = Arrays.asList(1.0, 0.5, 0.7, 0.8
                , 1.5, 1.0, 1.5, 0.6);

        startIntegrationValue = 0.0;
        finishIntegrationValue = 1.0;
        inputs = new ArrayList<>();
        speeds = new ArrayList<>();
        psis = new ArrayList<>();

        sections = new ArrayList<>();

        for (double m = 1.0; m <= M; m++) {
            inputs.add(new InputO(m));
            speeds.add(new SpeedO(m));
            psis.add(new PsiO(m));
            int name = (int) (m - 1);
            sections.add(new SectionO(
                    name + 1.0
                    , ksis.get(name)
                    , startIntegrationValue
                    , finishIntegrationValue
                    , t -> inputs.get(name).getValue(t)
                    , t -> speeds.get(name).getValue(t)
                    , t -> psis.get(name).getValue(t)
            ));
        }

        getSectionByName(1.0).setParents(Arrays.asList(getSectionByName(3.0)));
        getSectionByName(2.0).setParents(Arrays.asList(getSectionByName(3.0)));

        getSectionByName(3.0).setParents(Arrays.asList(getSectionByName(6.0)));
        getSectionByName(4.0).setParents(Arrays.asList(getSectionByName(6.0)));
        getSectionByName(5.0).setParents(Arrays.asList(getSectionByName(6.0)));

        getSectionByName(6.0).setParents(Arrays.asList(getSectionByName(7.0), getSectionByName(8.0)));

        getSectionByName(6.0).setOutputDistribution(
                new HashMap<SectionO, Double>() {
                    {
                        put(getSectionByName(7.0), 2.0);
                        put(getSectionByName(8.0), 3.0);
                    }
                }
        );

        getSectionByName(3.0).setChildren(Arrays.asList(getSectionByName(1.0), getSectionByName(2.0)));
        getSectionByName(6.0).setChildren(Arrays.asList(getSectionByName(3.0), getSectionByName(4.0), getSectionByName(5.0)));
        getSectionByName(7.0).setChildren(Arrays.asList(getSectionByName(6.0)));
        getSectionByName(8.0).setChildren(Arrays.asList(getSectionByName(6.0)));

    }


    public void executeSection(SectionO section, double tau) {
        if (Objects.isNull(section)) {
            return;
        }
        List<SectionO> sections = section.getChildren();
        if (!Objects.isNull(sections)) {
            sections.forEach(child -> executeSection(child, tau));
        }
        section.execute(tau);
        // System.out.println(section.getName());
    }


    public SectionO getSectionByName(Double name) {
        List<SectionO> filteredSection = sections.stream().filter(section -> name.equals(section.getName())).collect(Collectors.toList());
        if (filteredSection.isEmpty()) {
            System.out.println("filteredSection isEmpty");
        }
        return filteredSection.get(0);
    }

    public List<SectionO> getRootSection() {
        return sections.stream().filter(section -> Objects.isNull(section.getParents())).collect(Collectors.toList());
    }

    public List<SectionO> getSections() {
        return sections;
    }

    public static void main(String[] args) {
        double tau = 0.0;
        TransportSystemO transportSystem = new TransportSystemO(1);

        transportSystem.getRootSection().stream().forEach(
                section -> transportSystem.executeSection(transportSystem.getSectionByName(section.getName()), tau)
        );
        System.out.println();

    }

}
