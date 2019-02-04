package settings;

import string.StringUtil;

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

    public static String getProjectSettingsMapValue(String key) {
        String value = StringUtil.OptionalIsNullOrEmpty(projectSettings.getMap().get(key), defaultSettings.getMap().get(key));
        if (!projectSettings.getMap().containsKey(key)) {
            projectSettings.getMap().put(key, value);
        }
        return value.trim();
    }


}
