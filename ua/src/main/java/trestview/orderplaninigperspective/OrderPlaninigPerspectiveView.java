package trestview.orderplaninigperspective;

import designpatterns.InitializableDS;
import designpatterns.MVC;
import designpatterns.ObservableDS;
import designpatterns.TableBoxMVC;
import designpatterns.observerdsall.BorderPaneObserverDS;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import persistence.loader.XmlRW;
import trestview.linechart.LineChartController;
import trestview.linechart.LineChartModel;
import trestview.linechart.LineChartP;
import trestview.orderplaninigperspective.schemaroute.SchemaOrderPlaninigController;
import trestview.orderplaninigperspective.schemaroute.SchemaOrderPlaninigModel;
import trestview.orderplaninigperspective.schemaroute.SchemaOrderPlaninigView;
import trestview.table.tablemodel.abstracttablemodel.Rule;
import trestview.tasks.conveyorPDE.v_constcontrol.band.VConstTimeControlBandConveyorPdeModel;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

public class OrderPlaninigPerspectiveView extends BorderPaneObserverDS {
    public OrderPlaninigPerspectiveView(ObservableDS observableDS, InitializableDS initializableDS ) {
        super(observableDS,initializableDS);
        FXMLLoader fxmlLoader = XmlRW.fxmlLoad(this,initializableDS, "trestview/orderplaninigperspective/orderplaninigperspectiveView.fxml","ui", "trestview/orderplaninigperspective/orderplaninigperspectiveStyle.css");
        SplitPane splitPane = new SplitPane();
        BorderPane borderPane = new BorderPane();
//----------------------------------------------------------------------------------------------------------------------
        MVC schemaRouteMVC = new MVC(SchemaOrderPlaninigModel.class, SchemaOrderPlaninigController.class, SchemaOrderPlaninigView.class, observableDS, Rule.RoutePerspective);
        SchemaOrderPlaninigView view = (SchemaOrderPlaninigView) schemaRouteMVC.getView();
        //TODO debug
/*        view.addEventHandler(MouseEvent.MOUSE_MOVED, (SchemaOrderPlaninigController)schemaRouteMVC.getController());
        view.addEventHandler(MouseEvent.MOUSE_DRAGGED, (SchemaOrderPlaninigController)schemaRouteMVC.getController());
        view.addEventHandler(MouseEvent.MOUSE_RELEASED, (SchemaOrderPlaninigController)schemaRouteMVC.getController());*/
//----------------------------------------------------------------------------------------------------------------------
        TableBoxMVC tableBoxWorkMVC = new TableBoxMVC( observableDS,fxmlLoader.getResources().getString("ListManufacturing"),"file:pde\\src\\main\\resources\\images\\icons\\RowWork.png",Rule.Work);
        tableBoxWorkMVC.addObserverForTable(schemaRouteMVC);
//----------------------------------------------------------------------------------------------------------------------
        TableBoxMVC tableBoxOrderMVC = new TableBoxMVC( tableBoxWorkMVC.getTableMVC(),fxmlLoader.getResources().getString("ListOrder"),"file:pde\\src\\main\\resources\\images\\icons\\RowOrder.png",Rule.Order);
        tableBoxOrderMVC.addObserverForTable(schemaRouteMVC);
//----------------------------------------------------------------------------------------------------------------------
        TableBoxMVC tableBoxLineMVC = new TableBoxMVC( tableBoxOrderMVC.getTableMVC(),fxmlLoader.getResources().getString("ListLine"),"file:pde\\src\\main\\resources\\images\\icons\\RowLine2.png",Rule.Line);
        tableBoxLineMVC.addObserverForTable(schemaRouteMVC);
//----------------------------------------------------------------------------------------------------------------------
        TableBoxMVC tableBoxRouteMVC = new TableBoxMVC( tableBoxLineMVC.getTableMVC(),fxmlLoader.getResources().getString("ListRoute"),"file:pde\\src\\main\\resources\\images\\icons\\RowRoute.png",Rule.Route);
        tableBoxRouteMVC.addObserverForTable(schemaRouteMVC);
//----------------------------------------------------------------------------------------------------------------------
        TableBoxMVC tableBoxLinerouteMVC = new TableBoxMVC( tableBoxRouteMVC.getTableMVC(),fxmlLoader.getResources().getString("ListLineroute"),"file:pde\\src\\main\\resources\\images\\icons\\RowLineroute.png",Rule.Lineroute);
        tableBoxLinerouteMVC.addObserverForTable(schemaRouteMVC);
//----------------------------------------------------------------------------------------------------------------------
        TableBoxMVC tableBoxLinespecMVC = new TableBoxMVC( tableBoxLinerouteMVC.getTableMVC(),fxmlLoader.getResources().getString("ListLinespec"),"file:pde\\src\\main\\resources\\images\\icons\\RowResource.png",Rule.Linespec);
        tableBoxLinespecMVC.addObserverForTable(schemaRouteMVC);
//----------------------------------------------------------------------------------------------------------------------
        VBox vboxSplitPaneLeft = new VBox();
        vboxSplitPaneLeft.getChildren().addAll(tableBoxWorkMVC.getVbox(),
                                                tableBoxOrderMVC.getVbox(),
                                                tableBoxLineMVC.getVbox(),
                                                tableBoxRouteMVC.getVbox(),
                                                tableBoxLinerouteMVC.getVbox(),
                                                tableBoxLinespecMVC.getVbox()
                                                );
        vboxSplitPaneLeft.setSpacing(5);   // The amount of vertical space between each child in the vbox.
        vboxSplitPaneLeft.setPadding(new Insets(10, 0, 0, 10));   // The top,right,bottom,left padding around the region's content. This space will be included in the calculation of the region's minimum and preferred sizes. By default padding is Insets.EMPTY and cannot be set to null.
//----------------------------------------------------------------------------------------------------------------------
        borderPane.setCenter((StackPane) schemaRouteMVC.getView());
        splitPane.getItems().addAll(vboxSplitPaneLeft, borderPane);
        splitPane.setDividerPositions(0.2f, 0.6f);
        setCenter(splitPane);


    }
    @Override
    public void update(Observable o, Object arg) {
        /*NOP*/
    }

}
