package com.fitness.Model.Archive;

import com.fitness.Model.Person.Customer;
import com.fitness.Model.Person.Employee;
import com.fitness.Model.Work.Employment;

public class Archive {
    private long id;
    private Customer customer;
    private Employee employee;
    private Employment employment;
    private boolean bonus;

    public Archive(){}

    public Archive(long id, Customer customer, Employee employee, Employment employment, boolean bonus) {
        this.id = id;
        this.customer = customer;
        this.employee = employee;
        this.employment = employment;
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

    public Employment getEmployment() {
        return employment;
    }

    public void setEmployment(Employment employment) {
        this.employment = employment;
    }

    public boolean isBonus() {
        return bonus;
    }

    public void setBonus(boolean bonus) {
        this.bonus = bonus;
    }

}
