package trestview.resourcelink.schemawork;

import entityProduction.Machine;
import entityProduction.Modelmachine;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class Q extends BorderPane{
    private Modelmachine modelmachine;
    private Machine machine;

    private ImageView imvQ = new ImageView();
    private int idQ;


    private Double  x;
    private Double  y;
    private Double  oX;
    private Double  oY;

    private Double  angle;

    private double scaleEquipment =1.0;
    private Rectangle rInner;
    private double scale;
 //   private Label idMachine;
    private Text idMacine;

    private Rectangle rOuter;

    public Q(Machine machine) {
        this.machine = machine;
        this.scaleEquipment = machine.getWork().getScaleEquipment();
        this.idQ = machine.getId();
        idMacine = new Text(Integer.toString(idQ));
        Double fontSize = 12.0;
        idMacine.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC,fontSize));
        idMacine.setFill(Color.RED);
        //idMacine.setStroke(Color.BLUE);




        ImageView imvWork = new ImageView();
        imvWork.setImage(new javafx.scene.image.Image("file:"+this.machine.getWork().getScheme()));
        scale = imvWork.getImage().getHeight()/ this.machine.getWork().getOverallSize();
        this.modelmachine = machine.getModelmachine();
        oX = modelmachine.getWorkSizeX()*scale/2.0;
        oY = modelmachine.getWorkSizeY()*scale/2.0;
        angle = this.machine.getAngle();

        idMacine.setY(fontSize-oY);    idMacine.setX(-oX);

        rInner = new Rectangle(-modelmachine.getOverallDimensionX()*scale/2.0,-modelmachine.getOverallDimensionY()*scale/2.0,modelmachine.getOverallDimensionX()*scale,modelmachine.getOverallDimensionY()*scale);
        rOuter = new Rectangle(-oX,-oY,2*oX,2*oY);


        //region: Affine coordinate transformation
        this.imvQ.setImage(new javafx.scene.image.Image("file:"+machine.getModelmachine().getImg() ));
        this.imvQ.fitWidthProperty().setValue(modelmachine.getOverallDimensionX()*scale* scaleEquipment);
        this.imvQ.fitHeightProperty().setValue(imvQ.getImage().getHeight()/imvQ.getImage().getWidth()*imvQ.getFitWidth());
        this.imvQ.setLayoutX(rInner.getX());
        this.imvQ.setLayoutY(rInner.getY());
        //endregion

        this.x=machine.getLocationX()*imvWork.getImage().getWidth() ;// - (oX*Math.cos(angle/(2.0*Math.PI))-oY*Math.sin(angle/(2.0*Math.PI)));
        this.y=machine.getLocationY()*imvWork.getImage().getHeight() ; //- (oX*Math.sin(angle/(2.0*Math.PI))+oY*Math.cos(angle/(2.0*Math.PI)));





      //  this.imageview.setImage(new javafx.scene.image.Image("file:"+work.getScheme() ));
        //this.scale = machine.getWork().


       // rInner.setFill(null);

        rInner.setFill(Color.TRANSPARENT);        rInner.setStrokeWidth(5);        rInner.setStroke(Color.RED);
        rOuter.setFill(Color.TRANSPARENT);        rOuter.setStrokeWidth(5);        rOuter.setStroke(Color.BLUE);



        BorderPane bp = new BorderPane();
        bp.getChildren().addAll(rInner,rOuter);

        bp.setRotate(getAngle());
   //     bp.setLayoutX(0);
   //     bp.setLayoutY(0);

        getChildren().addAll(imvQ,bp, idMacine);
       // getChildren().addAll(imvQ,rInner,rOuter, idMacine);
 //     rInner.setLayoutX(0);   rInner.setLayoutY(0);
    //    setCursor(Cursor.HAND);

    }

    public int getIdQ() {
        return idQ;
    }

    public void setIdQ(int idQ) {
        this.idQ = idQ;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getAngle() {
        return angle;
    }

    public void setAngle(Double angle) {
        this.angle = angle;
    }

    public Double getoX() {
        return oX;
    }

    public void setoX(Double oX) {
        this.oX = oX;
    }

    public Double getoY() {
        return oY;
    }

    public void setoY(Double oY) {
        this.oY = oY;
    }

    public Rectangle getrOuter() { return rOuter;}

    public ImageView getImvQ()   { return imvQ;  }

    public Rectangle getrInner() {
        return rInner;
    }
}
