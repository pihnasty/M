package trestview.tasks.conveyorPDE;


import designpatterns.ObservableDS;
import trestview.linechart.LineChartInterface;
import trestview.table.tablemodel.abstracttablemodel.Rule;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import java.util.ResourceBundle;
import java.util.function.DoubleFunction;


public class VСonConveyorPdeModel extends ObservableDS implements LineChartInterface {

    private String titleOx;
    private String titleOy;
    private String titleGraph;

    private double xMin;
    private double xMax;

    private double xTickUnit;
    private double yMin;
    private double yMax;
    private double yTickUnit;


    private List<Point2D.Double> listConT;
    private List<Point2D.Double> listConS;
    private List<Point2D.Double> list;

    private List<List<Point2D.Double>> pullListConT;
    private List<List<Point2D.Double>> pullListConS;
    private List<List<Point2D.Double>> pullList;

    private List<String> listConTLegend;
    private List<String> listConSLegend;
    private List<String> listLegend;

    private double tMax = 2.0;
    private double tMin = 0.0;
    private double sMax =1.0;


    public enum Conditions {v1, v2,v3};

    public List<Point2D.Double> getListConT() {
        return listConT;
    }

    public void setListConT(List<Point2D.Double> listConT) {
        this.listConT = listConT;
    }

    public List<Point2D.Double> getListConS() {
        return listConS;
    }

    public void setListConS(List<Point2D.Double> listConS) {
        this.listConS = listConS;
    }


    public VСonConveyorPdeModel(ObservableDS o, Rule rule) {
        super(o,rule);
        double _s0 = 0.2;
        double _t0 = 0.0;



        DoubleFunction<Double> sinConveyorPdeModelS     = _r -> (2 + Math.sin(2 * Math.PI * _r))/3.0;
        DoubleFunction<Double> sinConveyorPdeModelT     = _r -> (2 + Math.sin(2 * Math.PI *(0.5- _r)))/3.0;
        DoubleFunction<Double> pow2BoundaryCondition  = _r -> { double ftMax = 1.0/3.0;   return  _r*0.5+0.25;   };

        DoubleFunction<Double>  conditionForS = null;
        DoubleFunction<Double>  conditionForT = null;


        Conditions v = Conditions.v2;

        switch (v) {
            case v1:  _s0 = 0.5;  _t0 =  0.0;  tMax =1.0;  conditionForS = sinConveyorPdeModelS; conditionForT=sinConveyorPdeModelT;   break;


            case v2:  _s0 = 0.2;  _t0 = 0.2;   tMin =0.0;  tMax =1.0;  conditionForS = sinConveyorPdeModelS; conditionForT=pow2BoundaryCondition;   break;


            case v3:  _s0 = 0.5;  _t0 = 0.0;   tMin =-0.5;  tMax =0.5;  conditionForS = sinConveyorPdeModelS; conditionForT=sinConveyorPdeModelT;   break;

            default:                                            break;
        }





                //  (_r*_r)*ftMax;// 0.3;
        //    2) (_r*_r)*ftMax;        1)    (10*Math.pow(_r, 2)/100)*ftMax;}

        pullListConS = new ArrayList<>();
        pullListConT = new ArrayList<>();

        listConSLegend = new ArrayList<>();
        listConTLegend = new ArrayList<>();


        for (double _s=0; _s<1.0;  _s+=0.1)   {
            pullListConS.add(xi0ForConstS(_s, _s0, _t0, conditionForS, conditionForT));
            listConSLegend.add("S/Sd="+ String.format("%3.1f",_s));

        }
        for ( double _t=tMin; _t<tMax; _t+=0.1)   {
            pullListConT.add(xi0ForConstT(_t, _s0, _t0, conditionForS, conditionForT));
            listConTLegend.add("t/Td="+ String.format("%3.1f",_t));
        }


    }


    public VСonConveyorPdeModel dataBuildVСonConveyorPdeModel(String s) {
        VСonConveyorPdeModel vСonConveyorPdeModel =
                new VСonConveyorPdeModel(this, null).setTitleGraph(ResourceBundle.getBundle("ui").getString("productionLine"))
                                                    .setxMin(0.0).setyMin(0.0).setyMax(2.0).setyTickUnit(0.1);
        if(s=="oX=S") {
            vСonConveyorPdeModel        .setTitleX(ResourceBundle.getBundle("ui").getString("titleOxS"))
                                        .setxMax(sMax).setxTickUnit(sMax/10.0)
                                        .setTitleY(ResourceBundle.getBundle("ui").getString("titleOyS"))
                                        .setList(getListConT())
                                        .setPullList(pullListConT).setListLegend(listConTLegend);
        }
        if (s=="oX=T"){
            vСonConveyorPdeModel        .setTitleX(ResourceBundle.getBundle("ui").getString("titleOxT"))
                                        .setxMin(tMin).setxMax(tMax).setxTickUnit( (tMax-tMin)/10.0)
                                        .setTitleY(ResourceBundle.getBundle("ui").getString("titleOyT"))
                                        .setList(getListConS())
                                        .setPullList(pullListConS).setListLegend(listConSLegend);
        }

        if(s.length()>4) {
            if (s.substring(0, 9).equals("oX=Salone")) {
                List<List<Point2D.Double>> pullListAlone = new ArrayList<>();
                List<String> listLegendAlone = new ArrayList<>();

                int i = Integer.parseInt(s.substring(10, 11));
                pullListAlone.add(pullListConT.get(i));
                listLegendAlone.add(listConTLegend.get(i));

                vСonConveyorPdeModel.setTitleGraph("")
                        .setTitleX((i+1)+")   "+listConTLegend.get(i))
                        .setxMax(sMax).setxTickUnit(sMax/10.0)

                        .setPullList(pullListAlone).setListLegend(listLegendAlone);
            }
            if (s.substring(0, 9).equals("oX=Talone")) {
                List<List<Point2D.Double>> pullListAlone = new ArrayList<>();
                List<String> listLegendAlone = new ArrayList<>();

                int i = Integer.parseInt(s.substring(10, 11));
                pullListAlone.add(pullListConS.get(i));
                listLegendAlone.add(listConSLegend.get(i));

                vСonConveyorPdeModel.setTitleGraph("")
                        .setTitleX((i+1)+")   "+listConSLegend.get(i))
                        .setxMin(tMin).setxMax(tMax).setxTickUnit( (tMax-tMin)/10.0)

                        .setPullList(pullListAlone).setListLegend(listLegendAlone);
            }
        }


        return vСonConveyorPdeModel;
    }

