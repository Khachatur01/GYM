package com.fitness.Style;

import javafx.scene.control.Button;

public class ButtonStyle {
    public static void select(Button unselected){
        unselected.setStyle(
                "-fx-border-color: #367bf5;" +
                "-fx-border-width: 1px;" +
                "-fx-border-radius: 5px"
        );
    }

    public static void unselect(Button selected) {
        if(selected == null) return;
        selected.setStyle(
                "-fx-border-color: transparent;" +
                "-fx-border-width: 0px;" +
                "-fx-border-radius: 0px"
        );
    }
}
