package com.fitness.Model.Configuration;

import com.fitness.Model.Person.Employee;

import java.util.List;

public class WorkingDay {
    private Employee employee;
    private List<WeekDay> workingDays;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public List<WeekDay> getWorkingDays() {
        return workingDays;
    }

    public void setWorkingDays(List<WeekDay> workingDays) {
        this.workingDays = workingDays;
    }
}
