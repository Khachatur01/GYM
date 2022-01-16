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
import com.fitness.Model.Work.EmploymentQuantity;
import com.fitness.Service.Archive.ArchiveService;
import com.fitness.Service.BackYear.BackYearService;
import com.fitness.Service.Clear;
import com.fitness.Service.Configuration.SettingsService;
import com.fitness.Service.Fill;
import com.fitness.Service.Person.CustomerService;
import com.fitness.Service.Person.EmployeeService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class WithCardController extends GridPane implements Controller {
    @FXML
    private TextField cardTextField;
    @FXML
    private ComboBox<Employment> employmentComboBox;
    @FXML
    private ToggleButton todayEmployeesToggleButton;
    @FXML
    private ToggleButton allEmployeesToggleButton;
    @FXML
    private ComboBox<Employee> employeeComboBox;
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
    private Label registrationDateLabel;
    @FXML
    private Label lastVisitLabel;
    @FXML
    private TableView<EmploymentQuantity> employmentQuantityTable;
    @FXML
    private TableColumn<EmploymentQuantity, String> employmentColumn;
    @FXML
    private TableColumn<EmploymentQuantity, Integer> quantityColumn;
    @FXML
    private CheckBox backYearCheckBox;
    @FXML
    private GridPane backYearGridPane;
    @FXML
    private DatePicker datePicker;
    private MaskField timeMaskField;

    @FXML
    private CheckBox bonusCheckBox;
    @FXML
    private Button enterButton;

    private SettingsService settingsService = new SettingsService();
    private CustomerService customerService = new CustomerService();
    private EmployeeService employeeService = new EmployeeService();
    private ArchiveService archiveService = new ArchiveService();

    private boolean fieldsAreClean = true;
    private Customer selectedCustomer = null;
    private ObservableList<Employee> allEmployees = null;
    private ObservableList<Employee> todayEmployees = null;

    public WithCardController() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fitness/fragment/enter/with_card.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();

        timeMaskField = new MaskField();
        timeMaskField.setMask("DD:DD");
        timeMaskField.setStyle("-fx-min-width: 100; -fx-pref-width: 100; -fx-max-width: 100; -fx-alignment: center");
        this.backYearGridPane.add(timeMaskField, 2, 1);

        initListeners();
    }

    private void initListeners() {
        cardTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            Customer customer = null;
            try {
                customer = customerService.getByCard(newValue);
            } catch (SQLException e) {
                Log.error("Can't fetch customer by card number");
            }
            if(!fieldsAreClean) {
                Clear.label(
                        fullNameLabel,
                        phoneLabel,
                        phone2Label,
                        addressLabel,
                        subscriptionLabel,
                        registrationDateLabel,
                        lastVisitLabel
                );
                Clear.table(employmentQuantityTable);
                fieldsAreClean = true;
                selectedCustomer = null;
                enterButton.setDisable(false);
            } else if (customer != null){
                Fill.customerStatic(
                        customer,
                        fullNameLabel,
                        phoneLabel,
                        phone2Label,
                        addressLabel,
                        subscriptionLabel,
                        registrationDateLabel,
                        lastVisitLabel,
                        employmentQuantityTable
                );
                fieldsAreClean = false;
                selectedCustomer = customer;

                try {
                    enterButton.setDisable(!customerService.hasAvailableEmployment(customer));

                    initEmploymentComboBox(customer);
                } catch (SQLException e) {
                    Log.error("Can't customer data");
                }
            }
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
                this.todayEmployees = FXCollections.observableArrayList(employeeService.getTodayBy(selectedEmployment, true));
                Week currentWeek = DateTime.getCurrentWeek();
                for(WorkingDay workingDay: settingsService.getWorkingDays())
                    for(WeekDay weekDay: workingDay.getWorkingDays())
                        if(weekDay.getWeek() == currentWeek && weekDay.isWorkingDay() && workingDay.getEmployee().getId() == 0)
                            this.todayEmployees.add(workingDay.getEmployee());

                if(todayEmployeesToggleButton.isSelected()) {
                    employeeComboBox.setItems(this.todayEmployees);
                    employeeComboBox.getSelectionModel().selectFirst();
                } else {
                    employeeComboBox.setItems(this.allEmployees);
                    employeeComboBox.getSelectionModel().selectFirst();
                }
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
                if(dateTime != null && dateTime.before(customerService.getRegistrationDate(selectedCustomer))) {
                    datePicker.requestFocus();
                    return;
                }
                boolean isBonusCard = customerService.isBonus(selectedCustomer);
                if(archiveService.add(
                        dateTime,
                        selectedCustomer,
                        employeeComboBox.getSelectionModel().getSelectedItem(),
                        employmentComboBox.getSelectionModel().getSelectedItem(),
                        isBonusCard || bonusCheckBox.isSelected()
                    ) != null)

                    this.stop();
            } catch (SQLException e) {
                Log.error("Can't enter with card");
            }

        });

        backYearCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> backYearGridPane.setDisable(!newValue));
    }

    private void initEmploymentComboBox(Customer customer) throws SQLException {
        ObservableList<Employment> employments = FXCollections.observableArrayList();
        /* get non bonus visits count by employment */
        for(EmploymentQuantity employmentQuantity : customerService.getAvailableEmploymentQuantities(customer))
            if(employmentQuantity.getQuantity() != 0)
                employments.add(employmentQuantity.getEmployment());


        employmentComboBox.setItems(employments);
    }

    private void initTable() {
        employmentColumn.setCellValueFactory(new PropertyValueFactory<>("employment"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
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
        initTable();
        initDatePicker();
    }

    @Override
    public void stop() {
        Clear.textField(cardTextField);
        Clear.comboBox(employmentComboBox, employeeComboBox);
        Clear.label(
                fullNameLabel,
                phoneLabel,
                phone2Label,
                addressLabel,
                subscriptionLabel,
                lastVisitLabel
        );
        Clear.table(employmentQuantityTable);
        Clear.checkBox(bonusCheckBox);
        fieldsAreClean = true;
        selectedCustomer = null;
        allEmployees = null;
        todayEmployees = null;
    }
}
