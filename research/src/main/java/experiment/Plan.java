package experiment;

import java.util.*;

import static common.ProjectConstant.ModelNanes.ONE_PARAMETER_LINEAR_DEPENDENCE;

public class Plan {
    private List<String> outputFactors = new ArrayList<>();
    private List<String> inputFactors = new ArrayList<>();
    private List<String> typesOfModels = new ArrayList<>();
    private Map<String,String> boundaryConditions = new HashMap<>();

    private Map<String,Map<String,String>> hiddenLayers = new HashMap<>();

    private Map<String,String> parametersOfModel = new HashMap<>();

    private static Plan ourInstance = new Plan();

    public static Plan getInstance() {
        return ourInstance;
    }

    private Plan() {

    }

    public static Plan getDefaultPlan() {
        Plan defaultPlan = new Plan();

        defaultPlan.inputFactors.addAll(Arrays.asList("1.SEVERE PERSISTENT", "9.Lemon", "8.Sheep wool"    //  >0.21
            , "8.Rabbit hair", "1.group", "2.Atopic dermatitis", "8.Pillow feather", "2.Allergic rhinitis"
            , "8.Domestic dust", "8.Dog hair", "4.Bronchial asthma in father"
            , "4.Bronchial asthma in relatives of second generation", "14.age", "3.Number of years from the first symptoms"));
        defaultPlan.inputFactors.addAll(Arrays.asList("1.MILD PERSISTENT", "6.Color", "11.FEV1"    //  <-0.21
            , "7.Timothy", "5.ESR", "12.CD8 10*3 cells", "12.CD4 10*3 cells"));

        defaultPlan.outputFactors.addAll(Arrays.asList("13.TLSP", "1.SEVERE PERSISTENT"));
        defaultPlan.typesOfModels.addAll(Arrays.asList(ONE_PARAMETER_LINEAR_DEPENDENCE));

        return defaultPlan;
    }


    public List<String> getOutputFactors() {
        return outputFactors;
    }

    public void setOutputFactors(List<String> outputFactors) {
        this.outputFactors = outputFactors;
    }

    public List<String> getInputFactors() {
        return inputFactors;
    }

    public void setInputFactors(List<String> inputFactors) {
        this.inputFactors = inputFactors;
    }

    public List<String> getTypesOfModels() {
        return typesOfModels;
    }

    public void setTypesOfModels(List<String> typesOfModels) {
        this.typesOfModels = typesOfModels;
    }

    public Map<String, String> getBoundaryConditions() {
        return boundaryConditions;
    }

    public void setBoundaryConditions(Map<String, String> boundaryConditions) {
        this.boundaryConditions = boundaryConditions;
    }

    public Map<String, String> getParametersOfModel() {
        return parametersOfModel;
    }

    public void setParametersOfModel(Map<String, String> parametersOfModel) {
        this.parametersOfModel = parametersOfModel;
    }

    public Map<String, Map<String, String>> getHiddenLayers() {
        return hiddenLayers;
    }

    public void setHiddenLayers(Map<String, Map<String, String>> hiddenLayers) {
        this.hiddenLayers = hiddenLayers;
    }
}
