package com.fitness.Model.Work;

public class EmploymentQuantity {
    private Employment employment;
    private int quantity;

    public EmploymentQuantity(){}

    public EmploymentQuantity(Employment employment, int quantity) {
        this.employment = employment;
        this.quantity = quantity;
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

    @Override
    public boolean equals(Object subscriptionEmployment){
        return subscriptionEmployment != null && this.employment.getId() == ((EmploymentQuantity)subscriptionEmployment).getEmployment().getId();
    }
}
