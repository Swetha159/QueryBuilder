package querybuilder;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.zoho.training.exceptions.TaskException;

public class QueryBuilder {
	private String queryType;
	private List<String> columns;
	private String table;
	private QueryBuilder subqueryBuilder;
	private String subqueryAlias;

	private List<String> joins;
	private List<String> orderBy;
	private List<String> groupBy;
	private Integer limit;
	private Integer offset;
	private List<String> setValues;
	private List<Condition> conditions;
	private List<List<String>> values ;
	private String[] havingCondition;
    private List<Object> val;
	
	public QueryBuilder() {
		this.columns = new ArrayList<>();
		this.conditions = new ArrayList<>();
		this.joins = new ArrayList<>();
		this.orderBy = new ArrayList<>();
		this.groupBy = new ArrayList<>();
		this.values = new ArrayList<>();
		this.setValues = new ArrayList<>();
		this.val = new ArrayList<>();
	}

	public QueryBuilder create(Column column) {
		this.queryType = "CREATE";
		this.table = column.getTableName();
		return this;
	}

	public QueryBuilder columnDescription(String... columns) {
		for (String column : columns) {
			this.columns.add(column);
		}
		return this;
	}

	public QueryBuilder select(Object... columns) {
		this.queryType = "SELECT";
		if (columns == null) {
			this.columns.add("*");
			return this;
		}
		for (Object col: columns) {
			if (col instanceof Column) {
				this.columns.add(((Column) col).getQualifiedName());
	        } else if (col instanceof String) {
	        	this.columns.add((String) col);
	        } 
		}
		return this;
	}

	public QueryBuilder values(Object... rowValues) {
//	    List<String> formatted = Arrays.stream(rowValues)
//	        .map(val -> {
//	            if (val == null) {
//	                return "NULL";
//	            } else if (val instanceof String) {
//	                return "'" + val + "'";
//	            } else {
//	                return val.toString();
//	            }
//	        })
//	        .collect(Collectors.toList());

		for (Object value : rowValues) {
			   this.val.add(value);
		}
//	    this.val.add(formatted);
	    return this;
	}
	public QueryBuilder insert(Column column) {
		this.queryType = "INSERT";
		this.table = column.getTableName();
	
		return this;
	}

	public QueryBuilder update(Column column) {
		this.queryType = "UPDATE";
		this.table = column.getTableName();
		return this;
	}

	public QueryBuilder set(Column column, Object value) {
//	    if (value == null) {
//	        this.setValues.add(column + " = NULL");
//	    } else if (value instanceof String) {
//	       
//	    } else {
//	        this.setValues.add(column + " = " + value.toString());
//	    }
	    this.setValues.add(column.getColumnName() + " = " + "?");
        this.val.add(value);
	    return this;
	}


	public QueryBuilder deleteFrom(Column column) {
		this.queryType = "DELETE";
		this.table = column.getTableName();
		return this;
	}

	public QueryBuilder from(Column column) {
		this.table = column.getTableName();
		return this;
	}

	public QueryBuilder where(Column column, String operator, Object value) {
		this.conditions.add(new Condition(column, operator, '?', null));
		val.add(value);
		return this;
	}

	public QueryBuilder and(Column column, String operator, Object value) {
		this.conditions.add(new Condition(column, operator, '?', "AND"));
		val.add(value);
		return this;
	}

	public QueryBuilder or(Column column, String operator, Object value) {
		this.conditions.add(new Condition(column, operator, '?', "OR"));
		val.add(value);
		return this;
	}
	
	public QueryBuilder whereIn(Column column, List<Object> values) throws TaskException {
	    return addInCondition(column, "IN", values, null);
	}

	public QueryBuilder andIn(Column column, List<Object> values) throws TaskException {
	    return addInCondition(column, "IN", values, "AND");
	}

	public QueryBuilder orIn(Column column, List<Object> values) throws TaskException {
	    return addInCondition(column, "IN", values, "OR");
	}

	private QueryBuilder addInCondition(Column column, String operator, List<Object> values, String connector) throws TaskException {
	    if (values == null || values.isEmpty()) {
	        throw new TaskException("IN clause cannot have empty values");
	    }

	    String placeholders = values.stream().map(v -> "?").collect(Collectors.joining(", "));
	    this.conditions.add(new Condition(column, operator, placeholders, connector));
	    this.val.addAll(values);
	    return this;
	}


	
//	public QueryBuilder join(String type, String table, String condition) {
//	    this.joins.add(type.toUpperCase() + " JOIN " + table + " ON " + condition);
//	    return this;
//	}

	public QueryBuilder join(String type, Column table,String alias ,  OnClause... onClause) {
	    String on= Arrays.stream(onClause)
	                            .map(OnClause::toString)
	                            .collect(Collectors.joining(" "));
	    this.joins.add(type.toUpperCase() + " JOIN " + table.getTableName() + " AS "+ alias + " " + on);
	    return this;
	}
	
