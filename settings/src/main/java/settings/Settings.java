package settings;

import java.util.Map;

public interface Settings {

    class Keys {
        public static String PROJECT_PATH = "project-path";
        public static String DEFAULT_PROJECT_PATH = "default-project-path";
        public static String PART_OF_THE_TEST_DATA = "part-of-the-test-data";
        public static String DEFAULT_PART_OF_THE_TEST_DATA = "part-of-the-test-data";
    }

    class Values {
        public static String DEFAULT_PROJECT_PATH = "default_settings";
        public static String SETTINGS_CSV ="settings.csv";
        public static String RAW_DATA_TABLE_CSV ="raw_data_table.csv";
        public static String NAME_CATEGORY_TABLE_CSV ="name_category_table.csv";
        public static String SEPARATED_RAW_DATA_TABLE_CSV ="separated_raw_data_table.csv";
        public static String TESTED_RAW_DATA_TABLE_CSV ="tested_raw_data_table.csv";
    }

    String getName();

    Settings getInstance();

    Map<String, String> getMap();

}

