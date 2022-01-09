package com.fitness.DataSource;

import com.fitness.DataSource.Memory.DBMemory;

import java.sql.Connection;
import java.sql.SQLException;

public class DB {
    private static Connection connection = null;

    public static boolean connect() throws SQLException {
        connection = DBMemory.fetchConnection();
        if(connection != null) {
            System.out.println("connected to db");
            return true;
        }
        return false;
    }

    public static void setConnection(Connection connection) {
        DB.connection = connection;
    }
    public static Connection getConnection(){
        return connection;
    }

    public static void disconnect() throws SQLException {
        if(connection != null) {
            connection.close();
            connection = null;
        }
        System.out.println("disconnected from db");
    }

}