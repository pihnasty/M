package designpatterns;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import trestview.hboxpane.HboxpaneController;
import trestview.hboxpane.HboxpaneModel;
import trestview.hboxpane.HboxpaneView;
import trestview.routeperspective.schemaroute.SchemaRouteModel;
import trestview.table.TableController;
import trestview.table.TableViewP;
import trestview.table.tablemodel.TableModel;
import trestview.table.tablemodel.abstracttablemodel.Rule;

import java.util.Observer;

public class TableBoxMVC {
    private MVC tableMVC;
    private VBox vbox;

    public TableBoxMVC( MVC tableObservable, String textForLabel, String pathForImageLabel, Rule rule) {
        this((ObservableDS) tableObservable.getModel(), textForLabel, pathForImageLabel, rule);
        tableObservable.addObserverP((TableModel)tableMVC.getModel());
    }
    public TableBoxMVC( ObservableDS observableDS, String textForLabel, String pathForImageLabel, Rule rule) {
        Label labelTable = new Label(textForLabel);
        labelTable.setGraphic(new ImageView(new Image( pathForImageLabel)));

        this.tableMVC  = new MVC (TableModel.class, TableController.class, TableViewP.class,observableDS, rule);
        MVC hboxpaneMVC = new MVC (HboxpaneModel.class,HboxpaneController.class,HboxpaneView.class,observableDS, rule);
        hboxpaneMVC.addObserverP( (TableModel)tableMVC.getModel());

        vbox = new VBox();
        HBox hbox = new HBox();

        hbox.getChildren().addAll((HboxpaneView)hboxpaneMVC.getView(), labelTable);

        vbox.getChildren().addAll(hbox,(TableViewP)tableMVC.getView());
        vbox.setSpacing(5);   // The amount of vertical space between each child in the vbox.
        vbox.setPadding(new Insets(10, 0, 0, 10));   // The top,right,bottom,left padding around the region's content. This space will be included in the calculation of the region's minimum and preferred sizes. By default padding is Insets.EMPTY and cannot be set to null.
    }
    public void addObserverForTable (MVC observerForTable){
        tableMVC.addObserverP((Observer) observerForTable.getModel());
    }
    public VBox getVbox() {
        return vbox;
    }
    public MVC getTableMVC() {
        return tableMVC;
    }
}
