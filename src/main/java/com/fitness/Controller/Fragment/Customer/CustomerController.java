package com.fitness.Controller.Fragment;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class CustomerController extends GridPane {
    @FXML
    private TableView<?> customersTable;
    @FXML
    private TableColumn<?, ?> cardColumn;
    @FXML
    private TableColumn<?, ?> fullNameColumn;
    @FXML
    private TableColumn<?, ?> subscriptionColumn;
    @FXML
    private TableColumn<?, ?> phoneColumn;
    @FXML
    private TableColumn<?, ?> phone2Column;
    @FXML
    private TableColumn<?, ?> addressColumn;
    @FXML
    private Button editButton;
    @FXML
    private Button registerButton;
    @FXML
    private Button deleteButton;

    public CustomerController() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fitness/fragment/customer.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
    }
}
