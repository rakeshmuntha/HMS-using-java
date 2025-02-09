import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/HMS"; // Your MySQL URL
        String user = "root";  // MySQL username
        String password = "rakesh@2231"; // MySQL password

        try {
            // Establish a connection
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to MySQL successfully!");

            // Create a statement
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FRO.ljdnf;lsanflaskM doctors";

            // Execute query
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") +
                        ", Name: " + rs.getString("name"));
            }

            // Close connections
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}