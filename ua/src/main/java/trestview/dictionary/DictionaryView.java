package trestview.dictionary;

import designpatterns.InitializableDS;
import designpatterns.MVC;
import designpatterns.ObservableDS;
import entityProduction.Work;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import persistence.loader.DataSet;
import persistence.loader.XmlRW;
import trestview.hboxpane.HboxpaneController;
import trestview.hboxpane.HboxpaneModel;
import trestview.hboxpane.HboxpaneView;
import trestview.table.TableController;
import trestview.table.TableViewP;
import trestview.table.tablemodel.TableModel;
import trestview.table.tablemodel.TableMolelBuilder;

import javax.swing.*;
import java.io.IOException;
import java.util.*;

/**
 * Created by pom on 07.02.2016.
 */
public class  DictionaryView extends Dialog implements Observer {

    private ObservableDS dictionaryModel;
    private DataSet dataSet;



    public DictionaryView() {
    }

    public DictionaryView (ObservableDS dictionaryModel, InitializableDS dictionaryController ) {
        this.dictionaryModel =dictionaryModel;
        this.dataSet =dictionaryModel.getDataSet();
        FXMLLoader fxmlLoader = XmlRW.fxmlLoad(this,dictionaryController, "trestview\\dictionary\\dictionaryView.fxml","ui", "");

        setTitle(fxmlLoader.getResources().getString("Dictionary")+":  "+fxmlLoader.getResources().getString(((DictionaryModel)dictionaryModel).gettClass().getSimpleName()));
        setGraphic( new ImageView(new Image("file:pde\\src\\main\\resources\\images\\icons\\"+((DictionaryModel)dictionaryModel).gettClass().getSimpleName()+".png")));

        setHeaderText(fxmlLoader.getResources().getString("HeaderText"));
        setResizable(true);             //  Defines whether the Stage is resizable or not by the user.
        setHeight(700);

        MVC tableMVC  = new MVC (TableModel.class, TableController.class, TableViewP.class, dictionaryModel, dictionaryModel.getRule() );
        MVC hboxpaneMVC = new MVC (HboxpaneModel.class,HboxpaneController.class,HboxpaneView.class,dictionaryModel, dictionaryModel.getRule());
        hboxpaneMVC.addObserverP( (TableModel)tableMVC.getModel());

        VBox vbox = new VBox();
        vbox.getChildren().addAll((HboxpaneView)hboxpaneMVC.getView(),(TableViewP)tableMVC.getView());

        vbox.setSpacing(5);   // The amount of vertical space between each child in the vbox.
        vbox.setPadding(new Insets(10, 0, 0, 10));   // The top,right,bottom,left padding around the region's content. This space will be included in the calculation of the region's minimum and preferred sizes. By default padding is Insets.EMPTY and cannot be set to null.

        getDialogPane().setContent( vbox);
      //  getDialogPane().setContent(tableView);

        ButtonType searchButtonType = new ButtonType("Search", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(searchButtonType,ButtonType.CANCEL);        // Create the layout for the controls.

        Optional<Pair<String, Boolean>> result = showAndWait();        // Display dialog box and wait for user response.

        // If the user closed the dialog box via the search button, output the
        // chosen search text and case-sensitive search status.



    }

    @Override
    public void update(Observable o, Object arg) {
        dictionaryModel = (DictionaryModel)o;
    }
}
