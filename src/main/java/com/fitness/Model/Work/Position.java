package com.fitness.Model.Work;

import com.fitness.Controller.Constant.Default;

public class Position {
    private long id = Default.ID.getValue();
    private String name;

    public Position(){}

    public Position(long id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public String toString() {
        return this.name;
    }
}
