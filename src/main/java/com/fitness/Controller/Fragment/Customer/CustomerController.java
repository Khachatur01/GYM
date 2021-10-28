package com.fitness.Controller.Fragment.Customer;

import com.fitness.Controller.Constant.Fragment;
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

public class CustomerController extends GridPane {
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

    ObservableList<Customer> customers = FXCollections.observableArrayList();
    CustomerService customerService = new CustomerService();

    public CustomerController() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fitness/fragment/customer/customer.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();

        initTable();
        initListeners();
    }

    private void initListeners(){
        editButton.setOnAction(event -> {
            Window.openFragment(Fragment.EDIT_CUSTOMER);
        });

        addButton.setOnAction(event -> {
            Window.openFragment(Fragment.ADD_CUSTOMER);
        });

        deleteButton.setOnAction(event -> {
            ButtonType yes = new ButtonType("Այո", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("Ոչ", ButtonBar.ButtonData.CANCEL_CLOSE);
            ButtonType removeHistory = new ButtonType("Ջնջել պատմությունը", ButtonBar.ButtonData.OK_DONE);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Հաստատում");
            alert.setHeaderText("Հետևյալ հաճախորդը կջնջվի");
            alert.setContentText("Ցանկանու՞մ եք ջնջել հաճախորդին");
            alert.getButtonTypes().setAll(yes, removeHistory, no);

            Optional<ButtonType> result =  alert.showAndWait();
            Customer customer = customersTable.getSelectionModel().getSelectedItem();

            if(result.isPresent() && customer != null){
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


        customers.add(new Customer(-1, new Person.Name("name", "surname"), "123", "098", "099", "address", null));
        customers.add(new Customer(-1, new Person.Name("name1", "surname"), "123", "098", "099", "address", null));
        customers.add(new Customer(-1, new Person.Name("name2", "surname"), "123", "098", "099", "address", null));
        customers.add(new Customer(-1, new Person.Name("name3", "surname"), "123", "098", "099", "address", null));

        customersTable.setItems(customers);
    }

    private void removeCustomer(Customer customer, boolean removeHistory) {
        customersTable.getItems().remove(customer);
        customers.remove(customer);
        customerService.remove(customer, removeHistory);
    }

}