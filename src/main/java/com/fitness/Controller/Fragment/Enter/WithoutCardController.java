package com.fitness.Controller.Fragment.Enter;

import com.fitness.Constant.Week;
import com.fitness.Controller.Controller;
import com.fitness.DataSource.Log.Log;
import com.fitness.Element.MaskField;
import com.fitness.Model.Configuration.WeekDay;
import com.fitness.Model.Configuration.WorkingDay;
import com.fitness.Model.Person.Customer;
import com.fitness.Model.Person.Employee;
import com.fitness.Model.Work.DateTime;
import com.fitness.Model.Work.Employment;
import com.fitness.Service.Archive.ArchiveService;
import com.fitness.Service.BackYear.BackYearService;
import com.fitness.Service.Clear;
import com.fitness.Service.Configuration.SettingsService;
import com.fitness.Service.Fill;
import com.fitness.Service.Person.CustomerService;
import com.fitness.Service.Person.EmployeeService;
import com.fitness.Service.Work.EmploymentService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class WithoutCardController extends GridPane implements Controller {
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField surnameTextField;

    private MaskField phoneMaskField;
    private MaskField phone2MaskField;

    @FXML
    private TextField addressTextField;
    @FXML
    private TextField priceTextField;
    @FXML
    private ComboBox<Employment> employmentComboBox;
    @FXML
    private ToggleButton todayEmployeesToggleButton;
    @FXML
    private ToggleButton allEmployeesToggleButton;
    @FXML
    private ComboBox<Employee> employeeComboBox;
    @FXML
    private CheckBox bonusCheckBox;
    @FXML
    private CheckBox backYearCheckBox;
    @FXML
    private GridPane backYearGridPane;
    @FXML
    private DatePicker datePicker;
    private MaskField timeMaskField;
    @FXML
    private Button enterButton;

    private boolean fieldsAreClean = true;
    private Customer customer;

    private SettingsService settingsService = new SettingsService();
    private CustomerService customerService = new CustomerService();
    private EmployeeService employeeService = new EmployeeService();
    private EmploymentService employmentService = new EmploymentService();
    private ArchiveService archiveService = new ArchiveService();

    private ObservableList<Employee> allEmployees = null;
    private ObservableList<Employee> todayEmployees = null;

    public WithoutCardController() throws IOException{
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/com/fitness/fragment/enter/without_card.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();

        phoneMaskField = new MaskField();
        phoneMaskField.setMask("+374(DD) DD-DD-DD");
        phoneMaskField.getStyleClass().add("textField");
        GridPane.setValignment(phoneMaskField, VPos.TOP);
        GridPane.setHalignment(phoneMaskField, HPos.LEFT);

        phone2MaskField = new MaskField();
        phone2MaskField.setMask("+374(DD) DD-DD-DD");
        phone2MaskField.getStyleClass().add("textField");
        GridPane.setValignment(phone2MaskField, VPos.TOP);
        GridPane.setHalignment(phone2MaskField, HPos.LEFT);

        this.add(phoneMaskField, 0, 7);
        this.add(phone2MaskField, 0, 9);

        timeMaskField = new MaskField();
        timeMaskField.setMask("DD:DD");
        timeMaskField.setStyle("-fx-min-width: 100; -fx-pref-width: 100; -fx-max-width: 100; -fx-alignment: center");
        this.backYearGridPane.add(timeMaskField, 2, 1);
    }

    private void phoneMaskFieldListener(String newValue) {
        this.customer = null;
        try {
            this.customer = customerService.getByPhone(newValue);
        } catch (SQLException e) {
            Log.warning("Can't fetch guest customer by phone number");
        }
        if(!fieldsAreClean) {
            Clear.textField(
                    nameTextField,
                    surnameTextField,
                    addressTextField
            );
            Clear.maskField(
                    phoneMaskField,
                    phone2MaskField
            );
            fieldsAreClean = true;
        } else if (this.customer != null){
            Fill.guestCustomer(
                    this.customer,
                    nameTextField,
                    surnameTextField,
                    phoneMaskField,
                    phone2MaskField,
                    addressTextField
            );
            fieldsAreClean = false;
        }
    }

    private void initListeners() {
        phoneMaskField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!phoneMaskField.isEmpty())
                phoneMaskFieldListener(newValue);
        });
        phone2MaskField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!phone2MaskField.isEmpty())
                phoneMaskFieldListener(newValue);
        });

        todayEmployeesToggleButton.setOnAction(event -> {
            employeeComboBox.setItems(this.todayEmployees);
            employeeComboBox.getSelectionModel().selectFirst();
        });
        allEmployeesToggleButton.setOnAction(event -> {
            employeeComboBox.setItems(this.allEmployees);
            employeeComboBox.getSelectionModel().selectFirst();
        });
        employmentComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, selectedEmployment) -> {
            if(selectedEmployment == null) return;
            try {
                this.allEmployees = FXCollections.observableArrayList(employeeService.getBy(selectedEmployment, true));
                if(todayEmployeesToggleButton.isSelected()) {
                    this.todayEmployees = FXCollections.observableArrayList();
                    Week currentWeek = DateTime.getCurrentWeek();
                    for(WorkingDay workingDay: settingsService.getWorkingDays())
                        for(WeekDay weekDay: workingDay.getWorkingDays())
                            if(weekDay.getWeek() == currentWeek && weekDay.isWorkingDay())
                                this.todayEmployees.add(workingDay.getEmployee());
                    employeeComboBox.setItems(this.todayEmployees);
                    employeeComboBox.getSelectionModel().selectFirst();
                } else {
                    employeeComboBox.setItems(this.allEmployees);
                    employeeComboBox.getSelectionModel().selectFirst();
                }

                priceTextField.setText(selectedEmployment.getPrice() + "");
            } catch (SQLException e) {
                Log.error("Can't fetch employees by employment");
            }
        });
        enterButton.setOnAction(event -> {
            DateTime dateTime = null;
            if(backYearCheckBox.isSelected()) {
                dateTime = BackYearService.getDateTime(timeMaskField, datePicker);
                if(dateTime == null) return;
            }

            try {
                Employee employee = employeeComboBox.getSelectionModel().getSelectedItem();
                Employment employment = employmentComboBox.getSelectionModel().getSelectedItem();
                if(employee == null || employment == null) return;

                if(archiveService.add(
                        dateTime,
                        getCustomer(),
                        employee,
                        employment,
                        bonusCheckBox.isSelected()
                ) != null)

                    this.stop();
            } catch (SQLException e) {
                Log.error("Can't enter without card");
            }
        });

        backYearCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> backYearGridPane.setDisable(!newValue));
    }

    private Customer getCustomer() {
        if(this.customer == null || (
                /* or if existing user data changed, create new user */
                this.customer.getName().getFirstName().equals(nameTextField.getText()) ||
                this.customer.getName().getLastName().equals(surnameTextField.getText()) ||
                this.customer.getAddress().equals(addressTextField.getText())
            )
        ) {
            try {
                customer = customerService.addGuest(
                        nameTextField,
                        surnameTextField,
                        phoneMaskField,
                        phone2MaskField,
                        addressTextField
                );
            } catch (SQLException e) {
                Log.error("Can't add guest customer");
            }
        }
        return this.customer;
    }


    private void initEmploymentComboBox() throws SQLException{
        employmentComboBox.setItems(
                FXCollections.observableArrayList(
                        employmentService.getActual()
                )
        );
    }

    private void initDatePicker() {
        StringConverter<LocalDate> converter = DateTime.getConverter();

        LocalDate localDate = LocalDate.now();
        datePicker.setConverter(converter);
        datePicker.setValue(localDate);
    }

    @Override
    public void start() {
        makeActive();
        initDatePicker();
        try {
            initEmploymentComboBox();
        } catch (SQLException e) {
            Log.error("Can't fetch actual employments");
        }

        initListeners();
    }

    @Override
    public void stop() {
        Clear.textField(
                nameTextField,
                surnameTextField,
                addressTextField,
                priceTextField
        );
        Clear.maskField(
                phoneMaskField,
                phone2MaskField
        );
        Clear.comboBox(employeeComboBox, employmentComboBox);
        fieldsAreClean = true;
    }
}
