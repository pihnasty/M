package test.trestview.table.tablemodel;

//import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class TableMolelBuilderTest {

    //  public static <cL> AbstractFactoryTableModel build (ArrayList<cL> tab) {


 //   @Test
    public void build_test() {

   //     DictionaryModel dm = new DictionaryModel();

        //     System.out.println( TableMolelBuilder.build(tabLineroutes, RowLineroute.class) );
    }

  //  @Test
    public void ruleList_test() {
        RuleList dm = new RuleList("A","B","B","B","B");
        dm.stream().filter(s->{System.out.println(s); return true; }).count();   //         for(String s: args) { this.add(s); }
    }

  //  @Test
    public void ParametersColumnMap_test() {
        System.out.println( ResourceBundle.getBundle("resources.ui").getString("File"));
    }


}

class RuleList extends ArrayList {
    public RuleList (String ... args) {
        Arrays.asList(args).stream().map(s->this.add(s)).count();   //         for(String s: args) { this.add(s); }
    }
}


class ParametersColumnMap  {

    public ParametersColumnMap () {


        ResourceBundle.getBundle("resources.ui").getString("File");

    }
}



