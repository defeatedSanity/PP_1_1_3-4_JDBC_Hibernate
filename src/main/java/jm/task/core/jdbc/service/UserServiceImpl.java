package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDaoJDBCImpl jdbc = new UserDaoJDBCImpl();
    private UserDaoHibernateImpl hiber = new UserDaoHibernateImpl();
    public void createUsersTable() {
        hiber.createUsersTable();
    }

    public void dropUsersTable() {
        hiber.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        hiber.saveUser(name, lastName, age);
    }

    public void removeUserById(long id) {
        hiber.removeUserById(id);
    }

    public List<User> getAllUsers() {
        return hiber.getAllUsers();
    }

    public void cleanUsersTable() {
        hiber.cleanUsersTable();
    }
}
