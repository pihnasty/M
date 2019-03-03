package settings;

import io.csv.read.CsvReaderP;
import logging.LoggerP;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;

class NamesSettings {
    static final String DEFAULT="DEFAULT";
    static final String PROJECT="PROJECT";
    static final String GLOBAL="GLOBAL";
}

public enum EnumSettings implements Settings {

    DEFAULT(NamesSettings.DEFAULT),PROJECT(NamesSettings.PROJECT), GLOBAL(NamesSettings.GLOBAL);

    private Settings instance;
    private String name;
    private Map<String, String> map;


    EnumSettings(String name) {
        this.name = name;
        this.instance = this;
        map=readSettingsFromFile();
    }

    public Settings getInstance() {

        return instance;

    }

    public Map<String,String> getMap() {
        return map;
    }

    @Override
    public void setMap(Map<String, String> map) {
        this.map=map;
    }

    public String getName() {
        return name;
    }

    public String getFileName() {
        return  getSubDirectory(name) +name.toLowerCase()+"_" + Values.SETTINGS_CSV;
    }

    private Map<String, String> readSettingsFromFile() {
        Map<String, String> map = new HashMap<>();
        String path = getPath(name);
        return readSettingsFromFile(path, getFileName());
    }

    public static Map<String, String> readSettingsFromFile(String path, String fileName) {
        Map<String, String> map = new HashMap<>();
        if(!path.isEmpty()) {
            CsvReaderP csvReaderP = new CsvReaderP("%8.3f ", ';', path, fileName);
            List<List<String>> table = null;
            try {
                table = csvReaderP.readFromFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            table.forEach(
                e -> {
                    if(e.size()<2) {
                        LoggerP.write(Level.SEVERE, "Неправильный формат строки (ключ/значение)");
                    } else {
                        map.put(e.get(0).trim(), e.get(1).trim());
                    }

                }
            );
        }
        return map;
    }

    private String getPath(String name) {
        String path = "";
        switch (name) {
            case NamesSettings.DEFAULT:
                path = Values.DEFAULT_PROJECT_PATH;
                break;
            case NamesSettings.PROJECT:
                path = DEFAULT.map.get(Settings.Keys.PROJECT_PATH);
                break;
            case NamesSettings.GLOBAL:
                path = DEFAULT.map.get(Settings.Keys.PROJECT_PATH);
                break;
            default:
                try {
                    throw new Exception();
                } catch (Exception e) {


                    LoggerP.logger.log(Level.SEVERE,
                        LoggerP.loader.getResources().getString("path.not.found")+ " "+getName()
                        , "LoggerP");
                    e.printStackTrace();
                }
        }
        return Objects.isNull(path) ? "" : path;
    }

    private String getSubDirectory(String name) {
        String subDirectory = "";
        switch (name) {
            case NamesSettings.DEFAULT:
                break;
            case NamesSettings.PROJECT:
                subDirectory=Settings.Values.SETTINGS + "//";
                break;
            case NamesSettings.GLOBAL:
                subDirectory=Settings.Values.SETTINGS + "//";
                break;
            default:
                try {
                    throw new Exception();
                } catch (Exception e) {


                    LoggerP.logger.log(Level.SEVERE,
                        LoggerP.loader.getResources().getString("path.not.found")+ " "+getName()
                        , "LoggerP");
                    e.printStackTrace();
                }
        }
        return Objects.isNull(subDirectory) ? "" : subDirectory;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
