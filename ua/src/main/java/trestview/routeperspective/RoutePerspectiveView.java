package trestview.routeperspective;

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
import trestview.routeperspective.schemaroute.SchemaRouteController;
import trestview.routeperspective.schemaroute.SchemaRouteModel;
import trestview.routeperspective.schemaroute.SchemaRouteView;
import trestview.table.tablemodel.abstracttablemodel.Rule;

import java.util.Observable;

public class RoutePerspectiveView extends BorderPaneObserverDS {
    public RoutePerspectiveView (ObservableDS observableDS, InitializableDS initializableDS ) {
        super(observableDS,initializableDS);
        FXMLLoader fxmlLoader = XmlRW.fxmlLoad(this,initializableDS, "trestview/routeperspective/routeperspectiveView.fxml","ui", "trestview/routeperspective/routeperspectiveStyle.css");
        SplitPane splitPane = new SplitPane();
        BorderPane borderPane = new BorderPane();
//----------------------------------------------------------------------------------------------------------------------
        MVC schemaRouteMVC = new MVC(SchemaRouteModel.class, SchemaRouteController.class, SchemaRouteView.class, observableDS, Rule.RoutePerspective);
        SchemaRouteView view = (SchemaRouteView) schemaRouteMVC.getView();
        view.addEventHandler(MouseEvent.MOUSE_MOVED, (SchemaRouteController)schemaRouteMVC.getController());
        view.addEventHandler(MouseEvent.MOUSE_DRAGGED, (SchemaRouteController)schemaRouteMVC.getController());
        view.addEventHandler(MouseEvent.MOUSE_RELEASED, (SchemaRouteController)schemaRouteMVC.getController());
//----------------------------------------------------------------------------------------------------------------------
        TableBoxMVC tableBoxWorkMVC = new TableBoxMVC( observableDS,fxmlLoader.getResources().getString("ListManufacturing"),"file:pde\\src\\main\\resources\\images\\icons\\RowWork.png",Rule.Work);
        tableBoxWorkMVC.addObserverForTable(schemaRouteMVC);
//----------------------------------------------------------------------------------------------------------------------
        TableBoxMVC tableBoxSubject_labourMVC = new TableBoxMVC( tableBoxWorkMVC.getTableMVC(),fxmlLoader.getResources().getString("ListSubject_labour"),"file:pde\\src\\main\\resources\\images\\icons\\RowSubject_labour.png",Rule.Subject_labour);
        tableBoxSubject_labourMVC.addObserverForTable(schemaRouteMVC);
//----------------------------------------------------------------------------------------------------------------------
        TableBoxMVC tableBoxRouteMVC = new TableBoxMVC( tableBoxSubject_labourMVC.getTableMVC(),fxmlLoader.getResources().getString("ListRoute"),"file:pde\\src\\main\\resources\\images\\icons\\RowRoute.png",Rule.Route);
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
                                                tableBoxSubject_labourMVC.getVbox() ,
                                                tableBoxRouteMVC.getVbox(),
                                                tableBoxLinerouteMVC.getVbox(),
                                                tableBoxLinespecMVC.getVbox());
        vboxSplitPaneLeft.setSpacing(5);   // The amount of vertical space between each child in the vbox.
        vboxSplitPaneLeft.setPadding(new Insets(10, 0, 0, 10));   // The top,right,bottom,left padding around the region's content. This space will be included in the calculation of the region's minimum and preferred sizes. By default padding is Insets.EMPTY and cannot be set to null.
//----------------------------------------------------------------------------------------------------------------------
        borderPane.setCenter((StackPane)schemaRouteMVC.getView());
        splitPane.getItems().addAll(vboxSplitPaneLeft, borderPane);
        splitPane.setDividerPositions(0.2f, 0.6f);
        setCenter(splitPane);
    }
    @Override
    public void update(Observable o, Object arg) {
        /*NOP*/
    }
}
