package settings;

import java.util.Map;

public interface Settings {

    class Keys {
        public static String PROJECT_PATH = "project-path";
        public static String DEFAULT_PROJECT_PATH = "default-project-path";
    }

    class Values {
        public static String DEFAULT_PROJECT_PATH = "defaultsettings";
        public static String SETTINGS_CSV ="settings.csv";
    }



    String getName();

    Settings getInstance();

    Map<String, String> getMap();

}

