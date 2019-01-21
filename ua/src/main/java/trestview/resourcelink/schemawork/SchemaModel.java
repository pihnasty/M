package trestview.resourcelink.schemawork;

import designpatterns.ObservableDS;
import entityProduction.Machine;
import entityProduction.Work;
import javafx.scene.Cursor;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import persistence.loader.DataSet;
import persistence.loader.XmlRW;
import persistence.loader.tabDataSet.RowMachine;
import trestview.resourcelink.ResourceLinkModel;
import trestview.table.tablemodel.TableModel;
import trestview.table.tablemodel.abstracttablemodel.Rule;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.stream.Collectors;

public class SchemaModel extends ObservableDS implements Observer {


    private Cursor cursor;
    private MouseEvent mouseEvent;
    private Work work;
    private Double kScale;
    private List<Q> qs;
    private Q qCurrent = null;



    public SchemaModel(ObservableDS observableDS, Rule rule) {
        super(observableDS, rule);

        this.qs = new ArrayList();




        if (this.rule == Rule.Work) {
            if (!( observableDS).getTrest().getWorks().isEmpty()) {
                createDataSchemaModel((observableDS).getTrest().getWorks().get(0));
            }
        }



    }

    @Override
    public void update(Observable o, Object arg) {
       if ((this.rule == Rule.Work))  {   //
            if ((o.getClass()==TableModel.class)) {
                updateForTableWorkMVC   ((TableModel) o);
                updateForTableMachineMVC((TableModel) o);
            }
        }
        changed();
    }

    private void updateForTableMachineMVC(TableModel o) {


        if (o.getRule()== Rule.Machine   )   {
            this.dataSet = o.getDataSet();
            createDataSchemaModel(work);
            if (!((ResourceLinkModel) observableDS).getTrest().getWorks().isEmpty()) {
                //              createDataSchemaModel(((ResourceLinkModel) observableModel).getTrest().getWorks().get(0));
            }
        }
    }

    private void updateForTableWorkMVC(TableModel o) {
        if (o.getRule()==Rule.Work   ) createDataSchemaModel((Work) ((TableModel<Work>) o).getSelectRow());
    }


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

    private void createDataSchemaModel(Work work) {
        qs.clear();
        this.work = work;
        qs.addAll(work.getMachines().stream().map(Q::new).collect(Collectors.toList())); //for (Machine machine : work.getMachines()) {  qs.add(new Q(machine)); }
    }

    public Cursor getCursor() {
        return cursor;
    }

    private void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    public void changeCursor(MouseEvent event) {
        this.mouseEvent = event;
        if (find(new Point((int) mouseEvent.getX(), (int) mouseEvent.getY())) == null) {
            setCursor(Cursor.DEFAULT);
        } else {
            setCursor(Cursor.HAND);
        }
        changed();
    }


    public void qCurrentIsNull() {
        qCurrent = null;
    }

    public void changeLocation(MouseEvent event) {
        this.mouseEvent = event;
        double eps = 0.1;   //   Error move
        Point p = new Point((int) mouseEvent.getX(), (int) mouseEvent.getY());
        if (qCurrent == null)
            qCurrent = find(p);
        double x = (p.getX() / kScale);
        double y = (p.getY() / kScale);

        ImageView imvWork = new ImageView();
        imvWork.setImage(new javafx.scene.image.Image("file:" + work.getScheme()));

//--begin--  This unit takes into account the imposition of the equipment. To make it work, you must comment out the line:   if(qCurrent==null)
        if (qCurrent == null) {
            setCursor(Cursor.DEFAULT);
        } else {
            if (isEmptyPoint(qCurrent).equals("N")) {
                for (Machine m : work.getMachines())
                    if (m.getId() == qCurrent.getIdQ()) {
                        double dxLocationX = x / imvWork.getImage().getWidth() - m.getLocationX();
                        double dyLocationY = y / imvWork.getImage().getHeight() - m.getLocationY();

                        if (Math.abs(dxLocationX) > eps) {
                            dxLocationX = eps * Math.signum(dxLocationX);
                            m.setLocationX(m.getLocationX() + dxLocationX);
                        } else m.setLocationX(x / imvWork.getImage().getWidth());
                        if (Math.abs(dyLocationY) > eps) {
                            dyLocationY = eps * Math.signum(dyLocationY);
                            m.setLocationY(m.getLocationY() + dyLocationY);
                        } else m.setLocationY(y / imvWork.getImage().getHeight());
                        saveMachineIntoRownachineDataSet(m);
                    }

            }
            else {
                for (Machine m2 : work.getMachines())
                    if (m2.getId() == qCurrent.getIdQ()) {
                        double dxLocationX = x / imvWork.getImage().getWidth() - m2.getLocationX();
                        double dyLocationY = y / imvWork.getImage().getHeight() - m2.getLocationY();

                        dxLocationX = 0.5 * eps * Math.signum(dxLocationX);
                        m2.setLocationX(m2.getLocationX() - dxLocationX);

                        dyLocationY = 0.5 * eps * Math.signum(dyLocationY);
                        m2.setLocationY(m2.getLocationY() - dyLocationY);
                        saveMachineIntoRownachineDataSet(m2);
                    }

            }
            for (Q qw : qs) {
                //       System.out.println("q.getBoundsInParent().contains(qw.getBoundsInParent())="+q.getBoundsInParent().intersects(qw.getBoundsInParent()));
            }
//--end--  This unit takes into account the imposition of the equipment. To make it work, you must comment out the line:   if(qCurrent==null)

        }

        createDataSchemaModel(work);
        changed();
    }



