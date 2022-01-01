package com.fitness.Model.Archive;

import com.fitness.Model.Person.Customer;
import com.fitness.Model.Person.Employee;
import com.fitness.Model.Work.DateTime;
import com.fitness.Model.Work.Employment;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Timestamp;

public class Archive {
    private long id;
    private DateTime date;
    private Customer customer;
    private Employee employee;
    private Employment employment;
    private boolean bonus;

    public Archive(){}

    public Archive(long id, Timestamp date, Customer customer, Employee employee, Employment employment, boolean bonus) {
        this.id = id;
        this.date = new DateTime(date.getTime());
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

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
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

    public String getPrice() {
        if(this.isBonus())
            return "";
        /* registration */
        if(this.employment == null && this.employee == null && this.customer.getSubscription() != null)
            return this.customer.getSubscription().getPrice() + "";
        /* enter without card */
        if(this.customer.getSubscription() == null && this.employment != null)
            return this.employment.getPrice() + "";
        return "";
    }

}
