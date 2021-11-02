package com.fitness.Service.Person;

import com.fitness.DAO.Person.CustomerDAO;
import com.fitness.Model.Person.Customer;
import com.fitness.Model.Person.Person;
import com.fitness.Model.Work.Subscription;
import com.fitness.Service.Verify;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CustomerService {
    private static Customer selected = null;
    private CustomerDAO customerDAO = new CustomerDAO();

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

    public Customer add(TextField cardTextField, TextField nameTextField, TextField surnameTextField, TextField phoneTextField, TextField phone2TextField, TextField addressTextField, ComboBox<Subscription> subscriptionComboBox) throws SQLException {
        Customer newCustomer = this.makeCustomer(cardTextField, nameTextField, surnameTextField, phoneTextField, phone2TextField, addressTextField, subscriptionComboBox);
        customerDAO.add(newCustomer);
        return newCustomer;
    }

    public Customer edit(Customer customer, TextField cardTextField, TextField nameTextField, TextField surnameTextField, TextField phoneTextField, TextField phone2TextField, TextField addressTextField, ComboBox<Subscription> subscriptionComboBox) throws SQLException {
        Customer newCustomer = this.makeCustomer(cardTextField, nameTextField, surnameTextField, phoneTextField, phone2TextField, addressTextField, subscriptionComboBox);
        newCustomer.setId(customer.getId());
        customerDAO.edit(newCustomer);
        return newCustomer;
    }

    public void remove(Customer customer) throws SQLException {
        if(customer == null) return;

        ButtonType archive = new ButtonType("Արխիվացնել", ButtonBar.ButtonData.OK_DONE);
        ButtonType back = new ButtonType("Հետ", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType removeHistory = new ButtonType("Ջնջել պատմությունը", ButtonBar.ButtonData.OK_DONE);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Հաստատում");
        alert.setHeaderText("Հաստատեք, որ ցանկանում եք ջնջել հաճախորդին");
        alert.setContentText("Ցանկանու՞մ եք ջնջել հաճախորդին");
        alert.getButtonTypes().setAll(archive, removeHistory, back);

        Optional<ButtonType> result =  alert.showAndWait();

        if(result.isPresent()){
            if(result.get() == archive)
                customerDAO.remove(customer, false);
            else if (result.get() == removeHistory)
                customerDAO.remove(customer, true);
        }
    }

    public List<Customer> getActual() throws SQLException{
        return customerDAO.getActual();
    }
    public List<Customer> getAll() throws SQLException{
        return customerDAO.getAll();
    }
    public Customer getByCard(String card) throws SQLException {
        return customerDAO.getByCard(card);
    }

    public Customer getSelected(){
        return selected;
    }
    public void select(Customer selected){
        CustomerService.selected = selected;
    }
    public void removeSelected(){
        CustomerService.selected = null;
    }

}
