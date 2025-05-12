package querybuilder;

import java.util.List;

public class Query {

	private String query ;
	private List<Object> values ;
	
	public Query(String query, List<Object> values)
	{
		this.query=query;
		this.values = values;
	}
	
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public List<Object> getValues() {
		return values;
	}
	public void setValues(List<Object> values) {
		this.values = values;
	} 
}
