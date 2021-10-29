package com.fitness.Controller;

import com.fitness.Controller.Constant.Fragment;
import com.fitness.Window;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    @FXML
    private Button enterButton;
    @FXML
    private Button cardButton;
    @FXML
    private Button withoutCardButton;
    @FXML
    private Button customersButton;
    @FXML
    private Button employeesButton;
    @FXML
    private Button servicesButton;
    @FXML
    private Button subscriptionButton;
    @FXML
    private Button reportButton;
    @FXML
    private Button settingsButton;
    @FXML
    private GridPane activePane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Window.setActivePane(activePane);

        cardButton.setOnAction(event -> {
            Window.getFragment(Fragment.WITH_CARD, enterButton).start();
        });
        withoutCardButton.setOnAction(event -> {
            Window.getFragment(Fragment.WITHOUT_CARD, enterButton).start();
        });
        customersButton.setOnAction(event -> {
            Window.getFragment(Fragment.CUSTOMER, customersButton).start();
        });
        employeesButton.setOnAction(event -> {
            Window.getFragment(Fragment.EMPLOYEE, employeesButton).start();
        });
        servicesButton.setOnAction(event -> {
            Window.getFragment(Fragment.SERVICE, servicesButton).start();
        });
        subscriptionButton.setOnAction(event -> {
            Window.getFragment(Fragment.SUBSCRIPTION, subscriptionButton).start();
        });
        reportButton.setOnAction(event -> {
            Window.getFragment(Fragment.REPORT, reportButton).start();
        });
        settingsButton.setOnAction(event -> {
            Window.getFragment(Fragment.SETTINGS, settingsButton).start();
        });
    }
}
