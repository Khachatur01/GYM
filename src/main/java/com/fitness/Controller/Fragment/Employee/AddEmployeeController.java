package com.fitness.Controller.Fragment.Employee;

import com.fitness.Controller.Constant.Fragment;
import com.fitness.Controller.Controller;
import com.fitness.Model.Work.Position;
import com.fitness.Model.Work.Employment;
import com.fitness.Service.Clear;
import com.fitness.Service.Person.EmployeeService;
import com.fitness.Service.Work.PositionService;
import com.fitness.Service.Work.EmploymentService;
import com.fitness.Window;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.sql.SQLException;

public class AddEmployeeController extends GridPane implements Controller {
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
    private ComboBox<Position> positionComboBox;
    @FXML
    private Button previousButton;
    @FXML
    private Button addButton;

    private EmployeeService employeeService = new EmployeeService();
    private EmploymentService employmentService = new EmploymentService();
    private PositionService positionService = new PositionService();

    public AddEmployeeController() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fitness/fragment/employee/add_employee.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
    }

    private void initListeners(){
        addButton.setOnAction(event -> {
            //return null when something went wrong
            if(employeeService.add(
                    nameTextField,
                    surnameTextField,
                    phoneTextField,
                    phone2TextField,
                    addressTextField,
                    positionComboBox) != null) {
                this.stop();
                Window.getFragment(Fragment.CUSTOMER).start();
            }
        });

        previousButton.setOnAction(event -> {
            employeeService.setCache(null);
            this.stop();
            Window.getFragment(Fragment.EMPLOYEE).start();
        });
    }

    public void initComboBoxes() throws SQLException {
        positionComboBox.setItems(FXCollections.observableArrayList(positionService.getActual()));
    }

    @Override
    public void start() {
        makeActive();
        initListeners();
        try {
            initComboBoxes();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        Clear.textField(
                nameTextField,
                surnameTextField,
                phoneTextField,
                phone2TextField,
                addressTextField
        );
        Clear.comboBox(positionComboBox);
    }
}
