package com.fitness.Model.Report;

import com.fitness.Model.Work.Employment;

import java.util.ArrayList;
import java.util.List;

public class Report {
    private Employment employment;
    private List<Salary> salaries = new ArrayList<>();

    public Report(){}
    public Report(Employment employment, List<Salary> salaries) {
        this.employment = employment;
        this.salaries = salaries;
    }

    public Employment getEmployment() {
        return employment;
    }

    public void setEmployment(Employment employment) {
        this.employment = employment;
    }

    public List<Salary> getSalaries() {
        return salaries;
    }

    public void setSalaries(List<Salary> salaries) {
        this.salaries = salaries;
    }

    public void addSalary(Salary salary) {
        if(salaries == null) salaries = new ArrayList<>();
        this.salaries.add(salary);
    }

    public int getTotalQuantity() {
        int quantity = 0;
        for(Salary salary: salaries)
            quantity += salary.getQuantity();
        return quantity;
    }

    public int getTotalPrice() {
        int price = 0;
        for(Salary salary: salaries)
            price += salary.getPrice();
        return price;
    }
}
