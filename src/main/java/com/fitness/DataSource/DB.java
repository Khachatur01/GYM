package com.fitness.DataSource;

import com.fitness.DataSource.Log.Log;
import com.fitness.DataSource.Memory.DBMemory;

import java.sql.Connection;
import java.sql.SQLException;

public class DB {
    private static Connection connection = null;

    public static boolean connect() {
        connection = DBMemory.fetchConnection();
        if(connection != null) {
            Log.info("Connected to database");
            return true;
        }
        Log.error("Can't connect to database");
        return false;
    }

    public static void setConnection(Connection connection) {
        DB.connection = connection;
    }
    public static Connection getConnection(){
        return connection;
    }

    public static void disconnect() {
        if(connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                Log.error("Can't disconnect from database");
            }
            connection = null;
        } else {
            Log.info("Disconnected from database");
        }
    }
}