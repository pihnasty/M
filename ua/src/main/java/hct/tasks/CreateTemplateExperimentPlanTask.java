package hct.tasks;

import javafx.concurrent.Task;
import logging.LoggerP;
import main.ProjectManager;
import mainwindows.MainWindowView;
import settings.Settings;

import java.util.Objects;
import java.util.logging.Level;

public class CreateTemplateExperimentPlanTask extends Task<Void> {

    private ProjectManager projectManager;
    private String pathToProject;

    public CreateTemplateExperimentPlanTask(ProjectManager projectManager, String pathToProject) {
        this.projectManager=projectManager;
        LoggerP.write(Level.INFO, "CreateTemplateExperimentPlanTask started...");
        this.pathToProject=pathToProject;
    }

    @Override
    protected Void call() throws Exception {
        if (Objects.isNull(pathToProject))   {
            throw new Exception(
                MainWindowView.getResourceString("сreate.template.experiment.title.message.no.choose.directory")
            );
        }
        projectManager.createTemplateExperimentPlan(pathToProject+Settings.Values.TEMPLATE_EXPERIMENT_PLAN_JSON );
        return null;
    }
}
