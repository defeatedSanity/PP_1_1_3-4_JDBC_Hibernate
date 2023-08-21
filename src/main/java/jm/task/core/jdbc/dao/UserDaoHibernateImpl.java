package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    Transaction transaction = null;

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory("create-only").openSession()) {

            // start the transaction
            transaction = session.beginTransaction();
            session.createNativeQuery("create TABLE IF NOT EXISTS users(id BIGINT NOT NULL auto_increment, name CHAR(30) NOT NULL, surname CHAR(30) NOT NULL, age TINYINT, primary key (id))");
            // save student object
            // commit transction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory("drop").openSession()) {

            // start the transaction
            transaction = session.beginTransaction();

            // save student object
            session.createNativeQuery("drop table if exists users");

            // commit transction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        try (Session session = Util.getSessionFactory("update").openSession()) {

            // start the transaction
            transaction = session.beginTransaction();

            // save student object
            session.save(user);

            // commit transction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory("update").openSession()) {

            // start the transaction
            transaction = session.beginTransaction();

            User user = session.get(User.class, id);
            session.delete(user);
            // commit transction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = null;
        try (Session session = Util.getSessionFactory("update").openSession()) {

            // start the transaction
            transaction = session.beginTransaction();

            list = session.createQuery("FROM User").list();
            // commit transction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory("create-drop").openSession()) {

            // start the transaction
            transaction = session.beginTransaction();

            // commit transction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
