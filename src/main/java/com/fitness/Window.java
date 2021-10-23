package com.fitness;

import com.fitness.Controller.Fragment.WithCardController;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Window {
    private static WithCardController withCard;

    public void close(Stage stage){
        stage.close();
    }

    public Stage open(String url, Stage stage) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource(url));
        stage.setScene(new Scene(parent));
        stage.show();
        return stage;
    }

    public void initMenuPanes() throws IOException {
        withCard = new WithCardController();

    }

    public WithCardController getWithCardLoader() {
        return withCard;
    }
}
