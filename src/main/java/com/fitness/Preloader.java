package com.fitness;

import com.fitness.Controller.Constant.Page;
import com.fitness.DataSource.DB;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Preloader extends Thread{
    private Stage stage;
    private Window window = new Window();

    public Preloader(Stage stage){
        this.stage = stage;
    }

    @Override
    public void run(){
        try {
            //heavy task
            DB.connect();
            // very heavy task
            window.initMenuPanes();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        Platform.runLater(() -> {
            try {
                window.close(this.stage);
                window.open(Page.MENU.getValue(), new Stage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
