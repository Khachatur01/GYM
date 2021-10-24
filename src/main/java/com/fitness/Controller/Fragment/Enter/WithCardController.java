package com.fitness.Controller.Fragment;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.SortedSet;
import java.util.TreeSet;

public class WithCardController extends GridPane {
    @FXML
    public Button confirmButton;
    @FXML
    public TextField cardNumberTextField;


    public WithCardController() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fitness/fragment/enter/with-card.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
    }


}
