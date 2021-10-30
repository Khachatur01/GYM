package com.fitness.Element;

import com.fitness.Model.Work.Service;
import com.fitness.Model.Work.Subscription;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

import java.util.Map;

public class SubscriptionButton {
    private static SubscriptionButton selected = null;
    private Button button;
    private Subscription subscription;

    public SubscriptionButton(Subscription subscription){
        this.subscription = subscription;
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);

        Label header = new Label(subscription.getName());
        header.getStyleClass().add("subscription_header");
        vBox.getChildren().add(header);

        vBox.getChildren().add(new Separator());

        Map<Service, Integer> services = subscription.getServices();
        int servicesCount = services.size();
        int currentServiceNumber = 0;

        for(Map.Entry<Service, Integer> service: services.entrySet()){
            Label label;
            if(currentServiceNumber == 2 && servicesCount > 3) {
                label = new Label("...");
                label.getStyleClass().add("subscription_label");
                vBox.getChildren().add(label);
                break;
            } else {
                label = new Label(service.getValue() + " " + service.getKey().getName());
                label.getStyleClass().add("subscription_label");
                vBox.getChildren().add(label);
            }

            currentServiceNumber++;
        }
        vBox.getChildren().add(new Separator());

        Label footer = new Label(subscription.getPrice() + " դրամ");
        footer.getStyleClass().add("subscription_footer");
        vBox.getChildren().add(footer);

        this.button = new Button("", vBox);
        button.getStyleClass().add("subscription_box");
    }

    public static void editSelected(Subscription subscription) {
        if(subscription == null) return;
        selected.setSubscription(subscription);
        selected.getButton().setText(subscription.getName());
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public void setOnAction() {
        this.button.setOnAction(event -> {
            if(selected != null)
                selected.getButton().getStyleClass().remove("selected");

            selected = this;
            selected.getButton().getStyleClass().add("selected");
        });
    }

    public static SubscriptionButton getSelected() {
        return selected;
    }

    public static void removeSelected() {
        SubscriptionButton.selected = null;
    }
}
