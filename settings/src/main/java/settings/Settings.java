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
        public static String PROJECT_NAME = "project-name";


        // for the parameters of the multi-model
        public static String NUMBER_REGRESSORS = "number-regressors";
        public static String MAX_NUMBER_MODELS = "max-number-models";

        // for the parameters of the neural
        public static String COUNT_NODE = "count-node";
        public static String ACTIVATION_FUNCTION = "activation-function";
        public static String DISTRIBUTE_ERROR = "distribute-error";
        public static String OPTIMIZATION_METHOD = "optimization-method";
        public static String TYPE_LAYER = "type-layer";
        public static String ALPHA = "alpha";



    }

    class Values {
        public static String DEFAULT_PROJECT_PATH = "default_settings";
        public static String SETTINGS_CSV ="settings.csv";
        public static String RAW_DATA_TABLE_CSV ="raw_data//raw_data_table.csv";
        public static String NAME_CATEGORY_TABLE_CSV ="raw_data//name_category_table.csv";
        public static String SEPARATED_RAW_DATA_TABLE_CSV ="raw_data//separated_raw_data_table.csv";
        public static String TESTED_RAW_DATA_TABLE_CSV ="raw_data//tested_raw_data_table.csv";

        public static String NORMALIZED_SEPARATED_RAW_DATA_TABLE_CSV ="factors//normalized_separated_raw_data_table.csv";
        public static String COVARIANCE_COEFFICIENTS_SEPARATED_RAW_DATA_TABLE_CSV ="factors//covariance_coefficients_separated_raw_data_table.csv";
        public static String SIGNIFICANCE_FACTORS_SEPARATED_RAW_DATA_TABLE_CSV ="factors//significance_factors_separated_raw_data_table.csv";

        public static String CHARACTERISTICS_SEPARATED_RAW_DATA_TABLE_CSV ="factors//characteristics_separated_raw_data_table.csv";
        public static String CHARACTERISTICS_DIMENSIONLESS_SEPARATED_RAW_DATA_TABLE_CSV ="factors//characteristics_dimensionless_separated_raw_data_table.csv";


        public static String NAME_NUMBER_FACTOR = "0.number";

        // for the parameters of the multi-model
        public static String MODEL_NUMBER_FACTOR = "0.Model";
        public static String OUTPUT_FACTOR = "0.OutputFactors";
        public static String COEFFICIENT_A = "0.A";
        public static String NUMBER_OBSERVATIONS = "0.Observations";
        public static String NUMBER_CONSTRAINTS = "0.Constraints";
        public static String SSE = "0.SSE";
        public static String MSE = "0.MSE";
        public static String  stDeviationMSE = "0.stDevMSE";


        public static String ONE_PARAMETER_MODEL_KOEF_A_TABLE_CSV ="result//one_parameter_model//koef_a_table.csv";
        public static String ONE_PARAMETER_MODEL_KOEF_B_TABLE_CSV ="result//one_parameter_model//koef_b_table.csv";
        public static String ONE_PARAMETER_MODEL_PDF_OPM_PAGE ="result//one_parameter_model//pdfOneFactor//opm_book_page";
        public static String TWO_PARAMETER_MODEL_PDF_OPM_PAGE ="result//two_parameter_model//pdfTwoFactor//opm_book_page";
        public static String TWO_PARAMETER_MODEL_PDF_PDF_BOUNDARY_VALUE ="result//two_parameter_model//pdfBoundaryValue//opm_book_boundary_value_page";
        public static String TWO_PARAMETER_MODEL_PROBIT ="result//two_parameter_model//pdf_probit//opm_book_probit_page";
        public static String SETTINGS ="settings";

        public static String MULTI_MODEL_DIMENSION_LESS_KOEF_CSV ="result//multi_model//dimension_less_koef_table.csv";
        public static String MULTI_MODEL_DIMENSION_KOEF_CSV ="result//multi_model//dimension_koef_table.csv";
        public static String MULTI_MODEL_RESIDUAL_PLOT_PAGE_PDF ="result//multi_model//pdfResidualPlot//ResidualPlotPage";

        public static String TEMPLATE_EXPERIMENT_PLAN_JSON ="template_experiment_plan.json";
        public static String EXPERIMENT_PLAN_JSON ="experiment_plan//experiment_plan.json";

        public static String NEURAL_NETWORk_MODEL_TEST_DATA_CSV ="result//neural_network_model//test_data.csv";
        public static String NEURAL_NETWORk_MODEL_SPEED_INPUT_TEST_DATA_CSV ="result//neural_network_model//speed_input//speed_input_test_data.csv";
        public static String NEURAL_NETWORk_MODEL_WS_SERIALIZE ="result//neural_network_model//ws_serialize";
        public static String DATA_TABLE_AFTER_ANALYSIS_CSV ="result//neural_network_model//data_after_analysis.csv";
    }

    String getName();

    Settings getInstance();

    Map<String, String> getMap();

    void setMap(Map<String, String> map);

}

