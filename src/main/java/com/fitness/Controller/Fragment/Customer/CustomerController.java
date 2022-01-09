package com.fitness.Controller.Fragment.Customer;

import com.fitness.Constant.Fragment;
import com.fitness.Controller.Controller;
import com.fitness.DataSource.Log.Log;
import com.fitness.Model.Person.Customer;
import com.fitness.Model.Person.Person;
import com.fitness.Model.Work.Subscription;
import com.fitness.Service.Person.CustomerService;
import com.fitness.Window;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.sql.SQLException;

public class CustomerController extends GridPane implements Controller {
    @FXML
    private TableView<Customer> customersTable;
    @FXML
    private TableColumn<Customer, String> cardColumn;
    @FXML
    private TableColumn<Customer, Person.Name> fullNameColumn;
    @FXML
    private TableColumn<Customer, Subscription> subscriptionColumn;
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
            customerService.select(customer);
            if(customer != null)
                Window.getFragment(Fragment.EDIT_CUSTOMER).start();
        });

        addButton.setOnAction(event -> {
            Window.getFragment(Fragment.ADD_CUSTOMER).start();
        });

        deleteButton.setOnAction(event -> {
            Customer customer = customersTable.getSelectionModel().getSelectedItem();
            try {
                customerService.remove(customer);
            } catch (SQLException e) {
                Log.error("Can't delete customer");
            }
            customers.remove(customer);
        });
    }

    private void initTable() throws SQLException {
        cardColumn.setCellValueFactory(new PropertyValueFactory<>("card"));
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        subscriptionColumn.setCellValueFactory(new PropertyValueFactory<>("subscription"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        phone2Column.setCellValueFactory(new PropertyValueFactory<>("phone2"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        customersTable.getItems().clear();

        customers.setAll(customerService.getActual());

        customersTable.setItems(customers);
    }

    @Override
    public void start() {
        makeActive();
        try {
            initTable();
        } catch (SQLException e) {
            Log.warning("Can't fetch customers");
        }
        initListeners();
    }

    @Override
    public void stop() {

    }
}