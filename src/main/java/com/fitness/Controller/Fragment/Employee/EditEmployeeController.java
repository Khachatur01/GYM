package com.fitness.Controller.Fragment.Employee;

import com.fitness.Controller.Constant.Fragment;
import com.fitness.Controller.Controller;
import com.fitness.Model.Person.Employee;
import com.fitness.Model.Work.Position;
import com.fitness.Service.Clear;
import com.fitness.Service.Fill;
import com.fitness.Service.Person.EmployeeService;
import com.fitness.Service.Work.EmploymentService;
import com.fitness.Service.Work.PositionService;
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

public class EditEmployeeController extends GridPane implements Controller {
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
    private Button editButton;

    private EmployeeService employeeService = new EmployeeService();
    private PositionService positionService = new PositionService();

    public EditEmployeeController() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fitness/fragment/employee/edit_employee.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
    }
    public void loadOldData() throws SQLException {
        positionComboBox.setItems(FXCollections.observableArrayList(positionService.getActual()));
        Employee employee = employeeService.getSelected();
        if(employee != null)
            Fill.employee(employee, nameTextField, surnameTextField, phoneTextField, phone2TextField, addressTextField, positionComboBox);
    }
    public void initListeners(){
        editButton.setOnAction(event -> {
            /* return null when something went wrong */
            try {
                if(employeeService.edit(
                        employeeService.getSelected(),
                        nameTextField,
                        surnameTextField,
                        phoneTextField,
                        phone2TextField,
                        addressTextField,
                        positionComboBox) != null) {
                    this.stop();
                    Window.getFragment(Fragment.EMPLOYEE).start();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        previousButton.setOnAction(event -> {
            employeeService.removeSelected();
            this.stop();
            Window.getFragment(Fragment.EMPLOYEE).start();
        });
    }

    @Override
    public void start() {
        makeActive();
        initListeners();
        try {
            loadOldData();
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
