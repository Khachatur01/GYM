package com.fitness.Element;

import com.fitness.Model.Work.Position;
import com.fitness.Model.Work.Employment;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class EmploymentButton {
    private static EmploymentButton selected = null;
    private Button button;
    private Employment employment;

    public EmploymentButton(Employment employment){
        this.employment = employment;
        this.button = new Button(employment.getName());
        button.getStyleClass().add("employment_box");
    }

    public static void editSelected(Employment employment) {
        if(employment == null) return;
        selected.setEmployment(employment);
        selected.getButton().setText(employment.getName());
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public Employment getEmployment() {
        return employment;
    }

    public void setEmployment(Employment employment) {
        this.employment = employment;
    }

    public void setOnAction(TextField employmentNameTextField, TextField priceTextField) {
        this.button.setOnAction(event -> {
            if(selected != null)
                selected.getButton().getStyleClass().remove("selected");

            selected = this;
            selected.getButton().getStyleClass().add("selected");
            employmentNameTextField.setText(selected.employment.getName());
            priceTextField.setText(selected.employment.getPrice() + "");
        });
    }

    public static EmploymentButton getSelected() {
        return selected;
    }

    public static void removeSelected() {
        EmploymentButton.selected = null;
    }
}
