package com.fitness.Model.Work;

import com.fitness.Controller.Constant.Default;

public class Position {
    private long id = Default.ID.getValue();
    private String name;
    private Employment employment;
    private boolean archived;

    public Position(){}

    public Position(long id, String name, Employment employment, boolean archived) {
        this.id = id;
        this.name = name;
        this.employment = employment;
        this.archived = archived;
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

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
