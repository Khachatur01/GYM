package com.fitness;

import com.fitness.Constant.Page;
import com.fitness.DataSource.DB;
import com.fitness.DataSource.Log.Log;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.IOException;

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
            if(!DB.connect())
                Log.error("Can't connect to database");

            window.initMenuPanes();
        } catch (IOException e) {
            Log.error("Pages initialization error");
        }
        Platform.runLater(() -> {
            try {
                window.close(this.stage);

                Stage stage = new Stage();
                stage.setMinWidth(1450);
                stage.setMinHeight(800);

                window.open(Page.MENU.getValue(), stage);
            } catch (IOException e) {
                Log.error("Can't start application");
            }
        });
    }
}
