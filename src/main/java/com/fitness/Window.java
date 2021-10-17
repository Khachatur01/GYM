package com.fitness;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Window {
    public void close(Stage stage){
        stage.close();
    }

    public Stage open(String url, Stage stage) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource(url));
        stage.setScene(new Scene(parent));
        stage.show();
        return stage;
    }

}
