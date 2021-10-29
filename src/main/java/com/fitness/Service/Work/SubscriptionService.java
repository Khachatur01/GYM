package com.fitness.Service.Work;

import com.fitness.Model.Work.Subscription;

import java.util.ArrayList;
import java.util.List;

public class SubscriptionService {
    OfferService offerService = new OfferService();

    public List<Subscription> getSubscriptions(){
        List<Subscription> subscriptions = new ArrayList<>();
        subscriptions.add(new Subscription(1, "sub1", 5000, offerService.getOfferBySubscriptionId(1)));
        subscriptions.add(new Subscription(2, "sub2", 6000, offerService.getOfferBySubscriptionId(2)));

        return subscriptions;
    }
    //@TODO
}
