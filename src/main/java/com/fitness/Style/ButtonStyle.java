package com.fitness.Style;

import javafx.scene.control.Button;

public class ButtonStyle {
    public static void select(Button button){
        button.setStyle(
                "-fx-border-color: #367bf5;" +
                "-fx-border-width: 1px;" +
                "-fx-border-radius: 5px"
        );
    }
}
