package querybuilder;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import com.zoho.training.exceptions.TaskException;

public class QueryBuilderTest {

    public static void main(String[] args) {
        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement()) {

         
//            String createQuery = new QueryBuilder()
//                    .create("SwethaSS")
//                    .columnDescription(
//                            "nominee_id BIGINT PRIMARY KEY AUTO_INCREMENT",
//                            "customer_id BIGINT",
//                            "account_no BIGINT",
//                            "nominee_name VARCHAR(25)"
//                    ).build();
//            System.out.println(createQuery);
//            statement.executeUpdate(createQuery);

         
            String insertQuery = new QueryBuilder()
                    .insert("SwethaSS")
                    .values(111, 12, 13, "Charlie")
                    .values(121, 13, 14, "Denny")
                    .values(131, 14, 15, "Charlie")
                    .values(141, 14, 16, "Charlie")
                    .build();
            System.out.println(insertQuery);
            statement.executeUpdate(insertQuery);

    
            String groupQuery = new QueryBuilder()
                    .select("nominee_name")
                    .count("*", "count")
                    .from("SwethaSS")
                    .groupBy("nominee_name")
                    .having("COUNT(*) > 1")
                    .build();
            System.out.println(groupQuery);
            try (ResultSet rs = statement.executeQuery(groupQuery)) {
                while (rs.next()) {
                    System.out.println(rs.getString("nominee_name") + " - " + rs.getInt("count"));
                }
            }

            Map<String, String> caseMap = new HashMap<>();
            caseMap.put("nominee_name = 'Charlie'", "'Group A'");
            caseMap.put("nominee_name = 'Denny'", "'Group B'");
            String updateQuery = new QueryBuilder()
                    .update("SwethaSS")
                    .setConditional("nominee_name", caseMap, "'Unknown'")
                    .build();
            System.out.println(updateQuery);
            statement.executeUpdate(updateQuery);

          
            String selectUpdated = new QueryBuilder()
                    .select("nominee_id", "nominee_name")
                    .from("SwethaSS")
                    .build();
            try (ResultSet rs = statement.executeQuery(selectUpdated)) {
                while (rs.next()) {
                    System.out.println(rs.getInt("nominee_id") + " - " + rs.getString("nominee_name"));
                }
            }

         
            String deleteQuery = new QueryBuilder()
                    .deleteFrom("SwethaSS")
                    .where("nominee_id", "=", 11)
                    .build();
            System.out.println(deleteQuery);
            statement.executeUpdate(deleteQuery);

        
            QueryBuilder sub = new QueryBuilder()
                    .select("nominee_id", "account_no")
                    .from("SwethaSS")
                    .where("account_no", ">", 13);

            String subQuery = new QueryBuilder()
                    .select("nominee_id")
                    .fromSubquery(sub, "SubTable")
                    .where("account_no", "<", 16)
                    .build();

          
            System.out.println(subQuery);
            try (ResultSet rs = statement.executeQuery(subQuery)) {
                while (rs.next()) {
                    System.out.println("From subquery: nominee_id = " + rs.getInt("nominee_id"));
                }
            }

        } catch (SQLException | TaskException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
	
