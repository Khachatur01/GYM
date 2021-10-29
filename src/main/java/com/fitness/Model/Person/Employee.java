package com.fitness.Model.Person;

import com.fitness.Model.Work.Offer;
import com.fitness.Model.Work.Position;

public class Employee extends Person{
    private Offer offer;
    private Position position;

    public Employee() {}

    public Employee(long id, Name name, String phone, String phone2, String address, Offer offer, Position position) {
        super(id, name, phone, phone2, address);
        this.offer = offer;
        this.position = position;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

}
