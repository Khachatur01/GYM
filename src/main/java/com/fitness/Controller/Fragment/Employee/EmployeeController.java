package com.fitness.Controller.Fragment;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class EmployeeController extends GridPane {
    public EmployeeController() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fitness/fragment/employee.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
    }
}
