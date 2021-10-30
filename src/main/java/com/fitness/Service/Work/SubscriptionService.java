package com.fitness.Service.Work;

import com.fitness.Model.Work.Service;
import com.fitness.Model.Work.Subscription;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SubscriptionService {
    ServiceService serviceService = new ServiceService();

    private Service makeSubscription(){
        return null;
    }

    public Service add(){
        return null;
    }
    public Service edit(){
        return null;
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

    public List<Subscription> getSubscriptions(){
        List<Subscription> subscriptions = new ArrayList<>();
        subscriptions.add(new Subscription(1, "sub1", 5000, serviceService.getServiceBySubscriptionId(1)));
        subscriptions.add(new Subscription(2, "sub2", 6000, serviceService.getServiceBySubscriptionId(2)));

        return subscriptions;
    }

    //@TODO
}
