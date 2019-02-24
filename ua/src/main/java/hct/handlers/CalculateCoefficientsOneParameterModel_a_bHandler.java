package hct.handlers;

import designpatterns.ObservableDS;
import hct.callbacks.CalculateCoefficientsOneParameterModel_a_bCallBack;
import hct.callbacks.CreateFactorsCallBack;
import hct.tasks.CalculateCoefficientsOneParameterModel_a_bTask;
import hct.tasks.CreateFactorsTask;
import javafx.concurrent.Worker;
import main.ProjectManager;

public class CalculateCoefficientsOneParameterModel_a_bHandler {

    public void create_a_b(ObservableDS observableDS) {
        ProjectManager projectManager = ((ProjectManager) observableDS);
        CalculateCoefficientsOneParameterModel_a_bTask calculateCoefficientsOneParameterModel_a_bTask
            = new CalculateCoefficientsOneParameterModel_a_bTask(projectManager);
        CalculateCoefficientsOneParameterModel_a_bCallBack calculateCoefficientsOneParameterModel_a_bCallBack
            = new CalculateCoefficientsOneParameterModel_a_bCallBack();

        calculateCoefficientsOneParameterModel_a_bTask.stateProperty().addListener(
            (observable, oldState, newState) -> {
                if (newState == Worker.State.SUCCEEDED || newState == Worker.State.FAILED) {
                    calculateCoefficientsOneParameterModel_a_bCallBack.call(newState, calculateCoefficientsOneParameterModel_a_bTask.getException());
                }
            }
        );
        new Thread(calculateCoefficientsOneParameterModel_a_bTask).start();
    }
}
