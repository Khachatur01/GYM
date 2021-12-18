package com.fitness.Controller.Fragment.Enter;

import com.fitness.Controller.Controller;
import com.fitness.Element.MaskField;
import com.fitness.Model.Person.Customer;
import com.fitness.Model.Person.Employee;
import com.fitness.Model.Work.Employment;
import com.fitness.Service.Archive.ArchiveService;
import com.fitness.Service.Clear;
import com.fitness.Service.Fill;
import com.fitness.Service.Person.CustomerService;
import com.fitness.Service.Person.EmployeeService;
import com.fitness.Service.Work.EmploymentService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.sql.SQLException;

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
    private ComboBox<Employee> employeeComboBox;
    @FXML
    private CheckBox bonusCheckBox;
    @FXML
    private Button enterButton;

    private boolean fieldsAreClean = true;

    private CustomerService customerService = new CustomerService();
    private EmployeeService employeeService = new EmployeeService();
    private EmploymentService employmentService = new EmploymentService();
    private ArchiveService archiveService = new ArchiveService();

    public WithoutCardController() throws IOException{
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/com/fitness/fragment/enter/without_card.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();

        phoneMaskField = new MaskField();
        phoneMaskField.setMask("+374(DD) DD-DD-DD");
        phoneMaskField.getStyleClass().add("textField");
        GridPane.setValignment(phoneMaskField, VPos.TOP);
        GridPane.setHalignment(phoneMaskField, HPos.CENTER);

        phone2MaskField = new MaskField();
        phone2MaskField.setMask("+374(DD) DD-DD-DD");
        phone2MaskField.getStyleClass().add("textField");
        GridPane.setValignment(phone2MaskField, VPos.TOP);
        GridPane.setHalignment(phone2MaskField, HPos.CENTER);

        this.add(phoneMaskField, 0, 6);
        this.add(phone2MaskField, 0, 8);
    }

    private void phoneMaskFieldListener(String newValue) {
        Customer customer = null;
        try {
            customer = customerService.getByPhone(newValue);
        } catch (SQLException e) {
            e.printStackTrace();
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
        } else if (customer != null){
            Fill.guestCustomer(
                    customer,
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
            phoneMaskFieldListener(newValue);
        });
        phone2MaskField.textProperty().addListener((observable, oldValue, newValue) -> {
            phoneMaskFieldListener(newValue);
        });

        employmentComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, selectedEmployment) -> {
            try {
                employeeComboBox.setItems(
                        FXCollections.observableArrayList(
                                employeeService.getBy(selectedEmployment, true)
                        )
                );
                priceTextField.setText(selectedEmployment.getPrice() + "");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        enterButton.setOnAction(event -> {
            try {
                if(archiveService.add(
                        makeCustomer(),
                        employeeComboBox.getSelectionModel().getSelectedItem(),
                        employmentComboBox.getSelectionModel().getSelectedItem(),
                        bonusCheckBox.isSelected()
                ) != null)

                    this.stop();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private Customer makeCustomer() {
        Customer customer = null;
        try {
            customer = customerService.getByPhone(phoneMaskField.getText());
            if(customer == null)
                customer = customerService.addGuest(
                        nameTextField,
                        surnameTextField,
                        phoneMaskField,
                        phone2MaskField,
                        addressTextField
                );

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customer;
    }


    private void initComboBoxes() throws SQLException{
        employmentComboBox.setItems(
                FXCollections.observableArrayList(
                        employmentService.getActual()
                )
        );
    }

    @Override
    public void start() {
        makeActive();
        try {
            initComboBoxes();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        initListeners();
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
        fieldsAreClean = true;
    }
}
