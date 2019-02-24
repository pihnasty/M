package collection;

import java.util.ArrayList;
import java.util.List;

public class ListP {


    public  static  List<List<String>> deepCloneArrayList(List<List<String>> covarianceСoefficients) {
        List<List<String>> result = new ArrayList<>();
        covarianceСoefficients.forEach(
            row -> {
                List<String> rowA = new ArrayList<>();
                row.forEach(cell -> rowA.add(cell));
                result.add(rowA);
            }
        );
        return result;
    }


}
