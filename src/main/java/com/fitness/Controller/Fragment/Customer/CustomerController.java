package com.fitness.Controller.Fragment.Customer;

import com.fitness.Controller.Constant.Fragment;
import com.fitness.Controller.Controller;
import com.fitness.Model.Person.Customer;
import com.fitness.Model.Person.Person;
import com.fitness.Service.Person.CustomerService;
import com.fitness.Window;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.Optional;

public class CustomerController extends GridPane implements Controller {
    @FXML
    private TableView<Customer> customersTable;
    @FXML
    private TableColumn<Customer, String> cardColumn;
    @FXML
    private TableColumn<Customer, Person.Name> fullNameColumn;
    @FXML
    private TableColumn<Customer, String> subscriptionColumn;
    @FXML
    private TableColumn<Customer, String> phoneColumn;
    @FXML
    private TableColumn<Customer, String> phone2Column;
    @FXML
    private TableColumn<Customer, String> addressColumn;
    @FXML
    private Button editButton;
    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;

    private ObservableList<Customer> customers = FXCollections.observableArrayList();
    private CustomerService customerService = new CustomerService();

    public CustomerController() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fitness/fragment/customer/customer.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
    }

    private void initListeners(){
        editButton.setOnAction(event -> {
            Customer customer = customersTable.getSelectionModel().getSelectedItem();
            customerService.setCache(customer);
            if(customer != null)
                Window.getFragment(Fragment.EDIT_CUSTOMER).start();
        });

        addButton.setOnAction(event -> {
            Window.getFragment(Fragment.ADD_CUSTOMER).start();
        });

        deleteButton.setOnAction(event -> {
            Customer customer = customersTable.getSelectionModel().getSelectedItem();
            if(customer == null) return;

            ButtonType yes = new ButtonType("Ջնջել", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("Հետ", ButtonBar.ButtonData.CANCEL_CLOSE);
            ButtonType removeHistory = new ButtonType("Ջնջել պատմությունը", ButtonBar.ButtonData.OK_DONE);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Հաստատում");
            alert.setHeaderText("Հաստատեք, որ ցանկանում եք ջնջել հաճախորդին");
            alert.setContentText("Ցանկանու՞մ եք ջնջել հաճախորդին");
            alert.getButtonTypes().setAll(yes, removeHistory, no);

            Optional<ButtonType> result =  alert.showAndWait();

            if(result.isPresent()){
                if(result.get() == yes)
                    removeCustomer(customer, true);
                else if (result.get() == removeHistory)
                    removeCustomer(customer, false);
            }
        });
    }

    private void initTable(){
        cardColumn.setCellValueFactory(new PropertyValueFactory<>("card"));
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        subscriptionColumn.setCellValueFactory(new PropertyValueFactory<>("subscription"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        phone2Column.setCellValueFactory(new PropertyValueFactory<>("phone2"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        customersTable.getItems().clear();

        customers.setAll(customerService.getCustomers());

        customersTable.setItems(customers);
    }

    private void removeCustomer(Customer customer, boolean removeHistory) {
        customersTable.getItems().remove(customer);
        customers.remove(customer);
        customerService.remove(customer, removeHistory);
    }

    @Override
    public void start() {
        initTable();
        initListeners();
    }
}