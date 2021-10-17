package com.fitness.DataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
    private static final String URL = "jdbc:mysql://localhost:3306/Fitness";
    private static final String username = "root";
    private static final String password = "admin8402";
    private static Connection connection = null;

    public static void connect() throws SQLException {
        connection = DriverManager.getConnection(URL, username, password);
        System.out.println("connected to db");
    }

    public static Connection getConnection(){
        return connection;
    }

    public static void disconnect() throws SQLException {
        if(connection != null)
            connection.close();

        System.out.println("disconnected from db");
    }
}
