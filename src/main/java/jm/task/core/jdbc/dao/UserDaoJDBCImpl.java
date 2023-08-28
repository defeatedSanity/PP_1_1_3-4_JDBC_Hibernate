package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDaoJDBCImpl implements UserDao {
    private Statement statement;

    public Statement getStatement() {
        if (statement == null) {
            try {
                statement = Objects.requireNonNull(Util.getConnection()).createStatement();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return statement;
    }

    public ResultSet readDB(String query) {
        try {
            return getStatement().executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void updateDB(String update) {
        try {
            getStatement().executeUpdate(update);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void createUsersTable() {
        updateDB("create TABLE " +
                "IF NOT EXISTS users" +
                "(id BIGINT NOT NULL auto_increment," +
                " name CHAR(30) NOT NULL," +
                " surname CHAR(30) NOT NULL," +
                " age TINYINT, primary key (id))");

    }
    @Override
    public void dropUsersTable() {
        updateDB("drop table if exists users");
    }
    @Override
    public void saveUser(String name, String lastName, byte age) {
        updateDB(String.format("insert into users(name, surname, age) values ('%s', '%s', '%d');",
                name, lastName, age));
        System.out.printf("User с именем - %s добавлен в базу данных %n",
                name);
    }
    @Override
    public void removeUserById(long id) {
        updateDB(String.format("delete from users where id='%d'", id));
    }
    @Override
    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        try (ResultSet userSet = readDB("select * from users;")) {
            while (userSet.next()) {
                result.add(new User(
                        userSet.getString(2),
                        userSet.getString(3),
                        userSet.getByte(4)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;

    }
    @Override
    public void cleanUsersTable() {
        updateDB("delete from users");
    }
}
