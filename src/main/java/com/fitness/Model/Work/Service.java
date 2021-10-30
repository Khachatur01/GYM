package com.fitness.Model.Work;

public class Service {
    private long id;
    private String name;
    private int price;
    private Position position;

    public Service(){}

    public Service(long id, String name, int price, Position position) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.position = position;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
