package com.fitness.DAO.Person;

import com.fitness.DAO.DAO;
import com.fitness.DataSource.DB;
import com.fitness.Model.Person.Customer;
import com.fitness.Model.Person.Person;
import com.fitness.Model.Work.Subscription;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO implements DAO<Customer>{
    private Customer make(ResultSet result) throws SQLException {
        return new Customer(
                result.getLong("id"),
                new Person.Name(
                        result.getString("name"),
                        result.getString("surname")
                ),
                result.getString("card"),
                result.getString("phone"),
                result.getString("phone2"),
                result.getString("address"),
                null, /* subscription */
                result.getBoolean("archived")
        );
    }
    public Customer getByCard(String card) throws SQLException {
        Customer customer = null;
        PreparedStatement preparedStatement = DB.getConnection().prepareStatement(
                "SELECT * FROM `customer` WHERE `card` LIKE ? HAVING count(*) = 1"
        );
        preparedStatement.setString(1, "%" + card + "%");
        ResultSet result = preparedStatement.executeQuery();
        while(result.next()){
            customer = this.make(result);
        }
        return customer;
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
        List<Customer> customers = new ArrayList<>();
        PreparedStatement preparedStatement = DB.getConnection().prepareStatement(
                "SELECT * FROM `customer`" + (actual ? " WHERE `archived` = 0" : "")
        );
        ResultSet result = preparedStatement.executeQuery();
        while(result.next()){
            customers.add(this.make(result));
        }
        return customers;
    }

    @Override
    public List<Customer> getAll() throws SQLException {
        return this.get(false);
    }

    @Override
    public List<Customer> getActual() throws SQLException {
        return this.get(true);
    }
}
