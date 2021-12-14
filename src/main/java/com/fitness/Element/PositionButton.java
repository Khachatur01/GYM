package com.fitness.Element;

import com.fitness.Model.Work.Employment;
import com.fitness.Model.Work.Position;
import javafx.collections.ObservableList;
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
        selected.getPosition().setName(position.getName());
        selected.getPosition().setEmployments(position.getEmployments());
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

    public void setOnAction(TextField positionNameTextField, ObservableList<Employment> employments) {
        this.button.setOnAction(event -> {
            select(positionNameTextField, employments);
        });
    }

    public static PositionButton getSelected() {
        return selected;
    }

    public static void removeSelected() {
        PositionButton.selected = null;
    }

    public void select(TextField positionNameTextField, ObservableList<Employment> employments) {
        if(selected != null)
            selected.getButton().getStyleClass().remove("selected");

        selected = this;
        this.button.getStyleClass().add("selected");
        positionNameTextField.setText(this.position.getName());
        employments.setAll(this.position.getEmployments());
    }
}
