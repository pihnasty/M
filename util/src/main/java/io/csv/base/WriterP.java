package io.csv.base;

import java.io.IOException;
import java.util.List;

public interface WriterP {
    public void writeToFile(List<List<String>> table);
    String getFullPathToFile();

}
