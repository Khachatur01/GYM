package com.fitness.DAO.Person;

import com.fitness.DAO.DAO;
import com.fitness.Model.Person.Customer;
import com.fitness.Model.Person.Person;

import java.sql.SQLException;
import java.util.List;

public class CustomerDAO implements DAO<Customer> {
    public Customer getByCard(String card){
        //@TODO get from database
        if(card.equals("123"))
            return new Customer(-1, new Person.Name("name", "surname"), "123", "098", "099", "address", null, false);
        else
            return null;
    }

    @Override
    public void add(Customer customer) throws SQLException {

    }

    @Override
    public void edit(Customer customer) throws SQLException {

    }

    @Override
    public void remove(Customer customer, boolean removeHistory) throws SQLException {

    }

    @Override
    public List<Customer> get(boolean actual) throws SQLException {
        return null;
    }

    @Override
    public List<Customer> getAll() throws SQLException {
        return null;
    }

    @Override
    public List<Customer> getActual() throws SQLException {
        return null;
    }
}
