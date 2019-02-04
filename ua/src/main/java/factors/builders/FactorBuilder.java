package factors.builders;

import factors.Factor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FactorBuilder {

    private List<List<String>> separatedRawDataTable;


    public FactorBuilder(List<List<String>> separatedRawDataTable) {
        this.separatedRawDataTable = separatedRawDataTable;
    }

    public Factor constructFactor(String categoryIdAndName, Map<String,String> mapNameAdnCategore) {
        Factor factor = new Factor(categoryIdAndName,mapNameAdnCategore);
        factor.setValues(getValues(factor));
        return factor;
    }



    private Map<String,String> getValues(Factor factor) {

        HashMap<String,String> values = new HashMap<>();

//        separatedRawDataTable.





        return null;
    }
}
