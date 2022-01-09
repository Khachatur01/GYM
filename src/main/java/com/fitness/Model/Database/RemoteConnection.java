package com.fitness.Model.Database;

import java.io.Serializable;

public class RemoteConnection implements Serializable {
    private String URI;
    private String database;
    private String username;
    private String password;

    public RemoteConnection() {}
    public RemoteConnection(String URI, String database, String username, String password) {
        this.URI = URI;
        this.database = database;
        this.username = username;
        this.password = password;
    }

    public String getURI() {
        return URI;
    }

    public void setURI(String URI) {
        this.URI = URI;
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
