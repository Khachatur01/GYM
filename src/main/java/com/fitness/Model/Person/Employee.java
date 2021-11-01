package com.fitness.Model.Person;

import com.fitness.Model.Work.Position;
import com.fitness.Model.Work.Employment;

public class Employee extends Person{
    private Position position;
    private boolean archived;

    public Employee() {}

    public Employee(long id, Name name, String phone, String phone2, String address, Position position, boolean archived) {
        super(id, name, phone, phone2, address);
        this.position = position;
        this.archived = archived;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }
}
