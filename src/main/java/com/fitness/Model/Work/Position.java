package com.fitness.Model.Work;

public class Position {
    private String name;
    private Service service;

    public Position(String name, Service service) {
        this.name = name;
        this.service = service;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
