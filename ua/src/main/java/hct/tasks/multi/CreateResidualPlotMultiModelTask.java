package hct.tasks.multi;

import designpatterns.MVC;
import hct.tasks.base.BaseTask;
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
import java.util.*;
import java.util.logging.Level;

public class CreateResidualPlotMultiModelTask extends BaseTask {


    public CreateResidualPlotMultiModelTask(ProjectManager projectManager, boolean isSaveAsPdf) {
        super(projectManager,isSaveAsPdf);
        LoggerP.write(Level.INFO, "CreateResidualPlotMultiModelTask started...");
    }

    protected ScrollPane calculateChart() {
        ScrollPane scrollPane = new ScrollPane();
        VBox vBox = new VBox();
        MathP.Counter counter = MathP.getCounter(1,1);
        TreeMap<String, List<List<String>>> residualsTables = (TreeMap<String, List<List<String>>> ) projectManager.getProject().getResidualsTables();

        residualsTables.forEach(
            (key, residualsTable) -> {


                DataP data = new DataP();
                List<Point2D.Double> listExperiment = new ArrayList<>();
                List<Point2D.Double> listExpected =  new ArrayList<>();

                List<List<String>> residualsTableWithOutHeader = new ArrayList<>(residualsTable);
                residualsTableWithOutHeader.remove(0);

                for (List<String> row: residualsTableWithOutHeader ) {
                    addExperimentPoint(listExperiment,row.get(6), row.get(1));
                    addExperimentPoint(listExpected,row.get(6), row.get(7));
                }


                data.addList(listExperiment);
                data.addList(listExpected);

                data.setxMin(
                    StringUtil.parseToDouble(residualsTableWithOutHeader.get(0).get(6))
                );
                data.setxMax(
                    StringUtil.parseToDouble(residualsTableWithOutHeader.get(residualsTableWithOutHeader.size()-1).get(6))
                );
                data.setyMin(
                    StringUtil.parseToDouble(residualsTableWithOutHeader.get(0).get(1))
                );
                data.setyMax(
                    StringUtil.parseToDouble(residualsTableWithOutHeader.get(residualsTableWithOutHeader.size()-1).get(1))
                );


                data.setTitleX("Z-value");
                data.setTitleY("Residual");
                data.setLegend("experiment");
                data.setTitleGraph("Residual Model "+key);
                data.setLegend("regression");



//                List<Point2D.Double> listRegressionOneModel = getListRegressionOneModel(inputFactor1, valueCoefficientA, valueCoefficientB);
//                data.addList(listRegressionOneModel);

                    MVC LineChart1MVC  = new MVC (LineChartModel.class, LineChartController.class, LineChartP.class, data, null );
                    vBox.getChildren().add((LineChartP)LineChart1MVC.getView());

//                    if (isSaveAsPdf) {
//                        saveToPdf(counter, LineChart1MVC, new Label(),new Label());
//                    }
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
                + Settings.Values.MULTI_MODEL_RESIDUAL_PLOT_PAGE_PDF
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

    private void addExperimentPoint(List<Point2D.Double> listExperiment, String inputValue, String outputValue) {
        if (Objects.nonNull(outputValue) && Objects.nonNull(inputValue)) {
            listExperiment.add(new Point2D.Double(
                StringUtil.parseToDouble(inputValue)
                , StringUtil.parseToDouble(outputValue)
            ));
        }
    }

}
