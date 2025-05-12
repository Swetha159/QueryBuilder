package querybuilder;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

import com.zoho.training.exceptions.TaskException;
import com.zoho.training.property.PropertiesHelper;


public class ConfigFileTest {
	public static void main(String args[]) throws TaskException,IOException
	
	{
		File file = new File("config.properties");
		PropertiesHelper helper = new PropertiesHelper();
		Properties property = helper.getProperty();
		helper.addProperty(property,"DB_USER", "SwethaS");
		helper.addProperty(property,"DB_PASSWORD", "1765");
		helper.addProperty(property,"DB_URL", "jdbc:mysql://localhost:3306/querybuilder");
		helper.addProperty(property,"DB_DRIVER", "com.mysql.jdbc.Driver");
		helper.storePropertyInFile(property,"DB Connection",file);
		System.out.println("Properties Added");
	}
}