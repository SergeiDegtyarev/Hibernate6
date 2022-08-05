package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import org.hibernate.SessionFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        SessionFactory sessionFactory = Util.getSessionFactory();

//        UserService user = new UserServiceImpl();
//        user.createUsersTable();
//        user.saveUser("Иван","Сергеевич", (byte) 50);
//        user.saveUser("Сергей","Александрович", (byte) 28);
//        user.saveUser("Елена","Владимировна", (byte) 36);
//        user.saveUser("Антон","Антонович", (byte) 21);
//        user.getAllUsers();
//        user.cleanUsersTable();
//        user.dropUsersTable();


    }

}
