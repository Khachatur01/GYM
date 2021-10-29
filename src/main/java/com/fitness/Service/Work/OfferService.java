package com.fitness.Service.Work;

import com.fitness.Model.Work.Offer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OfferService {
    public Map<Offer, Integer> getOfferBySubscriptionId(long id){
        Map<Offer, Integer> offers = new HashMap<>();
        offers.put(new Offer(1, "offer1", 1000), 5);
        offers.put(new Offer(2, "offer2", 1000), 7);
        offers.put(new Offer(3, "offer3", 1000), 3);
        offers.put(new Offer(4, "offer4", 1000), 2);
        return offers;
    }

    public List<Offer> getOffers() {
        return new ArrayList<>();
    }
    //@TODO
}
