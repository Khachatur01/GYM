package com.fitness.Model.Database;

import java.io.Serializable;

public class LocalConnection implements Serializable {
    private int port;
    private String database;
    private String username;
    private String password;

    public LocalConnection() {}
    public LocalConnection(int port, String database, String username, String password) {
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
