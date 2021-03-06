package com.fitness.Controller.Fragment.Customer;

import com.fitness.Constant.Fragment;
import com.fitness.Controller.Controller;
import com.fitness.DataSource.Log.Log;
import com.fitness.Element.MaskField;
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
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

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
    private MaskField phoneMaskField;
    private MaskField phone2MaskField;
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

        this.initMaskFields();
        this.initTextFieldFocusingByKey(new TextField[] {
                cardTextField, nameTextField, surnameTextField,
                phoneMaskField, phone2MaskField, addressTextField,
        });
        this.initListeners();
    }

    private void loadOldData() throws SQLException {
        subscriptionComboBox.setItems(FXCollections.observableArrayList(subscriptionService.getActual()));
        customer = customerService.getSelected();
        if(customer != null)
            Fill.customer(customer, cardTextField, nameTextField, surnameTextField, phoneMaskField, phone2MaskField, addressTextField, subscriptionComboBox);
    }
    private void initMaskFields() {
        phoneMaskField = new MaskField();
        phoneMaskField.setMask("+374(DD) DD-DD-DD");
        GridPane.setValignment(phoneMaskField, VPos.CENTER);
        GridPane.setHalignment(phoneMaskField, HPos.RIGHT);
        GridPane.setVgrow(phoneMaskField, Priority.ALWAYS);
        GridPane.setHgrow(phoneMaskField, Priority.ALWAYS);

        phone2MaskField = new MaskField();
        phone2MaskField.setMask("+374(DD) DD-DD-DD");
        GridPane.setValignment(phone2MaskField, VPos.CENTER);
        GridPane.setHalignment(phone2MaskField, HPos.RIGHT);
        GridPane.setVgrow(phone2MaskField, Priority.ALWAYS);
        GridPane.setHgrow(phone2MaskField, Priority.ALWAYS);

        this.add(phoneMaskField, 1, 5);
        this.add(phone2MaskField, 1, 6);
    }
    private void initListeners(){
        editButton.setOnAction(event -> {
            this.confirm();
        });

        previousButton.setOnAction(event -> {
            this.back();
        });
    }

    private void confirm() {
        //return null when something went wrong
        try {
            if(customerService.edit(
                    customer,
                    cardTextField,
                    nameTextField,
                    surnameTextField,
                    phoneMaskField,
                    phone2MaskField,
                    addressTextField,
                    subscriptionComboBox) != null) {
                this.stop();
                Window.getFragment(Fragment.CUSTOMER).start();
            }
        } catch (SQLException e) {
            Log.error("Can't edit customer data", e);
        }
    }
    private void back() {
        customerService.removeSelected();
        this.stop();
        Window.getFragment(Fragment.CUSTOMER).start();
    }

    @Override
    public void start() {
        makeActive();
        try {
            loadOldData();
        } catch (SQLException e) {
            Log.error("Can't fetch customer data", e);
        }

        this.getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                this.confirm();
            } else if (event.getCode() == KeyCode.ESCAPE) {
                this.back();
            }
        });
    }

    @Override
    public void stop() {
        Clear.textField(
                cardTextField,
                nameTextField,
                surnameTextField,
                addressTextField
        );
        Clear.maskField(
                phoneMaskField,
                phone2MaskField
        );
        Clear.comboBox(subscriptionComboBox);
    }
}
