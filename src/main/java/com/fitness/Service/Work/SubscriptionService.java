package com.fitness.Service.Work;

import com.fitness.DAO.Work.SubscriptionDAO;
import com.fitness.Model.Work.Employment;
import com.fitness.Model.Work.EmploymentQuantity;
import com.fitness.Model.Work.Subscription;
import com.fitness.Service.Verify;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SubscriptionService {
    private SubscriptionDAO subscriptionDAO = new SubscriptionDAO();
    private EmploymentService employmentService = new EmploymentService();

    private Subscription makeSubscription(TextField nameTextField, TextField priceTextField, ObservableList<EmploymentQuantity> employmentsQuantity){
        Subscription subscription = null;
        String name = nameTextField.getText();
        String price = priceTextField.getText();

        if(     Verify.name(name, nameTextField) &&
                Verify.price(price, priceTextField) &&
                !employmentsQuantity.isEmpty()
        ){
            subscription = new Subscription();
            subscription.setName(name);
            subscription.setPrice(Integer.parseInt(price));
            subscription.setEmploymentsQuantities(employmentsQuantity);
        }
        return subscription;
    }

    public Subscription add(TextField nameTextField, TextField priceTextField, ObservableList<EmploymentQuantity> employmentsQuantity) throws SQLException {
        Subscription newSubscription = this.makeSubscription(nameTextField, priceTextField, employmentsQuantity);
        subscriptionDAO.add(newSubscription);
        return newSubscription;
    }
    public Subscription edit(Subscription subscription, TextField nameTextField, TextField priceTextField, ObservableList<EmploymentQuantity> employmentsQuantity) throws SQLException {
        Subscription newSubscription = this.makeSubscription(nameTextField, priceTextField, employmentsQuantity);
        newSubscription.setId(subscription.getId());

        subscriptionDAO.edit(newSubscription);
        return newSubscription;
    }

    public void remove(Subscription subscription){
        if(subscription == null) return;

        ButtonType delete = new ButtonType("Ջնջել", ButtonBar.ButtonData.OK_DONE);
        ButtonType back = new ButtonType("Հետ", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType removeHistory = new ButtonType("Ջնջել պատմությունը", ButtonBar.ButtonData.OK_DONE);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Հաստատում");
        alert.setHeaderText("Հաստատեք, որ ցանկանում եք ջնջել աբոնեմենտը");
        alert.setContentText("Ցանկանու՞մ եք ջնջել աբոնեմենտը");
        alert.getButtonTypes().setAll(delete, removeHistory, back);

        Optional<ButtonType> result =  alert.showAndWait();

        if(result.isPresent()){
            if(result.get() == delete)
                remove(subscription, false);
            else if (result.get() == removeHistory)
                remove(subscription, true);
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

    public EmploymentQuantity addEmploymentQuantity(ComboBox<Employment> employmentComboBox, TextField quantityTextField){
        EmploymentQuantity employmentQuantity = makeEmploymentQuantity(employmentComboBox, quantityTextField);
        // don't add to database
        return employmentQuantity;
    }


    public List<EmploymentQuantity> getEmploymentsQuantity(Subscription subscription){
        List<EmploymentQuantity> subscriptionEmployments = new ArrayList<>();
        return subscriptionEmployments;
    }

    public List<Subscription> getSubscriptions(){
        List<Subscription> subscriptions = new ArrayList<>();
        return subscriptions;
    }

    //@TODO

}
