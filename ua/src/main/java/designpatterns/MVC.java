package designpatterns;

import menu.MenuModel;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Observable;
import java.util.Observer;

public class MVC {

    private Object model;
    private Method methodAddObserver;
    private Object view;
    private Object controller;

    public   MVC (Class mClass, Class cClass, Class vClass, ObservableDS observableDS, MenuModel.Rule rule )   {
        try {
            Constructor mConstructor = mClass.getConstructor(ObservableDS.class, MenuModel.Rule.class);
            model = mConstructor.newInstance(observableDS,rule);

            Constructor cConstructor = cClass.getConstructor( ObservableDS.class);
            controller = cConstructor.newInstance(model);

            Constructor vConstructor = vClass.getConstructor( ObservableDS.class, InitializableDS.class);
            view = vConstructor.newInstance(model,controller);

            methodAddObserver = Observable.class.getDeclaredMethod("addObserver",Observer.class );
            methodAddObserver.invoke(model, view);

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e)  {      e.printStackTrace();        }
    }

    public Object getModel() {   return model;   }
    public Object getView()  {   return view;    }
    public Object getController() { return controller;  }

    public MVC addObserverP (Observer observer) {
        try   {   methodAddObserver.invoke(model, observer); }
        catch (IllegalAccessException | InvocationTargetException e)    { e.printStackTrace();    }
        return this;
    }
}
