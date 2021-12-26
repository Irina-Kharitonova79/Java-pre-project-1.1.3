package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private Connection connection = null;

    public UserDaoJDBCImpl() {
        try {
            connection = Util.getConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void runStatement(String sql) {
        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            statement.executeUpdate(sql);
            throw new SQLSyntaxErrorException();
        } catch (SQLException e) {
        }
    }

    public void createUsersTable() {
        runStatement("create table Users (\n" +
                "    ID       int unsigned auto_increment,\n" +
                "    NAME     varchar(32) not null,\n" +
                "    LASTNAME varchar(64) not null,\n" +
                "    AGE      tinyint unsigned not null,\n" +
                "    primary key (ID))");
    }

    public void dropUsersTable() {
        runStatement("drop table Users");
    }

    public void saveUser(String name, String lastName, byte age) {
        runStatement("insert into Users (NAME, LASTNAME, AGE) " +
                "values ('" + name + "', '" + lastName + "', '" + age + "')");
        System.out.println("User с именем - " + name + " добавлен в базу данных");
    }

    public void removeUserById(long id) {
        runStatement("delete from Users where ID = " + id);
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            ResultSet resultSet = statement.executeQuery("select ID, NAME, LASTNAME, AGE from Users");
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("ID"));
                user.setName(resultSet.getString("NAME"));
                user.setLastName(resultSet.getString("LASTNAME"));
                user.setAge(resultSet.getByte("AGE"));
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void cleanUsersTable() {
        runStatement("truncate table Users");
    }
}
