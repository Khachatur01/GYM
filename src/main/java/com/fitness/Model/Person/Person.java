package com.fitness.Model.Person;

import com.fitness.Controller.Constant.Default;

public abstract class Person {
    public static class Name {
        private String firstName;
        private String lastName;

        public Name(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        @Override
        public String toString(){
            return this.firstName + " " + this.lastName;
        }
    }

    private long id = Default.ID.getValue();
    private Name name;
    private String phone;
    private String phone2;
    private String address;

    public Person(){}

    public Person(long id, Name name, String phone, String phone2, String address) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.phone2 = phone2;
        this.address = address;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone1) {
        this.phone = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFullName(){
        return this.name.toString();
    }

}
