package io.csv.write;

import io.csv.read.CsvReaderP;
import org.junit.Test;


import java.io.IOException;
import java.util.ArrayList;

import java.util.List;

public class CsvReaderPTest {


    @Test
    public void writeToFile() throws IOException {

        List<List<String>> table = new ArrayList<>();

        List<String> list1 = new ArrayList<>();
        list1.add("file"); list1.add("name this file");

        List<String> list2 = new ArrayList<>();
        list2.add("file2"); list2.add("name this file2");

        table.add(list1); table.add(list2);



        CsvWriterP csvWriterP = new CsvWriterP("%8.3f ", ';',"D:\\A\\M\\util\\src\\test\\java\\io\\csv\\write\\data", "p1.csv");
        csvWriterP.writeToFile( table);



        CsvReaderP csvReaderP = new CsvReaderP("%8.3f ", ';',"D:\\A\\M\\util\\src\\test\\java\\io\\csv\\write\\data", "p1.csv");

        List<List<String>> table2 = csvReaderP.readFromFile();


    }

}
