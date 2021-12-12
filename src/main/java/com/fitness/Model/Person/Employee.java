package com.fitness.Model.Person;

import com.fitness.Model.Work.Position;

import java.util.List;

public class Employee extends Person{
    private List<Position> positions;
    private boolean archived;

    public Employee() {}

    public Employee(long id, Name name, String phone, String phone2, String address, List<Position> positions, boolean archived) {
        super(id, name, phone, phone2, address);
        this.positions = positions;
        this.archived = archived;
    }

    public List<Position> getPositions() {
        return this.positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    @Override
    public String toString(){
        return this.getFullName();
    }
}
