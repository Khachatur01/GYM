package com.fitness.Controller.Fragment.Employee;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class EmployeeController extends GridPane {
    @FXML
    private TableView<?> employeesTable;
    @FXML
    private TableColumn<?, ?> positionColumn;
    @FXML
    private TableColumn<?, ?> fullNameColumn;
    @FXML
    private TableColumn<?, ?> phoneColumn;
    @FXML
    private TableColumn<?, ?> phone2Column;
    @FXML
    private TableColumn<?, ?> addressColumn;
    @FXML
    private Button editButton;
    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;

    public EmployeeController() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fitness/fragment/employee/employee.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
    }
}
