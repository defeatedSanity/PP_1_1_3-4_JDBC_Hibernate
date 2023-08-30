package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private final SessionFactory sessionFactory;

    public UserDaoHibernateImpl() {
        sessionFactory = Util.getSessionFactory();
    }


    @Override
    public void createUsersTable() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.createSQLQuery("create TABLE IF NOT EXISTS users" +
                        "(id BIGINT NOT NULL auto_increment, " +
                        "name CHAR(30) NOT NULL, " +
                        "lastname CHAR(30) NOT NULL, " +
                        "age TINYINT, primary key (id))").
                addEntity(User.class).executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    public void dropUsersTable() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.createSQLQuery("drop table if exists users").
                addEntity(User.class).executeUpdate();
        session.getTransaction().commit();
    }


    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        User user = new User(name, lastName, age);
        session.save(user);
        session.getTransaction().commit();
    }


    @Override
    public void removeUserById(long id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        User user = session.get(User.class, id);
        session.delete(user);
        session.getTransaction().commit();
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = null;
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        list = session.createQuery("FROM User").list();
        session.getTransaction().commit();
        return list;
    }

    @Override
    public void cleanUsersTable() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.createSQLQuery("delete from users").addEntity(User.class).executeUpdate();
        session.getTransaction().commit();
    }
}
