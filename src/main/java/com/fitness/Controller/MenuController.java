package com.fitness.Controller;

import com.fitness.Constant.Fragment;
import com.fitness.Model.Work.DateTime;
import com.fitness.Window;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    @FXML
    private Label calendarLabel;
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
    private Button positionsButton;
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

        calendarLabel.setText(Objects.requireNonNull(DateTime.getCurrentWeek()).getArmFullName());

        cardButton.setOnAction(event -> {
            Window.stopActiveController();
            Controller controller = Window.getFragment(Fragment.WITH_CARD, enterButton);
            Window.setActiveController(controller);

            controller.start();
        });
        withoutCardButton.setOnAction(event -> {
            Window.stopActiveController();
            Controller controller = Window.getFragment(Fragment.WITHOUT_CARD, enterButton);
            Window.setActiveController(controller);

            controller.start();
        });
        customersButton.setOnAction(event -> {
            Window.stopActiveController();
            Controller controller = Window.getFragment(Fragment.CUSTOMER, customersButton);
            Window.setActiveController(controller);

            controller.start();
        });
        employeesButton.setOnAction(event -> {
            Window.stopActiveController();
            Controller controller = Window.getFragment(Fragment.EMPLOYEE, employeesButton);
            Window.setActiveController(controller);

            controller.start();
        });
        servicesButton.setOnAction(event -> {
            Window.stopActiveController();
            Controller controller = Window.getFragment(Fragment.EMPLOYMENT, servicesButton);
            Window.setActiveController(controller);

            controller.start();
        });
        positionsButton.setOnAction(event -> {
            Window.stopActiveController();
            Controller controller = Window.getFragment(Fragment.POSITION, positionsButton);
            Window.setActiveController(controller);

            controller.start();
        });
        subscriptionButton.setOnAction(event -> {
            Window.stopActiveController();
            Controller controller = Window.getFragment(Fragment.SUBSCRIPTION, subscriptionButton);
            Window.setActiveController(controller);

            controller.start();
        });
        reportButton.setOnAction(event -> {
            Window.stopActiveController();
            Controller controller = Window.getFragment(Fragment.REPORT, reportButton);
            Window.setActiveController(controller);

            controller.start();
        });
        settingsButton.setOnAction(event -> {
            Window.stopActiveController();
            Controller controller = Window.getFragment(Fragment.SETTINGS, settingsButton);
            Window.setActiveController(controller);

            controller.start();
        });

        Platform.runLater(() -> {
            Controller defaultController = Window.getFragment(Fragment.WITH_CARD, enterButton);
            Window.setActiveController(defaultController);
            defaultController.start();
        });
    }
}
