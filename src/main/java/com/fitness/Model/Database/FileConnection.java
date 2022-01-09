package com.fitness.Model.Database;

import java.io.Serializable;

public class FileConnection implements Serializable {
    private String file;

    public FileConnection() {
    }
    public FileConnection(String file) {
        this.file = file;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
