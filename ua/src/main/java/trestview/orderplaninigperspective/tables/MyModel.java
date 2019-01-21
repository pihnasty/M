package trestview.orderplaninigperspective.tables;

import designpatterns.ObservableDS;
import entityProduction.Order;
import entityProduction.Work;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by dummy_user on 5/14/2017.
 */
public class MyModel extends ObservableDS implements Observer {
    private Work work;

    public MyModel(Work work) {
        this.work = work;
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    public Work getWork() {
        return work;
    }
}
