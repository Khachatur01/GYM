package com.fitness;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            new Launcher();
            Launcher.main(args);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
