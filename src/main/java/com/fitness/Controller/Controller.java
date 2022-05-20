package com.fitness.Controller;

import com.fitness.Window;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public interface Controller {
    default void initTextFieldFocusingByKey(TextField[] textFields) {
        for (int i = 0; i < textFields.length; i++) {
            final int j = i;
            textFields[i].setOnKeyPressed(event -> {
                int prev = (j == 0) ? textFields.length - 1 : j - 1;
                int next = (j == textFields.length - 1) ? 0 : j + 1;
                if (event.getCode() == KeyCode.TAB && event.isShiftDown()) {
                    textFields[prev].requestFocus();
                } else if (event.getCode() == KeyCode.TAB)
                    textFields[next].requestFocus();
            });
        }
    }
    void start();
    void stop();

    default void makeActive(){
        Window.setActiveController(this);
    }
}
