package mainwindows;


import com.sun.javafx.print.PrinterJobImpl;
import designpatterns.MVC;
import designpatterns.observerdsall.BorderPaneObserverDS;
import experiment.Plan;
import factors.Factor;
import factors.FactorManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import linechart.DataP;
import linechart.LineChartController;
import linechart.LineChartModel;
import linechart.LineChartP;
import main.AppProject;
import main.ProjectManager;
import math.MathP;
import menu.MenuController;
import menu.MenuModel;
import menu.MenuView;
import persistence.loader.XmlRW;
import javafx.scene.control.ScrollPane;
import settings.ProviderSettings;
import settings.Settings;

import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Destination;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;


public class MainWindowView extends BorderPaneObserverDS {

    public  static FXMLLoader loaderRecource;
    ProjectManager projectManager;

    public MainWindowView() {
        super(null,null);

        loaderRecource = XmlRW.fxmlLoad(this,this, "mainwindows/mainwindowview.fxml","ui","");

        projectManager = ProjectManager.getInstance();
        AppProject project =AppProject.getInstance();
        projectManager.setProject(project);



        MVC menu = new MVC(MenuModel.class, MenuController.class, MenuView.class, projectManager,null );
        this.setTop((MenuView)menu.getView());
        ((MenuModel) menu.getModel()).addObserver(this);


    }

    @Override
    public void update(Observable o, Object arg) {
        if (o.getClass()==MenuModel.class) { updateCenter((MenuModel) o);   }
    }
    private void updateCenter(MenuModel o) {
        switch (o.getMenuItemCall()) {
            case BUILD_RAW_DATA_GRAPH:
                ScrollPane scrollPane = new ScrollPane();
                calculateChart(scrollPane);
                setCenter(scrollPane);
                break;
            case DEFAULT:
                setCenter(new ScrollPane());
                break;
            default:
                break;
        }
    }

    private void calculateChart(ScrollPane scrollPane) {

        VBox vBox = new VBox();
        MathP.Counter counter = MathP.getCounter(1,1);

        Plan planPlanExperiment = projectManager.getPlanExperiment();
        FactorManager factorManager = projectManager.getFactorManager();
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

                        List<String> nameCoefficientA = projectManager.getKoefficientA().get(0).stream().map(name -> name.trim()).collect(Collectors.toList());

                        int outputFactorNumber = nameCoefficientA.indexOf(outputFactorCategoryIdAndName);
                        int inputFactorNumber = nameCoefficientA.indexOf(inputFactorCategoryIdAndName);

                        double valueCoefficientA =
                        Double.parseDouble(
                            projectManager.getKoefficientA().get(outputFactorNumber).get(inputFactorNumber).replace(",",".")
                        );

                        double valueCoefficientB =
                            Double.parseDouble(
                                projectManager.getKoefficientB().get(outputFactorNumber).get(inputFactorNumber).replace(",",".")
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

                        VBox tempVbox= new VBox();

//                        tempVbox.getChildren().add( (LineChartP)LineChart1MVC.getView());
//
//                        saveNodeToPdf( tempVbox
//                            , ProviderSettings.getProjectSettingsMapValue(
//                                Settings.Keys.PROJECT_PATH)+"//"+Settings.Values.ONE_PARAMETER_MODEL_PDF_OPM_PAGE+
//
//                                String.format("%03d",  counter.get())
//
//
//                                +".pdf");


                    }
                );



            }

        );
//        Button btn = new Button();
//        btn.setText("Printed to PDF");
        //      btn.setTranslateY(100);

 //       vBox.getChildren().add(btn);

        // this.setCenter(vBox);


        // Set content for ScrollPane
        scrollPane.setContent(vBox);

        // Always show vertical scroll bar
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        // Horizontal scroll bar is only displayed when needed
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);



//        btn.setOnAction(new EventHandler<ActionEvent>() {
//
//            @Override
//            public void handle(ActionEvent event) {
//                saveNodeToPdf();
//            }
//
//
//        });



    }

    private void saveNodeToPdf(Node node, String filePath) {
        System.out.println("To Printer!");
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        String path =  io.file.Paths.getPathToDirectory(filePath);

        Path p1 = Paths.get(path);
        if (Files.notExists(p1)) {
            try {
                Files.createDirectories(p1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        PrinterJob job = PrinterJob.createPrinterJob();
        if(job != null){
            addPathToPdfFile(job,"file://"+filePath);
 //           job.getJobSettings();
//            job.showPageSetupDialog( node.getScene().getWindow());
//            job.showPrintDialog(node.getScene().getWindow());
            job.printPage(node);
            job.endJob();
        }
    }

    private void addPathToPdfFile(PrinterJob job, String filePath) {
        try {
            java.lang.reflect.Field field = job.getClass().getDeclaredField("jobImpl");
            field.setAccessible(true);
            PrinterJobImpl jobImpl = (PrinterJobImpl) field.get(job);
            field.setAccessible(false);

            field = jobImpl.getClass().getDeclaredField("printReqAttrSet");
            field.setAccessible(true);
            PrintRequestAttributeSet printReqAttrSet = (PrintRequestAttributeSet) field.get(jobImpl);
            field.setAccessible(false);

            printReqAttrSet.add(new Destination(new java.net.URI(filePath)));
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public static String getResourceString(String key) {
        return loaderRecource.getResources().getString(key);
    }
}
