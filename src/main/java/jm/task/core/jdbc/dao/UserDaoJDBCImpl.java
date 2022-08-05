package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoJDBCImpl implements UserDao {
    private final static Logger LOGGER = Logger.getLogger(UserDaoJDBCImpl.class.getName());
    private Connection conn = Util.getConnection();

    public UserDaoJDBCImpl() {

    }
    public void createUsersTable()  {
        try(Statement stmt = conn.createStatement();)
        {
            conn.setAutoCommit(false);
            String sql = "CREATE TABLE IF NOT EXISTS users" +
                    "(id BIGINT AUTO_INCREMENT, " +
                    " name VARCHAR(255) NOT NULL, " +
                    " lastName VARCHAR(255) NOT NULL, " +
                    " age TINYINT NOT NULL, " +
                    " PRIMARY KEY ( id ))";
            stmt.executeUpdate(sql);
            conn.commit();
            System.out.println("Таблица в базе данных успешно создана.");
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Ошибка при создании таблицы.");
            try {
                conn.rollback();
            } catch (SQLException ex) {
                LOGGER.log(Level.WARNING, "Ошибка в вызове метода rollback.");
            }
        }
    }

    public void dropUsersTable() {
        try(Statement stmt = conn.createStatement();)
        {
            conn.setAutoCommit(false);
            String sql = "DROP TABLE IF EXISTS users";
            stmt.executeUpdate(sql);
            conn.commit();
            System.out.println("Таблица из базы данных удалена.");
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Ошибка при удалении таблицы из базы данных.");
            try {
                conn.rollback();
            } catch (SQLException ex) {
                LOGGER.log(Level.WARNING, "Ошибка в вызове метода rollback.");
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try(Statement stmt = conn.createStatement();) {
            conn.setAutoCommit(false);
            String sql = "INSERT INTO users (name,lastName, age) Values (?,?,?);";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, lastName);
            pstmt.setByte(3,age);
            pstmt.executeUpdate();
            conn.commit();

            System.out.println("Пользователь " + name + " добавлен в базу.");
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Ошибка при сохрании пользователя в таблицу.");
            try {
                conn.rollback();
            } catch (SQLException ex) {
                LOGGER.log(Level.WARNING, "Ошибка в вызове метода rollback.");
            }
        }

    }

    public void removeUserById(long id) {
        try(Connection conn = Util.getConnection(); ) {
            conn.setAutoCommit(false);
            PreparedStatement  ps = conn.prepareStatement("DELETE FROM users WHERE id = ?");
            ps.setLong(1, id);
            ps.executeUpdate();
            conn.commit();
            System.out.println("Пользователь удален по ID.");
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Ошибка при удалении пользователя.");
            try {
                conn.rollback();
            } catch (SQLException ex) {
                LOGGER.log(Level.WARNING, "Ошибка в вызове метода rollback.");
            }
        }
    }

    public List<User> getAllUsers() {
        List <User> users = new ArrayList<>();
        try(
            Statement stmt = conn.createStatement();)
        {
            conn.setAutoCommit(false);
            String sql = "SELECT * FROM users";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                 User user = new User();
                 user.setId(rs.getLong("id"));
                 user.setName(rs.getString("name"));
                 user.setLastName(rs.getString("lastName"));
                 user.setAge(rs.getByte("age"));
                 users.add(user);
            }
            conn.commit();
            System.out.println("Список всех данных из таблицы: " + users);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Ошибка при получении данных из таблицы.");
            try {
                conn.rollback();
            } catch (SQLException ex) {
                LOGGER.log(Level.WARNING, "Ошибка в вызове метода rollback.");
            }
        }
        return users;
    }

    public void cleanUsersTable() {
        Connection conn = Util.getConnection();
        try(
            Statement stmt = conn.createStatement();
        ) {
            conn.setAutoCommit(false);
            String sql = "TRUNCATE userbd.users";
            stmt.executeUpdate(sql);
            conn.commit();
            System.out.println("Таблица очищена.");
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Ошибка при очистки таблицы.");
            try {
                conn.rollback();
            } catch (SQLException ex) {
                LOGGER.log(Level.WARNING, "Ошибка в вызове метода rollback.");
            }
        }
    }
}
