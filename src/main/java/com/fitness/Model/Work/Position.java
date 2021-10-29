package com.fitness.Model.Work;

public class Position {
    private String name;
    private Offer offer;

    public Position(String name, Offer offer) {
        this.name = name;
        this.offer = offer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
