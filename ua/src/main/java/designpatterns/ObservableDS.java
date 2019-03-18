package designpatterns;


import menu.MenuModel;


import java.util.Objects;
import java.util.Observable;

public class ObservableDS extends Observable {
    protected ObservableDS observableDS;


    public ObservableDS ()  {}

    public ObservableDS (ObservableDS observableDS, MenuModel.Rule rule)  {
        if(Objects.nonNull(observableDS)) {
            this.observableDS = observableDS;
        }
    }

    public void changed() {
        setChanged();
        notifyObservers();
    }

    public void changed(String param) {
        setChanged();
        notifyObservers(param);
    }

    public ObservableDS getObservableDS() {
        return observableDS;
    }

    public void setObservableDS(ObservableDS observableDS) {
        this.observableDS = observableDS;
    }

}
