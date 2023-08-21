package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import java.util.Properties;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import java.sql.*;

public class Util {
    private Util (){}
    private static SessionFactory sessionFactory;
    public static SessionFactory getSessionFactory(String operation) {
            try {
                Configuration configuration = new org.hibernate.cfg.Configuration();
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, connectionUrl);
                settings.put(Environment.USER, userName);
                settings.put(Environment.PASS, password);
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");

                settings.put(Environment.SHOW_SQL, "true");

                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                settings.put(Environment.HBM2DDL_AUTO, operation);

                configuration.setProperties(settings);

                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);


            } catch (Exception e) {
                e.printStackTrace();
            }
        return sessionFactory;
    }

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
