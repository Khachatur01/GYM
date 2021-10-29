package com.fitness.Service.Person;

import com.fitness.DAO.Person.CustomerDAO;
import com.fitness.Model.Person.Customer;
import com.fitness.Model.Person.Person;
import com.fitness.Model.Work.Subscription;
import com.fitness.Service.Verify;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class CustomerService {
    private static Customer cache = null;
    private CustomerDAO customerDAO = new CustomerDAO();

    public Customer getCustomer(String card){
        return customerDAO.getByCard(card);
    }
    public List<Customer> getCustomers() {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer(-1, new Person.Name("name", "surname"), "123", "098", "099", "address", null));
        customers.add(new Customer(-1, new Person.Name("name1", "surname"), "456", "098", "099", "address", null));
        customers.add(new Customer(-1, new Person.Name("name2", "surname"), "789", "098", null, "address", null));
        customers.add(new Customer(-1, new Person.Name("name3", "surname"), "147", "098", "099", "address", null));
        //@TODO get from database
        return customers;
    }

    private Customer makeCustomer(TextField cardTextField, TextField nameTextField, TextField surnameTextField, TextField phoneTextField, TextField phone2TextField, TextField addressTextField, ComboBox<Subscription> subscriptionComboBox){
        Customer customer = null;
        String card = cardTextField.getText();
        String name = nameTextField.getText();
        String surname = surnameTextField.getText();
        String phone = phoneTextField.getText();
        String phone2 = phone2TextField.getText();
        String address = addressTextField.getText();
        Subscription subscription = subscriptionComboBox.getValue();

        if(     Verify.card(card, cardTextField) &&
                Verify.name(name, nameTextField) &&
                Verify.surname(surname, surnameTextField) &&
                Verify.phone(phone, phoneTextField) &&
                Verify.address(address, addressTextField) &&
                Verify.subscription(subscription, subscriptionComboBox)
        ){
            customer = new Customer();
            customer.setCard(card);
            customer.setName(new Person.Name(name, surname));
            customer.setPhone(phone);
            customer.setPhone2(phone2);
            customer.setAddress(address);
            customer.setSubscription(subscription);
        }
        return customer;
    }

    public boolean add(TextField cardTextField, TextField nameTextField, TextField surnameTextField, TextField phoneTextField, TextField phone2TextField, TextField addressTextField, ComboBox<Subscription> subscriptionComboBox){
        Customer customer = this.makeCustomer(cardTextField, nameTextField, surnameTextField, phoneTextField, phone2TextField, addressTextField, subscriptionComboBox);
        //@TODO add customer to database;
        return customer != null;
    }

    public boolean edit(TextField cardTextField, TextField nameTextField, TextField surnameTextField, TextField phoneTextField, TextField phone2TextField, TextField addressTextField, ComboBox<Subscription> subscriptionComboBox){
        Customer customer = this.makeCustomer(cardTextField, nameTextField, surnameTextField, phoneTextField, phone2TextField, addressTextField, subscriptionComboBox);
        //@TODO update customer in database;
        return customer != null;
    }

    public void remove(Customer customer, boolean removeHistory){
        //@TODO
        System.out.println("removed");
    }

    public Customer getCache(){
        return cache;
    }
    public void setCache(Customer cache){
        CustomerService.cache = cache;
    }

}
