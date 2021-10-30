package com.fitness.Controller.Fragment.Subscription;

import com.fitness.Controller.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class AddSubscriptionController extends GridPane implements Controller {
    public AddSubscriptionController() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fitness/fragment/subscription/add_subscription.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
    }

    @Override
    public void start() {
        makeActive();
    }

    @Override
    public void stop() {

    }
}