	public QueryBuilder join(String type, Column table, OnClause... onClause) {
	    String on = Arrays.stream(onClause)
	                            .map(OnClause::toString)
	                            .collect(Collectors.joining(" "));
	    this.joins.add(type.toUpperCase() + " JOIN " + table.getTableName() +" " + on);
	    return this;
	}
	
	
	
	public QueryBuilder orderBy(Column column, boolean ascending) {
		this.orderBy.add(column.getColumnName() + (ascending ? " ASC" : " DESC"));
		return this;
	}

	public QueryBuilder groupBy(Column column) {
		this.groupBy.add(column.getColumnName());
		return this;
	}
	
	public QueryBuilder having(String[] condition) {
	    this.havingCondition = condition;
	    return this;
	}

	public QueryBuilder limit(int limit) {
		this.limit = limit;
		return this;
	}

	public QueryBuilder offset(int offset) {
		this.offset = offset;
		return this;
	}
	public QueryBuilder count(Column column, String alias) {
	    columns.add("COUNT(" + column.getColumnName() + ") AS " + alias);
	    return this;
	}

	public QueryBuilder sum(Column column, String alias) {
	    columns.add("SUM(" + column.getColumnName() + ") AS " + alias);
	    return this;
	}

	public QueryBuilder avg(Column column, String alias) {
	    columns.add("AVG(" + column.getColumnName() + ") AS " + alias);
	    return this;
	}

	public QueryBuilder min(Column column, String alias) {
	    columns.add("MIN(" + column.getColumnName() + ") AS " + alias);
	    return this;
	}

	public QueryBuilder max(Column column, String alias) {
	    columns.add("MAX(" + column.getColumnName() + ") AS " + alias);
	    return this;
	}

	public QueryBuilder fromSubquery(QueryBuilder subqueryBuilder, String alias) {
		this.subqueryBuilder = subqueryBuilder;
		this.subqueryAlias = alias;
		return this;
	}
	
	public QueryBuilder setConditional(Column column, Map<String, String> conditionThenMap, String elseValue) {
	    StringBuilder caseExpression = new StringBuilder("CASE ");
	    for (Map.Entry<String, String> entry : conditionThenMap.entrySet()) {
	        caseExpression.append("WHEN ").append(entry.getKey())
	                .append(" THEN ").append(entry.getValue()).append(" ");
	    }
	    caseExpression.append("ELSE ").append(elseValue).append(" END");

	    setValues.add(column.getColumnName() + " = " + caseExpression.toString());
	    return this;
	}

	public Query build() throws TaskException {
		StringBuilder sb = new StringBuilder();

		switch (queryType) {
		case "SELECT":
			sb.append("SELECT ").append(columns.isEmpty() ? "*" : String.join(", ", columns));
			if (subqueryBuilder != null && subqueryAlias != null) {
				sb.append(" FROM (").append(subqueryBuilder.build()).append(") AS ").append(subqueryAlias);
			} else {
				sb.append(" FROM ").append(table);
			}
            
			break;
		case "INSERT":
		    sb.append("INSERT INTO ")
		      .append(table);
		      if (!columns.isEmpty()) {
                  sb.append(" (").append(String.join(", ", columns)).append(") ");
              }
		      sb.append(" VALUES ");
		    int paramCount = val.size();
		    String placeholders = String.join(", ", Collections.nCopies(paramCount, "?"));
		   sb.append("(" + placeholders + ")");
		    break;
		case "UPDATE":
			sb.append("UPDATE ").append(table).append(" SET ").append(String.join(", ", setValues));
		
			break;
		case "DELETE":
			sb.append("DELETE FROM ").append(table);
			break;
		case "CREATE":
			sb.append("CREATE TABLE ").append(table).append(" (").append(String.join(", ", columns)).append(") ");
			break;
		default:
			throw new TaskException("query type not supported");
		}

		if (!joins.isEmpty()) {
			sb.append(" ").append(String.join(" ", joins));
		}

		if (!conditions.isEmpty()) {
			sb.append(" WHERE ").append(conditions.stream().map(Condition::toString).collect(Collectors.joining(" ")));
		}

		if (!groupBy.isEmpty()) {
			sb.append(" GROUP BY ").append(String.join(", ", groupBy));
		}
		
		if (havingCondition != null) {
		    sb.append(" HAVING ").append(havingCondition);
		}

		if (!orderBy.isEmpty()) {
			sb.append(" ORDER BY ").append(String.join(", ", orderBy));
		}

		if (limit != null) {
			sb.append(" LIMIT ").append(limit);
		}

		if (offset != null) {
			sb.append(" OFFSET ").append(offset);
		}
         
		return new Query(sb.toString(),val);
	}
}
