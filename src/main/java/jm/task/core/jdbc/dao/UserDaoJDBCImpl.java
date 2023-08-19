package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public void tryCatchUpdate (String query) {
        try {
            Util.updateDB(query);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public ResultSet tryCatchRead (String query) {
        try {
            return Util.readDB(query);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void createUsersTable() {
        tryCatchUpdate("create TABLE IF NOT EXISTS users(id BIGINT NOT NULL auto_increment, name CHAR(30) NOT NULL, surname CHAR(30) NOT NULL, age TINYINT, primary key (id))");

    }

    public void dropUsersTable() {
        tryCatchUpdate("drop table if exists users");
    }

    public void saveUser(String name, String lastName, byte age) {
        tryCatchUpdate(String.format("insert into users(name, surname, age) values ('%s', '%s', '%d');",
                name, lastName, age));
        System.out.printf("User с именем - %s добавлен в базу данных%n",
                name);
    }

    public void removeUserById(long id) {
        tryCatchUpdate(String.format("delete from users where id='%d'", id));
    }

    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        try (ResultSet userSet = tryCatchRead("select * from users;");) {
            while (userSet.next()) {
                result.add (new User(
                        userSet.getString(2),
                        userSet.getString(3),
                        userSet.getByte(4)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;

    }

    public void cleanUsersTable() {
        tryCatchUpdate("delete from users");
    }
}
