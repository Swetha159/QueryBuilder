package querybuilder;

public class Condition {
    String column;
    String operator;
    Object value;
    String connector; 

    public Condition(String column, String operator, Object value, String connector) {
        this.column = column;
        this.operator = operator;
        this.value = value;
        this.connector = connector;
    }

    @Override
    public String toString() {
        String valueStr = (value instanceof String) ? "'" + value + "'" : value.toString();
        String base = column + " " + operator + " " + valueStr;
        return (connector != null ? connector + " " : "") + base;
    }
}

