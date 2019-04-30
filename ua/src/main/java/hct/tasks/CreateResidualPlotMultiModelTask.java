package hct.tasks;

import designpatterns.MVC;
import experiment.Plan;
import factors.Factor;
import factors.FactorManager;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import linechart.DataP;
import linechart.LineChartController;
import linechart.LineChartModel;
import linechart.LineChartP;
import logging.LoggerP;
import main.ProjectManager;
import math.MathP;
import models.TwoParameterModel;
import settings.ProviderSettings;
import settings.Settings;
import string.StringUtil;

import java.awt.geom.Point2D;
import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class CreateResidualPlotMultiModelTask extends Task<Void> {

    private ProjectManager projectManager;
    boolean isSaveAsPdf;

    public CreateResidualPlotMultiModelTask(ProjectManager projectManager, boolean isSaveAsPdf) {
        this.projectManager=projectManager;
        this.isSaveAsPdf=isSaveAsPdf;
        LoggerP.write(Level.INFO, "CreateResidualPlotMultiModelTask started...");
    }

    @Override
    protected Void call() {
     Platform.runLater(() -> {
            projectManager.changeScrollPane(e->calculateChart(isSaveAsPdf));
        });
        return null;
    }

    private ScrollPane calculateChart(boolean iSsaveToPDF) {
        ScrollPane scrollPane = new ScrollPane();
        VBox vBox = new VBox();
        MathP.Counter titleCounter = MathP.getCounter(1,1);
        MathP.Counter counter = MathP.getCounter(1,1);
        Plan planPlanExperiment = projectManager.getPlanExperiment();
        FactorManager factorManager = projectManager.getProject().getFactorManager();

        if(Objects.isNull(factorManager)) {
            projectManager.createFactors();
            factorManager = projectManager.getProject().getFactorManager();
        }

        Map<String,Factor> factors = factorManager.getFactors();

        planPlanExperiment.getOutputFactors().forEach(
            outputFactorCategoryIdAndName -> {

                planPlanExperiment.getInputFactors().stream().filter(factor1-> !outputFactorCategoryIdAndName.equals(factor1)).forEach(
                    inputFactor1CategoryIdAndName-> {
                        List<List<String>> covarianceCoefficients = projectManager.getProject().getCovarianceCoefficients();
                        List<List<String>> characteristicsSeparatedRawDataTable = projectManager.getProject().getCharacteristicsSeparatedRawDataTable();

                        Factor outputFactor = factors.get(outputFactorCategoryIdAndName);
                        Factor inputFactor1 = factors.get(inputFactor1CategoryIdAndName);
                        Map<String, Double> valuesOutputFactor = outputFactor.getDoubleValues();
                        Map<String, Double> valuesInput1Factor = inputFactor1.getDoubleValues();


                        TwoParameterModel twoParameterModel = new TwoParameterModel(covarianceCoefficients,characteristicsSeparatedRawDataTable);

                        planPlanExperiment.getInputFactors().stream().filter(
                            factor2->!outputFactorCategoryIdAndName.equals(factor2)&&!inputFactor1CategoryIdAndName.equals(factor2)
                        ).forEach(
                            inputFactor2CategoryIdAndName -> {


                                DataP data = new DataP();
                                List<Point2D.Double> listExperiment = new ArrayList<>();

                                valuesOutputFactor.keySet().forEach(
                                    key -> {
                                        addExperimentPoint(listExperiment, valuesOutputFactor, valuesInput1Factor, key);
                                    }
                                );

                                data.addList(listExperiment);
                                data.setxMin(inputFactor1.getMinValue());
                                data.setxMax(inputFactor1.getMaxValue());
                                data.setyMin(outputFactor.getMinValue());
                                data.setyMax(outputFactor.getMaxValue());

                                data.setTitleX(inputFactor1CategoryIdAndName);
                                data.setTitleY(outputFactorCategoryIdAndName);
                                data.setLegend("experiment");
                                data.setTitleGraph(titleCounter.get().toString()+". Dependence of the factor " +outputFactorCategoryIdAndName
                                    +"\n on the factor "+inputFactor1CategoryIdAndName
                                    +"\n and on the factor "+inputFactor2CategoryIdAndName);

                                List<String> nameCoefficient = covarianceCoefficients.get(0).stream().map(name -> name.trim()).collect(Collectors.toList());

                                int outputFactorNumber = nameCoefficient.indexOf(outputFactorCategoryIdAndName);
                                int inputFactor_1_Number = nameCoefficient.indexOf(inputFactor1CategoryIdAndName);

                                double valueCoefficientA =
                                    Double.parseDouble(
                                        projectManager.getProject().getKoefficientA().get(outputFactorNumber).get(inputFactor_1_Number).replace(",",".")
                                    );

                                double valueCoefficientB =
                                    Double.parseDouble(
                                        projectManager.getProject().getKoefficientB().get(outputFactorNumber).get(inputFactor_1_Number).replace(",",".")
                                    );

                                data.setLegend("regression");
                                List<Point2D.Double> listRegressionOneModel = getListRegressionOneModel(inputFactor1, valueCoefficientA, valueCoefficientB);
                                data.addList(listRegressionOneModel);




//-------------------------------------------------------------------------------------------------------------------
                                if(!inputFactor2CategoryIdAndName.equalsIgnoreCase(inputFactor1CategoryIdAndName)) {

                                    Factor inputFactor2 = factors.get(inputFactor2CategoryIdAndName);
                                    Map<String, Double> valuesInput2Factor = inputFactor2.getDoubleValues();
                                    int inputFactor_2_Number = nameCoefficient.indexOf(inputFactor2CategoryIdAndName);

                                    twoParameterModel.calculateKoefficientsBeta(outputFactor, inputFactor1, inputFactor2);

                                    double twoModelValueCoefficientA = twoParameterModel.getKoefficientA();
                                    double coefficientB1 = twoParameterModel.getKoefficientB1();
                                    double coefficientB2 = twoParameterModel.getKoefficientB2();

                                    List <Double> nonRepeatingValueFactor2 =  new ArrayList<>(new HashSet<>(inputFactor2.getDoubleValues().values()));
                                    nonRepeatingValueFactor2.sort(Comparator.naturalOrder());


                                    int maxNumbernonRepeatingValueFactor2 = 5;

                                    if (nonRepeatingValueFactor2.size() > maxNumbernonRepeatingValueFactor2) {
                                        List<Double> newNonRepeatingValueFactor2 = new ArrayList<>();
                                        double minValueFactor2 = inputFactor2.getMinValue();
                                        double maxValueFactor2 = inputFactor2.getMaxValue();
                                        double delta = (maxValueFactor2 - minValueFactor2) / maxNumbernonRepeatingValueFactor2;

                                        for (int i = 0; i < maxNumbernonRepeatingValueFactor2; i++) {
                                            newNonRepeatingValueFactor2.add(minValueFactor2 + i * delta);
                                        }
                                        nonRepeatingValueFactor2 = newNonRepeatingValueFactor2;
                                    }

                                    nonRepeatingValueFactor2.forEach(value -> {
                                            List<Point2D.Double> listRegression = getListRegressionOneModel(inputFactor1, twoModelValueCoefficientA + coefficientB2 * value, coefficientB1);
                                            data.addList(listRegression);
                                            data.setLegend("regression :"+inputFactor2CategoryIdAndName+" = "+ value);
                                        }
                                    );



                                    MVC LineChart1MVC  = new MVC (LineChartModel.class, LineChartController.class, LineChartP.class, data, null );
                                    vBox.getChildren().add((LineChartP)LineChart1MVC.getView());

                                    String covarianceCoefficient = projectManager.getProject()
                                        .getCovarianceCoefficients().get(outputFactorNumber).get(inputFactor_1_Number).replace(",",".");
                                    Label labelForOneModel = getLabelToOneModel(outputFactorCategoryIdAndName, inputFactor1CategoryIdAndName, valueCoefficientA, valueCoefficientB, covarianceCoefficient);
                                    Label labelForTwoModel = getLabelToTwoModel(
                                        outputFactorCategoryIdAndName
                                        , inputFactor1CategoryIdAndName
                                        , inputFactor2CategoryIdAndName
                                        , twoModelValueCoefficientA
                                        , coefficientB1
                                        , coefficientB2
                                        , twoParameterModel.getKoefficientBeta1()
                                        , twoParameterModel.getKoefficientBeta2()
                                        );
                                    vBox.getChildren().add(labelForOneModel);
                                    vBox.getChildren().add(labelForTwoModel);
                                    if (iSsaveToPDF) {
                                        saveToPdf(counter, LineChart1MVC, labelForOneModel,labelForTwoModel);
                                    }

                                }
//-------------------------------------------------------------------------------------------------------------------
                            }
                        );


                    }
                );
            }
        );
        scrollPane.setContent(vBox);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        return scrollPane;
    }

    private void saveToPdf(MathP.Counter counter, MVC lineChart1MVC, Label labelForOneModel, Label  labelForTwoModel) {
        VBox tempVbox = new VBox();
        tempVbox.getChildren().add((LineChartP) lineChart1MVC.getView());
        tempVbox.getChildren().add(labelForOneModel);
        tempVbox.getChildren().add(labelForTwoModel);
        projectManager.saveNodeToPdf(tempVbox
            , ProviderSettings.getProjectSettingsMapValue(
                Settings.Keys.PROJECT_PATH) + "//"
                + Settings.Values.TWO_PARAMETER_MODEL_PDF_OPM_PAGE
                + String.format("%03d", counter.get())+ ".pdf");
    }

    private Label getLabelToOneModel(String outputFactorCategoryIdAndName, String inputFactor1CategoryIdAndName, double valueCoefficientA, double valueCoefficientB, String covarianceCoefficient) {
        return new Label(
                                        "Covariance coefficient                  :   " + covarianceCoefficient.trim() + "\n"
                                            + "Regression model                        :   " + StringUtil.addBrackets(outputFactorCategoryIdAndName) + "=" + valueCoefficientA + (valueCoefficientB > 0 ? " + " : "")
                                            +valueCoefficientB + " * " + StringUtil.addBrackets(inputFactor1CategoryIdAndName) + "\n"
                                            + "Regression model (short)             :   " + "Y = " + valueCoefficientA + (valueCoefficientB > 0 ? " + " : "") +
                                            +valueCoefficientB + " * X"+"\n"
                                            + "Dimensionless regression model :   " + "G = "+  covarianceCoefficient.trim() + " * Z"+ "\n"
                                    );
    }

    private Label getLabelToTwoModel(
        String outputFactorCategoryIdAndName
        , String inputFactor1CategoryIdAndName
        , String inputFactor2CategoryIdAndName
        , double twoModelValueCoefficientA
        , double coefficientB1
        , double coefficientB2
        , double koefficientBeta1
        , double koefficientBeta2) {
        return new Label(
             "Regression model                        :   " + StringUtil.addBrackets(outputFactorCategoryIdAndName)
                 + "=" + String.format("%.2f",twoModelValueCoefficientA) + (coefficientB1 > 0 ? " + " : "") +  String.format("%.2f",coefficientB1) + " * " + StringUtil.addBrackets(inputFactor1CategoryIdAndName)
                 + (coefficientB2 > 0 ? " + " : "") +  String.format("%.2f",coefficientB2) + " * " + StringUtil.addBrackets(inputFactor2CategoryIdAndName) + "\n"
                 + "Regression model (short)             :   " + "Y = "
                 +  String.format("%.2f",twoModelValueCoefficientA) + (coefficientB1 > 0 ? " + " : "") +  String.format("%.2f",coefficientB1) + " * X1" + (coefficientB2 > 0 ? " + " : "") +  String.format("%.2f",coefficientB2) + " * X2"+ "\n"
                 + "Dimensionless regression model :   " + "G = "+  String.format("%.2f",koefficientBeta1) + " * Z1"  +  (koefficientBeta2 > 0 ? " + " : "") +  String.format("%.2f",koefficientBeta2) + " * Z2"+ "\n"
                 + "\n\n\n\n\n\n"
        );
    }

    private List<Point2D.Double> getListRegressionOneModel(Factor inputFactor, double valueCoefficientA, double valueCoefficientB) {
        List<Point2D.Double> listRegression = new ArrayList<>();

        int numberOfX = 100;
        double dx = (inputFactor.getMaxValue() - inputFactor.getMinValue())/numberOfX;
        for (int iInputFactorNumber=0; iInputFactorNumber<numberOfX; iInputFactorNumber++) {
            double x = inputFactor.getMinValue()+dx*iInputFactorNumber;
            double y = valueCoefficientA+valueCoefficientB*x;
            listRegression.add(new Point2D.Double(x,y));
        }
        return listRegression;
    }

    private void addExperimentPoint(List<Point2D.Double> listExperiment, Map<String, Double> valuesOutputFactor, Map<String, Double> valuesInput1Factor, String key) {
        Double outputValue = valuesOutputFactor.get(key);
        Double inputValue = valuesInput1Factor.get(key);
        if (Objects.nonNull(outputValue) && Objects.nonNull(inputValue)) {
            listExperiment.add(new Point2D.Double(inputValue, outputValue));
        }
    }


}
