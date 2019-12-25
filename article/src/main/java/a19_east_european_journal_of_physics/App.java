package a19_east_european_journal_of_physics;

import com.opencsv.CSVWriter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class App {


    public static void main(String[] args) {

        double tauMax = 0.5;
        double deltaTau = 0.01;
        double deltaKsi = 0.0001;

        String filePath = "D:\\A\\M\\article\\src\\main\\java\\results\\result_022.csv";
        CommonConstants.v_g = 5.0;
        CommonConstants.v_f1 = 5.0*CommonConstants.v_g ;
        CommonConstants.w_01_Is =0.0;
        CommonConstants.z1_Is =0.0;
        CommonConstants.z2_Is =1.0;

/*



---------------------------------------------- */


        W0_dKsi w0_dKsi = new W0_dKsi();

        List<List<String>> tableWithHeader = new ArrayList();
        List<String> rowHeader = new ArrayList<>();

        for (double ksi = 0.0; ksi <= 1.0; ksi += deltaKsi) {

            if (ksi == 0.0) {
                rowHeader.add(String.format("%10.4f ", 0.0));
                for (double tau = 0.0; tau < tauMax; tau += deltaTau) {
                    rowHeader.add(String.format("%10.4f ", tau));
                }
                tableWithHeader.add(rowHeader);
            }

            List<String> row = new ArrayList<>();
            row.add(String.format("%10.4f ", ksi));
            for (double tau = 0.0; tau < tauMax; tau += deltaTau) {
                row.add(String.format("%10.4f ", w0_dKsi.getValue(tau, ksi)));
            }
            tableWithHeader.add(row);
        }


        addInitialDataToTable(tableWithHeader);

        writeToFile(tableWithHeader, filePath);

    }

    private static void addInitialDataToTable(List<List<String>> tableWithHeader) {
        List<String> rowInitialData = new ArrayList<>();
        rowInitialData.add("CommonConstants.Q_c");
        rowInitialData.add(String.format("%10.4f ", CommonConstants.Q_c));
        tableWithHeader.add(rowInitialData);

        rowInitialData = new ArrayList<>();
        rowInitialData.add("CommonConstants.Q_r");
        rowInitialData.add(String.format("%10.4f ", CommonConstants.Q_r));
        tableWithHeader.add(rowInitialData);

        rowInitialData = new ArrayList<>();
        rowInitialData.add("CommonConstants.v_g");
        rowInitialData.add(String.format("%10.4f ", CommonConstants.v_g));
        tableWithHeader.add(rowInitialData);

        rowInitialData = new ArrayList<>();
        rowInitialData.add("CommonConstants.v_b");
        rowInitialData.add(String.format("%10.4f ", CommonConstants.v_b));
        tableWithHeader.add(rowInitialData);

        rowInitialData = new ArrayList<>();
        rowInitialData.add("CommonConstants.v_f0");
        rowInitialData.add(String.format("%10.4f ", CommonConstants.v_f0));
        tableWithHeader.add(rowInitialData);

        rowInitialData = new ArrayList<>();
        rowInitialData.add("CommonConstants.v_f1");
        rowInitialData.add(String.format("%10.4f ", CommonConstants.v_f1));
        tableWithHeader.add(rowInitialData);

        rowInitialData = new ArrayList<>();
        rowInitialData.add("v_f = tau -> v_f0 + v_f1 * tau");
        tableWithHeader.add(rowInitialData);

        rowInitialData = new ArrayList<>();
        rowInitialData.add("CommonConstants.v_1 ");
        rowInitialData.add(String.format("%10.4f ", CommonConstants.v_1));
        tableWithHeader.add(rowInitialData);

        rowInitialData = new ArrayList<>();
        rowInitialData.add("v_2 = tau ->     v_g  * Math.sqrt (v_f .apply(tau)+v_b*(1+Q_r/Q_c))");
        tableWithHeader.add(rowInitialData);

        rowInitialData = new ArrayList<>();
        rowInitialData.add("CommonConstants.alfa1");
        rowInitialData.add(String.format("%10.4f ", CommonConstants.alfa1));
        tableWithHeader.add(rowInitialData);

        rowInitialData = new ArrayList<>();
        rowInitialData.add("CommonConstants.w_01_Is");
        rowInitialData.add(String.format("%10.4f ", CommonConstants.w_01_Is));
        tableWithHeader.add(rowInitialData);

        rowInitialData = new ArrayList<>();
        rowInitialData.add("CommonConstants.z0_Is ");
        rowInitialData.add(String.format("%10.4f ", CommonConstants.z0_Is));
        tableWithHeader.add(rowInitialData);

        rowInitialData = new ArrayList<>();
        rowInitialData.add("CommonConstants.z1_Is ");
        rowInitialData.add(String.format("%10.4f ", CommonConstants.z1_Is));
        tableWithHeader.add(rowInitialData);

        rowInitialData = new ArrayList<>();
        rowInitialData.add("CommonConstants.z2_Is ");
        rowInitialData.add(String.format("%10.4f ", CommonConstants.z2_Is));
        tableWithHeader.add(rowInitialData);
    }

    public static void writeToFile(List<List<String>> table, String fullPathToFile) {
        BufferedWriter writer = null;

        try {
            writer = Files.newBufferedWriter(Paths.get(fullPathToFile));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (CSVWriter csvWriter = new CSVWriter(writer, ';', CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.RFC4180_LINE_END)) {
            List<String[]> data = toStringArray(table);
            csvWriter.writeAll(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String[]> toStringArray(List<List<String>> table) {
        List<String[]> records = new ArrayList<>();
        table.forEach(row -> records.add(row.stream().toArray(String[]::new)));
        return records;
    }




}
