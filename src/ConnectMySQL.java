import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectMySQL {
    String jdbcURL = "jdbc:mysql://localhost:3306/users";
    String db_username = "root";
    String db_password = "admin";

    ConnectMySQL() {
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, db_username, db_password);
            connection.close();
        } catch (SQLException error) {
            System.out.println("Error in connecting to MySQL server.");
            System.out.println(error);
        }
    }

    public boolean verifyUser(String user, String passwd) {
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, db_username, db_password);
            String sql = "SELECT * FROM user_info";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                String username = result.getString("username");
                String password = result.getString("password");
                if (user.equals(username) && passwd.equals(password)) {
                    return true;
                }
            }
            connection.close();

        } catch (SQLException error) {
            System.out.println("Error in connecting to MySQL server.");
            System.out.println(error);
        }
        return false;
    }
}