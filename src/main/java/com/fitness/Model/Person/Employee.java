package com.fitness.Model.Person;

import com.fitness.Model.Work.Position;
import com.fitness.Model.Work.Employment;

public class Employee extends Person{
    private Employment employment;
    private Position position;
    private boolean archived;

    public Employee() {}

    public Employee(long id, Name name, String phone, String phone2, String address, Employment employment, Position position, boolean archived) {
        super(id, name, phone, phone2, address);
        this.employment = employment;
        this.position = position;
        this.archived = archived;
    }

    public Employment getEmployment() {
        return employment;
    }

    public void setEmployment(Employment employment) {
        this.employment = employment;
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
