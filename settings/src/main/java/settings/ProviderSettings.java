package settings;

public class ProviderSettings {

    private final static Settings defaultSettings = EnumSettings.DEFAULT.getInstance();
    private final static Settings projectSettings = EnumSettings.PROJECT.getInstance();
    private final static Settings globalSettings = EnumSettings.GLOBAL.getInstance();

    public static Settings getSettings(EnumSettings settingsType) {
        switch (settingsType) {
            case GLOBAL:
                 return globalSettings ;
            case PROJECT:
                return projectSettings;
            case DEFAULT:
                return defaultSettings;
        }
        return null;
    }
}
