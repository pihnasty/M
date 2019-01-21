package trestview.resourcelink;

import designpatterns.ObservableDS;
import entityProduction.Trest;
import persistence.loader.DataSet;
import persistence.loader.SectionDataSet;
import trestmodel.TrestModel;
import trestview.menu.TMenuModel;
import trestview.resourcelink.schemawork.Q;
import trestview.table.tablemodel.abstracttablemodel.Rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Created by pom on 07.02.2016.
 */
public class ResourceLinkModel extends ObservableDS  {


    private Observable trestModel;
    private DataSet dataSet;



    private SectionDataSet sectionDataSet;

    private Trest trest;

    public ResourceLinkModel(Observable trestModel) {
        super(null);
        this.trestModel = trestModel;
        this.trest =  ((TrestModel)trestModel).getTrest();
        this.dataSet = ((TrestModel)trestModel).getDataSet();
        this.sectionDataSet = ((TrestModel)trestModel).getSectionDataSet();

    }

    public ResourceLinkModel(ObservableDS trestModel, Rule rule) {
        super(trestModel, rule);
        this.trestModel = trestModel;
        this.trest =  ((TrestModel)trestModel).getTrest();
        this.dataSet = ((TrestModel)trestModel).getDataSet();
        this.sectionDataSet = ((TrestModel)trestModel).getSectionDataSet();

    }

    public void changed() {
        setChanged();
        notifyObservers();
    }

    public Observable getTrestModel()                { changed();   return trestModel;  }
    public void setTrestModel(Observable trestModel) { this.trestModel = trestModel;    }
    public DataSet getDataSet()                      { return dataSet;                  }
    public Trest getTrest()                          { return trest;                    }
    public void setTrest(Trest trest)                { this.trest = trest;              }
    public SectionDataSet getSectionDataSet()        { return sectionDataSet;           }
    public void setSectionDataSet(SectionDataSet sectionDataSet) { this.sectionDataSet = sectionDataSet; }
}
