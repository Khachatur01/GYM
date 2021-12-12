package com.fitness.Model.Person;

import com.fitness.Model.Work.Subscription;

public class Customer extends Person {
    private String card;
    private Subscription subscription;
    private boolean archived;

    public Customer(){}

    public Customer(long id, Name name, String card, String phone, String phone2, String address, Subscription subscription, boolean archived) {
        super(id, name, phone, phone2, address);
        this.card = card;
        this.subscription = subscription;
        this.archived = archived;
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

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }
}
