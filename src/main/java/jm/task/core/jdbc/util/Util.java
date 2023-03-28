package jm.task.core.jdbc.util;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import static java.sql.DriverManager.getConnection;

import java.sql.*;
import java.util.List;

public class Util {

    public static void main(String[] args) {


    }

    public static Connection getConn() throws SQLException {
        return getConnection("jdbc:mysql://localhost/db_java_pp", "user", "user");

    }
}


