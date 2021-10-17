package com.fitness.Model.Person;

import com.fitness.Model.Work.Subscription;

import java.util.Date;

public class Client extends Person{
    private String cardNumber;
    private Subscription subscription;

    public Client(){}

    public Client(long id, Name name, String cardNumber, String phone1, String phone2, String address, Subscription subscription, Date registrationDate) {
        super(id, name, phone1, phone2, address, registrationDate);
        this.cardNumber = cardNumber;
        this.subscription = subscription;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }
}
