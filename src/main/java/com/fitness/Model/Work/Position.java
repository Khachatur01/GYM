package com.fitness.Model.Work;

import com.fitness.Constant.Default;

import java.util.List;

public class Position {
    private long id = Default.ID.getValue();
    private String name;
    private List<Employment> employments;
    private boolean archived;

    public Position(){}

    public Position(long id, String name, List<Employment> employments, boolean archived) {
        this.id = id;
        this.name = name;
        this.employments = employments;
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

    public List<Employment> getEmployments() {
        return employments;
    }

    public void setEmployments(List<Employment> employments) {
        this.employments = employments;
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
