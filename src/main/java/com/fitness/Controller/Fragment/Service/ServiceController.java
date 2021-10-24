package com.fitness.Controller.Fragment;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class ServiceController extends GridPane {
    public ServiceController() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fitness/fragment/service.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
    }
}