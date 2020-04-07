package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Connection connection = null;

    public static Connection getInstance()
    {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/musicalbums", "testing", "test");
            } catch (SQLException e) {
                System.out.println("Could not connect to the database.");
            }
        }
        return connection;
    }
}
