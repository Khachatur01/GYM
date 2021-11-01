package com.fitness.Service;

import com.fitness.Model.Person.Customer;
import com.fitness.Model.Person.Employee;
import com.fitness.Model.Work.Position;
import com.fitness.Model.Work.Employment;
import com.fitness.Model.Work.Subscription;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class Fill {
    public static void customer(Customer customer, TextField cardTextField, TextField nameTextField, TextField surnameTextField, TextField phoneTextField, TextField phone2TextField, TextField addressTextField, ComboBox<Subscription> subscriptionComboBox){
        cardTextField.setText(customer.getCard());
        nameTextField.setText(customer.getName().getFirstName());
        surnameTextField.setText(customer.getName().getLastName());
        phoneTextField.setText(customer.getPhone());
        phone2TextField.setText(customer.getPhone2());
        addressTextField.setText(customer.getAddress());
        subscriptionComboBox.getSelectionModel().select(customer.getSubscription());
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
