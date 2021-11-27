package com.fitness.Model.Report;

import com.fitness.Model.Person.Employee;
import com.fitness.Model.Work.Employment;

public class Report {
    private Employment employment;
    private Employee employee;
    private int quantity;
    private int price;

    public Report(){}
    public Report(Employment employment, Employee employee, int quantity, int price) {
        this.employment = employment;
        this.employee = employee;
        this.quantity = quantity;
        this.price = price;
    }

    public Employment getEmployment() {
        return employment;
    }

    public void setEmployment(Employment employment) {
        this.employment = employment;
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
