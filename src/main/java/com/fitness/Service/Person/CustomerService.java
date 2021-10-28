package com.fitness.Service.Person;

import com.fitness.Model.Person.Customer;

public class CustomerService {

    public void add(Customer customer){
        System.out.println("added");
    }
    public void edit(Customer customer){
        System.out.println("edited");
    }
    public void remove(Customer customer, boolean removeHistory){
        System.out.println("removed");
    }
    //@TODO
}
