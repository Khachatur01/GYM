package com.fitness.Element;

import com.fitness.Model.Work.Position;
import com.fitness.Model.Work.Service;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class ServiceButton {
    private static ServiceButton selected = null;
    private Button button;
    private Service service;

    public ServiceButton(Service service){
        this.service = service;
        this.button = new Button(service.getName());
        button.getStyleClass().add("service_box");
    }

    public static void editSelected(Service service) {
        if(service == null) return;
        selected.setService(service);
        selected.getButton().setText(service.getName());
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public void setOnAction(TextField serviceNameTextField, ComboBox<Position> positionComboBox, TextField priceTextField) {
        this.button.setOnAction(event -> {
            if(selected != null)
                selected.getButton().getStyleClass().remove("selected");

            selected = this;
            selected.getButton().getStyleClass().add("selected");
            serviceNameTextField.setText(selected.getService().getName());
            positionComboBox.getSelectionModel().select(selected.getService().getPosition());
            priceTextField.setText(selected.getService().getPrice() + "");
        });
    }

    public static ServiceButton getSelected() {
        return selected;
    }

    public static void removeSelected() {
        ServiceButton.selected = null;
    }
}
