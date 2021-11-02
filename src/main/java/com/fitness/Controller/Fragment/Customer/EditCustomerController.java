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
import java.sql.SQLException;

public class EditCustomerController extends GridPane implements Controller {
    @FXML
    private TextField cardTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField surnameTextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private TextField phoneTextField;
    @FXML
    private TextField phone2TextField;
    @FXML
    private ComboBox<Subscription> subscriptionComboBox;
    @FXML
    private Button previousButton;
    @FXML
    private Button editButton;

    private CustomerService customerService = new CustomerService();
    private SubscriptionService subscriptionService = new SubscriptionService();
    private Customer customer;

    public EditCustomerController() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fitness/fragment/customer/edit_customer.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
    }

    public void loadOldData() throws SQLException {
        subscriptionComboBox.setItems(FXCollections.observableArrayList(subscriptionService.getActual()));
        customer = customerService.getSelected();
        if(customer != null)
            Fill.customer(customer, cardTextField, nameTextField, surnameTextField, phoneTextField, phone2TextField, addressTextField, subscriptionComboBox);
    }
    public void initListeners(){
        editButton.setOnAction(event -> {
            //return null when something went wrong
            try {
                if(customerService.edit(
                        customer,
                        cardTextField,
                        nameTextField,
                        surnameTextField,
                        phoneTextField,
                        phone2TextField,
                        addressTextField,
                        subscriptionComboBox) != null) {
                    this.stop();
                    Window.getFragment(Fragment.CUSTOMER).start();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        previousButton.setOnAction(event -> {
            customerService.removeSelected();
            this.stop();
            Window.getFragment(Fragment.CUSTOMER).start();
        });
    }

    @Override
    public void start() {
        makeActive();
        try {
            loadOldData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        initListeners();
    }

    @Override
    public void stop() {
        Clear.textField(
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
