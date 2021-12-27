package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static Connection connection;

    static {
        try {
            connection = Util.getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            statement.executeUpdate("create table Users (\n" +
                    "    ID       int unsigned auto_increment,\n" +
                    "    NAME     varchar(32) not null,\n" +
                    "    LASTNAME varchar(64) not null,\n" +
                    "    AGE      tinyint unsigned not null,\n" +
                    "    primary key (ID))");
            throw new SQLSyntaxErrorException();
        } catch (SQLException e) {
        }
    }

    public void dropUsersTable() {
        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            statement.executeUpdate("drop table Users");
            throw new SQLSyntaxErrorException();
        } catch (SQLException e) {
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement("insert into Users (NAME, LASTNAME, AGE) " +
                    "values (?, ?, ?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (SQLException e) {
        }
    }

    public void removeUserById(long id) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement("delete from Users where ID = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from Users");
            ResultSet resultSet = preparedStatement.executeQuery();
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
        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            statement.executeUpdate("truncate table Users");
            throw new SQLSyntaxErrorException();
        } catch (SQLException e) {
        }
    }
}