    private void change() {

        setChanged();
        notifyObservers();

    }

    private double decision(double _s, double  _t, double  _s0,double  _t0, DoubleFunction<Double> a, DoubleFunction<Double> b) {
        double _r = r(_s,_t,_s0,_t0);

   //     System.out.println("->"+(b.apply(_t0 )-a.apply(_s0) ) * ( h(_s-_s0)*h(_s0-_s) + h(_t-_t0)*h(_t0-_t) )+"          "+_s+"             "+_t);

        return a.apply(  _r +_s0) * (h(_r) + h(_s0-_s))
             + b.apply(_t0 - _r ) * (h(-_r)- h(_s0-_s))
           //  +8*(b.apply(_t0 )-a.apply(_s0) ) * ( h(_s-_s0)*h(_s0-_s) * h(_t-_t0)*h(_t0-_t) )
             +2.0*(b.apply(_t0 )-a.apply(_s0) ) * ( h(_r)*h(-_r)  )
                ;

    }

    private double h(double r) {
        if (r < 0) return 0;
        if (r == 0) return 0.5;
        else return 1.0;
    }

    private double r(double _s, double _t, double _s0, double _t0) {
        return _s - _t - _s0 + _t0 ;
    }

    private List<Point2D.Double> xi0ForConstT(double _t, double _s0, double _t0, DoubleFunction<Double> a, DoubleFunction<Double> b) {
        List<Point2D.Double> doubleList = new ArrayList<>();
        double dS = 0.001;

            for (double _s = 0.0; _s <= 1.0; _s += dS) {
                Point2D.Double p = new Point2D.Double(_s,
                        decision(_s, _t, _s0, _t0, a, b)
                );
                doubleList.add(p);
            }

        return doubleList;
    }


    private List<Point2D.Double> xi0ForConstS(double _s, double _s0, double _t0, DoubleFunction<Double> a,DoubleFunction<Double> b) {
        List<Point2D.Double> doubleList = new ArrayList<>();
        double tD = 10.0;
        double dT = 0.01;
            for (double _t = tMin; _t <= tD; _t += dT) {
                Point2D.Double p = new Point2D.Double(_t,
                                                       decision(_s, _t, _s0, _t0 , a, b)
                                                     );
                doubleList.add(p);
            }
        return doubleList;

    }


    @Override
    public List<String> getListLegend() {
        return listLegend;
    }

    public VСonConveyorPdeModel setListLegend(List<String> listLegend) {
        this.listLegend = listLegend;
        return this;
    }


    @Override
    public String getTitleY() {
        return titleOy;
    }

    public VСonConveyorPdeModel setTitleY(String titleOy) {
        this.titleOy = titleOy;
        return this;
    }
    @Override
    public String getTitleX() {
        return titleOx;
    }

    public VСonConveyorPdeModel setTitleX(String titleOx) {
        this.titleOx = titleOx;
        return this;
    }
    @Override
    public List<Point2D.Double> getList() {
        return list;
    }

    public VСonConveyorPdeModel setList(List<Point2D.Double> list) {
        this.list = list;
        return this;
    }

    @Override
    public List<List<Point2D.Double>> getPullList() {
        return pullList;
    }
    public VСonConveyorPdeModel setPullList(List<List<Point2D.Double>> pullList) {
        this.pullList = pullList;
        return this;
    }

    @Override
    public double getxMin() {
        return xMin;
    }

    public VСonConveyorPdeModel setxMin(double xMin) {
        this.xMin = xMin;
        return this;
    }

    @Override
    public double getxMax() {
        return xMax;
    }

    public VСonConveyorPdeModel setxMax(double xMax) {
        this.xMax = xMax;
        return this;
    }

    @Override
    public double getyMax() {
        return yMax;
    }

    public VСonConveyorPdeModel setyMax(double yMax) {
        this.yMax = yMax;
        return this;
    }

    @Override
    public double getyMin() {
        return yMin;
    }

    public VСonConveyorPdeModel setyMin(double yMin) {
        this.yMin = yMin;
        return this;
    }

    @Override
    public String getTitleGraph() {
        return titleGraph;
    }

    public VСonConveyorPdeModel setTitleGraph(String titleGraph) {
        this.titleGraph = titleGraph;
        return this;
    }

    @Override
    public double getxTickUnit() {
        return xTickUnit;
    }

    public VСonConveyorPdeModel setxTickUnit(double xTickUnit) {
        this.xTickUnit = xTickUnit;
        return this;
    }

    @Override
    public double getyTickUnit() {
        return yTickUnit;
    }

    public VСonConveyorPdeModel setyTickUnit(double yTickUnit) {
        this.yTickUnit = yTickUnit;
        return this;
    }
}