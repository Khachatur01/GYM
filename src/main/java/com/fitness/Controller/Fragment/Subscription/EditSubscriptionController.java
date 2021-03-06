package com.fitness.Controller.Fragment.Subscription;

import com.fitness.Constant.Fragment;
import com.fitness.Controller.Controller;
import com.fitness.DataSource.Log.Log;
import com.fitness.Element.SubscriptionButton;
import com.fitness.Model.Work.Employment;
import com.fitness.Model.Work.EmploymentQuantity;
import com.fitness.Model.Work.Subscription;
import com.fitness.Service.Clear;
import com.fitness.Service.Work.EmploymentService;
import com.fitness.Service.Work.SubscriptionService;
import com.fitness.Window;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.sql.SQLException;

public class EditSubscriptionController extends GridPane implements Controller {
    @FXML
    private TextField nameTextField;
    @FXML
    private Label totalPriceLabel;
    @FXML
    private TableView<EmploymentQuantity> subscriptionEmploymentTable;
    @FXML
    private TableColumn<EmploymentQuantity, Employment> employmentColumn;
    @FXML
    private TableColumn<EmploymentQuantity, Integer> quantityColumn;
    @FXML
    private TableColumn<EmploymentQuantity, Integer> priceColumn;
    @FXML
    private Button deleteEmploymentButton;
    @FXML
    private Button addEmploymentButton;
    @FXML
    private ComboBox<Employment> employmentComboBox;
    @FXML
    private TextField quantityTextField;
    @FXML
    private TextField priceTextField;
    @FXML
    private Button editSubscriptionButton;
    @FXML
    private Button previousButton;

    private EmploymentService employmentService = new EmploymentService();
    private SubscriptionService subscriptionService = new SubscriptionService();
    private ObservableList<EmploymentQuantity> employmentQuantities = FXCollections.observableArrayList();
    private Subscription selectedSubscription = null;

    private int totalPrice = 0;

    public EditSubscriptionController() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fitness/fragment/subscription/edit_subscription.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();

        this.initTextFieldFocusingByKey(new TextField[] {
                nameTextField, quantityTextField, priceTextField,
        });
        this.initListeners();
    }

    private void initTable(){
        employmentQuantities.addAll(
                selectedSubscription.getEmploymentsQuantities()
        );

        employmentColumn.setCellValueFactory(new PropertyValueFactory<>("employment"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        subscriptionEmploymentTable.setItems(employmentQuantities);
    }
    private void initTextFields() {
        nameTextField.setText(selectedSubscription.getName());
    }
    private void initComboBox() throws SQLException {
        employmentComboBox.setItems(FXCollections.observableArrayList(
                employmentService.getAll()
        ));
    }
    private void initTotalPrice() {
        for(EmploymentQuantity employmentQuantity: subscriptionEmploymentTable.getItems())
            this.totalPrice += employmentQuantity.getPrice();

        totalPriceLabel.setText(this.totalPrice + " ????????");
    }
    private void initListeners(){
        addEmploymentButton.setOnAction(event -> {
            EmploymentQuantity employmentQuantity = subscriptionService.addEmploymentQuantity(employmentComboBox, quantityTextField, priceTextField);
            if(employmentQuantity != null && !employmentQuantities.contains(employmentQuantity)) {
                employmentQuantities.add(employmentQuantity);
                this.totalPrice += employmentQuantity.getPrice();
                totalPriceLabel.setText(this.totalPrice + " ????????");
            }
        });
        deleteEmploymentButton.setOnAction(event -> {
            EmploymentQuantity employmentQuantity = subscriptionEmploymentTable.getSelectionModel().getSelectedItem();
            if(employmentQuantity != null){
                employmentQuantities.remove(employmentQuantity);
                this.totalPrice -= employmentQuantity.getPrice();
                totalPriceLabel.setText(this.totalPrice + " ????????");
            }
        });

        editSubscriptionButton.setOnAction(event -> {
            this.confirm();
        });
        previousButton.setOnAction(event -> {
            this.back();
        });
    }

    private void confirm() {
        try {
            if (subscriptionService.edit(
                    selectedSubscription,
                    nameTextField,
                    employmentQuantities) != null) {
                this.stop();
                Window.getFragment(Fragment.SUBSCRIPTION).start();
            }
        } catch (SQLException e) {
            Log.error("Can't edit subscription", e);
        }
    }
    private void back() {
        this.stop();
        Window.getFragment(Fragment.SUBSCRIPTION).start();
    }

    @Override
    public void start() {
        makeActive();
        selectedSubscription = SubscriptionButton.getSelected().getSubscription();
        initTable();
        initTextFields();
        initTotalPrice();
        try {
            initComboBox();
        } catch (SQLException e) {
            Log.error("Can't fetch actual employments", e);
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
                nameTextField,
                quantityTextField,
                priceTextField
        );
        Clear.table(subscriptionEmploymentTable);
        Clear.comboBox(employmentComboBox);
        this.totalPriceLabel.setText("0 ????????");
        this.totalPrice = 0;
    }
}
