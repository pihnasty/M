package settings;

import java.util.Map;

public interface Settings {

    class Keys {
        public static String PROJECT_PATH = "project-path";
        public static String DEFAULT_PROJECT_PATH = "default-project-path";
        public static String PART_OF_THE_TEST_DATA = "part-of-the-test-data";
        public static String DEFAULT_PART_OF_THE_TEST_DATA = "part-of-the-test-data";
        public static String DEFAULT_SPLIT_FOR_CATEGORY_AND_NAME = "split-for-category-and-name";
        public static String NAME_NUMBER_FACTOR = "name-number-factor";
        public static String LENGTH_CELL = "length-cell";

    }

    class Values {
        public static String DEFAULT_PROJECT_PATH = "default_settings";
        public static String SETTINGS_CSV ="settings.csv";
        public static String RAW_DATA_TABLE_CSV ="raw_data_table.csv";
        public static String NAME_CATEGORY_TABLE_CSV ="name_category_table.csv";
        public static String SEPARATED_RAW_DATA_TABLE_CSV ="separated_raw_data_table.csv";
        public static String NORMALIZED_SEPARATED_RAW_DATA_TABLE_CSV ="normalized_separated_raw_data_table.csv";
        public static String TESTED_RAW_DATA_TABLE_CSV ="tested_raw_data_table.csv";
        public static String NAME_NUMBER_FACTOR = "0.number";
        public static String COVARIANCE_COEFFICIENTS_SEPARATED_RAW_DATA_TABLE_CSV ="covariance_coefficients_separated_raw_data_table.csv";
        public static String SIGNIFICANCE_FACTORS_SEPARATED_RAW_DATA_TABLE_CSV ="significance-factors_separated_raw_data_table.csv";
        public static String CHARACTERISTICS_SEPARATED_RAW_DATA_TABLE_CSV ="characteristics_separated_raw_data_table.csv";
        public static String CHARACTERISTICS_DIMENSIONLESS_SEPARATED_RAW_DATA_TABLE_CSV ="characteristics_dimensionless_separated_raw_data_table.csv";

        public static String ONE_PARAMETER_MODEL_KOEF_A_TABLE_CSV ="one_parameter_model//koef_a_table.csv";
        public static String ONE_PARAMETER_MODEL_KOEF_B_TABLE_CSV ="one_parameter_model//koef_b_table.csv";
        public static String ONE_PARAMETER_MODEL_PDF_OPM_PAGE ="one_parameter_model//pdf5//opm_book_page";

        public static String TEMPLATE_EXPERIMENT_PLAN_JSON ="template_experiment_plan.json";

    }

    String getName();

    Settings getInstance();

    Map<String, String> getMap();

}

