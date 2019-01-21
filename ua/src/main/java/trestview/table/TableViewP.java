package trestview.table;

import com.sun.org.apache.xpath.internal.SourceTree;
import designpatterns.ColumnDate;
import designpatterns.ColumnModelmachine;
import designpatterns.InitializableDS;
import designpatterns.ObservableDS;
import entityProduction.*;
import entityProduction.Line;
import entityProduction.Lineroute;
import entityProduction.Linespec;
import entityProduction.Machine;
import entityProduction.Modelmachine;
import entityProduction.Order;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import persistence.loader.DataSet;
import persistence.loader.XmlRW;
import persistence.loader.tabDataSet.*;
//import resources.images.icons.IconT;
//import resources.images.works.WorkT;
import trestview.hboxpane.MethodCall;
import trestview.table.tablemodel.TableModel;
import trestview.table.tablemodel.abstracttablemodel.ParametersColumn;
import trestview.table.tablemodel.abstracttablemodel.Rule;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class TableViewP<cL> extends TableView<cL> implements Observer {

    private TableModel tableModel;
    private Property<TableModel> tableModelProperty;
    private ObservableList data;
    private ObservableList dataComboboxModelMachne;
    private int selectIndex = -1;
    private MethodCall methodCall;
    private ArrayList<cL> tab;
    private Class tClass;
    private DataSet dataSet;
    private cL selectRow;
    private boolean wasSelectedRow = false;

    public TableViewP() {}

    public TableViewP(ObservableDS observableDS, InitializableDS tableController) {

        this.tableModel = (TableModel) observableDS;
        this.tab= tableModel.getTab();
        this.tClass=tableModel.gettClass();
        this.dataSet = tableModel.getDataSet();
        this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        this.setTableMenuButtonVisible(true);
        this.setEditable(true);
        if (tClass == RowFunctiondist.class) this.setEditable(false);
        XmlRW.fxmlLoad(this,tableController, "trestview\\table\\TableView.fxml","ui", "");

        this.tableModel.getParametersOfColumns().stream().map(p-> getTableColumnP((ParametersColumn)p)).count();  // for ( ParametersColumn p: (ArrayList<ParametersColumn>) this.tableModel.getParametersOfColumns() )  getTableColumnP(p);

        isEditable();

        setPrefHeight(800);
        repaintTable();
        getSelectionModel().selectedIndexProperty().addListener(new RowSelectChangeListener());

    }


    private TableColumn<cL, ?> getTableColumnP(ParametersColumn parametersColumn) {

        TableColumn<cL,?> tableCol = new TableColumn<>();

        if (parametersColumn.getcLs()==Date.class) {
            TableColumn<cL,Date> tableColumn = new TableColumn  (parametersColumn.getName());

            // for the table of Order
            setDateColumn(parametersColumn, tableColumn,"dateBegin",tClass);
            setDateColumn(parametersColumn, tableColumn,"dateEnd",tClass);

            tableCol=tableColumn;
        }


        if (parametersColumn.getcLs()==String.class) {
            TableColumn<cL,String> tableColumn = new TableColumn  (parametersColumn.getName());
            setStringColumn(parametersColumn, tableColumn,"name",tClass);
            setStringColumn(parametersColumn, tableColumn,"scheme",tClass);

            // for the table of Lineroute
            setStringColumn(parametersColumn, tableColumn,"nameOperation",tClass);
            setStringColumn(parametersColumn, tableColumn,"nameMachine",tClass);

            setStringColumn(parametersColumn, tableColumn,"description",tClass);
            setStringColumn(parametersColumn, tableColumn,"pathData",tClass);
            setStringColumn(parametersColumn, tableColumn,"modelmachine",tClass);
            setStringColumn(parametersColumn, tableColumn,"unitName",tClass);

            // for the table of Linespec
            setStringColumn(parametersColumn, tableColumn,"resourceName",tClass);
            setStringColumn(parametersColumn, tableColumn,"functionOEMName",tClass);
            // for the table of Line
            setStringColumn(parametersColumn, tableColumn,"subject_labourName",tClass);



            tableCol=tableColumn;
        }

        if (parametersColumn.getcLs()==int.class) {
            TableColumn<cL,Integer> tableColumn = new TableColumn  (parametersColumn.getName());
            setIntegerColumn(parametersColumn, tableColumn,"id",tClass);

            // for the table of Lineroute
            setIntegerColumn(parametersColumn, tableColumn,"numberWork",tClass);
            setIntegerColumn(parametersColumn, tableColumn,"inputBufferMin",tClass);
            setIntegerColumn(parametersColumn, tableColumn,"inputBuffer",tClass);
            setIntegerColumn(parametersColumn, tableColumn,"inputBufferMax",tClass);
            setIntegerColumn(parametersColumn, tableColumn,"outputBufferMin",tClass);
            setIntegerColumn(parametersColumn, tableColumn,"outputBuffer",tClass);
            setIntegerColumn(parametersColumn, tableColumn,"outputBufferMax",tClass);

            tableCol=tableColumn;
        }
        if (parametersColumn.getcLs()==double.class) {
            TableColumn<cL,Double> tableColumn = new TableColumn  (parametersColumn.getName());
            setDoubleColumn(parametersColumn, tableColumn,"overallSize",tClass);
            setDoubleColumn(parametersColumn, tableColumn,"scaleEquipment",tClass);
            setDoubleColumn(parametersColumn, tableColumn,"locationX",tClass);
            setDoubleColumn(parametersColumn, tableColumn,"locationY",tClass);
            setDoubleColumn(parametersColumn, tableColumn,"angle",tClass);
            setDoubleColumn(parametersColumn, tableColumn,"state",tClass);
            setDoubleColumn(parametersColumn, tableColumn,"averageValue",tClass);
            setDoubleColumn(parametersColumn, tableColumn,"meanSquareDeviation",tClass);
            setDoubleColumn(parametersColumn, tableColumn,"price",tClass);
            // for the table of Linespec
            setDoubleColumn(parametersColumn, tableColumn,"m",tClass);
            setDoubleColumn(parametersColumn, tableColumn,"sigma",tClass);
            // for the table of Line
            setDoubleColumn(parametersColumn, tableColumn,"quantity",tClass);
            tableCol=tableColumn;
        }
        if (parametersColumn.getcLs()==Image.class) {
            if (tableModel.getRule() == Rule.Work) {
                TableColumn<cL,String> tableColumn = new TableColumn  (parametersColumn.getName());
                setImageColumn(parametersColumn, tableColumn,"image",tClass);
                tableCol=tableColumn;
            }
            if (tableModel.getRule() == Rule.Machine) {
                TableColumn<cL,RowModelmachine> tableColumn = new TableColumn  (parametersColumn.getName());
                setImageColumnForMachne(parametersColumn, tableColumn,"image",tClass);
                tableCol=tableColumn;
            }

        }

        if (parametersColumn.getcLs()==Modelmachine.class) {
            TableColumn<cL,RowModelmachine> tableColumn = new TableColumn  (parametersColumn.getName());
            setComboBoxColumn(parametersColumn, tableColumn,"modelmachine",tClass);
            tableCol=tableColumn;
        }


        getColumns().addAll(tableCol);
        tableCol.setMinWidth(parametersColumn.getWidth());
        tableCol.setPrefWidth(parametersColumn.getWidth());
        tableCol.setEditable(parametersColumn.isEditable());

        return tableCol;
    }



    private void setStringColumn(ParametersColumn parametersColumn, TableColumn<cL, String> tableColumn,String fielgName, Class tclass ) {

        if(parametersColumn.getFielgName().equals(fielgName)) {

            tableColumn.setCellValueFactory(new PropertyValueFactory(fielgName));

            tableColumn.setCellFactory(TextFieldTableCell.<cL>forTableColumn());

            tableColumn.setOnEditCommit(  (TableColumn.CellEditEvent<cL, String> t) -> {

                //if(tclass==RowWork.class)
                if(fielgName=="name") ((RowIdNameDescription) t.getTableView().getItems().get( t.getTablePosition().getRow()) ).setName(t.getNewValue());

                if(fielgName=="modelmachine") ((RowIdNameDescription) t.getTableView().getItems().get( t.getTablePosition().getRow()) ).setName(t.getNewValue());

                //if(tclass==Lineroute.class)
                if(fielgName=="nameOperation") ((Lineroute) t.getTableView().getItems().get( t.getTablePosition().getRow()) ).setNameOperation(t.getNewValue());
                if(fielgName=="nameMachine") ((Lineroute) t.getTableView().getItems().get( t.getTablePosition().getRow()) ).setNameMachine(t.getNewValue());

                //if(tclass==Linespec.class)
                if(fielgName=="resourceName") ((Linespec) t.getTableView().getItems().get( t.getTablePosition().getRow()) ).setResourceName(t.getNewValue());
                if(fielgName=="functionOEMName") ((Linespec) t.getTableView().getItems().get( t.getTablePosition().getRow()) ).setFunctionOEMName(t.getNewValue());
                if(fielgName==" unitName") ((Linespec) t.getTableView().getItems().get( t.getTablePosition().getRow()) ).setUnitName(t.getNewValue());
                if(fielgName=="subject_labourName") ((Line) t.getTableView().getItems().get( t.getTablePosition().getRow()) ).setSubject_labourName(t.getNewValue());


                if(fielgName=="scheme" || fielgName=="pathData" ) {
                    File f = new File(t.getNewValue());
                    String schemePath = "Image\\Manufacturing";
                    if (!f.exists()) {
                        FileChooser chooser = new FileChooser();
                        chooser.setInitialDirectory(new File (schemePath));
                        chooser.getExtensionFilters().addAll(
                                new FileChooser.ExtensionFilter("All Images", "*.*"),
                                new FileChooser.ExtensionFilter("JPG", "*.jpg"), new FileChooser.ExtensionFilter("PNG", "*.png"),
                                new FileChooser.ExtensionFilter("BMP", "*.bmp"), new FileChooser.ExtensionFilter("GIF", "*.gif")
                        );
                        chooser.setTitle( ResourceBundle.getBundle("ui").getString("nameFileScheme"));
                        Stage st = new Stage();
                        st.setMaxWidth(400);
                        st.setMaxHeight(400);
                        f = chooser.showOpenDialog(st);
                        if (f!=null) {
                            try {
                                Files.copy(f.toPath(), new File(schemePath+"\\m"+f.getName()).toPath());

                                if(new File(schemePath+"\\"+f.getName()).delete()){
                                }else System.out.println("Файл file.txt не был найден в корневой папке проекта");
                                Files.copy(new File(schemePath+"\\m"+f.getName()).toPath(), new File(schemePath+"\\"+f.getName()).toPath());
                                if(new File(schemePath+"\\m"+f.getName()).delete()){
                                }else System.out.println("Файл file.txt не был найден в корневой папке проекта");

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if (fielgName== "scheme")        ((RowWork) t.getTableView().getItems().get(t.getTablePosition().getRow())).setScheme(schemePath+"\\"+f.getName());
                            if (fielgName== "pathData") ((Functiondist) t.getTableView().getItems().get(t.getTablePosition().getRow())).setPathData (schemePath+"\\"+f.getName());
                            repaintTable();
                        }
                        else {
                            if (fielgName== "scheme") ((RowWork) t.getTableView().getItems().get(t.getTablePosition().getRow())).setScheme(t.getOldValue());
                            if (fielgName== "pathData") ((Functiondist) t.getTableView().getItems().get(t.getTablePosition().getRow())).setPathData (t.getOldValue());
                        }
                    }
                }
                if(fielgName=="description") { ((RowIdNameDescription) t.getTableView().getItems().get( t.getTablePosition().getRow()) ).setDescription(t.getNewValue());
                    ((TableCell)t.getTableView().getItems().get( t.getTablePosition().getRow())).setTooltip(new Tooltip(t.getNewValue()));}

            });
        }
    }
    private void setIntegerColumn(ParametersColumn parametersColumn, TableColumn<cL, Integer> tableColumn,String fielgName, Class tclass ) {
        if(parametersColumn.getFielgName().equals(fielgName)) {
            tableColumn.setCellValueFactory(new PropertyValueFactory(fielgName));
            tableColumn.setCellFactory(TextFieldTableCell.<cL, Integer>forTableColumn(new IntegerStringConverter()));
            tableColumn.setOnEditCommit(  (TableColumn.CellEditEvent<cL, Integer> t) -> {
                if(fielgName=="id")   ((RowIdNameDescription) t.getTableView().getItems().get( t.getTablePosition().getRow()) ).setId(t.getNewValue());
                if(fielgName=="numberWork")   ((Lineroute) t.getTableView().getItems().get( t.getTablePosition().getRow()) ).setNumberWork(t.getNewValue());
                if(fielgName=="inputBufferMin")   ((Lineroute) t.getTableView().getItems().get( t.getTablePosition().getRow()) ).setInputBufferMin(t.getNewValue());
                if(fielgName=="inputBuffer")      ((Lineroute) t.getTableView().getItems().get( t.getTablePosition().getRow()) ).setInputBuffer(t.getNewValue());
                if(fielgName=="inputBufferMax")   ((Lineroute) t.getTableView().getItems().get( t.getTablePosition().getRow()) ).setInputBufferMax(t.getNewValue());
                if(fielgName=="outputBufferMin")   ((Lineroute) t.getTableView().getItems().get( t.getTablePosition().getRow()) ).setOutputBufferMin(t.getNewValue());
                if(fielgName=="outputBuffer")      ((Lineroute) t.getTableView().getItems().get( t.getTablePosition().getRow()) ).setOutputBuffer(t.getNewValue());
                if(fielgName=="outputBufferMax")   ((Lineroute) t.getTableView().getItems().get( t.getTablePosition().getRow()) ).setOutputBufferMax(t.getNewValue());
            });
        }
    }
    private void setDateColumn(ParametersColumn parametersColumn, TableColumn<cL, Date> tableColumn,String fielgName, Class tclass ) {
        if(parametersColumn.getFielgName().equals(fielgName)) {
            tableColumn.setCellValueFactory(new PropertyValueFactory(fielgName));
            tableColumn.setCellFactory(TextFieldTableCell.<cL, Date>forTableColumn(new DateStringConverter()));
            tableColumn.setOnEditCommit(  (TableColumn.CellEditEvent<cL, Date> t) -> {
                if(fielgName=="dateBegin")  ((ColumnDate) t.getTableView().getItems().get(t.getTablePosition().getRow())).setDateBegin(t.getNewValue());
                if(fielgName=="dateEnd") ((ColumnDate)  t.getTableView().getItems().get( t.getTablePosition().getRow()) ).setDateEnd(t.getNewValue());
            });
        }
    }
    private void setDoubleColumn(ParametersColumn parametersColumn, TableColumn<cL, Double> tableColumn,String fielgName, Class tclass ) {
        if(parametersColumn.getFielgName().equals(fielgName)) {
            tableColumn.setCellValueFactory(new PropertyValueFactory(fielgName));
            tableColumn.setCellFactory(TextFieldTableCell.<cL, Double>forTableColumn(new DoubleStringConverter()));
            tableColumn.setOnEditCommit(  (TableColumn.CellEditEvent<cL, Double> t) -> {
            if(fielgName=="overallSize")   ((RowWork) t.getTableView().getItems().get( t.getTablePosition().getRow()) ).setOverallSize(t.getNewValue());
            if(fielgName=="scaleEquipment")   ((RowWork) t.getTableView().getItems().get( t.getTablePosition().getRow()) ).setScaleEquipment(t.getNewValue());
            if(fielgName=="locationX")      ((RowMachine) t.getTableView().getItems().get( t.getTablePosition().getRow()) ).setLocationX (t.getNewValue());
            if(fielgName=="locationY")      ((RowMachine) t.getTableView().getItems().get( t.getTablePosition().getRow()) ).setLocationY (t.getNewValue());
            if(fielgName=="angle")      ((RowMachine) t.getTableView().getItems().get( t.getTablePosition().getRow()) ).setAngle (t.getNewValue());
            if(fielgName=="state")      ((RowMachine) t.getTableView().getItems().get( t.getTablePosition().getRow()) ).setState (t.getNewValue());
            if(fielgName=="averageValue")      ((Functiondist) t.getTableView().getItems().get( t.getTablePosition().getRow()) ).setAverageValue (t.getNewValue());
            if(fielgName=="meanSquareDeviation")      ((Functiondist) t.getTableView().getItems().get( t.getTablePosition().getRow()) ).setMeanSquareDeviation(t.getNewValue());
            //if(tclass==Linespec.class)
            if(fielgName=="m")      ((Linespec) t.getTableView().getItems().get( t.getTablePosition().getRow()) ).setM(t.getNewValue());
            if(fielgName=="sigma")      ((Linespec) t.getTableView().getItems().get( t.getTablePosition().getRow()) ).setSigma(t.getNewValue());
            if(fielgName=="quantity")      ((Line) t.getTableView().getItems().get( t.getTablePosition().getRow()) ).setQuantity(t.getNewValue());

            });
        }
    }
    private void setComboBoxColumn(ParametersColumn parametersColumn, TableColumn<cL, RowModelmachine> tableColumn, String fielgName, Class tclass ) {
        if(parametersColumn.getFielgName().equals(fielgName)) {
            Callback<TableColumn<cL, RowModelmachine>, TableCell<cL, RowModelmachine>> comboBoxCellFactory = param -> new ComboBoxEditingCell();



            if(fielgName=="modelmachine") {
                tableColumn.setCellValueFactory(p -> new SimpleObjectProperty<RowModelmachine>(  DataSet.getById(
                        ((Machine) p.getValue()).getModelmachine().getId(),
                        dataSet.getTabModelmachines())));
                tableColumn.setCellFactory(comboBoxCellFactory);
                tableColumn.setOnEditCommit((TableColumn.CellEditEvent<cL, RowModelmachine> t) -> {
                    if (fielgName == "modelmachine") {
                        RowModelmachine rowModelmachine = t.getNewValue();
                        Modelmachine modelmachine = new Modelmachine(rowModelmachine.getId(), rowModelmachine.getName(),
                                rowModelmachine.getImg(),
                                dataSet.select(rowModelmachine, dataSet.getTabMachines(), dataSet.getTabModelmachineMachines()),   //  ArrayList<Machine> machines
                                rowModelmachine.getOverallDimensionX(), rowModelmachine.getOverallDimensionY(),
                                rowModelmachine.getWorkSizeX(), rowModelmachine.getWorkSizeY(), rowModelmachine.getDescription());
                        for (int i = 0; i < tab.size(); i++)
                            if (((Machine) tab.get(i)).getModelmachine().getId() == t.getOldValue().getId())
                                if (((Machine) selectRow).getId() ==  ((Machine) tab.get(i)).getId())
                                    ((Machine) tab.get(i)).setModelmachine(modelmachine);
                    }
                });
            }
        }
    }
    private void setImageColumnForMachne(ParametersColumn parametersColumn, TableColumn<cL, RowModelmachine> tableColumn, String fielgName, Class tclass) {

        if (tableModel.getRule() == Rule.Machine) {
            System.out.println("if (tableModel.getRule() == Rule.Machine)");
            Callback<TableColumn<cL, RowModelmachine>, TableCell<cL, RowModelmachine>> imageMachineCellFactory = param -> new ImageMachineEditingCell();
            tableColumn.setCellFactory(imageMachineCellFactory);
            tableColumn.setCellValueFactory(
                    p -> new SimpleObjectProperty<RowModelmachine>(DataSet.getById(
                            ((Machine) p.getValue()).getModelmachine().getId(),
                            dataSet.getTabModelmachines()))
            );
        }

    }
    private void setImageColumn(ParametersColumn parametersColumn, TableColumn<cL, String> tableColumn, String fielgName, Class tclass ) {
        if(parametersColumn.getFielgName().equals(fielgName)) {
            // if (tableModel.getRule() == Rule.Work)
            tableColumn.setCellValueFactory(new PropertyValueFactory("scheme"));

            tableColumn.setCellFactory( new Callback<TableColumn<cL, String>,TableCell<cL, String>>(){
                @Override
                public TableCell<cL, String> call(TableColumn<cL, String> param) {
                    TableCell<cL, String> cell = new TableCell<cL, String>(){
                        @Override
                        public void updateItem(String item, boolean empty) {
                            if(item!=null){
                                HBox box= new HBox();
                                box.setSpacing(10) ;
                                ScrollPane sp = new ScrollPane();
                                sp.setPrefSize(50,50);
                                ImageView imageview = new ImageView();
                                imageview.setFitHeight(100);    imageview.setFitWidth(100);
                                sp.setPannable(false);
                                imageview.setImage(new Image("file:"+item ));
                                setTooltip(new Tooltip(item));
                                box.getChildren().addAll(imageview);
                                sp.setContent(imageview);
                                setGraphic(sp);
                            }
                        }
                    };
                    return cell;
                }
            });
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (     (!( ((TableModel)o).getMethodCall() == MethodCall.selectRowTable))    ) {
            this.getSelectionModel().getSelectedIndex();
            updateTableModel((TableModel) o);
            this.requestFocus();
            this.getSelectionModel().select(selectIndex);
            this.getFocusModel().focus(selectIndex);
        }

        if (     (( ((TableModel)o).getMethodCall() == MethodCall.selectRowTable)) && wasSelectedRow==false) {

            switch (((TableModel)o).getRule()) {
                case Machine:
                case Subject_labour:
                case Route:
                case Lineroute:
                case Linespec:
                case Order:
                case Line:
              //      this.getSelectionModel().getSelectedIndex();
                    updateTableModel((TableModel) o);
              //      this.requestFocus();
              //      this.getSelectionModel().select(selectIndex);
              //      this.getFocusModel().focus(selectIndex);
                    break;
            }
        }

        if(wasSelectedRow==true) wasSelectedRow=false;

//        if (     (( ((TableModel)o).getMethodCall() == MethodCall.selectRowTable))&& tClass== Machine.class) {
//            this.getSelectionModel().getSelectedIndex();
//            updateTableModel((TableModel) o);
//            this.requestFocus();
//            this.getSelectionModel().select(selectIndex);
//            this.getFocusModel().focus(selectIndex);
//        }
    }
    // Repaints table after the data changes
    public void repaintTable() {
        getItems().clear();
        data = FXCollections.observableArrayList();
        for (Object row : this.tab) data.add(row);
        setEditable(true);
        setItems(data);


        if (tableModel.getRule()== Rule.Machine) {
            dataComboboxModelMachne = FXCollections.observableArrayList();
            for (Object row : dataSet.getTabModelmachines()) dataComboboxModelMachne.add(row);
        }

    }

    private void updateTableModel(TableModel o) {

        switch (o.getMethodCall()) {

            case selectRowTable:
             //   if(selectIndex<0 ) selectIndex =0;
                this.tab= tableModel.getTab();
                repaintTable();
                break;
            case addRowTable:
               if(selectIndex<data.size()) selectIndex++;
               data.add(selectIndex,((TableModel)o).getSelectRow() );
                break;
            case saveRowTable:
                tableModel.getDataSet().saveDataset(); // Save new path add read new database  from new directory path
                break;
            case editRowTable:
                this.setEditable(true);
                break;
            case delRowTable:
                if(selectIndex<0 ) selectIndex =0;
                if (!data.isEmpty()) data.remove(selectIndex);
                //if (getItems().size() == 0) {   return;   }
                break;
            default:
        }
    }

    private class RowSelectChangeListener implements ChangeListener<Number> {
        @Override
        public void changed(ObservableValue<? extends Number> ov, Number oldVal, Number newVal) {
            wasSelectedRow = true;
            selectIndex = newVal.intValue();
            if ((selectIndex < 0) || (selectIndex >= data.size())) {
                return;
            }

            setEditable(true);
            selectRow = (cL)data.get(selectIndex);
            tableModel.setSelectRow(selectRow );
            tableModel.selectRowForTwoTableModel();
            return;
        }
    }


    class ComboBoxEditingCell extends TableCell<cL, RowModelmachine> {
//        ImageView imageview = new ImageView();
//        imageview.setFitHeight(100);    imageview.setFitWidth(100);
//        sp.setPannable(false);
//        imageview.setImage(new Image("file:"+item ));
        private ComboBox<RowModelmachine> comboBox;

        private RowModelmachine r;

        private ComboBoxEditingCell() {
           if( selectIndex>=0 )
            r  = DataSet.getById(((Machine) data.get(selectIndex)).getModelmachine().getId(), dataSet.getTabModelmachines());
        }

        @Override
        public void startEdit() {
            if (!isEmpty()) {
                super.startEdit();
                createComboBox();
                setText(null);
                setGraphic(comboBox);
            }
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();

             setText(r.getName());
            setGraphic(null);
        }

        @Override
        public void updateItem(RowModelmachine item, boolean empty) {
            super.updateItem(item, empty);

            if (item == null || empty) {
                setText(null);
                setGraphic(null);
            } else {
                setText(item.getName());             //-----------------------------------------------------------------
                setTextFill(Color.CHOCOLATE);
     //           setStyle("-fx-background-color: yellow");
                if (isEditing()) {
                    if (comboBox != null) {
                       comboBox.setValue(r);
                    }
                    setText(r.getName());



//                    updateTableModel(tableModel);
//                    ImageView imageview = new ImageView();
//                    imageview.setFitHeight(100);    imageview.setFitWidth(100);
//
//                    imageview.setImage(new Image("file:"+item ));
//                    comboBox.setItems((ObservableList<RowModelmachine>) imageview);
//
//                    setGraphic(comboBox);

                } else {
                  if (r!=null)   {

                      if (selectIndex<0) selectIndex =0;
                      System.out.println("isEditing: r.getName()= " + r.getName());
            //            System.out.println("isEditing:comboBox.getSelectionModel().getSelectedItem()= " + comboBox.getSelectionModel().getSelectedItem());
                      System.out.println("selectIndex="+selectIndex);


                      r  = DataSet.getById(((Machine) data.get(selectIndex)).getModelmachine().getId(), dataSet.getTabModelmachines());

                      for ( int i=0; i< dataSet.getTabModelmachineMachines().size(); i++ ) {
                          RowIdId2 row = dataSet.getTabModelmachineMachines().get(i);
                          if ( row.getId2() == ((Machine) data.get(selectIndex)).getId()) dataSet.getTabModelmachineMachines().get(i).setId(item.getId());
                      }
                      setText(item.getName());
                      updateTableModel(tableModel);
                  }
                    setGraphic(null);
                }

            }
        }

        private void createComboBox() {

            comboBox = new ComboBox(dataComboboxModelMachne);

                        comboBoxConverter(comboBox);
            r  = DataSet.getById(((Machine) data.get(selectIndex)).getModelmachine().getId(), dataSet.getTabModelmachines());

            comboBox.valueProperty().set( r ); // comboBox.valueProperty().set(getTyp());
            comboBox.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            comboBox.setOnAction((e) -> {
                System.out.println("Committed: " + comboBox.getSelectionModel().getSelectedItem());
                r  = DataSet.getById((comboBox.getSelectionModel().getSelectedItem()).getId(), dataSet.getTabModelmachines());
                commitEdit(comboBox.getSelectionModel().getSelectedItem());
            });

        }

        private void comboBoxConverter(ComboBox<RowModelmachine> comboBox) {
            // Define rendering of the list of values in ComboBox drop down.
            comboBox.setCellFactory((c) -> {
                return new ListCell<RowModelmachine>() {
                    @Override
                    protected void updateItem(RowModelmachine item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                        } else {
                            setText(item.getName());
                        }
                    }
                };
            });
        }
}

    class ImageMachineEditingCell extends TableCell<cL, RowModelmachine> {

        private ComboBox<RowModelmachine> comboBox;

        private RowModelmachine r;

        private ImageMachineEditingCell() {
            if( selectIndex>=0 )
                r  = DataSet.getById(((Machine) data.get(selectIndex)).getModelmachine().getId(), dataSet.getTabModelmachines());
        }

        @Override
        public void updateItem(RowModelmachine item, boolean empty) {
            super.updateItem(item, empty);
            if(item!=null){
                double heigth =50.0;
                double Spacing = 10.0;
                HBox box= new HBox();
                box.setSpacing(Spacing) ;
                ScrollPane sp = new ScrollPane();
                sp.setPrefSize(heigth,heigth);
                Image image = new Image("file:"+item.getImg() );
                ImageView imageview = new ImageView();
                imageview.setImage(image);
                double k = image.getWidth()/image.getHeight();
                imageview.setFitHeight(heigth-Spacing/2);     imageview.setFitWidth(k*(heigth-Spacing/2));
                sp.setPannable(false);
                box.getChildren().addAll(imageview);
                sp.setContent(imageview);
                setGraphic(sp);
            }
            else {
        //     setStyle("-fx-background-color: yellow");
                    setGraphic(null);
            }
        }
    }

}


