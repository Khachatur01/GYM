package com.fitness.Controller.Fragment.Employee;

import com.fitness.Controller.Constant.Fragment;
import com.fitness.Controller.Controller;
import com.fitness.Element.MaskField;
import com.fitness.Model.Work.Position;
import com.fitness.Service.Clear;
import com.fitness.Service.Person.EmployeeService;
import com.fitness.Service.Work.EmploymentService;
import com.fitness.Service.Work.PositionService;
import com.fitness.Window;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.io.IOException;
import java.sql.SQLException;

public class AddEmployeeController extends GridPane implements Controller {
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField surnameTextField;

    private MaskField phoneMaskField;
    private MaskField phone2MaskField;

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

        phoneMaskField = new MaskField();
        phoneMaskField.setMask("+374(DD) DD-DD-DD");
        phoneMaskField.getStyleClass().add("textField");
        GridPane.setValignment(phoneMaskField, VPos.CENTER);
        GridPane.setHalignment(phoneMaskField, HPos.RIGHT);
        GridPane.setVgrow(phoneMaskField, Priority.ALWAYS);
        GridPane.setHgrow(phoneMaskField, Priority.ALWAYS);

        phone2MaskField = new MaskField();
        phone2MaskField.setMask("+374(DD) DD-DD-DD");
        phone2MaskField.getStyleClass().add("textField");
        GridPane.setValignment(phone2MaskField, VPos.CENTER);
        GridPane.setHalignment(phone2MaskField, HPos.RIGHT);
        GridPane.setVgrow(phone2MaskField, Priority.ALWAYS);
        GridPane.setHgrow(phone2MaskField, Priority.ALWAYS);
    }

    private void initListeners(){
        addButton.setOnAction(event -> {
            /* return null when something went wrong */
            try {
                if(employeeService.add(
                        nameTextField,
                        surnameTextField,
                        phoneMaskField,
                        phone2MaskField,
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
                addressTextField
        );
        Clear.maskField(
                phoneMaskField,
                phone2MaskField
        );
        Clear.comboBox(positionComboBox);
    }
}
