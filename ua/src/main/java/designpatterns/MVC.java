package designpatterns;

import persistence.loader.DataSet;
import trestview.table.tablemodel.abstracttablemodel.Rule;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by pom on 26.03.2016.
 */
public class MVC {

    private Object model;
    private Method methodAddObserver;
    private Object view;
    private Object controller;
//    @Deprecated
//    public   MVC (Class mClass, Class cClass, Class vClass, ArrayList  arrayList, Rule rule)   {
//        try {
//            Constructor mConstructor = mClass.getConstructor(ArrayList.class, Rule.class);
//            model = mConstructor.newInstance(arrayList, rule);
//
//            Constructor cConstructor = cClass.getConstructor( mClass);
//            controller = cConstructor.newInstance(model);
//
//            Constructor vConstructor = vClass.getConstructor( mClass, cClass);
//            view = vConstructor.newInstance(model,controller);
//
//            methodAddObserver = Observable.class.getDeclaredMethod("addObserver",Observer.class );
//            methodAddObserver.invoke(model, view);
//
//        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e)  {      e.printStackTrace();        }
//    }

//    @Deprecated
//    public   MVC (Class mClass, Class cClass, Class vClass, DataSet dataSet, Rule rule )   {
//        try {
//            Constructor mConstructor = mClass.getConstructor(DataSet.class, Rule.class);
//            model = mConstructor.newInstance(dataSet,rule);
//
//            Constructor cConstructor = cClass.getConstructor( mClass);
//            controller = cConstructor.newInstance(model);
//
//            Constructor vConstructor = vClass.getConstructor( mClass, cClass);
//            view = vConstructor.newInstance(model,controller);
//
//            methodAddObserver = Observable.class.getDeclaredMethod("addObserver",Observer.class );
//            methodAddObserver.invoke(model, view);
//
//        } catch (NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalAccessException e)  {      e.printStackTrace();        }
//    }

//    @Deprecated
//    public   MVC (Class mClass, Class cClass, Class vClass, Observable o, Rule rule )   {
//        try {
//            Constructor mConstructor = mClass.getConstructor(Observable.class, Rule.class);
//            model = mConstructor.newInstance(o,rule);
//
//            Constructor cConstructor = cClass.getConstructor( mClass);
//            controller = cConstructor.newInstance(model);
//
//            Constructor vConstructor = vClass.getConstructor( mClass, cClass);
//            view = vConstructor.newInstance(model,controller);
//
//            methodAddObserver = Observable.class.getDeclaredMethod("addObserver",Observer.class );
//            methodAddObserver.invoke(model, view);
//
//        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e)  {      e.printStackTrace();        }
//    }
// Main Constructor
    public   MVC (Class mClass, Class cClass, Class vClass, ObservableDS o, Rule rule )   {
        try {
            Constructor mConstructor = mClass.getConstructor(ObservableDS.class, Rule.class);
            model = mConstructor.newInstance(o,rule);

            Constructor cConstructor = cClass.getConstructor( ObservableDS.class);
            controller = cConstructor.newInstance(model);

            Constructor vConstructor = vClass.getConstructor( ObservableDS.class, InitializableDS.class);
            view = vConstructor.newInstance(model,controller);

            methodAddObserver = Observable.class.getDeclaredMethod("addObserver",Observer.class );
            methodAddObserver.invoke(model, view);

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e)  {      e.printStackTrace();        }
    }


//    @Deprecated
//    public   MVC (Class mClass, Class cClass, Class vClass, Observable o )   {
//        try {
//            Constructor mConstructor = mClass.getConstructor(Observable.class);
//            model = mConstructor.newInstance(o);
//
//            Constructor cConstructor = cClass.getConstructor( mClass);
//            controller = cConstructor.newInstance(model);
//
//            Constructor vConstructor = vClass.getConstructor( mClass, cClass);
//            view = vConstructor.newInstance(model,controller);
//
//            methodAddObserver = Observable.class.getDeclaredMethod("addObserver",Observer.class );
//            methodAddObserver.invoke(model, view);
//
//        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {      e.printStackTrace();    }
//    }


//    @Deprecated
//    public   MVC (Class mClass, Class cClass, Class vClass, DataSet dataSet )   {
//        try {
//            Constructor mConstructor = mClass.getConstructor(DataSet.class);
//            model = mConstructor.newInstance(dataSet);
//
//            Constructor cConstructor = cClass.getConstructor( mClass);
//            controller = cConstructor.newInstance(model);
//
//            Constructor vConstructor = vClass.getConstructor( mClass, cClass);
//            view = vConstructor.newInstance(model,controller);
//
//            methodAddObserver = Observable.class.getDeclaredMethod("addObserver",Observer.class );
//            methodAddObserver.invoke(model, view);
//
//        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
//            e.printStackTrace();
//        }
//    }

    /*
    TMenuModel menuModel = new TMenuModel(this.trestModel);
        TMenuController menuController = new TMenuController(menuModel);
        TMenuView menuView = new TMenuView(menuModel, menuController);
        menuModel.addObserver(menuView);
    */

    public Object getModel() {   return model;   }
    public Object getView()  {   return view;    }
    public Object getController() { return controller;  }

    public MVC addObserverP (Observer observer) {
        try   {   methodAddObserver.invoke(model, observer); }
        catch (IllegalAccessException | InvocationTargetException e)    { e.printStackTrace();    }
        return this;
    }
}
