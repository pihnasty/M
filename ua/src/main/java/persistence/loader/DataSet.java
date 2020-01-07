package persistence.loader;

import persistence.loader.tabDataSet.*;

import java.util.ArrayList;

public class DataSet {


    static public ArrayList<RowSetting> tSettings;                    // установки и настройки системы. Хранятся в коллекции разные для разных перспектив. Модификатор доступа static , т.к. хранятся гастройки отдельно от объектов

    private ArrayList<RowTrest> tabTrests;                    //
    private ArrayList<RowWork> tabWorks;
    private ArrayList<RowTrestWork> tabTrestsWorks;

    private ArrayList<RowMachine> tabMachines;
    private ArrayList<RowWorkMachine> tabWorksMachines;

    private ArrayList<RowModelmachine> tabModelmachines;

    private ArrayList<RowEmployee> tabEmployees;
    private ArrayList<RowWorkEmployee> tabWorksEmployees;

    private ArrayList<RowSubject_labour> tabSubject_labours;
    private ArrayList<RowWorkSubject_labour> tabWorksSubject_labours;

    private ArrayList<RowOrder> tabOrders;
    private ArrayList<RowWorkOrder> tabWorksOrders;

    private ArrayList<RowLine> tabLines;
    private ArrayList<RowOrderLine> tabOrdersLines;

    private ArrayList<RowLineSubject_labour> tabLinesSubject_labours;
    private ArrayList<RowLineUnit> tabLinesUnits;                    // <RowLineUnit>

    private ArrayList<RowRoute> tabRoutes;
    private ArrayList<RowRouteLineroute> tabRoutesLineroutes;
    private ArrayList<RowResource> tabResources;

    private ArrayList<RowSubject_labourRoute> tabSubject_laboursRoutes;
    private ArrayList<RowSubject_labourUnit> tabSubject_laboursUnits;

    private ArrayList<RowUnit> tabUnits;

    private ArrayList<RowLinespec> tabLinespecs;

    private ArrayList<RowLinespecResource> tabLinespecsResources;

    private ArrayList<RowLinespecUnit> tabLinespecsUnits;
    private ArrayList<RowFunctionOEM> tabFunctionOEMs;
    private ArrayList<RowLinespecFunctionOEM> tabLinespecsFunctionOEMs;

    private ArrayList<RowLineroute> tabLineroutes;
    private ArrayList<RowOperation> tabOperations;
    private ArrayList<RowLinerouteOperation> tabLineroutesOperations;
    private ArrayList<RowLinerouteMachine> tabLineroutesMachines;
    private ArrayList<RowLinerouteEmployee> tabLineroutesEmployees;
    private ArrayList<RowLinerouteLinespec> tabLineroutesLinespecs;
    private ArrayList<RowTypemachine> tabTypemachines;
    private ArrayList<RowTypemachineModelmachine> tabTypemachineModelmachines;
    private ArrayList<RowModelmachineMachine> tabModelmachineMachines;
    private ArrayList<RowParametrfunctiondist> tabParametrfunctiondists = new ArrayList<>();

    private ArrayList<RowFunctiondistParametrfunctiondist> tabFunctiondistsParametrfunctiondistsTest = new ArrayList<>();




    public DataSet() {
        tSettings = new ArrayList<RowSetting>();
        tabTrests = new ArrayList<RowTrest>();
        tabWorks = new ArrayList<RowWork>();
        tabTrestsWorks = new ArrayList<RowTrestWork>();
        tabMachines = new ArrayList<RowMachine>();
        tabWorksMachines = new ArrayList<RowWorkMachine>();
        tabEmployees = new ArrayList<RowEmployee>();
        tabWorksEmployees = new ArrayList<RowWorkEmployee>();
        tabSubject_labours = new ArrayList<RowSubject_labour>();
        tabSubject_laboursRoutes = new ArrayList<>();
        tabSubject_laboursUnits = new ArrayList<>();
        tabWorksSubject_labours = new ArrayList<RowWorkSubject_labour>();
        tabOrders = new ArrayList<RowOrder>();
        tabWorksOrders = new ArrayList<RowWorkOrder>();
        tabLines = new ArrayList<RowLine>();
        tabLinesSubject_labours = new ArrayList<RowLineSubject_labour>();
        tabLinesUnits = new ArrayList<RowLineUnit>();    //<RowLineUnit>
        tabUnits = new ArrayList<RowUnit>();
        tabOrdersLines = new ArrayList<RowOrderLine>();
        tabOperations = new ArrayList<RowOperation>();
        tabRoutes = new ArrayList<RowRoute>();
        tabRoutesLineroutes = new ArrayList<RowRouteLineroute>();
        tabLinespecs = new ArrayList<RowLinespec>();

        tabResources = new ArrayList<RowResource>();
        tabLinespecsResources = new ArrayList<RowLinespecResource>();
        tabLinespecsUnits = new ArrayList<RowLinespecUnit>();
        tabFunctionOEMs = new ArrayList<RowFunctionOEM>();
        tabLinespecsFunctionOEMs = new ArrayList<RowLinespecFunctionOEM>();
        tabLineroutes = new ArrayList<RowLineroute>();
        tabLineroutesOperations = new ArrayList<RowLinerouteOperation>();
        tabLineroutesMachines = new ArrayList<RowLinerouteMachine>();
        tabLineroutesEmployees = new ArrayList<RowLinerouteEmployee>();
        tabLineroutesLinespecs = new ArrayList<RowLinerouteLinespec>();
        tabModelmachines = new ArrayList<RowModelmachine>();
        tabTypemachines = new ArrayList<RowTypemachine>();
        tabTypemachineModelmachines = new ArrayList<>();
        tabModelmachineMachines = new ArrayList<>();




    }

    //-------------------------------------------------------------------------------
    public <cL> cL createObject( RowIdNameDescription row) {
        Object m = null;


//----------------------------------------------------------------------------------------------------------------------
//        if (row.getClass() == RowOperation.class) {
//            m = new Operation(((RowOperation) row).getId(), ((RowOperation) row).getName(), ((RowOperation) row).getDescription());
//        }



        return (cL)m;
    }

    /**
     * Возвращаем максимальное значение Id, которое хранится в таблицах полей
     * Dataset (имеются в виду таблицы вида: tabTrests, tabWorks; ).
     *
     * @param cL тип данных строк, содержащихся в таблице. Каждая строка имеет
     *           id и наследует RowIdNameDescription
     * @return Максимальное значение id, содержащегося в таблицу
     */
    public int IdMax(Class cL) {
        ArrayList<RowIdNameDescription> tab = getTabIND(cL);
        if (tab == null) {
            System.out.println("DataSet.IdMax: Таблица пуста");
            return 1;
        }
        int idMax = tab.get(0).getId();
        for (RowIdNameDescription row : tab) {
            if (idMax < row.getId()) {
                idMax = row.getId();
            }
        }
        return idMax;
    }

    /**
     * Возврает таблицу DataSet, тип данных строк которой соответствует
     * соответствует типу данных объектов Trest (Например типу Work
     * соответствуют данные с типом строк RowWork) Применяется при проверки
     * таблицы по id и возврате максимального элемента в методе public int
     * DataSet.IdMax(Class cL)
     *
     * @param cL
     * @return
     */
    public <T> ArrayList<T> getTabIND(Class cL) {
        // используется для дерева Trest.class а для таблиц RowTrest.class

        return null;
    }






}
