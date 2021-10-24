package com.fitness.Controller.Fragment;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class SubscriptionController extends GridPane {
    public SubscriptionController() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fitness/fragment/subscription.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
    }
}