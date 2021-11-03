package com.fitness.Controller.Fragment.Archive;

import com.fitness.Controller.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class ArchiveController extends GridPane implements Controller {
    public ArchiveController() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fitness/fragment/report/report.fxml"));
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