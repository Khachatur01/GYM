package com.fitness.Controller.Fragment.Enter;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class WithoutCardController extends GridPane {
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField surnameTextField;
    @FXML
    private TextField phoneTextField;
    @FXML
    private TextField phone2TextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private TextField priceTextField;
    @FXML
    private ComboBox<?> serviceComboBox;
    @FXML
    private ComboBox<?> employeeComboBox;
    @FXML
    private CheckBox pastTenseCheckbox;
    @FXML
    private DatePicker tenseDatePicker;
    @FXML
    private HBox tenseHBox;
    @FXML
    private TextField tenseHourTextField;
    @FXML
    private TextField tenseMinuteTextField;
    @FXML
    private Button enterButton;

    public WithoutCardController() throws IOException{
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/com/fitness/fragment/enter/without-card.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
    }

}
