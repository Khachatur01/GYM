package com.fitness.Service.Person;

import com.fitness.DAO.Person.CustomerDAO;
import com.fitness.Element.MaskField;
import com.fitness.Model.Person.Customer;
import com.fitness.Model.Person.Person;
import com.fitness.Model.Work.EmploymentQuantity;
import com.fitness.Model.Work.Subscription;
import com.fitness.Service.Verify;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class CustomerService {
    private static Customer selected = null;
    private CustomerDAO customerDAO = new CustomerDAO();

    public Customer makeGuestCustomer(TextField nameTextField, TextField surnameTextField, MaskField phoneMaskField, MaskField phone2MaskField, TextField addressTextField){
        Customer customer = null;
        String name = nameTextField.getText();
        String surname = surnameTextField.getText();
        String phone = phoneMaskField.getText();
        String phone2 = phone2MaskField.getText();
        String address = addressTextField.getText();

        if(     Verify.name(name, nameTextField) &&
                Verify.surname(surname, surnameTextField) &&
                Verify.phone(phone, phoneMaskField) &&
                Verify.phone2(phone2, phone2MaskField) &&
                (address.equals("") || Verify.address(address, addressTextField))
        ){
            customer = new Customer();
            customer.setName(new Person.Name(name, surname));
            customer.setPhone(phone);
            customer.setPhone2(phone2);
            customer.setAddress(address);
        }
        return customer;
    }
    private Customer makeCustomer(TextField cardTextField, TextField nameTextField, TextField surnameTextField, MaskField phoneMaskField, MaskField phone2MaskField, TextField addressTextField, ComboBox<Subscription> subscriptionComboBox){
        Customer customer = null;
        String card = cardTextField.getText();
        String name = nameTextField.getText();
        String surname = surnameTextField.getText();
        String phone = phoneMaskField.getText(); /* get with mask */
        String phone2 = phone2MaskField.getText(); /* get with mask */
        String address = addressTextField.getText();
        Subscription subscription = subscriptionComboBox.getValue();

        if(     Verify.card(card, cardTextField) &&
                Verify.name(name, nameTextField) &&
                Verify.surname(surname, surnameTextField) &&
                Verify.phone(phone, phoneMaskField) &&
                Verify.phone2(phone2, phone2MaskField) &&
                Verify.address(address, addressTextField) &&
                Verify.subscription(subscription, subscriptionComboBox)
        ){
            if (phone2MaskField.getPlainText().length() == 0)
                phone2 = null;

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

    public Customer addGuest(TextField nameTextField, TextField surnameTextField, MaskField phoneMaskField, MaskField phone2MaskField, TextField addressTextField) throws SQLException {
        Customer newCustomer = this.makeGuestCustomer(nameTextField, surnameTextField, phoneMaskField, phone2MaskField, addressTextField);
        customerDAO.addGuest(newCustomer);
        return newCustomer;
    }

    public Customer add(TextField cardTextField, TextField nameTextField, TextField surnameTextField, MaskField phoneMaskField, MaskField phone2MaskField, TextField addressTextField, ComboBox<Subscription> subscriptionComboBox) throws SQLException {
        Customer newCustomer = this.makeCustomer(cardTextField, nameTextField, surnameTextField, phoneMaskField, phone2MaskField, addressTextField, subscriptionComboBox);
        customerDAO.add(newCustomer);
        return newCustomer;
    }

    public Customer edit(Customer customer, TextField cardTextField, TextField nameTextField, TextField surnameTextField, MaskField phoneMaskField, MaskField phone2MaskField, TextField addressTextField, ComboBox<Subscription> subscriptionComboBox) throws SQLException {
        Customer newCustomer = this.makeCustomer(cardTextField, nameTextField, surnameTextField, phoneMaskField, phone2MaskField, addressTextField, subscriptionComboBox);
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

    public void remove(Customer customer, boolean removeHistory) throws SQLException {
        customerDAO.remove(customer, removeHistory);
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
    public Customer getByPhone(String phone) throws SQLException {
        return customerDAO.getByPhone(phone);
    }
    public Date getLastVisit(Customer customer) throws SQLException {
        return customerDAO.getLastVisit(customer);
    }
    public Date getRegistrationDate(Customer customer) throws SQLException {
        return customerDAO.getRegistrationDate(customer);
    }

    public boolean hasAvailableEmployment(Customer customer) throws SQLException {
        boolean hasAvailableEmployment = false;
        List<EmploymentQuantity> employmentQuantities = this.getAvailableEmploymentQuantities(customer);
        for(EmploymentQuantity employmentQuantity: employmentQuantities) {
            if(employmentQuantity.getQuantity() != 0) {
                hasAvailableEmployment = true;
                break;
            }
        }
        return hasAvailableEmployment;
    }
    /* get non bonus visits count by employment */
    public List<EmploymentQuantity> getAvailableEmploymentQuantities(Customer customer) throws SQLException {
        return customerDAO.getAvailableEmploymentQuantities(customer);
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
