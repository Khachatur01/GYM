package com.fitness.Model.Work;

import com.fitness.Controller.Constant.Default;

import java.util.List;

public class Subscription {
    private long id = Default.ID.getValue();
    private String name;
    private int price;
    private List<EmploymentQuantity> employmentsQuantities;

    public Subscription(){}

    public Subscription(long id, String name, int price, List<EmploymentQuantity> employmentsQuantities) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.employmentsQuantities = employmentsQuantities;
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

    public List<EmploymentQuantity> getEmploymentsQuantities() {
        return employmentsQuantities;
    }

    public void setEmploymentsQuantities(List<EmploymentQuantity> employmentsQuantities) {
        this.employmentsQuantities = employmentsQuantities;
    }

    @Override
    public String toString(){
        return this.name;
    }

    @Override
    public boolean equals(Object subscription){
        return subscription != null && this.id == ((Subscription)subscription).id;
    }
}
