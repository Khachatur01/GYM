package com.fitness.Model.Archive;

import com.fitness.Model.Person.Customer;
import com.fitness.Model.Person.Employee;

public class Archive {
    private long id;
    private Customer customer;
    private Employee employee;
    private boolean registration;
    private boolean bonus;

    public Archive(){}

    public Archive(long id, Customer customer, Employee employee, boolean registration, boolean bonus) {
        this.id = id;
        this.customer = customer;
        this.employee = employee;
        this.registration = registration;
        this.bonus = bonus;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public boolean isRegistration() {
        return registration;
    }

    public void setRegistration(boolean registration) {
        this.registration = registration;
    }

    public boolean isBonus() {
        return bonus;
    }

    public void setBonus(boolean bonus) {
        this.bonus = bonus;
    }
}
