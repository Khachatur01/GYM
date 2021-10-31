package com.fitness.Model.Work;

import com.fitness.Controller.Constant.Default;

public class Employment {
    private long id = Default.ID.getValue();
    private String name;
    private int price;
    private Position position;

    public Employment(){}

    public Employment(long id, String name, int price, Position position) {
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

    @Override
    public String toString(){
        return this.name;
    }
}
