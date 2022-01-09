package com.fitness;

import com.fitness.Constant.Page;
import com.fitness.DataSource.DB;
import com.fitness.DataSource.Log.Log;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Preloader extends Thread {
    private final Stage stage;
    private final Window window = new Window();

    public Preloader(Stage stage){
        this.stage = stage;
    }

    @Override
    public void run(){
        Log.initLogger();
        try {
            /* heavy tasks */
            DB.connect();
            window.initMenuPanes();
        } catch (IOException e) {
            Log.info("connection error");
        }
        Platform.runLater(() -> {
            try {
                window.close(this.stage);

                Stage stage = new Stage();
                stage.setMinWidth(1450);
                stage.setMinHeight(730);

                window.open(Page.MENU.getValue(), stage);
            } catch (IOException e) {
                Log.info("connection error");
            }
        });
    }
}
