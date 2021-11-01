package com.fitness.Controller.Fragment.Subscription;

import com.fitness.Controller.Constant.Fragment;
import com.fitness.Controller.Controller;
import com.fitness.Element.SubscriptionButton;
import com.fitness.Model.Work.Employment;
import com.fitness.Model.Work.Subscription;
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

public class EditSubscriptionController extends GridPane implements Controller {
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
    private Button editSubscriptionButton;
    @FXML
    private Button previousButton;

    private EmploymentService employmentService = new EmploymentService();
    private SubscriptionService subscriptionService = new SubscriptionService();
    private ObservableList<EmploymentQuantity> employmentQuantities = FXCollections.observableArrayList();
    private Subscription selectedSubscription = null;

    public EditSubscriptionController() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fitness/fragment/subscription/edit_subscription.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
    }

    private void initTable(){
        employmentQuantities.addAll(
                selectedSubscription.getEmploymentsQuantities()
        );

        employmentColumn.setCellValueFactory(new PropertyValueFactory<>("employment"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        subscriptionEmploymentTable.setItems(employmentQuantities);
    }

    private void initTextFields() {
        nameTextField.setText(selectedSubscription.getName());
        priceTextField.setText(selectedSubscription.getPrice() + "");
    }

    private void initComboBox() throws SQLException {
        employmentComboBox.setItems(FXCollections.observableArrayList(
                employmentService.getEmployments()
        ));
    }

    private void initListeners(){
        addEmploymentButton.setOnAction(event -> {
            EmploymentQuantity employmentQuantity = subscriptionService.addEmploymentQuantity(employmentComboBox, quantityTextField);

            if(employmentQuantity != null && !employmentQuantities.contains(employmentQuantity)) {
                employmentQuantities.add(employmentQuantity);
            }
        });
        deleteEmploymentButton.setOnAction(event -> {
            EmploymentQuantity employmentQuantity = subscriptionEmploymentTable.getSelectionModel().getSelectedItem();
            if(employmentQuantity != null){
                employmentQuantities.remove(employmentQuantity);
            }
        });

        editSubscriptionButton.setOnAction(event -> {
            try {
                if(subscriptionService.edit(
                        selectedSubscription,
                        nameTextField,
                        priceTextField,
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
        selectedSubscription = SubscriptionButton.getSelected().getSubscription();
        initTable();
        initTextFields();
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
                priceTextField,
                quantityTextField
        );
        Clear.table(subscriptionEmploymentTable);
        Clear.comboBox(employmentComboBox);
    }
}
