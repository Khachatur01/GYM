package com.fitness.Controller.Fragment.Customer;

import com.fitness.Controller.Constant.Fragment;
import com.fitness.Controller.Controller;
import com.fitness.Model.Person.Customer;
import com.fitness.Model.Work.Subscription;
import com.fitness.Service.Clear;
import com.fitness.Service.Fill;
import com.fitness.Service.Person.CustomerService;
import com.fitness.Service.Work.SubscriptionService;
import com.fitness.Window;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class AddCustomerController extends GridPane implements Controller {
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

    private CustomerService customerService = new CustomerService();
    private SubscriptionService subscriptionService = new SubscriptionService();

    private static boolean fieldAreClean = true;

    public AddCustomerController() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fitness/fragment/customer/add_customer.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
    }
    private void initListeners(){
        oldCardTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            Customer customer = customerService.getCustomer(newValue);
            if(!fieldAreClean) {
                Clear.textField(
                        cardTextField,
                        nameTextField,
                        surnameTextField,
                        phoneTextField,
                        phone2TextField,
                        addressTextField
                );
                Clear.comboBox(subscriptionComboBox);
                fieldAreClean = true;
            } else if (customer != null){
                customer.setCard(""); // don't fill card number field
                Fill.customer(customer, cardTextField, nameTextField, surnameTextField, phoneTextField, phone2TextField, addressTextField, subscriptionComboBox);
                fieldAreClean = false;
            }
        });

        addButton.setOnAction(event -> {
            //return null when something went wrong
            customerService.add(
                    cardTextField,
                    nameTextField,
                    surnameTextField,
                    phoneTextField,
                    phone2TextField,
                    addressTextField,
                    subscriptionComboBox
            );
        });

        previousButton.setOnAction(event -> {
            customerService.setCache(null);
            this.stop();
            Window.getFragment(Fragment.CUSTOMER).start();
        });

    }
    public void initComboBox(){
        subscriptionComboBox.setItems(FXCollections.observableArrayList(subscriptionService.getSubscriptions()));
    }

    @Override
    public void start() {
        makeActive();
        initListeners();
        initComboBox();
    }

    @Override
    public void stop() {
        Clear.textField(
                oldCardTextField,
                cardTextField,
                nameTextField,
                surnameTextField,
                phoneTextField,
                phone2TextField,
                addressTextField
        );
        Clear.comboBox(subscriptionComboBox);
    }
}
