package com.fitness;

import com.fitness.Constant.Page;
import com.fitness.DataSource.DB;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.SQLException;

public class Launcher extends Application {
    Window window = new Window();

    @Override
    public void start(Stage stage) throws Exception{
        stage.initStyle(StageStyle.UNDECORATED);
        window.open(Page.PRELOADER.getValue(), stage);

        /* closes preloader(heavy task) page and opens main menu */
        new Preloader(stage).start();
    }

    public static void main(String[] args) throws SQLException {
        launch(args);
        DB.disconnect();
    }

}
