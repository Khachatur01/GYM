package com.fitness.Controller.Fragment.Report;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class ReportController extends GridPane {
    public ReportController() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fitness/fragment/report/report.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
    }
}