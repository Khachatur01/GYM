package com.fitness.Controller.Fragment.Customer;

import com.fitness.Controller.Constant.Fragment;
import com.fitness.Controller.Controller;
import com.fitness.Element.MaskField;
import com.fitness.Model.Archive.Archive;
import com.fitness.Model.Person.Customer;
import com.fitness.Model.Work.Subscription;
import com.fitness.Service.Archive.ArchiveService;
import com.fitness.Service.Clear;
import com.fitness.Service.Fill;
import com.fitness.Service.Person.CustomerService;
import com.fitness.Service.Work.SubscriptionService;
import com.fitness.Window;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.io.IOException;
import java.sql.SQLException;

public class AddCustomerController extends GridPane implements Controller {
    @FXML
    private TextField oldCardTextField;
    @FXML
    private TextField cardTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField surnameTextField;

    private MaskField phoneMaskField;
    private MaskField phone2MaskField;

    @FXML
    private TextField addressTextField;
    @FXML
    private ComboBox<Subscription> subscriptionComboBox;
    @FXML
    private CheckBox bonusCheckBox;
    @FXML
    private Button previousButton;
    @FXML
    private Button addButton;

    private CustomerService customerService = new CustomerService();
    private SubscriptionService subscriptionService = new SubscriptionService();
    private ArchiveService archiveService = new ArchiveService();

    private static boolean fieldsAreClean = true;

    public AddCustomerController() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fitness/fragment/customer/add_customer.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();

        phoneMaskField = new MaskField();
        phoneMaskField.setMask("+374(DD) DD-DD-DD");
        phoneMaskField.getStyleClass().add("textField");
        GridPane.setValignment(phoneMaskField, VPos.CENTER);
        GridPane.setHalignment(phoneMaskField, HPos.RIGHT);
        GridPane.setVgrow(phoneMaskField, Priority.ALWAYS);
        GridPane.setHgrow(phoneMaskField, Priority.ALWAYS);

        phone2MaskField = new MaskField();
        phone2MaskField.setMask("+374(DD) DD-DD-DD");
        phone2MaskField.getStyleClass().add("textField");
        GridPane.setValignment(phone2MaskField, VPos.CENTER);
        GridPane.setHalignment(phone2MaskField, HPos.RIGHT);
        GridPane.setVgrow(phone2MaskField, Priority.ALWAYS);
        GridPane.setHgrow(phone2MaskField, Priority.ALWAYS);

        this.add(phoneMaskField, 1, 6);
        this.add(phone2MaskField, 1, 7);
    }
    private void initListeners(){
        oldCardTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            Customer customer = null;
            try {
                customer = customerService.getByCard(newValue);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if(!fieldsAreClean) {
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
                fieldsAreClean = true;
            } else if (customer != null){
                customer.setCard(""); /* don't fill card number field */
                Fill.customer(customer, cardTextField, nameTextField, surnameTextField, phoneMaskField, phone2MaskField, addressTextField, subscriptionComboBox);
                fieldsAreClean = false;
            }
        });

        addButton.setOnAction(event -> {
            try {
                Customer customer = customerService.add(
                        cardTextField,
                        nameTextField,
                        surnameTextField,
                        phoneMaskField,
                        phone2MaskField,
                        addressTextField,
                        subscriptionComboBox
                );

                if(customer != null) {
                    archiveService.add(customer, null, true, bonusCheckBox.isSelected());

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
    public void initComboBox() throws SQLException {
        subscriptionComboBox.setItems(FXCollections.observableArrayList(subscriptionService.getActual()));
    }

    @Override
    public void start() {
        makeActive();
        try {
            initComboBox();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        initListeners();
    }

    @Override
    public void stop() {
        Clear.textField(
                oldCardTextField,
                cardTextField,
                nameTextField,
                surnameTextField,
                phoneMaskField,
                phone2MaskField,
                addressTextField
        );
        Clear.comboBox(subscriptionComboBox);
    }
}
