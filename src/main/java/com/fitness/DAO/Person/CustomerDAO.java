package com.fitness.DAO.Person;

import com.fitness.Model.Person.Customer;
import com.fitness.Model.Person.Person;

public class CustomerDAO {
    public Customer getByCard(String card){
        //@TODO get from database
        if(card.equals("123"))
            return new Customer(-1, new Person.Name("name", "surname"), "123", "098", "099", "address", null, false);
        else
            return null;
    }

}
