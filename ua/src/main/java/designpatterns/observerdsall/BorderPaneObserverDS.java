package designpatterns.observerdsall;

import designpatterns.InitializableDS;
import designpatterns.ObservableDS;
import javafx.scene.layout.BorderPane;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Max on 17.09.2016.
 */
public class BorderPaneObserverDS extends BorderPane implements Observer {
    protected ObservableDS observableDS;
    protected InitializableDS initializableDS;
    public BorderPaneObserverDS (ObservableDS observableDS, InitializableDS initializableDS ) {
        this.observableDS = observableDS;
        this.initializableDS = initializableDS;
    }
    @Override
    public void update(Observable o, Object arg) {

    }
}
