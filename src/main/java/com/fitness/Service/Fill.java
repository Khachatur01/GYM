package com.fitness.Service;

import com.fitness.DataSource.Log.Log;
import com.fitness.Element.MaskField;
import com.fitness.Model.Person.Customer;
import com.fitness.Model.Person.Employee;
import com.fitness.Model.Person.Person;
import com.fitness.Model.Work.EmploymentQuantity;
import com.fitness.Model.Work.Subscription;
import com.fitness.Service.Person.CustomerService;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.List;

public class Fill {
    private static final CustomerService customerService = new CustomerService();
    public static void customer(Customer customer, TextField cardTextField, TextField nameTextField, TextField surnameTextField, MaskField phoneTextField, MaskField phone2TextField, TextField addressTextField, ComboBox<Subscription> subscriptionComboBox){
        cardTextField.setText(customer.getCard());

        Fill.person(customer, nameTextField, surnameTextField, phoneTextField, phone2TextField, addressTextField);

        subscriptionComboBox.getSelectionModel().select(customer.getSubscription());
    }
    public static void guestCustomer(Customer customer, TextField nameTextField, TextField surnameTextField, MaskField phoneTextField, MaskField phone2TextField, TextField addressTextField){
        Fill.person(customer, nameTextField, surnameTextField, phoneTextField, phone2TextField, addressTextField);
    }

    public static void customerStatic(Customer customer, Label fullNameLabel, Label phoneLabel, Label phone2Label, Label addressLabel, Label subscriptionLabel, Label registrationDateLabel, Label lastVisitLabel, TableView<EmploymentQuantity> employmentQuantityTable){
        fullNameLabel.setText(customer.getFullName());
        phoneLabel.setText(customer.getPhone());

        String phone2 = customer.getPhone2();
        if(phone2 != null)
            phone2Label.setText(phone2);

        addressLabel.setText(customer.getAddress());
        subscriptionLabel.setText(customer.getSubscription().getName());
        try {
            lastVisitLabel.setText(customerService.getLastVisit(customer).toString());
        } catch (SQLException | NullPointerException ignored) {
            lastVisitLabel.setText("");
        }
        try {
            registrationDateLabel.setText(customerService.getRegistrationDate(customer).toString());
        } catch (SQLException | NullPointerException e) {
            registrationDateLabel.setText("");
        }
        try {
            List<EmploymentQuantity> availableEmploymentQuantities = customerService.getAvailableEmploymentQuantities(customer);
            employmentQuantityTable.setItems(
                    FXCollections.observableArrayList(
                            availableEmploymentQuantities
                    )
            );
        } catch (SQLException e) {
            Log.error("Can't fetch customer available employments with quantities", e);
        }
    }

    public static void employee(Employee employee, TextField nameTextField, TextField surnameTextField, MaskField phoneTextField, MaskField phone2TextField, TextField addressTextField) {
        Fill.person(employee, nameTextField, surnameTextField, phoneTextField, phone2TextField, addressTextField);
    }

    private static void person(Person person, TextField nameTextField, TextField surnameTextField, MaskField phoneTextField, MaskField phone2TextField, TextField addressTextField) {
        nameTextField.setText(person.getName().getFirstName());
        surnameTextField.setText(person.getName().getLastName());
        phoneTextField.setText(person.getPhone());

        String phone2 = person.getPhone2();
        if(phone2 != null)
            phone2TextField.setText(phone2);

        addressTextField.setText(person.getAddress());
    }
}
