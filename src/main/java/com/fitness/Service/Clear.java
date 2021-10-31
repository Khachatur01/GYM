package com.fitness.Service;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;


public class Clear {
    public static void textField(TextField...textFields){
        for(TextField textField: textFields) {
            textField.setText("");
            textField.getStyleClass()
                    .remove("textField_error");
        }
    }
    public static void comboBox(ComboBox...comboBoxes){
        for(ComboBox comboBox: comboBoxes) {
            comboBox.getSelectionModel().select(null);
            comboBox.getStyleClass().remove("combo-box_error");
        }
    }
}