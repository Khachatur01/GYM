package com.fitness.Model.Person;

import com.fitness.Model.Work.Position;
import com.fitness.Model.Work.Service;

public class Employee extends Person{
    private Service service;
    private Position position;

    public Employee() {}

    public Employee(long id, Name name, String phone, String phone2, String address, Service service, Position position) {
        super(id, name, phone, phone2, address);
        this.service = service;
        this.position = position;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

}
