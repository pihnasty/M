package trestview.resourcelink.schemawork;

import designpatterns.InitializableDS;
import designpatterns.ObservableDS;
import designpatterns.observerdsall.BorderPaneObserverDS;
import entityProduction.Machine;
import entityProduction.Work;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Cursor;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.awt.*;
import java.util.*;
import java.util.List;

public class SchemaView extends BorderPaneObserverDS {

    private ImageView imageview = new ImageView();
    private Work work;
    private Double scaleScheme;

    private BorderPane bp;

    private DoubleBinding kScale;
    private DoubleBinding hImv;
    private DoubleBinding wImv;
    private List<Q> qs;

    public SchemaView(ObservableDS observableDS, InitializableDS initializableDS) {
        super(observableDS,initializableDS);
        bp = new BorderPane();
        getChildren().addAll(bp);
        qs = ((SchemaModel)observableDS).getQs();
        setStyle("-fx-background-color: #336699;");

        setkScaleDefault();
        repaint((SchemaModel) this.observableDS);
        bp.scaleXProperty().bind(kScale);
        bp.scaleYProperty().bind(kScale);
    }

    private void repaint(SchemaModel schemaModel) {
        this.bp.getChildren().clear();
        this.work = schemaModel.getWork();
        this.imageview.setImage(new Image("file:"+work.getScheme() ));
        for(Q q: schemaModel.getQs()) {
          //  q.setRotate(q.getAngle());
            q.setLayoutX(q.getX());
            q.setLayoutY(q.getY());

        }
        if (imageview != null)           bp.getChildren().addAll(imageview);
        if (schemaModel.getQs() != null) bp.getChildren().addAll(schemaModel.getQs());
    }
    @Override
    public void update(Observable o, Object arg) {
        if(  ((SchemaModel) o).getMouseEvent().getEventType() ==   MouseEvent.MOUSE_MOVED)  setCursor(((SchemaModel) o).getCursor());
        if(  ((SchemaModel) o).getMouseEvent().getEventType() ==   MouseEvent.MOUSE_PRESSED) { qs = ((SchemaModel) o).getQs();      }
        repaint((SchemaModel) o);
        setHeight(getHeight()+1);        setHeight(getHeight()-1);
    }

    private void setkScaleDefault() {
        kScale = new DoubleBinding() {
            { super.bind(heightProperty()); }
            @Override
            protected double computeValue() {
                if(observableDS instanceof SchemaModel)((SchemaModel) observableDS).setkScale(  0.85*getHeight()/imageview.getImage().getHeight() );
                return 0.85*getHeight()/imageview.getImage().getHeight() ;
            }};
    }

}
