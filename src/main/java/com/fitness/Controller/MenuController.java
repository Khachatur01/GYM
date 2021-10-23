package com.fitness.Controller;

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

    Window window = new Window();
    Button selected;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cardButton.setOnAction(event -> {
            activePane.getChildren().setAll(window.getWithCardLoader());
            ButtonStyle.select(enterButton);
            selected = enterButton;
        });
    }
}
