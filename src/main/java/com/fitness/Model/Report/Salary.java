package com.fitness.Model.Report;


import com.fitness.Model.Person.Employee;

public class Salary {
    private Employee employee;
    private int quantity;
    private int price;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void incrementQuantity() {
        this.quantity++;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void incrementPrice(int price) {
        this.price += price;
    }
}