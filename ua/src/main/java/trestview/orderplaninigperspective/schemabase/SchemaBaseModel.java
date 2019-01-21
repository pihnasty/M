package trestview.orderplaninigperspective.schemabase;

import designpatterns.ObservableDS;
import entityProduction.Work;
import trestview.orderplaninigperspective.tables.MyController;
import trestview.orderplaninigperspective.tables.MyModel;
import trestview.orderplaninigperspective.tables.MyView;
import trestview.resourcelink.schemawork.Q;
import trestview.table.tablemodel.abstracttablemodel.Rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class SchemaBaseModel extends ObservableDS implements Observer {

    protected Work work;
    protected List<Q> qs;
    private List<MyView> tablePlusGraphic;

    public SchemaBaseModel(ObservableDS observableDS, Rule rule) {
        super(observableDS, rule);
        this.tablePlusGraphic = new ArrayList<>();
        this.qs = new ArrayList<>();
        if (!trest.getWorks().isEmpty()) createDataSchemaModel(trest.getWorks().get(0));
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    //TODO create MVC
    protected void createDataSchemaModel(Work work) {
        tablePlusGraphic.clear();
        this.work = work;
//        qs.addAll(work.getMachines().stream().map(Q::new).collect(Collectors.toList())); //for (Machine machine : work.getMachines()) {  qs.add(new Q(machine)); }
//        work.getSubject_labours().forEach();

     /*   this.work.getOrders().forEach(order -> {*/
            MyModel myModel = new MyModel(work);
            MyController myController = new MyController(myModel);
            MyView myView = new MyView(myModel, myController);
            tablePlusGraphic.add(myView);
     /*   });*/

    }

//------------------------------------------------------------------------

    public Work getWork() {
        return work;
    }

    public void setWork(Work work) {
        this.work = work;
    }

    public List<Q> getQs() {
        return qs;
    }

    public void setQs(List<Q> qs) {
        this.qs = qs;
    }

    public List<MyView> getTablePlusGraphic() {
        return tablePlusGraphic;
    }
}
