package com.fitness.Model.Work;

import java.util.Map;

public class Subscription {
    private String name;
    private int price;
    private Map<Offer, Integer> offers;

    public Subscription(String name, int price, Map<Offer, Integer> offers) {
        this.name = name;
        this.price = price;
        this.offers = offers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Map<Offer, Integer> getOffers() {
        return offers;
    }

    public void setOffers(Map<Offer, Integer> offers) {
        this.offers = offers;
    }
}
