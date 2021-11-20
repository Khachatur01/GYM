package com.fitness.Controller.Fragment.Enter;

import com.fitness.Controller.Controller;
import com.fitness.Model.Archive.Archive;
import com.fitness.Model.Person.Customer;
import com.fitness.Model.Person.Employee;
import com.fitness.Model.Work.Employment;
import com.fitness.Model.Work.EmploymentQuantity;
import com.fitness.Service.Archive.ArchiveService;
import com.fitness.Service.Clear;
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
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.sql.SQLException;

public class WithCardController extends GridPane implements Controller {
    @FXML
    private TextField cardTextField;
    @FXML
    private ComboBox<Employment> employmentComboBox;
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
    private Label lastVisitLabel;
    @FXML
    private TableView<EmploymentQuantity> employmentQuantityTable;
    @FXML
    private TableColumn<EmploymentQuantity, String> employmentColumn;
    @FXML
    private TableColumn<EmploymentQuantity, Integer> quantityColumn;
    @FXML
    private CheckBox bonusCheckBox;
    @FXML
    private Button enterButton;

    private CustomerService customerService = new CustomerService();
    private EmployeeService employeeService = new EmployeeService();
    private ArchiveService archiveService = new ArchiveService();

    private boolean fieldsAreClean = true;
    private Customer selectedCustomer = null;

    public WithCardController() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fitness/fragment/enter/with_card.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
    }

    private void initListeners(){
        cardTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            Customer customer = null;
            try {
                customer = customerService.getByCard(newValue);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if(!fieldsAreClean) {
                Clear.label(
                        fullNameLabel,
                        phoneLabel,
                        phone2Label,
                        addressLabel,
                        subscriptionLabel,
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
                        lastVisitLabel,
                        employmentQuantityTable
                );
                fieldsAreClean = false;
                selectedCustomer = customer;

                try {
                    if(customerService.hasAvailableEmployment(customer))
                        enterButton.setDisable(false);
                    else
                        enterButton.setDisable(true);

                    initEmploymentComboBox(customer);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        employmentComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, selectedEmployment) -> {
            try {
                employeeComboBox.setItems(
                        FXCollections.observableArrayList(
                            employeeService.getBy(selectedEmployment, true)
                    )
                );
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        enterButton.setOnAction(event -> {
            try {
                if(archiveService.add(
                        selectedCustomer,
                        employeeComboBox.getSelectionModel().getSelectedItem(),
                        false,
                        bonusCheckBox.isSelected()
                    ) != null)

                    this.stop();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        });
    }

    private void initEmploymentComboBox(Customer customer) throws SQLException {
        ObservableList<Employment> employments = FXCollections.observableArrayList();
        /* get non bonus visits count by employment */
        for(EmploymentQuantity employmentQuantity : customerService.getAvailableEmploymentQuantities(customer))
            if(employmentQuantity.getQuantity() != 0)
                employments.add(employmentQuantity.getEmployment());


        employmentComboBox.setItems(employments);
    }

    private void initTable(){
        employmentColumn.setCellValueFactory(new PropertyValueFactory<>("employment"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    }

    @Override
    public void start() {
        makeActive();
        initTable();
        initListeners();
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
    }
}
