package com.fitness.Controller.Fragment.Report;

import com.fitness.Controller.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class ReportController extends GridPane implements Controller {
    public ReportController() throws IOException {
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