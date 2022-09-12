package hu.icellmobilsoft.atr.sample.util;

import java.sql.*;

public class DBTest {
    static final String DB_URL = "jdbc:oracle:thin:@" + System.getenv("DB_URL");
    static final String USER = System.getenv("DB_USER");
    static final String PASS = System.getenv("DB_PASS");
    static final String QUERY = "SELECT * FROM JK_DEPARTMENT";

    public static void main(String[] args) {
        // Open a connection
        try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Oracle JDBC driver loaded ok.");

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(QUERY);) {
            while (rs.next()) {
                // Display values
                System.out.print("ID: " + rs.getInt("id"));
                System.out.print(", Name: " + rs.getString("name"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
