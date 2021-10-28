package com.fitness.Controller.Fragment.Customer;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class EditCustomerController extends GridPane {
    @FXML
    private TextField addressTextField;
    @FXML
    private TextField cardTextField;
    @FXML
    private Button editButton;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField phone2TextField;
    @FXML
    private TextField phoneTextField;
    @FXML
    private Button previousButton;
    @FXML
    private ComboBox<?> subscriptionComboBox;
    @FXML
    private TextField surnameTextField;

    public EditCustomerController() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fitness/fragment/employee/employee.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
    }
}
