package com.fitness.Service.Work;

import com.fitness.Model.Work.Employment;
import com.fitness.Model.Work.Position;
import com.fitness.Model.Work.Subscription;
import com.fitness.Model.Work.EmploymentQuantity;
import com.fitness.Service.Verify;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class SubscriptionService {
    private EmploymentService employmentService = new EmploymentService();

    private Subscription makeSubscription(Subscription subscription, TextField nameTextField, TextField priceTextField, ObservableList<EmploymentQuantity> employmentsQuantity){
        String name = nameTextField.getText();
        String price = priceTextField.getText();

        if(     Verify.name(name, nameTextField) &&
                Verify.price(price, priceTextField) &&
                !employmentsQuantity.isEmpty()
        ){
            subscription.setName(name);
            subscription.setPrice(Integer.parseInt(price));
            subscription.setEmploymentsQuantity(employmentsQuantity);
        } else {
            subscription = null;
        }
        return subscription;
    }

    public Subscription add(Subscription subscription, TextField nameTextField, TextField priceTextField, ObservableList<EmploymentQuantity> employmentsQuantity){
        Subscription newSubscription = this.makeSubscription(subscription, nameTextField, priceTextField, employmentsQuantity);
        //@TODO subscription and subscriptionEmployment to database
        return newSubscription;
    }
    public Subscription edit(Subscription subscription, TextField nameTextField, TextField priceTextField, ObservableList<EmploymentQuantity> employmentsQuantity){
        this.removeEmploymentQuantity(subscription);
        Subscription newSubscription = this.add(subscription, nameTextField, priceTextField, employmentsQuantity);
        return newSubscription;
    }
    public void remove(Subscription subscription){
        if(subscription == null) return;

        ButtonType yes = new ButtonType("Ջնջել", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("Հետ", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType removeHistory = new ButtonType("Ջնջել պատմությունը", ButtonBar.ButtonData.OK_DONE);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Հաստատում");
        alert.setHeaderText("Հաստատեք, որ ցանկանում եք ջնջել աբոնեմենտը");
        alert.setContentText("Ցանկանու՞մ եք ջնջել աբոնեմենտը");
        alert.getButtonTypes().setAll(yes, removeHistory, no);

        Optional<ButtonType> result =  alert.showAndWait();

        if(result.isPresent()){
            if(result.get() == yes)
                remove(subscription, true);
            else if (result.get() == removeHistory)
                remove(subscription, false);
        }
    }
    private void remove(Subscription subscription, boolean removeHistory){
        //TODO
        System.out.println("removed");
    }


    private EmploymentQuantity makeEmploymentQuantity(ComboBox<Employment> employmentComboBox, TextField quantityTextField){
        EmploymentQuantity subscriptionEmployment = null;
        Employment employment = employmentComboBox.getSelectionModel().getSelectedItem();
        String quantity = quantityTextField.getText();

        if(     Verify.employment(employment, employmentComboBox) &&
                Verify.quantity(quantity, quantityTextField)
        ){
            subscriptionEmployment = new EmploymentQuantity();
            subscriptionEmployment.setEmployment(employment);
            subscriptionEmployment.setQuantity(Integer.parseInt(quantity));
        }

        return subscriptionEmployment;
    }

    public EmploymentQuantity addSubscriptionEmployment(ComboBox<Employment> employmentComboBox, TextField quantityTextField){
        EmploymentQuantity employmentQuantity = makeEmploymentQuantity(employmentComboBox, quantityTextField);
        //don't add to database
        return employmentQuantity;
    }
    public void removeEmploymentQuantity(Subscription subscription) {
        subscription.setEmploymentsQuantity(new ArrayList<>());
        // @TODO delete subscription's all employmentQuantities
    }


    public List<EmploymentQuantity> getEmploymentBySubscriptionId(long id){
        //@TODO
        List<EmploymentQuantity> subscriptionEmployments= new ArrayList<>(Arrays.asList(
                new EmploymentQuantity(new Employment(0, "emp1", 1000, new Position(0, "pos")), 5),
                new EmploymentQuantity(new Employment(1, "emp2", 1200, new Position(1, "pos1")), 3)
        ));
        if(id == 2){
            subscriptionEmployments.add(new EmploymentQuantity(new Employment(2, "emp2", 1200, new Position(2, "pos2")), 3));
            subscriptionEmployments.add(new EmploymentQuantity(new Employment(3, "emp3", 1500, new Position(3, "pos3")), 4));
        }
        return subscriptionEmployments;
    }

    public List<Subscription> getSubscriptions(){
        List<Subscription> subscriptions = new ArrayList<>();
        subscriptions.add(new Subscription(1, "sub1", 5000, Arrays.asList(
                new EmploymentQuantity(new Employment(2, "emp2", 1200, new Position(2, "pos2")), 3),
                new EmploymentQuantity(new Employment(3, "emp3", 1000, new Position(3, "pos3")), 3)
        )));
        subscriptions.add(new Subscription(2, "sub2", 6000, Arrays.asList(
                new EmploymentQuantity(new Employment(2, "emp2", 1200, new Position(2, "pos2")), 5),
                new EmploymentQuantity(new Employment(3, "emp3", 1000, new Position(3, "pos3")), 4)
        )));

        return subscriptions;
    }

    //@TODO

}
