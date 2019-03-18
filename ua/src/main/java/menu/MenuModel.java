package menu;

import designpatterns.ObservableDS;
import entityProduction.*;
import persistence.loader.tabDataSet.*;


import java.util.Observable;

public class MenuModel extends ObservableDS {


    public MenuModel(ObservableDS observableDS, Rule rule) {
            super(observableDS, rule);
    }


    public void changed() {
        setChanged();
        notifyObservers();
    }

    public enum Rule {
        Functiondist2(Functiondist.class);

        Rule(Class clazz) {
            this.clazz = clazz;
        }

        private Class clazz;

        public Class getClassTab() {
            return this.clazz;
        }
    }
}
