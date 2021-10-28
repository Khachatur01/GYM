package com.fitness.Controller.Fragment.Customer;

import com.fitness.Controller.Constant.Fragment;
import com.fitness.Model.Person.Customer;
import com.fitness.Model.Person.Person;
import com.fitness.Model.Work.Subscription;
import com.fitness.Service.Person.CustomerService;
import com.fitness.Service.Verifier;
import com.fitness.Window;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class AddCustomerController extends GridPane {
    @FXML
    private TextField oldCardTextField;
    @FXML
    private TextField cardTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField surnameTextField;
    @FXML
    private TextField phoneTextField;
    @FXML
    private TextField phone2TextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private ComboBox<Subscription> subscriptionComboBox;
    @FXML
    private Button previousButton;
    @FXML
    private Button addButton;

    CustomerService customerService = new CustomerService();

    public AddCustomerController() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fitness/fragment/customer/add_customer.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
        initListeners();
    }

    private void initListeners(){
        addButton.setOnAction(event -> {
            Customer customer = new Customer();
            String card = cardTextField.getText();
            String name = nameTextField.getText();
            String surname = surnameTextField.getText();
            String phone = phoneTextField.getText();
            String phone2 = phone2TextField.getText();
            String address = addressTextField.getText();
            Subscription subscription = subscriptionComboBox.getValue();

            if(     Verifier.correctCard(card, cardTextField) &&
                    Verifier.correctName(name, nameTextField) &&
                    Verifier.correctSurname(surname, surnameTextField) &&
                    Verifier.correctPhone(phone, phoneTextField) &&
                    Verifier.correctPhone(phone2, phone2TextField) &&
                    Verifier.correctAddress(address, addressTextField) &&
                    subscription != null
            ){
                customer.setCard(card);
                customer.setName(new Person.Name(name, surname));
                customer.setPhone(phone);
                customer.setPhone2(phone2);
                customer.setAddress(address);
                customer.setSubscription(subscription);
                customerService.add(customer);
            }

        });

        previousButton.setOnAction(event -> {
            Window.openFragment(Fragment.CUSTOMER);
        });

    }
}
