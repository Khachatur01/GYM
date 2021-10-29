package com.fitness.Model.Work;

import java.util.Map;

public class Subscription {
    private long id;
    private String name;
    private int price;
    private Map<Service, Integer> services;

    public Subscription(long id, String name, int price, Map<Service, Integer> services) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.services = services;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Map<Service, Integer> getServices() {
        return services;
    }

    public void setServices(Map<Service, Integer> services) {
        this.services = services;
    }

    @Override
    public String toString(){
        return this.name;
    }
}
