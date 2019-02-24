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
import string.StringUtil;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class DefaultScrollPaneTask extends Task<Void> {

    private ProjectManager projectManager;

    public DefaultScrollPaneTask(ProjectManager projectManager) {
        this.projectManager=projectManager;
        LoggerP.write(Level.INFO, "CreateChartScrollPaneTask started...");
    }

    @Override
    protected Void call() {
        Platform.runLater(() -> {
            projectManager.cleanScrollPane();
        });
        return null;
    }

}
