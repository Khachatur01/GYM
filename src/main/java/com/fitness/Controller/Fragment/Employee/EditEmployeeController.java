package com.fitness.Controller.Fragment.Employee;

import com.fitness.Controller.Constant.Fragment;
import com.fitness.Controller.Controller;
import com.fitness.Element.MaskField;
import com.fitness.Model.Person.Employee;
import com.fitness.Model.Work.Position;
import com.fitness.Service.Clear;
import com.fitness.Service.Fill;
import com.fitness.Service.Person.EmployeeService;
import com.fitness.Service.Work.EmploymentService;
import com.fitness.Service.Work.PositionService;
import com.fitness.Window;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.io.IOException;
import java.sql.SQLException;

public class EditEmployeeController extends GridPane implements Controller {
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField surnameTextField;

    private MaskField phoneMaskField;
    private MaskField phone2MaskField;

    @FXML
    private TextField addressTextField;

    @FXML
    private TableView<Position> employeePositionTable;
    @FXML
    private TableColumn<Position, String> positionColumn;
    @FXML
    private Button deletePositionButton;
    @FXML
    private Button addPositionButton;
    @FXML
    private ComboBox<Position> positionComboBox;

    @FXML
    private Button previousButton;
    @FXML
    private Button editButton;

    private EmployeeService employeeService = new EmployeeService();
    private PositionService positionService = new PositionService();

    private ObservableList<Position> positions = FXCollections.observableArrayList();

    public EditEmployeeController() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fitness/fragment/employee/edit_employee.fxml"));
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

        this.add(phoneMaskField, 1, 4);
        this.add(phone2MaskField, 1, 5);
    }
    public void loadOldData() throws SQLException {
        positionComboBox.setItems(FXCollections.observableArrayList(positionService.getActual()));
        Employee employee = employeeService.getSelected();
        if(employee != null)
            Fill.employee(employee, nameTextField, surnameTextField, phoneMaskField, phone2MaskField, addressTextField, positionComboBox);
    }
    public void initListeners(){
        addPositionButton.setOnAction(event -> {
            Position position = positionComboBox.getSelectionModel().getSelectedItem();
            if(!positions.contains(position))
                positions.add(position);
        });
        deletePositionButton.setOnAction(event -> {
            positions.remove(employeePositionTable.getSelectionModel().getSelectedItem());
        });

        editButton.setOnAction(event -> {
            /* return null when something went wrong */
            try {
                if(employeeService.edit(
                        employeeService.getSelected(),
                        nameTextField,
                        surnameTextField,
                        phoneMaskField,
                        phone2MaskField,
                        addressTextField,
                        positions) != null) {
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

    public void initComboBox() throws SQLException {
        positionComboBox.setItems(FXCollections.observableArrayList(positionService.getActual()));
    }


    private void initTable() throws SQLException {
        positions.addAll(employeeService.getPositions(employeeService.getSelected(), true));
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        employeePositionTable.setItems(positions);
    }

    @Override
    public void start() {
        makeActive();
        initListeners();
        try {
            initTable();
            initComboBox();
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
                addressTextField
        );
        Clear.maskField(
                phoneMaskField,
                phone2MaskField
        );
        Clear.comboBox(positionComboBox);
        Clear.table(employeePositionTable);
    }
}
