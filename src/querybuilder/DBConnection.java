package querybuilder;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.zoho.training.exceptions.TaskException;
import com.zoho.training.property.PropertiesHelper;



public class DBConnection {
	   private static Connection conn;
	   private static String url;
	    private static String user;
	    private static String password;
	    private static String driver;
	    private DBConnection() {
	        
	    }

	    
	    public static Connection getConnection() throws TaskException {
	    	
	    	try {
	    		if (url == null || user == null || password == null || driver == null) {
	                loadConfigProperties();
	            }
	        if (conn == null || conn.isClosed()) {

	            Class.forName(driver);
	            conn = DriverManager.getConnection(url, user, password);
	        
	        }
	        return conn;
	    	}
	    	catch(SQLException | ClassNotFoundException e){
	    		throw new TaskException(e.getMessage(),e);
	    	}
	    }

	    private static void loadConfigProperties() throws TaskException {
	    	File file = new File("config.properties");
    		PropertiesHelper helper = new PropertiesHelper();
    		Properties configProperty = helper.getProperty();
    		try {
				helper.loadProperty(configProperty, file);
			} catch (IOException | TaskException e) {
				throw new TaskException(e.getMessage(),e);
			}
             url =configProperty.getProperty("DB_URL");
             user = configProperty.getProperty("DB_USER");
             password = configProperty.getProperty("DB_PASSWORD");
            driver = configProperty.getProperty("DB_DRIVER");
			
		}


		public static void closeConnection() throws TaskException{
	        try {
	            if (conn != null && !conn.isClosed()) {
	                conn.close();
	                System.out.println("Database Connection Closed!");
	            }
	        } catch (SQLException e) {
	        	throw new TaskException(e.getMessage(),e);
	            
	        }
	    }
	    
	   
}

