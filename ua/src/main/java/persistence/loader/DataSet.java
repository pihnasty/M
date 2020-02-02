package persistence.loader;

import persistence.loader.tabDataSet.*;

import java.util.ArrayList;

public class DataSet {


    static public ArrayList<RowSetting> tSettings;                    // установки и настройки системы. Хранятся в коллекции разные для разных перспектив. Модификатор доступа static , т.к. хранятся гастройки отдельно от объектов




    public DataSet() {

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
