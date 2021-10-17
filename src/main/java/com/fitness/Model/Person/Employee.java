package com.fitness.Model.Person;

import com.fitness.Model.Work.Position;

import java.util.Date;

public class Employee extends Person{
    private Position position;

    public Employee() {}

    public Employee(long id, Name name, String phone1, String phone2, String address, Date registrationDate, Position position) {
        super(id, name, phone1, phone2, address, registrationDate);
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
