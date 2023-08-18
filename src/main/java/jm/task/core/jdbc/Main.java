package jm.task.core.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        String userName = "root";
        String password ="52826552";
        String connectionUrl="jdbc:mysql://localhost:3306/users";
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager.getConnection(connectionUrl, userName, password);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("create table Users (" +
                    " id bigint not null," +
                    " name VARCHAR(30)," +
                    " surname varchar(30)," +
                    " primary key (id))");
        }

    }
}
