package com.fitness.Controller.Fragment.Employee;

import com.fitness.Constant.Fragment;
import com.fitness.Controller.Controller;
import com.fitness.DataSource.Log.Log;
import com.fitness.Model.Person.Employee;
import com.fitness.Model.Person.Person;
import com.fitness.Service.Person.EmployeeService;
import com.fitness.Window;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.sql.SQLException;

public class EmployeeController extends GridPane implements Controller {
    @FXML
    private TableView<Employee> employeesTable;
    @FXML
    private TableColumn<Employee, Person.Name> fullNameColumn;
    @FXML
    private TableColumn<Employee, String> phoneColumn;
    @FXML
    private TableColumn<Employee, String> phone2Column;
    @FXML
    private TableColumn<Employee, String> addressColumn;
    @FXML
    private Button editButton;
    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;

    private ObservableList<Employee> employees = FXCollections.observableArrayList();
    private EmployeeService employeeService = new EmployeeService();

    public EmployeeController() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fitness/fragment/employee/employee.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
    }
    private void initEmployeesTable() throws SQLException {
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        phone2Column.setCellValueFactory(new PropertyValueFactory<>("phone2"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        employeesTable.getItems().clear();

        employees.setAll(employeeService.getAll());

        employeesTable.setItems(employees);
    }

    private void initListeners(){
        editButton.setOnAction(event -> {
            Employee employee = employeesTable.getSelectionModel().getSelectedItem();
            employeeService.select(employee);
            if(employee != null)
                Window.getFragment(Fragment.EDIT_EMPLOYEE).start();
        });

        addButton.setOnAction(event -> {
            Window.getFragment(Fragment.ADD_EMPLOYEE).start();
        });

        deleteButton.setOnAction(event -> {
            Employee employee = employeesTable.getSelectionModel().getSelectedItem();
            try {
                employeeService.remove(employee);
            } catch (SQLException e) {
                Log.error("Can't delete employee");
            }
            employees.remove(employee);
        });
    }

    @Override
    public void start() {
        makeActive();
        try {
            initEmployeesTable();
        } catch (SQLException e) {
            Log.warning("Can't fetch employees");
        }
        initListeners();
    }

    @Override
    public void stop() {

    }
}
