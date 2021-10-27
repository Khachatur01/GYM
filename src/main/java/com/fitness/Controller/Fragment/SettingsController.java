package com.fitness.Controller.Fragment;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class SettingsController extends GridPane {
    public SettingsController() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fitness/fragment/settings.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
    }
}