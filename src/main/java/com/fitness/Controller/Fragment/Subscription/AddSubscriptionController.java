package com.fitness.Controller.Fragment.Subscription;

import com.fitness.Controller.Constant.Fragment;
import com.fitness.Controller.Controller;
import com.fitness.Model.Work.Employment;
import com.fitness.Model.Work.Subscription;
import com.fitness.Model.Work.EmploymentQuantity;
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
    private TextField priceTextField;
    @FXML
    private TableView<EmploymentQuantity> subscriptionEmploymentTable;
    @FXML
    private TableColumn<EmploymentQuantity, Employment> employmentColumn;
    @FXML
    private TableColumn<EmploymentQuantity, Integer> quantityColumn;
    @FXML
    private Button addEmploymentButton;
    @FXML
    private TextField quantityTextField;
    @FXML
    private ComboBox<Employment> employmentComboBox;
    @FXML
    private Button deleteEmploymentButton;
    @FXML
    private Button addSubscriptionButton;
    @FXML
    private Button previousButton;

    private EmploymentService employmentService = new EmploymentService();
    private SubscriptionService subscriptionService = new SubscriptionService();
    private ObservableList<EmploymentQuantity> subscriptionEmployments = FXCollections.observableArrayList();

    public AddSubscriptionController() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fitness/fragment/subscription/add_subscription.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
    }

    private void initTable(){
        employmentColumn.setCellValueFactory(new PropertyValueFactory<>("employment"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        subscriptionEmploymentTable.setItems(subscriptionEmployments);
    }

    private void initComboBox() throws SQLException {
        employmentComboBox.setItems(FXCollections.observableArrayList(
                employmentService.getEmployments()
        ));
    }

    private void initListeners(){
        addEmploymentButton.setOnAction(event -> {
            EmploymentQuantity subscriptionEmployment = subscriptionService.addSubscriptionEmployment(employmentComboBox, quantityTextField);

            if(subscriptionEmployment != null && !subscriptionEmployments.contains(subscriptionEmployment)) {
                subscriptionEmployments.add(subscriptionEmployment);
            }
        });
        deleteEmploymentButton.setOnAction(event -> {
            EmploymentQuantity subscriptionEmployment = subscriptionEmploymentTable.getSelectionModel().getSelectedItem();
            if(subscriptionEmployment != null){
                subscriptionEmployments.remove(subscriptionEmployment);
            }
        });

        addSubscriptionButton.setOnAction(event -> {
            if(subscriptionService.add(
                    new Subscription(),
                    nameTextField,
                    priceTextField,
                    subscriptionEmployments) != null) {
                this.stop();
                Window.getFragment(Fragment.SUBSCRIPTION).start();
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
        nameTextField.setText("");
        nameTextField.getStyleClass().remove("selected");
        priceTextField.setText("");
        priceTextField.getStyleClass().remove("selected");
        quantityTextField.setText("");
        quantityTextField.getStyleClass().remove("selected");
        subscriptionEmploymentTable.getItems().clear();
        employmentComboBox.getItems().clear();
    }
}
