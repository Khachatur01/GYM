package com.fitness.Controller;

import com.fitness.Controller.Constant.Fragment;
import com.fitness.Style.ButtonStyle;
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
            Window.openFragment(Fragment.WITH_CARD, enterButton);
        });
        withoutCardButton.setOnAction(event -> {
            Window.openFragment(Fragment.WITHOUT_CARD, enterButton);
        });
        customersButton.setOnAction(event -> {
            Window.openFragment(Fragment.CUSTOMER, customersButton);
        });
        employeesButton.setOnAction(event -> {
            Window.openFragment(Fragment.EMPLOYEE, employeesButton);
        });
        servicesButton.setOnAction(event -> {
            Window.openFragment(Fragment.SERVICE, servicesButton);
        });
        subscriptionButton.setOnAction(event -> {
            Window.openFragment(Fragment.SUBSCRIPTION, subscriptionButton);
        });
        reportButton.setOnAction(event -> {
            Window.openFragment(Fragment.REPORT, reportButton);
        });
        settingsButton.setOnAction(event -> {
            Window.openFragment(Fragment.SETTINGS, settingsButton);
        });
    }
}
