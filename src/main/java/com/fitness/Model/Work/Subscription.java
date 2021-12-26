package com.fitness.Model.Work;

import com.fitness.Controller.Constant.Default;

import java.util.ArrayList;
import java.util.List;

public class Subscription {
    private long id = Default.ID.getValue();
    private String name;
    private List<EmploymentQuantity> employmentsQuantities = new ArrayList<>();
    private boolean archived;

    public Subscription(){}

    public Subscription(long id, String name, List<EmploymentQuantity> employmentsQuantities, boolean archived) {
        this.id = id;
        this.name = name;
        this.employmentsQuantities = employmentsQuantities;
        this.archived = archived;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EmploymentQuantity> getEmploymentsQuantities() {
        return this.employmentsQuantities;
    }

    public void setEmploymentsQuantities(List<EmploymentQuantity> employmentsQuantities) {
        this.employmentsQuantities = employmentsQuantities;
    }

    public int getPrice() {
        int price = 0;
        for(EmploymentQuantity employmentQuantity: this.employmentsQuantities)
            price += employmentQuantity.getPrice();

        return price;
    }

    public int getEmploymentPrice(Employment employment) {
        for(EmploymentQuantity employmentQuantity: this.employmentsQuantities)
            if(employmentQuantity.getEmployment().getId() == employment.getId())
                return employmentQuantity.getPrice() / employmentQuantity.getQuantity();

        return 0;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
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
