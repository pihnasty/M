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

public class CreateChartScrollPaneTwoModelProbitTask extends Task<Void> {

    private ProjectManager projectManager;
    boolean isSaveAsPdf;

    public CreateChartScrollPaneTwoModelProbitTask(ProjectManager projectManager, boolean isSaveAsPdf) {
        this.projectManager=projectManager;
        this.isSaveAsPdf=isSaveAsPdf;
        LoggerP.write(Level.INFO, "CreateChartScrollPaneTwoModelProbitTask started...");
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

        planPlanExperiment.getOutputFactors()
        //    .stream().filter(f->f.equalsIgnoreCase("1.SEVERE PERSISTENT"))
            .forEach(
            outputFactorCategoryIdAndName -> {

                planPlanExperiment.getInputFactors()
                 //   .stream().filter(f->f.equalsIgnoreCase("13.TLSP"))
                    .forEach(
                    inputFactor1CategoryIdAndName-> {
                        List<List<String>> covarianceCoefficients = projectManager.getProject().getCovarianceCoefficients();
                        List<List<String>> characteristicsSeparatedRawDataTable = projectManager.getProject().getCharacteristicsSeparatedRawDataTable();

                        Factor outputFactor = factors.get(outputFactorCategoryIdAndName);
                        Factor inputFactor1 = factors.get(inputFactor1CategoryIdAndName);
                        Map<String, Double> valuesOutputFactor = outputFactor.getDoubleValues();
                        Map<String, Double> valuesInput1Factor = inputFactor1.getDoubleValues();


                        TwoParameterModel twoParameterModel = new TwoParameterModel(covarianceCoefficients,characteristicsSeparatedRawDataTable);

                        planPlanExperiment.getInputFactors()
                        //    .stream().filter(f->f.equalsIgnoreCase("3.Number of years from the first symptoms"))
                            .forEach(
                            inputFactor2CategoryIdAndName -> {


                                DataP data = new DataP();
                                List<Point2D.Double> listExperiment = new ArrayList<>();

                                valuesOutputFactor.keySet().forEach(
                                    key -> {
                                        addExperimentPoint(listExperiment, valuesOutputFactor, valuesInput1Factor, key);
                                    }
                                );

                                data.setxMin(inputFactor1.getMinValue());
                                data.setxMax(inputFactor1.getMaxValue());
                                data.setyMin(0.05);
                                data.setyMax(0.95);

                                data.setTitleX(inputFactor1CategoryIdAndName);
                                data.setTitleY("Probability");
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

//-------------------------------------------------------------------------------------------------------------------
                                if(!inputFactor2CategoryIdAndName.equalsIgnoreCase(inputFactor1CategoryIdAndName)) {

                                    Factor inputFactor2 = factors.get(inputFactor2CategoryIdAndName);
                                    Map<String, Double> valuesInput2Factor = inputFactor2.getDoubleValues();
                                    int inputFactor_2_Number = nameCoefficient.indexOf(inputFactor2CategoryIdAndName);

                                    twoParameterModel.calculateKoefficientsBeta(outputFactor, inputFactor1, inputFactor2);

                                    double twoModelValueCoefficientA = twoParameterModel.getKoefficientA();
                                    double coefficientB1 = twoParameterModel.getKoefficientB1();
                                    double coefficientB2 = twoParameterModel.getKoefficientB2();

                                    double standartDeviationY = twoParameterModel.getStandartDeviationY();
                                    double twoModelValueCoefficientA_z = (
                                        Double.parseDouble(
                                            planPlanExperiment.getBoundaryConditions().get(outputFactorCategoryIdAndName).replace(",", ".")
                                        ) - twoModelValueCoefficientA)/standartDeviationY;
                                    double coefficientB1_z = -coefficientB1/standartDeviationY;
                                    double coefficientB2_z = -coefficientB2/standartDeviationY;


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
                                            List<Point2D.Double> listRegression = getListProbitOneModel(inputFactor1, twoModelValueCoefficientA_z + coefficientB2_z * value, coefficientB1_z);
                                            data.addList(listRegression);
                                            data.setLegend("regression : "+ value+" = "+inputFactor2CategoryIdAndName );
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
//                                        , twoParameterModel.getKoefficientBeta1()
//                                        , twoParameterModel.getKoefficientBeta2()
//                                        , twoModelValueCoefficientA_z
                                        , coefficientB1_z
                                        , coefficientB2_z
                                        );
                                    Label labelForTwoModelProbit = getLabelToTwoModelProbit(
                                         inputFactor1CategoryIdAndName
                                        , inputFactor2CategoryIdAndName
                                        , twoModelValueCoefficientA_z
                                        , coefficientB1_z
                                        , coefficientB2_z
                                    );
                                    vBox.getChildren().add(labelForOneModel);
                                    vBox.getChildren().add(labelForTwoModel);
                                    vBox.getChildren().add(labelForTwoModelProbit);
                                    if (iSsaveToPDF) {
                                        saveToPdf(counter, LineChart1MVC, labelForOneModel,labelForTwoModel,labelForTwoModelProbit );
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

    private void saveToPdf(MathP.Counter counter, MVC lineChart1MVC, Label labelForOneModel, Label  labelForTwoModel, Label  labelForTwoModelProbit) {
        VBox tempVbox = new VBox();
        tempVbox.getChildren().add((LineChartP) lineChart1MVC.getView());
        tempVbox.getChildren().add(labelForOneModel);
        tempVbox.getChildren().add(labelForTwoModel);
        tempVbox.getChildren().add(labelForTwoModelProbit);
        projectManager.saveNodeToPdf(tempVbox
            , ProviderSettings.getProjectSettingsMapValue(
                Settings.Keys.PROJECT_PATH) + "//"
                + Settings.Values.TWO_PARAMETER_MODEL_PROBIT
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
//        , double twoModelValueCoefficientA_z
//        , double coefficientB1_z
//        , double coefficientB2_z
        , double koefficientBeta1
        , double koefficientBeta2 ) {
        return new Label(
            "Regression model                        :   " + StringUtil.addBrackets(outputFactorCategoryIdAndName)
                + "=" + String.format("%.2f",twoModelValueCoefficientA) + (coefficientB1 >= 0 ? " + " : "") +  String.format("%.2f",coefficientB1) + " * " + StringUtil.addBrackets(inputFactor1CategoryIdAndName)
                + (coefficientB2 >= 0 ? " + " : "") +  String.format("%.2f",coefficientB2) + " * " + StringUtil.addBrackets(inputFactor2CategoryIdAndName) + "\n"
                + "Regression model (short)             :   " + "Y = "
                +  String.format("%.2f",twoModelValueCoefficientA) + (coefficientB1 > 0 ? " + " : "") +  String.format("%.2f",coefficientB1) + " * X1" + (coefficientB2 >= 0 ? " + " : "") +  String.format("%.2f",coefficientB2) + " * X2"+ "\n"
                + "Dimensionless regression model :   " + "G = "+  String.format("%.2f",koefficientBeta1) + " * Z1"  +  (koefficientBeta2 >= 0 ? " + " : "") +  String.format("%.2f",koefficientBeta2) + " * Z2"+ "\n"
                + "\n"
        );
    }

    private Label getLabelToTwoModelProbit (
         String inputFactor1CategoryIdAndName
        , String inputFactor2CategoryIdAndName
        , double twoModelValueCoefficientA
        , double coefficientB1
        , double coefficientB2) {
        return new Label(
            "Regression model                        :   "
                + "Error =" + String.format("%.2f",twoModelValueCoefficientA) + (coefficientB1 > 0 ? " + " : "") +  String.format("%.2f",coefficientB1) + " * " + StringUtil.addBrackets(inputFactor1CategoryIdAndName)
                + (coefficientB2 > 0 ? " + " : "") +  String.format("%.2f",coefficientB2) + " * " + StringUtil.addBrackets(inputFactor2CategoryIdAndName) + "\n"
                + "Regression model (short)             :   " + "Z = "
                +  String.format("%.2f",twoModelValueCoefficientA) + (coefficientB1 > 0 ? " + " : "") +  String.format("%.2f",coefficientB1) + " * X1" + (coefficientB2 > 0 ? " + " : "") +  String.format("%.2f",coefficientB2) + " * X2"+ "\n"
                + "Probit model :   " + "P = 1.0/ [1.0 + exp(0.6267*Z) ]"+ "\n"
                + "Probit model :   " + "P =1.0 - 1.0/ [1.0 + exp( -0.6267*Z) ]"+ "\n"
                + "\n"
        );
    }

    private List<Point2D.Double> getListProbitOneModel(Factor inputFactor, double valueCoefficientA, double valueCoefficientB) {
        List<Point2D.Double> listRegression = new ArrayList<>();

        int numberOfX = 100;
        double dx = (inputFactor.getMaxValue() - inputFactor.getMinValue())/numberOfX;
        for (int iInputFactorNumber=0; iInputFactorNumber<numberOfX; iInputFactorNumber++) {
            double x = inputFactor.getMinValue()+dx*iInputFactorNumber;
            double y = valueCoefficientA+valueCoefficientB*x;
            double pi =  1.0/(1.0+ Math.exp(0.6267*y));   //1.0 - 1.0/(1.0+ Math.exp(- 0.6267*y));
            listRegression.add(new Point2D.Double(x,pi));
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
