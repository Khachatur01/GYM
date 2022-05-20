package com.fitness.DataSource;

import com.fitness.DataSource.Log.Log;
import com.fitness.DataSource.Memory.DBMemory;
import com.fitness.Model.Database.FileConnection;
import com.fitness.Model.Database.LocalConnection;
import com.fitness.Model.Database.RemoteConnection;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DB {
    private static Connection connection = null;

    public static boolean canConnect() {
        return DBMemory.createConnection() != null;
    }

    public static void recoverLastConnection() {
        Object connectionObject = DBMemory.fetchConnection();
        if(connectionObject instanceof LocalConnection) {
            LocalConnection localConnection = (LocalConnection) connectionObject;
            connect(localConnection);
        } else if(connectionObject instanceof RemoteConnection) {
            RemoteConnection remoteConnection = (RemoteConnection) connectionObject;
            connect(remoteConnection);
        } else if(connectionObject instanceof FileConnection) {
            FileConnection fileConnection = (FileConnection) connectionObject;
            connect(fileConnection);
        }
    }

    public static void connect(Serializable connection) {
        DBMemory.storeConnection(connection);
        DB.connection = DBMemory.createConnection();
        if(DB.connection != null) {
            Log.info("Connected to database");
        } else {
            Log.error("Can't connect to database", null);
        }
    }

    public static boolean connected() {
        if(DB.connection != null) {
            Log.info("Connected to database");
            return true;
        }
        Log.error("Can't connect to database", null);
        return false;
    }
    public static String getDatabase() throws SQLException {
        Object connectionObject = DBMemory.fetchConnection();
        if(connectionObject instanceof LocalConnection) {
            LocalConnection localConnection = (LocalConnection) connectionObject;
            return localConnection.getDatabase();
        } else if(connectionObject instanceof RemoteConnection) {
            RemoteConnection remoteConnection = (RemoteConnection) connectionObject;
            return remoteConnection.getDatabase();
        } else if(connectionObject instanceof FileConnection) {
            return null;
        } else {
            throw new SQLException("Not Connected to database");
        }
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
                Log.error("Can't disconnect from database", e);
            }
            connection = null;
        } else {
            Log.info("Disconnected from database");
        }
    }

    public static List<String> getExistingTables(String database) {
        List<String> tables = new ArrayList<>();
        Connection connection = DB.getConnection();
        if(connection != null) {
            try {
                PreparedStatement preparedStatement;
                if (database == null) { /* sqlite connection */
                    preparedStatement = connection.prepareStatement(
                            "SELECT name FROM sqlite_master"
                    );
                } else { /* local or remote connection */
                    preparedStatement = connection.prepareStatement(
                            "SELECT TABLE_NAME " +
                                "FROM information_schema.tables WHERE TABLE_SCHEMA = ?"
                    );
                    preparedStatement.setString(1, database);
                }
                ResultSet result = preparedStatement.executeQuery();
                while (result.next()) {
                    tables.add(result.getString(1)); /* get first column. for sqlite column name is 'name'. For others 'TABLE_NAME' */
                }
            } catch (SQLException ignored) {
                ignored.printStackTrace();
            }
        }
        return tables;
    }

    public static List<String> getValidTables() {
        return new ArrayList<>(Arrays.asList(
                "archive",
                "customer",
                "employee",
                "employee_position",
                "employment",
                "position",
                "subscription",
                "subscription_employment",
                "working_days"
        ));
    }
}
