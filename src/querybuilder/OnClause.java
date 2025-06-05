package querybuilder;

public class OnClause {

	private final String leftColumn;
	private final String rightColumn;
	private final String operator;
	private final String connector;

	public OnClause(Column leftColumn, String operator, Column rightColumn, String connector) {
		this.leftColumn = leftColumn.getQualifiedName();  
		this.rightColumn = rightColumn.getQualifiedName();
		this.operator = operator;
		this.connector = connector;
	}

	public OnClause(Column leftColumn, String operator, Column rightColumn) {
		this(leftColumn, operator, rightColumn, null);
	}

	@Override
	public String toString() {
		if (connector == null) {
			return "ON " + leftColumn + " " + operator + " " + rightColumn;
		} else {
			return connector.toUpperCase() + " " + leftColumn + " " + operator + " " + rightColumn;
		}
	}
}
