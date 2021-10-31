package com.fitness.Element;

import com.fitness.Model.Work.Subscription;
import com.fitness.Model.Work.EmploymentQuantity;
import com.fitness.Service.Work.EmploymentService;
import com.fitness.Service.Work.SubscriptionService;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

import java.util.List;

public class SubscriptionButton {
    private static SubscriptionButton selected = null;
    private Button button;
    private Subscription subscription;

    private EmploymentService employmentService = new EmploymentService();
    private SubscriptionService subscriptionService = new SubscriptionService();

    public SubscriptionButton(Subscription subscription){
        this.subscription = subscription;
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);

        Label header = new Label(subscription.getName());
        header.getStyleClass().add("subscription_header");
        vBox.getChildren().add(header);

        vBox.getChildren().add(new Separator());

        List<EmploymentQuantity> subscriptionEmployments = subscriptionService.getEmploymentBySubscriptionId(subscription.getId());
        int servicesCount = subscriptionEmployments.size();
        int currentEmploymentNumber = 0;

        for(EmploymentQuantity subscriptionEmployment: subscriptionEmployments){
            Label label;
            if(currentEmploymentNumber == 2 && servicesCount > 3) {
                label = new Label("...");
                label.getStyleClass().add("subscription_label");
                vBox.getChildren().add(label);
                break;
            } else {
                label = new Label(subscriptionEmployment.getEmployment() + " " + subscriptionEmployment.getQuantity());
                label.getStyleClass().add("subscription_label");
                vBox.getChildren().add(label);
            }

            currentEmploymentNumber++;
        }
        vBox.getChildren().add(new Separator());

        Label footer = new Label(subscription.getPrice() + " դրամ");
        footer.getStyleClass().add("subscription_footer");
        vBox.getChildren().add(footer);

        this.button = new Button("", vBox);
        button.getStyleClass().add("subscription_box");
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

    public static void editSelected(Subscription subscription) {
        if(subscription == null) return;
        selected.setSubscription(subscription);
        selected.getButton().setText(subscription.getName());
    }

}
