package com.fitness.Controller.Fragment;

import com.fitness.Controller.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class GeneralController extends GridPane implements Controller {
    public GeneralController() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fitness/fragment/general.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();

        initListeners();
    }

    private void initListeners() {

    }

    @Override
    public void start() {
        this.makeActive();
    }

    @Override
    public void stop() {
    }
}
