package com.fitness.Controller.Fragment.Enter;

import com.fitness.Controller.Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class WithCardController extends GridPane implements Controller {
    @FXML
    private TextField cardTextField;
    @FXML
    private ComboBox<?> serviceComboBox;
    @FXML
    private ComboBox<?> employeeComboBox;
    @FXML
    private Label fullNameLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label phone2Label;
    @FXML
    private Label addressLabel;
    @FXML
    private Label subscriptionLabel;
    @FXML
    private Label lastVisitLabel;
    @FXML
    private TableView<?> serviceTable;
    @FXML
    private TableColumn<?, ?> serviceColumn;
    @FXML
    private TableColumn<?, ?> quantityColumn;
    @FXML
    private CheckBox pastTenseCheckbox;
    @FXML
    private HBox tenseHBox;
    @FXML
    private DatePicker tenseDatePicker;
    @FXML
    private TextField tenseHourTextField;
    @FXML
    private TextField tenseMinuteTextField;
    @FXML
    private Button enterButton;

    public WithCardController() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fitness/fragment/enter/with-card.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
    }


    @Override
    public void start() {

    }
}
