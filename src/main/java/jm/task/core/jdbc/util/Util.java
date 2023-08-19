package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {
    private Util (){}
    private static final String userName = "root";
    private static final String password = "52826552";
    private static final String connectionUrl = "jdbc:mysql://localhost:3306/users";

    public static void updateDB(String query) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection conn = DriverManager.getConnection(connectionUrl, userName, password);
             Statement statement = conn.createStatement()) {
            statement.executeUpdate(query);
        }
    }

    public static ResultSet readDB(String query) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(connectionUrl, userName, password);
        Statement statement = conn.createStatement();
        return statement.executeQuery(query);

    }
}
