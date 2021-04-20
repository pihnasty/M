package string;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class StringUtil {


    public static String OptionalIsNullOrEmpty(String value, String defaultValue) {
        String newValue;
        if (Objects.isNull(value)) {
            newValue = Optional.ofNullable(value).orElse(defaultValue);
        } else {
            newValue = value.isEmpty() ? defaultValue : value;
        }
        return newValue;
    }

    public static String getStringFormat(String value, int length) {
        return String.format(" " + "%" + (value.length() < length ? length : value.length()) + "s ", value);
    }

    public static String getDoubleFormatValue (Double value, String headerValue, String defaultHeaderLenght) {
        int lengthCell = Integer.parseInt(defaultHeaderLenght);
        int lengthHeaderValue = headerValue.length();
        return  String.format(" " + "%"
            + (lengthHeaderValue<lengthCell?lengthCell:lengthHeaderValue)
            + ".4f ", value);
    }

    public static  String getDoubleFormatValue (Double value, int lengthHeaderValue, int lengthCell) {
        return  String.format(" " + "%"
            + (lengthHeaderValue<lengthCell?lengthCell:lengthHeaderValue)
            + ".4f ", value);
    }

    public static String getDoubleFormatValue(Double value, String headerValue) {
        return String.format(" " + "%"
            + headerValue.length()
            + ".4f ", value);
    }

    public static String getDoubleFormatValue(Double value, String headerValue, String presigion, int anything) {
        return String.format("%"
            + headerValue.length()
            + presigion, value);
    }

    public static String getDoubleFormatValue(Double value, int headerValueLength) {
        return String.format(" " + "%"
            + headerValueLength
            + ".4f ", value);
    }


    public static String addBrackets(String value) {
        return "["+value.trim()+"]";
    }

    public static double parseToDouble (String expression) {
        return Double.parseDouble(
            expression.replace(",", ".")
        );
    }

    public static String getDoubleToFormatTable(List<List<Double>> startTable, String columnSize, String presition) {
        if(Objects.isNull(startTable)) {
            return "\n";
        }
        StringBuilder convertTable = new StringBuilder();
        startTable.forEach(
            row -> {
                convertTable.append("\n");
                row.forEach(
                    cell -> convertTable.append(
                        StringUtil.getDoubleFormatValue(cell, columnSize , presition, 1)
                    )
                );
            }
        );
        return convertTable.toString();
    }

}
