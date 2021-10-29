package com.fitness.Controller.Fragment;

import com.fitness.Controller.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class SettingsController extends GridPane implements Controller {
    public SettingsController() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fitness/fragment/settings.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
    }

    @Override
    public void start() {

    }
}