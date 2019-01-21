package designpatterns.observerdsall;

import designpatterns.InitializableDS;
import designpatterns.ObservableDS;
import javafx.scene.layout.StackPane;

import java.util.Observable;
import java.util.Observer;

public class StackPaneObserverDS extends StackPane implements Observer {

    protected ObservableDS observableDS;
    protected InitializableDS initializableDS;
    public StackPaneObserverDS (ObservableDS observableDS, InitializableDS initializableDS ) {
        this.observableDS = observableDS;
        this.initializableDS = initializableDS;
    }
    @Override
    public void update(Observable o, Object arg) {

    }
}
