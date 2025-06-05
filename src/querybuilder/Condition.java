package querybuilder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Condition {
    String column;
    String operator;
    Object value;
    String connector; 

    public Condition(Column column, String operator, Object value, String connector) {
        this.column = column.getQualifiedName();
        this.operator = operator;
        this.value = value;
        this.connector = connector;
    }

    public Condition(Column column, String operator, Object value) {
        this(column, operator, value, null);
    }
   
    @Override
    public String toString() {
        String base;

        if ("IN".equals(operator) && value instanceof List) {
            List<?> valueList = (List<?>) value;
            String placeholders = valueList.stream().map(v -> "?").collect(Collectors.joining(", "));
            base = column + " IN (" + placeholders + ")";
        } else {
            String valueStr = (value instanceof String) ? "'" + value + "'" : value.toString();
            base = column + " " + operator + " " + valueStr;
        }

        return (connector != null ? connector + " " : "") + base;
    }

    public List<Object> getValueList() {
        return (value instanceof List) ? (List<Object>) value : Arrays.asList(value);
    }
}
