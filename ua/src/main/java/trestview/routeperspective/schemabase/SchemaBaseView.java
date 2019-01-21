package trestview.routeperspective.schemabase;

import designpatterns.InitializableDS;
import designpatterns.ObservableDS;
import designpatterns.observerdsall.BorderPaneObserverDS;
import entityProduction.Work;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import trestview.resourcelink.schemawork.Q;
import trestview.resourcelink.schemawork.SchemaModel;

import java.util.List;
import java.util.Observable;

public class SchemaBaseView extends BorderPaneObserverDS {

    private ImageView imageview = new ImageView();
    private Work work;
    protected Double scaleScheme;

    protected BorderPane bp;

    private DoubleBinding kScale;
    private DoubleBinding hImv;
    private DoubleBinding wImv;
    protected List<Q> qs;

    public SchemaBaseView(ObservableDS observableDS, InitializableDS initializableDS) {
        super(observableDS,initializableDS);
        bp = new BorderPane();
        getChildren().addAll(bp);
        setStyle("-fx-background-color: #336699;");

        setkScaleDefault();
        repaint((SchemaBaseModel) this.observableDS);
        bp.scaleXProperty().bind(kScale);
        bp.scaleYProperty().bind(kScale);
    }

    protected void repaint(SchemaBaseModel schemaModel) {
        this.bp.getChildren().clear();
        this.work = schemaModel.getWork();
        this.imageview.setImage(new Image("file:"+work.getScheme() ));
        if (imageview != null)           bp.getChildren().addAll(imageview);
    }
    @Override
    public void update(Observable o, Object arg) {
        if(  ((SchemaModel) o).getMouseEvent().getEventType() ==   MouseEvent.MOUSE_MOVED)  setCursor(((SchemaModel) o).getCursor());
        if(  ((SchemaModel) o).getMouseEvent().getEventType() ==   MouseEvent.MOUSE_PRESSED) { qs = ((SchemaModel) o).getQs();      }
        repaint((SchemaBaseModel) o);
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
