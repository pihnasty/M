package hct.tasks.base;

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

public class BaseTask extends Task<Void> {

    protected ProjectManager projectManager;
    protected boolean isSaveAsPdf;

    public BaseTask(ProjectManager projectManager) {
        this.projectManager=projectManager;
    }

    public BaseTask(ProjectManager projectManager, boolean isSaveAsPdf) {
        this(projectManager);
        this.isSaveAsPdf=isSaveAsPdf;
    }

    protected Void call() {
     Platform.runLater(() -> {
            projectManager.changeScrollPane(e->calculateChart());
        });
        return null;
    }


    protected ScrollPane calculateChart() {
        return null;
    }

}
