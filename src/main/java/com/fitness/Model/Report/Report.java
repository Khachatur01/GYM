package com.fitness.Model.Report;

import com.fitness.Model.Person.Employee;

public class Report {
    private Employee employee;
    private int quantity;
    private int price;

    public Report(){}
    public Report(Employee employee, int quantity, int price) {
        this.employee = employee;
        this.quantity = quantity;
        this.price = price;
    }

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
