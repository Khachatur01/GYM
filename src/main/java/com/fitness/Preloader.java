package com.fitness;

import com.fitness.Controller.Constant.Page;
import com.fitness.DataSource.DB;
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
        try {
            /* heavy tasks */
            DB.connect();
            window.initMenuPanes();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        Platform.runLater(() -> {
            try {
                window.close(this.stage);

                Stage stage = new Stage();
                stage.setMinWidth(1450);
                stage.setMinHeight(730);

                window.open(Page.MENU.getValue(), stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
