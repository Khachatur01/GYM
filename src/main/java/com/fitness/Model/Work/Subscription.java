package com.fitness.Model.Work;

import com.fitness.Controller.Constant.Default;

import java.util.List;

public class Subscription {
    private long id = Default.ID.getValue();
    private String name;
    private int price;
    private List<EmploymentQuantity> employmentsQuantity;

    public Subscription(){}

    public Subscription(long id, String name, int price, List<EmploymentQuantity> employmentsQuantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.employmentsQuantity = employmentsQuantity;
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

    public List<EmploymentQuantity> getEmploymentsQuantity() {
        return employmentsQuantity;
    }

    public void setEmploymentsQuantity(List<EmploymentQuantity> employmentsQuantity) {
        this.employmentsQuantity = employmentsQuantity;
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
