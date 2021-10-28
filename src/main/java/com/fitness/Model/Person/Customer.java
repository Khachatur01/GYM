package com.fitness.Model.Person;

import com.fitness.Model.Work.Subscription;

import java.util.Date;

public class Customer extends Person{
    private String card;
    private Subscription subscription;

    public Customer(){}

    public Customer(long id, Name name, String card, String phone, String phone2, String address, Subscription subscription) {
        super(id, name, phone, phone2, address);
        this.card = card;
        this.subscription = subscription;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }


}
