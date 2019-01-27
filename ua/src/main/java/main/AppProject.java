package main;

import designpatterns.ObservableDS;
import io.csv.read.CsvReaderP;
import io.csv.write.CsvWriterP;
import settings.EnumSettings;
import settings.ProviderSettings;
import settings.Settings;
import string.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class AppProject extends ObservableDS {
    private static AppProject ourInstance = new AppProject();

    public static AppProject getInstance() {
        return ourInstance;
    }

    Settings defaultSettings = ProviderSettings.getSettings(EnumSettings.DEFAULT);
    Settings projectSettings = ProviderSettings.getSettings(EnumSettings.PROJECT);
    Settings globalSettings = ProviderSettings.getSettings(EnumSettings.PROJECT);




    private AppProject() {

    }

    public Settings getDefaultSettings() {
        return defaultSettings;
    }

    public Settings getProjectSettings() {
        return projectSettings;
    }

    public Settings getGlobalSettings() {
        return globalSettings;
    }

    public String getProjectPath() {
        return StringUtil.OptionalIsNullOr(
             getDefaultSettings().getMap().get(Settings.Keys.PROJECT_PATH)
            ,getDefaultSettings().getMap().get(Settings.Keys.DEFAULT_PROJECT_PATH)
        );
    }

    public void saveProjectSettings() {

        new CsvWriterP("%8.3f ", ';'
            , Settings.Values.DEFAULT_PROJECT_PATH
            , EnumSettings.DEFAULT.getName().toLowerCase() + Settings.Values.SETTINGS_CSV
        ).writeToFile(
            defaultSettings.getMap().entrySet().stream().map(e -> new ArrayList<>(Arrays.asList(e.getKey(),e.getValue()))).collect(Collectors.toList())
        );

        new CsvWriterP("%8.3f ", ';'
            , getProjectPath()
            , EnumSettings.PROJECT.getName().toLowerCase() + Settings.Values.SETTINGS_CSV
        ).writeToFile(
            projectSettings.getMap().entrySet().stream().map(e -> new ArrayList<>(Arrays.asList(e.getKey(),e.getValue()))).collect(Collectors.toList())
        );

        new CsvWriterP("%8.3f ", ';'
            , getProjectPath()
            , EnumSettings.GLOBAL.getName().toLowerCase() + Settings.Values.SETTINGS_CSV
        ).writeToFile(
            globalSettings.getMap().entrySet().stream().map(e -> new ArrayList<>(Arrays.asList(e.getKey(),e.getValue()))).collect(Collectors.toList())
        );


    }

    public void openProject() {

    }

}
