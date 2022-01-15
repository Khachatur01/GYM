package com.fitness.Service;

import com.fitness.Element.MaskField;
import javafx.scene.control.*;


public class Clear {
    public static void label(Label...labels){
        for(Label label: labels) {
            label.setText("");
        }
    }
    public static void textField(TextField ...textFields){
        for(TextField textField: textFields) {
            textField.setText("");
            textField.getStyleClass()
                    .remove("textField_error");
        }
    }
    public static void maskField(MaskField...maskFields){
        for(MaskField maskField: maskFields) {
            maskField.setPlainText("");
            maskField.getStyleClass()
                    .remove("textField_error");
        }
    }
    public static void comboBox(ComboBox ...comboBoxes){
        for(ComboBox comboBox: comboBoxes) {
            comboBox.getItems().clear();
            comboBox.getStyleClass().remove("combo-box_error");
        }
    }

    public static void table(TableView ...tableViews) {
        for(TableView tableView: tableViews){
            tableView.getItems().clear();
        }
    }

    public static void checkBox(CheckBox ...checkBoxes) {
        for(CheckBox checkBox: checkBoxes){
            checkBox.setSelected(false);
        }
    }
}
