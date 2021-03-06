package com.fitness.Model.Work;

import com.fitness.Constant.Default;

public class Employment {
    private long id = Default.ID.getValue();
    private String name;
    private int price;
    private boolean archived;

    public Employment(){}

    public Employment(long id, String name, int price, boolean archived) {
        this.id = id;
        this.name = name;
        this.price = price;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    @Override
    public String toString(){
        return this.name;
    }

    @Override
    public boolean equals(Object employment) {
        return employment != null && this.id == ((Employment)employment).id;
    }
}
