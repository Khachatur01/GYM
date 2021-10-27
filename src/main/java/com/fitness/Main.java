package com.fitness;

import com.fitness.Controller.Constant.Page;
import com.fitness.DataSource.DB;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.SQLException;

public class Main extends Application {
    Window window = new Window();

    @Override
    public void start(Stage stage) throws Exception{
        stage.initStyle(StageStyle.UNDECORATED);
        window.open(Page.PRELOADER.getValue(), stage);

        // closes preloader(heavy task) page and opens main menu
        new Preloader(stage).start();

        // 1070 x 610 min window size
    }

    public static void main(String[] args) throws SQLException {
        launch(args);
        DB.disconnect();
    }

}
