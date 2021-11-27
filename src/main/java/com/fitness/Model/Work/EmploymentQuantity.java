package com.fitness.Model.Work;

public class EmploymentQuantity {
    private Employment employment;
    private int quantity;
    private int price;

    public EmploymentQuantity(){}

    public EmploymentQuantity(Employment employment, int quantity, int price) {
        this.employment = employment;
        this.quantity = quantity;
        this.price = price;
    }

    public Employment getEmployment() {
        return employment;
    }

    public void setEmployment(Employment employment) {
        this.employment = employment;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object subscriptionEmployment){
        return subscriptionEmployment != null && this.employment.getId() == ((EmploymentQuantity)subscriptionEmployment).getEmployment().getId();
    }
}