    private String isEmptyPoint(Q q) {
        Q q2 = null;
        String s = "N";
        for (int i = 0; i < 4; i++) {
            q2 = find(findPoint(q)[i], q);
            if (q2 != null)
                if (q2.getIdQ() != q.getIdQ()) {
                    System.out.println("Yes");
                    s = "Y";
                    return s;
                }
        }

        return s;
    }

    public MouseEvent getMouseEvent() {
        return mouseEvent;
    }

    public void setMouseEvent(MouseEvent mouseEvent) {
        this.mouseEvent = mouseEvent;
    }

    public Double getkScale() {
        return kScale;
    }

    public void setkScale(Double kScale) {
        this.kScale = kScale;
    }

    private Q find(Point p) {
        for (Q q : qs) {
            double x = (p.getX() / kScale - q.getLayoutX());
            double y = (p.getY() / kScale - q.getLayoutY());
            double t = 2.0 * Math.PI / 360;
            double xAngle = x * Math.cos(q.getAngle() * t) + y * Math.sin(q.getAngle() * t);
            double yAngle = -x * Math.sin(q.getAngle() * t) + y * Math.cos(q.getAngle() * t);
            if (q.getrOuter().contains(xAngle, yAngle)) {
                return q;
            }
        }
        return null;
    }

    private Q find(Point p, Q qCurrent_) {
        for (int i = 0; i < qs.size(); i++) {
            Q q = qs.get(i);
            if (qCurrent_.getIdQ() != q.getIdQ()) {
                double x = (p.getX() / kScale - q.getLayoutX());
                double y = (p.getY() / kScale - q.getLayoutY());
                double t = 2.0 * Math.PI / 360;
                double xAngle = x * Math.cos(q.getAngle() * t) + y * Math.sin(q.getAngle() * t);
                double yAngle = -x * Math.sin(q.getAngle() * t) + y * Math.cos(q.getAngle() * t);
                if (q.getrOuter().contains(xAngle, yAngle)) { return q;    }
            }
        }
        return null;
    }

    private Point[] findPoint(Q q) {

        Point[] points = new Point[]{new Point(), new Point(), new Point(), new Point()};
        points[0] = coordinatesPointAfterRotation((int) (-q.getrInner().getWidth() / 2.0), (int) (-q.getrInner().getHeight() / 2.0), q.getAngle(), q.getLayoutX(), q.getLayoutY());
        points[1] = coordinatesPointAfterRotation((int) (-q.getrInner().getWidth() / 2.0), (int) (q.getrInner().getHeight() / 2.0), q.getAngle(), q.getLayoutX(), q.getLayoutY());
        points[2] = coordinatesPointAfterRotation((int) (q.getrInner().getWidth() / 2.0), (int) (-q.getrInner().getHeight() / 2.0), q.getAngle(), q.getLayoutX(), q.getLayoutY());
        points[3] = coordinatesPointAfterRotation((int) (q.getrInner().getWidth() / 2.0), (int) (q.getrInner().getHeight() / 2.0), q.getAngle(), q.getLayoutX(), q.getLayoutY());

        return points;
    }

    // The coordinates of the point after rotation
    public Point coordinatesPointAfterRotation(int xBefore, int yBefore, double angle, double xO, double yO) {
        double t = 2.0 * Math.PI / 360;
        int xAfter = (int) ((xBefore * Math.cos(angle * t) + yBefore * Math.sin(angle * t) + xO) * kScale);
        int yAfter = (int) ((-xBefore * Math.sin(angle * t) + yBefore * Math.cos(angle * t) + yO) * kScale);
        return new Point(xAfter, yAfter);
    }

    private void saveMachineIntoRownachineDataSet(Machine m) {
        for (RowMachine r: dataSet.getTabMachines()) if (r.getId()==m.getId()) {
            XmlRW.FieldToField(r,m);
        }
    }
}
