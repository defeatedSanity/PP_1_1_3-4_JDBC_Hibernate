package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;

import java.util.Properties;

import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.*;

import static org.hibernate.cfg.Environment.*;

public class Util {
    private Util() {
    }

    private static final String USER_NAME = "forlearn";
    private static final String PASSWORD = "1";
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/for_learn";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(CONNECTION_URL, USER_NAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties settings = new Properties();
                settings.put(AvailableSettings.DRIVER, DRIVER);
                settings.put(URL, CONNECTION_URL);
                settings.put(USER, USER_NAME);
                settings.put(PASS, PASSWORD);
                settings.put(DIALECT, "org.hibernate.dialect.MySQLDialect");
                settings.put(SHOW_SQL, "true");

                settings.put(CURRENT_SESSION_CONTEXT_CLASS, "thread");

                settings.put(HBM2DDL_AUTO, "update");

                configuration.setProperties(settings);

                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}

