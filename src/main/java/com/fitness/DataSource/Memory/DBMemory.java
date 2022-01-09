package com.fitness.DataSource.Memory;

import com.fitness.Constant.Status;
import com.fitness.DataSource.DB;
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

    public static Object fetchObject() {
        try {
            return new ObjectInputStream(new FileInputStream(DBMemory.cacheDir)).readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Connection fetchConnection() {
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
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }
    public static void deleteConnection() {
        try {
            PrintWriter writer = new PrintWriter(DBMemory.cacheDir);
            writer.print("");
            writer.close();
            DB.disconnect();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}
