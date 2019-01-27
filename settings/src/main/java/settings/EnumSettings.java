package settings;

import io.csv.read.CsvReaderP;
import logging.LoggerP;

import java.io.File;
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
    private File fileSettings;


    EnumSettings(String name) {
        this.name = name;
        this.instance = this;
        map=readSettingsFromFile();
    }

    public Settings getInstance() {

        return instance;

    }

    public File getfileSettings() {
        return fileSettings;
    }

    public Map<String,String> getMap() {
        return map;
    }

    public String getName() {
        return name;
    }


    private Map<String, String> readSettingsFromFile() {
        Map<String, String> map = new HashMap<>();
        String path = getPath(name);

        if(!path.isEmpty()) {
            CsvReaderP csvReaderP = new CsvReaderP("%8.3f ", ';', path, name.toLowerCase() + Values.SETTINGS_CSV);
            List<List<String>> table = null;
            try {
                table = csvReaderP.readFromFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            table.forEach(e -> map.put(e.get(0).trim(), e.get(1).trim()));
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

}
