package com.fitness.Controller.Fragment.Customer;

import com.fitness.Constant.Fragment;
import com.fitness.Controller.Controller;
import com.fitness.DataSource.Log.Log;
import com.fitness.Element.MaskField;
import com.fitness.Model.Person.Customer;
import com.fitness.Model.Work.DateTime;
import com.fitness.Model.Work.Subscription;
import com.fitness.Service.Archive.ArchiveService;
import com.fitness.Service.BackYear.BackYearService;
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
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.util.StringConverter;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

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
    private CheckBox backYearCheckBox;
    @FXML
    private GridPane backYearGridPane;
    @FXML
    private DatePicker datePicker;
    private MaskField timeMaskField;

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

        this.add(phoneMaskField, 1, 6);
        this.add(phone2MaskField, 1, 7);

        timeMaskField = new MaskField();
        timeMaskField.setMask("DD:DD");
        timeMaskField.setStyle("-fx-min-width: 100; -fx-pref-width: 100; -fx-max-width: 100; -fx-alignment: center");
        this.backYearGridPane.add(timeMaskField, 2, 1);

    }
    private void initListeners(){
        oldCardTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            Customer customer = null;
            try {
                customer = customerService.getByCard(newValue);
            } catch (SQLException e) {
                Log.warning("Can't fetch customer data by card number");
            }
            System.out.println(customer);
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
            DateTime dateTime = null;
            if(backYearCheckBox.isSelected()) {
                dateTime = BackYearService.getDateTime(timeMaskField, datePicker);
                if(dateTime == null) return;
            }

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
                    archiveService.add(dateTime, customer, null, null, bonusCheckBox.isSelected());

                    this.stop();
                    Window.getFragment(Fragment.CUSTOMER).start();
                }
            } catch (SQLException e) {
                Log.error("Can't add customer");
            }

        });

        previousButton.setOnAction(event -> {
            customerService.removeSelected();
            this.stop();
            Window.getFragment(Fragment.CUSTOMER).start();
        });

        surnameTextField.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.TAB)
                phoneMaskField.requestFocus();
        });

        backYearCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> backYearGridPane.setDisable(!newValue));


    }
    public void initSubscriptionComboBox() throws SQLException {
        subscriptionComboBox.setItems(FXCollections.observableArrayList(subscriptionService.getActual()));
    }

    private void initDatePicker() {
        StringConverter<LocalDate> converter = DateTime.getConverter();

        LocalDate localDate = LocalDate.now();
        datePicker.setConverter(converter);
        datePicker.setValue(localDate);
    }

    @Override
    public void start() {
        makeActive();
        initDatePicker();
        try {
            initSubscriptionComboBox();
        } catch (SQLException e) {
            Log.error("Can't fetch actual subscriptions");
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
                addressTextField
        );
        Clear.maskField(
                phoneMaskField,
                phone2MaskField
        );
        Clear.comboBox(subscriptionComboBox);
    }
}
