package com.fitness.Controller.Fragment.Subscription;

import com.fitness.Controller.Constant.Fragment;
import com.fitness.Controller.Controller;
import com.fitness.Model.Work.Employment;
import com.fitness.Model.Work.EmploymentQuantity;
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
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.sql.SQLException;

public class AddSubscriptionController extends GridPane implements Controller {
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
    private Button addSubscriptionButton;
    @FXML
    private Button previousButton;

    private EmploymentService employmentService = new EmploymentService();
    private SubscriptionService subscriptionService = new SubscriptionService();
    private ObservableList<EmploymentQuantity> employmentQuantities = FXCollections.observableArrayList();

    private int totalPrice = 0;

    public AddSubscriptionController() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fitness/fragment/subscription/add_subscription.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
    }

    private void initTable(){
        employmentColumn.setCellValueFactory(new PropertyValueFactory<>("employment"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        subscriptionEmploymentTable.setItems(employmentQuantities);
    }

    private void initComboBox() throws SQLException {
        employmentComboBox.setItems(FXCollections.observableArrayList(
                employmentService.getActual()
        ));
    }

    private void initListeners(){
        addEmploymentButton.setOnAction(event -> {
            /* don't adds to database */
            EmploymentQuantity employmentQuantity = subscriptionService.addEmploymentQuantity(employmentComboBox, quantityTextField, priceTextField);
            if(employmentQuantity != null && !employmentQuantities.contains(employmentQuantity)) {
                employmentQuantities.add(employmentQuantity);
                this.totalPrice += employmentQuantity.getPrice();
                totalPriceLabel.setText(this.totalPrice + " դրամ");
            }
        });
        deleteEmploymentButton.setOnAction(event -> {
            EmploymentQuantity employmentQuantity = subscriptionEmploymentTable.getSelectionModel().getSelectedItem();
            if(employmentQuantity != null){
                employmentQuantities.remove(employmentQuantity);
                this.totalPrice -= employmentQuantity.getPrice();
                totalPriceLabel.setText(this.totalPrice + " դրամ");
            }
        });

        addSubscriptionButton.setOnAction(event -> {
            try {
                if(subscriptionService.add(
                        nameTextField,
                        employmentQuantities) != null) {
                    this.stop();
                    Window.getFragment(Fragment.SUBSCRIPTION).start();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        previousButton.setOnAction(event -> {
            this.stop();
            Window.getFragment(Fragment.SUBSCRIPTION).start();
        });
    }

    @Override
    public void start() {
        makeActive();
        initTable();
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
                nameTextField,
                quantityTextField
        );
        Clear.table(subscriptionEmploymentTable);
        Clear.comboBox(employmentComboBox);
        totalPriceLabel.setText("0 դրամ");
    }
}
