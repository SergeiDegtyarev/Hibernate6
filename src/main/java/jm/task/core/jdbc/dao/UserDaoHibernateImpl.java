package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoHibernateImpl implements UserDao {
    private final static Logger LOGGER = Logger.getLogger(UserDaoHibernateImpl.class.getName());
    private final SessionFactory sessionFactory = Util.getSessionFactory();
    private Transaction transaction;
    private SessionFactory SessionFactory;
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession();) {
            transaction = session.beginTransaction();
            String sql = "CREATE TABLE IF NOT EXISTS users " +
                    "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
                    "age TINYINT NOT NULL)";

            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
            System.out.println("Таблица в базе данных успешно создана.");
        } catch (Exception e) {
            transaction.rollback();
            LOGGER.log(Level.WARNING, "Выполнился rollback");
        }
    }

    @Override
    public void dropUsersTable() {
        try(Session session = Util.getSessionFactory().openSession();)
        {
            transaction = session.beginTransaction();
            String sql = "DROP TABLE IF EXISTS users";
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
            System.out.println("Таблица из базы данных удалена.");
        } catch (Exception e) {
            transaction.rollback();
            LOGGER.log(Level.WARNING, "Выполнился rollback");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try(Session session = Util.getSessionFactory().openSession();) {
            transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            transaction.commit();
            System.out.println("Пользователь " + name + " добавлен в базу.");
        } catch (Exception e) {
            transaction.rollback();
            LOGGER.log(Level.WARNING, "Выполнился rollback");
        }
    }

    @Override
    public void removeUserById(long id) {
        try(Session session = Util.getSessionFactory().openSession(); ) {
            transaction = session.beginTransaction();
            User users = session.get(User.class, id);
            session.delete(users);
            System.out.println("Пользователь удален по ID.");
        } catch (Exception e) {
            transaction.rollback();
            LOGGER.log(Level.WARNING, "Выполнился rollback");
        }
    }

    @Override
    public List<User> getAllUsers() {
        List <User> users = new ArrayList<>();
        try(Session session = Util.getSessionFactory().openSession();)
        {
            transaction = session.beginTransaction();
            users = (List<User>) session.createQuery("From User").list();
            transaction.commit();
            System.out.println("Список всех данных из таблицы: " + users);
        } catch (Exception e) {
            transaction.rollback();
            LOGGER.log(Level.WARNING, "Выполнился rollback");
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try(Session session = Util.getSessionFactory().openSession();) {
            transaction = session.beginTransaction();
            Query query = session.createQuery("DELETE FROM User");
            query.executeUpdate();
            transaction.commit();
            System.out.println("Таблица очищена.");
        } catch (Exception e) {
            transaction.rollback();
            LOGGER.log(Level.WARNING, "Выполнился rollback");
            }
        }

}
