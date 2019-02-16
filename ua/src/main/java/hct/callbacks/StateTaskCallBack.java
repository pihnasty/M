package hct.callbacks;

import dialogs.AlertDialog;
import javafx.util.Callback;

public interface StateTaskCallBack<P1,P2,R>  {
    R call(P1 param1, P2 param2) throws Exception;
}
