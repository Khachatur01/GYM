package com.fitness.Service;

import com.fitness.Model.Person.Customer;
import com.fitness.Model.Person.Employee;
import com.fitness.Model.Work.EmploymentQuantity;
import com.fitness.Model.Work.Position;
import com.fitness.Model.Work.Subscription;
import com.fitness.Service.Person.CustomerService;
import com.fitness.Service.Work.SubscriptionService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.List;

public class Fill {
    private static SubscriptionService subscriptionService = new SubscriptionService();
    private static CustomerService customerService = new CustomerService();
    public static void customer(Customer customer, TextField cardTextField, TextField nameTextField, TextField surnameTextField, TextField phoneTextField, TextField phone2TextField, TextField addressTextField, ComboBox<Subscription> subscriptionComboBox){
        cardTextField.setText(customer.getCard());
        nameTextField.setText(customer.getName().getFirstName());
        surnameTextField.setText(customer.getName().getLastName());
        phoneTextField.setText(customer.getPhone());
        phone2TextField.setText(customer.getPhone2());
        addressTextField.setText(customer.getAddress());
        subscriptionComboBox.getSelectionModel().select(customer.getSubscription());
    }
    public static void customerStatic(Customer customer, Label fullNameLabel, Label phoneLabel, Label phone2Label, Label addressLabel, Label subscriptionLabel, Label lastVisitLabel, TableView<EmploymentQuantity> employmentQuantityTable){
        fullNameLabel.setText(customer.getFullName());
        phoneLabel.setText(customer.getPhone());
        phone2Label.setText(customer.getPhone2());
        addressLabel.setText(customer.getAddress());
        subscriptionLabel.setText(customer.getSubscription().getName());
        try {
            lastVisitLabel.setText(customerService.getLastVisit(customer).toString());
        } catch (SQLException | NullPointerException ignored){
            lastVisitLabel.setText("");
        }
        try {
            List<EmploymentQuantity> availableEmploymentQuantities = customerService.getAvailableEmploymentQuantities(customer);

            employmentQuantityTable.setItems(
                    FXCollections.observableArrayList(
                            availableEmploymentQuantities
                    )
            );
        } catch (SQLException ignored) {

        }
    }

    public static void employee(Employee employee, TextField nameTextField, TextField surnameTextField, TextField phoneTextField, TextField phone2TextField, TextField addressTextField, ComboBox<Position> positionComboBox) {
        nameTextField.setText(employee.getName().getFirstName());
        surnameTextField.setText(employee.getName().getLastName());
        phoneTextField.setText(employee.getPhone());
        phone2TextField.setText(employee.getPhone2());
        addressTextField.setText(employee.getAddress());
        positionComboBox.getSelectionModel().select(employee.getPosition());
    }
}
