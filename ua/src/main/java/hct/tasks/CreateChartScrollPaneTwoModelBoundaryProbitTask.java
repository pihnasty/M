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

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CreateChartScrollPaneTwoModelBoundaryProbitTask extends Task<Void> {

    private ProjectManager projectManager;
    boolean isSaveAsPdf;

    public CreateChartScrollPaneTwoModelBoundaryProbitTask(ProjectManager projectManager, boolean isSaveAsPdf) {
        this.projectManager=projectManager;
        this.isSaveAsPdf=isSaveAsPdf;
        LoggerP.write(Level.INFO, "CreateChartScrollPaneTwoModelTask started...2");
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

        MathP.Counter counter = MathP.getCounter(1,1);
        Plan planPlanExperiment = projectManager.getPlanExperiment();
        FactorManager factorManager = projectManager.getProject().getFactorManager();

        if(Objects.isNull(factorManager)) {
            projectManager.createFactors();
            factorManager = projectManager.getProject().getFactorManager();
        }

        Map<String,Factor> factors = factorManager.getFactors();

        planPlanExperiment.getOutputFactors()
       //     .stream().filter(f->f.equalsIgnoreCase("1.SEVERE PERSISTENT"))
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
                         //   .stream().filter(f->f.equalsIgnoreCase("4.Bronchial asthma in relatives of second generation"))
                            .forEach(
                            inputFactor2CategoryIdAndName -> {

                                List<String> nameCoefficient = covarianceCoefficients.get(0).stream().map(name -> name.trim()).collect(Collectors.toList());

                                int outputFactorNumber = nameCoefficient.indexOf(outputFactorCategoryIdAndName);
                                int inputFactor_1_Number = nameCoefficient.indexOf(inputFactor1CategoryIdAndName);
//-------------------------------------------------------------------------------------------------------------------
                                if(!inputFactor2CategoryIdAndName.equalsIgnoreCase(inputFactor1CategoryIdAndName)) {
                                    Integer numberChart = counter.get();

                                    DataP data = new DataP();
                                    data.setTitleX(outputFactorCategoryIdAndName);
                                    data.setTitleY("Squared error");
                                    data.setLegend("experiment");
                                    data.setTitleGraph(  numberChart.toString()+". Dependence of the factor " +outputFactorCategoryIdAndName
                                        +"\n on the factor "+inputFactor1CategoryIdAndName
                                        +"\n and on the factor "+inputFactor2CategoryIdAndName);
                                    Factor inputFactor2 = factors.get(inputFactor2CategoryIdAndName);
                                    twoParameterModel.calculateKoefficientsBeta(outputFactor, inputFactor1, inputFactor2);

                                    double twoModelValueCoefficientA = twoParameterModel.getKoefficientA();
                                    double coefficientB1 = twoParameterModel.getKoefficientB1();
                                    double coefficientB2 = twoParameterModel.getKoefficientB2();


                                    List<Point2D.Double> summaryFactorsOutFactorsValues = new ArrayList<>();
                                    List<Double> executeOutFactorsValues = new ArrayList<>();
                                    valuesOutputFactor.keySet().forEach(
                                        key -> {
                                            if (Objects.nonNull(inputFactor1.getDoubleValues().get(key)) && Objects.nonNull(inputFactor2.getDoubleValues().get(key))) {
                                                Double executeOutFactorsValue
                                                    = twoModelValueCoefficientA
                                                    + coefficientB1 * inputFactor1.getDoubleValues().get(key)
                                                    + coefficientB2 * inputFactor2.getDoubleValues().get(key);
                                                summaryFactorsOutFactorsValues.add(
                                                    new Point2D.Double(executeOutFactorsValue, outputFactor.getDoubleValues().get(key))
                                                );

                                                Double v1 = coefficientB1 * inputFactor1.getDoubleValues().get(key);
                                                Double v2= coefficientB2 * inputFactor2.getDoubleValues().get(key);
                                                LoggerP.logger.log(Level.INFO,
                                                    v1.toString() + "   "+v2.toString()+"  "+ executeOutFactorsValue.toString()+ " value="+ inputFactor1.getDoubleValues().get(key)
                                                );;
                                                executeOutFactorsValues.add(executeOutFactorsValue);
                                            }
                                        }
                                    );

                                    double minValueOutputFactor =executeOutFactorsValues.stream().min(Comparator.naturalOrder()).get();
                                    double maxValueOutputFactor =executeOutFactorsValues.stream().max(Comparator.naturalOrder()).get();


                                    int maxNumber = 50;
                                    double reductionКange = 0.20;
                                    double delta = (maxValueOutputFactor - minValueOutputFactor) / maxNumber;

                                    if (minValueOutputFactor < outputFactor.getMinValue()) {
                                        minValueOutputFactor = outputFactor.getMinValue()+ maxNumber*reductionКange*delta;
                                        delta = (maxValueOutputFactor - minValueOutputFactor) / maxNumber;
                                    }

                                    if (maxValueOutputFactor > outputFactor.getMaxValue()) {
                                        maxValueOutputFactor = outputFactor.getMaxValue()-maxNumber*reductionКange*delta;
                                        delta = (maxValueOutputFactor - minValueOutputFactor) / maxNumber;
                                    }


                                    Double maxSumErrorValue = 0.0;

                                    List<Point2D.Double> sortedSummaryFactorsOutFactorsValues = summaryFactorsOutFactorsValues;
//                                        = summaryFactorsOutFactorsValues.stream().sorted(
//                                        (leftPoint,rightPoint)->{
//                                            return leftPoint.getX()>rightPoint.getX() ? 1 : -1;
//                                        }
//                                    ).collect(Collectors.toList());

                                    List<Point2D.Double> errorBondaryValues = new ArrayList<>();
                                    for(int i=1; i<=maxNumber; i++) {
                                        Double sumErrorValue = 0.0;
                                        Double bondaryValue = minValueOutputFactor + i*delta;
                                        for (Point2D.Double point : sortedSummaryFactorsOutFactorsValues ) {
                                            if (bondaryValue >= point.getX()) {
                                                if (point.getY() > bondaryValue) {
                                                    sumErrorValue += (point.getY() - bondaryValue) * (point.getY() - bondaryValue);
                                                }
                                            } else {
                                                if (point.getY() < bondaryValue) {
                                                    sumErrorValue += (point.getY() - bondaryValue) * (point.getY() - bondaryValue);
                                                }
                                            }
                                        }
                                        if (sumErrorValue > maxSumErrorValue) {
                                            maxSumErrorValue = sumErrorValue;
                                        }
                                        errorBondaryValues.add(new Point2D.Double(bondaryValue,sumErrorValue));
                                    }

                                    data.setxMin(minValueOutputFactor);
                                    data.setxMax(maxValueOutputFactor);
                                    data.setyMin(0);
                                    data.setyMax(maxSumErrorValue);

                                    data.addList(errorBondaryValues);

                                    MVC LineChart1MVC  = new MVC (LineChartModel.class, LineChartController.class, LineChartP.class, data, null );
                                    vBox.getChildren().add((LineChartP)LineChart1MVC.getView());

                                    projectManager.getProject()
                                        .getCovarianceCoefficients().get(outputFactorNumber).get(inputFactor_1_Number).replace(",",".");
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
                                    vBox.getChildren().add(labelForTwoModel);
                                    if (iSsaveToPDF) {
                                        saveToPdf(numberChart, LineChart1MVC,labelForTwoModel);
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

    private void saveToPdf(int numberChart, MVC lineChart1MVC, Label  labelForTwoModel) {
        VBox tempVbox = new VBox();
        tempVbox.getChildren().add((LineChartP) lineChart1MVC.getView());
        tempVbox.getChildren().add(labelForTwoModel);
        projectManager.saveNodeToPdf(tempVbox
            , ProviderSettings.getProjectSettingsMapValue(
                Settings.Keys.PROJECT_PATH) + "//"
                + Settings.Values.TWO_PARAMETER_MODEL_PDF_PDF_BOUNDARY_VALUE
                + String.format("%03d", numberChart)+ ".pdf");
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




}
