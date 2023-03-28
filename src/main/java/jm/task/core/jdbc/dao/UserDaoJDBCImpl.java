package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.getConnection;

public class UserDaoJDBCImpl implements UserDao {


    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = Util.getConn()) {
            connection.createStatement().execute(
                    "CREATE TABLE  user(\n" +
                            "    id int(3) auto_increment ,\n" +
                            "    name varchar(25) not null,\n" +
                            "    lastName varchar(25) not null,\n" +
                            "    age int null,\n" +
                            "    PRIMARY KEY (id)\n" +
                            ")");

        } catch (SQLException e) {
            dropUsersTable();
            createUsersTable();

        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConn()) {
            connection.createStatement().execute("DROP TABLE user");
        } catch (SQLException e) {
            System.out.println("Таблицы не существует");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConn()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO user" +
                            " (name, lastName, age)" +
                            "VALUES (?, ?, ?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConn()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE from user WHERE id = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public List<User> getAllUsers() {
        try (Connection connection = Util.getConn()) {
            List<User> userList = new ArrayList<>();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM user");
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getString("name"),
                        resultSet.getString("lastName"),
                        resultSet.getByte("age")
                );
                user.setId(resultSet.getLong("id"));
                userList.add(user);
            }
            return userList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConn()) {
            connection.createStatement().execute("TRUNCATE user");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
