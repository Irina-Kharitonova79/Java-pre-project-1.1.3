package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Util {
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        String userName = "test";
        String password = "test";
        String connectionUrl = "jdbc:mysql://localhost:3306/test?serverTimezone=Europe/Moscow";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(connectionUrl, userName, password);
        return connection;
    }
    // реализуйте настройку соеденения с БД
}
