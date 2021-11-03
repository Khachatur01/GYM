package com.fitness.DAO.Person;

import com.fitness.DAO.DAO;
import com.fitness.DataSource.DB;
import com.fitness.Model.Person.Customer;
import com.fitness.Model.Person.Person;
import com.fitness.Model.Work.Subscription;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO implements DAO<Customer>{
    private Customer make(ResultSet result) throws SQLException {
        return new Customer(
                result.getLong("customer.id"),
                new Person.Name(
                        result.getString("customer.name"),
                        result.getString("customer.surname")
                ),
                result.getString("customer.card"),
                result.getString("customer.phone"),
                result.getString("customer.phone2"),
                result.getString("customer.address"),
                new Subscription(
                        result.getLong("subscription.id"),
                        result.getString("subscription.name"),
                        result.getInt("subscription.price"),
                        null,
                        result.getBoolean("subscription.archived")
                ),
                result.getBoolean("customer.archived")
        );
    }
    public Customer getByCard(String card) throws SQLException {
        Customer customer = null;
        PreparedStatement preparedStatement = DB.getConnection().prepareStatement(
                "SELECT * FROM `customer`, `subscription` WHERE " +
                        "`customer`.`subscription_id` = `subscription`.`id` AND " +
                        "`card` = ?"
        );
        preparedStatement.setString(1, card);
        ResultSet result = preparedStatement.executeQuery();
        while(result.next()){
            customer = this.make(result);
        }
        return customer;
    }

    @Override
    public void add(Customer customer) throws SQLException {
        if(customer == null) return;
        PreparedStatement preparedStatement = DB.getConnection().prepareStatement(
                "INSERT INTO `customer`(`card`, `subscription_id`, `name`, `surname`, `phone`, `phone2`, `address`, `archived`) VALUES(?, ?, ?, ?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
        );
        preparedStatement.setString(1, customer.getCard());
        preparedStatement.setLong(2, customer.getSubscription().getId());
        preparedStatement.setString(3, customer.getName().getFirstName());
        preparedStatement.setString(4, customer.getName().getLastName());
        preparedStatement.setString(5, customer.getPhone());
        preparedStatement.setString(6, customer.getPhone2());
        preparedStatement.setString(7, customer.getAddress());
        preparedStatement.setBoolean(8, customer.isArchived());
        preparedStatement.executeUpdate();

        /* set generated id to new created customer */
        try (ResultSet generatedId = preparedStatement.getGeneratedKeys()) {
            if (generatedId.next()) {
                customer.setId(generatedId.getLong(1));
            }
        }
    }

    @Override
    public void edit(Customer customer) throws SQLException {
        if(customer == null) return;
        PreparedStatement preparedStatement = DB.getConnection().prepareStatement(
                "UPDATE `customer` SET `card` = ?, `subscription_id` = ?, `name` = ?, `surname` = ?, `phone` = ?, `phone2` = ?, `address` = ?, `archived` = ? WHERE `id` = ?",
                Statement.RETURN_GENERATED_KEYS
        );
        preparedStatement.setString(1, customer.getCard());
        preparedStatement.setLong(2, customer.getSubscription().getId());
        preparedStatement.setString(3, customer.getName().getFirstName());
        preparedStatement.setString(4, customer.getName().getLastName());
        preparedStatement.setString(5, customer.getPhone());
        preparedStatement.setString(6, customer.getPhone2());
        preparedStatement.setString(7, customer.getAddress());
        preparedStatement.setBoolean(8, customer.isArchived());
        preparedStatement.setLong(9, customer.getId());
        preparedStatement.executeUpdate();
    }

    @Override
    public void remove(Customer customer, boolean removeHistory) throws SQLException {
        PreparedStatement preparedStatement;
        if(removeHistory) {
            /* when customer deleted, customer visits will be deleted */
            preparedStatement = DB.getConnection().prepareStatement(
                    "DELETE FROM `customer` WHERE `id` = ?"
            );
        } else {
            preparedStatement = DB.getConnection().prepareStatement(
                    "UPDATE `customer` SET `archived` = 1 WHERE `id` = ?"
            );
        }
        preparedStatement.setLong(1, customer.getId());
        preparedStatement.executeUpdate();
    }

    @Override
    public List<Customer> get(boolean actual) throws SQLException {
        List<Customer> customers = new ArrayList<>();
        PreparedStatement preparedStatement = DB.getConnection().prepareStatement(
                "SELECT * FROM `customer`, `subscription` WHERE " +
                        "`customer`.`subscription_id` = `subscription`.`id`" +
                        (actual ? " AND `customer`.`archived` = 0" : "")
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
