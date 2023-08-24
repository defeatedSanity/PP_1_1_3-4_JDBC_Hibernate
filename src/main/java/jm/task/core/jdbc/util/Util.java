package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import java.util.Properties;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import java.sql.*;

public class Util {
    private Util (){}
    private static SessionFactory sessionFactory;
    private static Connection connection;
    private static Statement statement;
    private static final String USER_NAME = "forlearn";
    private static final String PASSWORD = "1";
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/for_learn";

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    public static SessionFactory getSessionFactory() {

        if (sessionFactory == null) {
            Configuration configuration = getConfiguration();

            configuration.addAnnotatedClass(User.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);



        }
        return sessionFactory;
    }


    private static Configuration getConfiguration() {
        Configuration configuration = new Configuration();
        Properties settings = new Properties();
        settings.put(AvailableSettings.DRIVER, DRIVER);
        settings.put(AvailableSettings.URL, CONNECTION_URL);
        settings.put(AvailableSettings.USER, USER_NAME);
        settings.put(AvailableSettings.PASS, PASSWORD);
        settings.put(AvailableSettings.DIALECT, "org.hibernate.dialect.MySQL5Dialect");

        settings.put(AvailableSettings.SHOW_SQL, "true");

        settings.put(AvailableSettings.CURRENT_SESSION_CONTEXT_CLASS, "thread");

        settings.put(AvailableSettings.HBM2DDL_AUTO, "update");

        configuration.setProperties(settings);
        return configuration;
    }


    public static void updateDB(String query) {
        setConnection();
        setStatement();
        try {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static ResultSet readDB(String query) {
        setConnection();
        setStatement();
        try {
            return statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void setStatement () {
        if (statement == null) {
            try {
                statement = connection.createStatement();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    private static void setConnection () {
        if (connection == null) {
            try {
                Class.forName(DRIVER);
                connection = DriverManager.getConnection(CONNECTION_URL, USER_NAME, PASSWORD);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
