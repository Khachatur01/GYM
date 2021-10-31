package com.fitness.Model.Work;

import com.fitness.Controller.Constant.Default;

public class Position {
    private long id = Default.ID.getValue();
    private String name;
    private Employment employment;

    public Position(){}

    public Position(long id, String name, Employment employment) {
        this.id = id;
        this.name = name;
        this.employment = employment;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Employment getEmployment() {
        return employment;
    }

    public void setEmployment(Employment employment) {
        this.employment = employment;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
