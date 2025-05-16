package querybuilder;

public class OnClause {
	
	private String leftColumn ; 
	private String rightColumn ; 
	private String operator ; 
	private String connector ;
	
	public OnClause(String leftColumn , String operator ,String rightColumn , String connector )
	{
		this.leftColumn = leftColumn ; 
		this.rightColumn = rightColumn ; 
		this.operator = operator ; 
		this.connector = connector ;
	}
	
	public OnClause(String leftColumn , String operator ,String rightColumn )
	{
		this.leftColumn = leftColumn ; 
		this.rightColumn = rightColumn ; 
		this.operator = operator ; 
		this.connector =null ;
	}
	
	@Override
	public String toString()
	{
		if(connector==null)
		{
		return " "+"ON"+" "+leftColumn +" "+operator+" "+rightColumn  ;
		}
		else
		{
			return " "+"connector"+" "+leftColumn +" "+operator+" "+rightColumn  ;
		}
	}
	

}
