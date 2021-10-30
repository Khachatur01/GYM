package com.fitness.Element;

import com.fitness.Model.Work.Position;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class PositionButton {
    private static PositionButton selected = null;
    private Button button;
    private Position position;

    public PositionButton(Position position){
        this.position = position;
        this.button = new Button(position.getName());
        button.getStyleClass().add("position_box");
    }

    public static void editSelected(Position position) {
        if(position == null) return;
        selected.setPosition(position);
        selected.getButton().setText(position.getName());
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setOnAction(TextField positionNameTextField) {
        this.button.setOnAction(event -> {
            if(selected != null)
                selected.getButton().getStyleClass().remove("selected");

            selected = this;
            selected.getButton().getStyleClass().add("selected");
            positionNameTextField.setText(selected.getPosition().getName());
        });
    }

    public static PositionButton getSelected() {
        return selected;
    }

    public static void removeSelected() {
        PositionButton.selected = null;
    }
}
