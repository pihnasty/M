package menu;

import designpatterns.ObservableDS;

public class MenuModel extends ObservableDS {


    public MenuModel(ObservableDS observableDS, Rule rule) {
            super(observableDS, rule);
    }


    public void changed() {
        setChanged();
        notifyObservers();
    }

    public enum Rule {

    }
}
