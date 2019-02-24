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
import settings.ProviderSettings;
import settings.Settings;
import string.StringUtil;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class CreateChartScrollPaneTask extends Task<Void> {

    private ProjectManager projectManager;
    boolean isSaveAsPdf;

    public CreateChartScrollPaneTask(ProjectManager projectManager, boolean isSaveAsPdf) {
        this.projectManager=projectManager;
        this.isSaveAsPdf=isSaveAsPdf;
        LoggerP.write(Level.INFO, "CreateChartScrollPaneTask started...");
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

        planPlanExperiment.getOutputFactors().forEach(
            outputFactorCategoryIdAndName -> {


                planPlanExperiment.getInputFactors().forEach(
                    inputFactorCategoryIdAndName-> {

                        DataP data = new DataP();

                        List<Point2D.Double> listExperiment = new ArrayList<>();

                        Factor outputFactor = factors.get(outputFactorCategoryIdAndName);
                        Factor inputFactor = factors.get(inputFactorCategoryIdAndName);

                        Map<String, Double> valuesOutputFactor = outputFactor.getDoubleValues();
                        Map<String, Double> valuesInputFactor = inputFactor.getDoubleValues();

                        valuesOutputFactor.keySet().forEach(
                            key -> {
                                Double outputValue = valuesOutputFactor.get(key);
                                Double inputValue = valuesInputFactor.get(key);
                                if (Objects.nonNull(outputValue) && Objects.nonNull(inputValue)) {
                                    listExperiment.add(new Point2D.Double(inputValue, outputValue));
                                }
                            }
                        );
                        data.addList(listExperiment);
                        data.setxMin(inputFactor.getMinValue());
                        data.setxMax(inputFactor.getMaxValue());
                        data.setyMin(outputFactor.getMinValue());
                        data.setyMax(outputFactor.getMaxValue());

                        data.setTitleX(inputFactorCategoryIdAndName);
                        data.setTitleY(outputFactorCategoryIdAndName);

                        data.setLegend("experiment");
                        data.setTitleGraph("Dependence of the factor " +outputFactorCategoryIdAndName +"\n on the factor "+inputFactorCategoryIdAndName);

                        List<String> nameCoefficientA = projectManager.getProject().getKoefficientA().get(0).stream().map(name -> name.trim()).collect(Collectors.toList());

                        int outputFactorNumber = nameCoefficientA.indexOf(outputFactorCategoryIdAndName);
                        int inputFactorNumber = nameCoefficientA.indexOf(inputFactorCategoryIdAndName);

                        double valueCoefficientA =
                            Double.parseDouble(
                                projectManager.getProject().getKoefficientA().get(outputFactorNumber).get(inputFactorNumber).replace(",",".")
                            );

                        double valueCoefficientB =
                            Double.parseDouble(
                                projectManager.getProject().getKoefficientB().get(outputFactorNumber).get(inputFactorNumber).replace(",",".")
                            );

                        List<Point2D.Double> listRegression = new ArrayList<>();

                        int numberOfX = 100;
                        double dx = (inputFactor.getMaxValue() - inputFactor.getMinValue())/numberOfX;
                        for (int iInputFactorNumber=0; iInputFactorNumber<numberOfX; iInputFactorNumber++) {
                            double x = inputFactor.getMinValue()+dx*iInputFactorNumber;
                            double y = valueCoefficientA+valueCoefficientB*x;
                            listRegression.add(new Point2D.Double(x,y));
                        }

                        data.setLegend("regression");

                        data.addList(listRegression);

                        MVC LineChart1MVC  = new MVC (LineChartModel.class, LineChartController.class, LineChartP.class, data, null );
                        vBox.getChildren().add((LineChartP)LineChart1MVC.getView());

                        String covarianceCoefficient = projectManager.getProject()
                            .getCovarianceCoefficients().get(outputFactorNumber).get(inputFactorNumber).replace(",",".");


                        Label label = new Label(
                             "Covariance coefficient      :   " + covarianceCoefficient.trim() + "\n"
                                + "Regression model            :   " + StringUtil.addBrackets(outputFactorCategoryIdAndName) + "=" + valueCoefficientA + (valueCoefficientB > 0 ? " + " : "") +
                                +valueCoefficientB + " * " + StringUtil.addBrackets(inputFactorCategoryIdAndName) + "\n"
                                + "Regression model (short) :   " +  "Y = " + valueCoefficientA + (valueCoefficientB > 0 ? " + " : "") +
                                +valueCoefficientB + " * X"  + "\n\n\n\n\n\n"
                        );

                        vBox.getChildren().add(label);

                        if(iSsaveToPDF) {

                            VBox tempVbox = new VBox();

                            tempVbox.getChildren().add((LineChartP) LineChart1MVC.getView());
                            tempVbox.getChildren().add(label);

                            projectManager.saveNodeToPdf(tempVbox
                                , ProviderSettings.getProjectSettingsMapValue(
                                    Settings.Keys.PROJECT_PATH) + "//" + Settings.Values.ONE_PARAMETER_MODEL_PDF_OPM_PAGE +

                                    String.format("%03d", counter.get())


                                    + ".pdf");
                        }

                    }
                );



            }

        );
        // Set content for ScrollPane
        scrollPane.setContent(vBox);
        // Always show vertical scroll bar
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        // Horizontal scroll bar is only displayed when needed
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        return scrollPane;
    }



}
