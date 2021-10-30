package com.fitness.Controller.Fragment.Subscription;

import com.fitness.Controller.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class EditSubscriptionController extends GridPane implements Controller {
    public EditSubscriptionController() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fitness/fragment/subscription/edit_subscription.fxml"));
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
