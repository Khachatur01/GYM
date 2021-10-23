package com.fitness;

import com.fitness.DataSource.DB;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.SQLException;

public class Main extends Application {
    Window window = new Window();
    @Override
    public void init(){

    }
    @Override
    public void start(Stage preloadStage) throws Exception{
        preloadStage.initStyle(StageStyle.UNDECORATED);
        window.open("preload.fxml", preloadStage);
        try {
            DB.connect();
            window.initMenuPanes();//must take a some time

            window.close(preloadStage);
            window.open("menu.fxml", new Stage());

        } catch (SQLException | IOException ignored){

        }
    }

    public static void main(String[] args) throws SQLException {
        launch(args);
        DB.disconnect();
    }

}
