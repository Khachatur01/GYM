package com.fitness.DataSource.Memory;

import com.fitness.DataSource.DB;
import com.fitness.DataSource.Log.Log;
import com.fitness.Model.Database.FileConnection;
import com.fitness.Model.Database.LocalConnection;
import com.fitness.Model.Database.RemoteConnection;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBMemory {
    private static final String cacheDir =
            System.getProperty("user.home") +
            System.getProperty("file.separator") +
            "gym_cache";

    public static Object fetchConnection() {
        try {
            return new ObjectInputStream(new FileInputStream(DBMemory.cacheDir)).readObject();
        } catch (IOException | ClassNotFoundException e) {
            Log.warning("Can't read from serialized cache");
            return null;
        }
    }

    public static Connection createConnection() {
        Connection connection = null;
        try {
            Object fetched = new ObjectInputStream(new FileInputStream(DBMemory.cacheDir)).readObject();

            if(fetched instanceof LocalConnection) {
                LocalConnection localConnection = (LocalConnection) fetched;
                String URL = "jdbc:mysql://localhost:" + localConnection.getPort() + "/" + localConnection.getDatabase();
                connection = DriverManager.getConnection(URL, localConnection.getUsername(), localConnection.getPassword());
            } else if(fetched instanceof RemoteConnection) {
                RemoteConnection remoteConnection = (RemoteConnection) fetched;
                String URL = "jdbc:mysql://" + remoteConnection.getURI() + "/" + remoteConnection.getDatabase();
                connection = DriverManager.getConnection(URL, remoteConnection.getUsername(), remoteConnection.getPassword());
            } else if(fetched instanceof FileConnection) {
                FileConnection fileConnection = (FileConnection) fetched;
                String URL = "jdbc:sqlite:" + fileConnection.getFile();
                connection = DriverManager.getConnection(URL);
            }
        } catch (IOException | ClassNotFoundException e) {
            Log.warning("Can't read connection from serialized cache");
        } catch (SQLException e) {
            Log.warning("Can't connect to database");
        }
        return connection;
    }
    public static void storeConnection(Serializable connection) {
        try {
            File cacheFile = new File(DBMemory.cacheDir);
            cacheFile.createNewFile();  /* if file already exists will do nothing */
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(cacheFile, false));

            objectOutputStream.writeObject(connection);
            objectOutputStream.close();
        } catch (IOException e) {
            Log.error("Can't write serialized object");
        }
    }
    public static void deleteConnection() {
        try {
            PrintWriter writer = new PrintWriter(DBMemory.cacheDir);
            writer.print("");
            writer.close();
            DB.disconnect();
        } catch (IOException e) {
            Log.error("Can't disconnect from database");
        }
    }
}
