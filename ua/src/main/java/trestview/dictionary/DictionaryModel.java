package trestview.dictionary;

import designpatterns.ObservableDS;
import trestmodel.TrestModel;
import trestview.menu.TMenuModel;
import trestview.table.tablemodel.abstracttablemodel.Rule;

import java.util.Observable;

/**
 * Created by pom on 07.02.2016.
 */
public class DictionaryModel extends ObservableDS  {



    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    private Rule rule;

    public Class gettClass() {
        return tClass;
    }

    public void settClass(Class tClass) {
        this.tClass = tClass;
    }

    private Class tClass;

     public DictionaryModel(ObservableDS observableDS, Rule rule) {
         super(observableDS,rule);
         this.rule = rule;        this.tClass = rule.getClassTab();


    }

    public ObservableDS getTMenuModel() {
        return observableDS;
    }

    public void setTMenuModel(ObservableDS observableDS) {
        this.observableDS = observableDS;
        changed();
    }

    public void changed() {
        setChanged();
        notifyObservers();
    }
}
