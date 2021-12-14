package com.fitness.DAO.Person;

import com.fitness.DAO.DAO;
import com.fitness.DataSource.DB;
import com.fitness.Model.Person.Customer;
import com.fitness.Model.Work.Employment;
import com.fitness.Model.Work.EmploymentQuantity;
import com.fitness.Model.Work.Subscription;
import com.fitness.Service.Create;
import com.fitness.Service.Work.SubscriptionService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomerDAO implements DAO<Customer>{
    private final SubscriptionService subscriptionService = new SubscriptionService();

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
        ResultSet generatedId = preparedStatement.getGeneratedKeys();
        if (generatedId.next()) {
            customer.setId(generatedId.getLong(1));
        }
    }
    public void addGuest(Customer customer) throws SQLException {
        if(customer == null) return;
        PreparedStatement preparedStatement = DB.getConnection().prepareStatement(
                "INSERT INTO `customer`(`name`, `surname`, `phone`, `phone2`, `address`) VALUES(?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
        );
        preparedStatement.setString(1, customer.getName().getFirstName());
        preparedStatement.setString(2, customer.getName().getLastName());
        preparedStatement.setString(3, customer.getPhone());
        preparedStatement.setString(4, customer.getPhone2());
        preparedStatement.setString(5, customer.getAddress());
        preparedStatement.executeUpdate();

        /* set generated id to new created customer */
        ResultSet generatedId = preparedStatement.getGeneratedKeys();
        if (generatedId.next()) {
            customer.setId(generatedId.getLong(1));
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
        if(result.next()){
            Subscription subscription = Create.subscription(result);
            Customer customer = Create.customer(result);
            customer.setSubscription(subscription);

            customers.add(customer);
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

    /* get non bonus visits count by employment */
    private int getEmploymentQuantity(Customer customer, Employment employment) throws SQLException {
        PreparedStatement preparedStatement = DB.getConnection().prepareStatement(
                "SELECT count(`archive`.`id`) AS 'quantity' FROM `archive` WHERE " +
                        "`archive`.`customer_id` = ? AND " +
                        "`archive`.`employment_id` = ? AND " +
                        "`archive`.`bonus` = 0"
        );
        preparedStatement.setLong(1, customer.getId());
        preparedStatement.setLong(2, employment.getId());

        ResultSet result = preparedStatement.executeQuery();
        if(result.next()){
            return result.getInt("quantity");
        }
        return 0;
    }

    public List<EmploymentQuantity> getAvailableEmploymentQuantities(Customer customer) throws SQLException{
        List<EmploymentQuantity> employmentQuantities = subscriptionService.getById(customer.getSubscription().getId()).getEmploymentsQuantities();

        for(EmploymentQuantity employmentQuantity: employmentQuantities){
            int maxQuantity = employmentQuantity.getQuantity();
            int employmentVisitsQuantity = this.getEmploymentQuantity(customer, employmentQuantity.getEmployment());
            int availableEmployment = maxQuantity - employmentVisitsQuantity;

            employmentQuantity.setQuantity(availableEmployment);
        }

        return employmentQuantities;
    }

    public Customer getByCard(String card) throws SQLException {
        Customer customer = null;
        PreparedStatement preparedStatement = DB.getConnection().prepareStatement(
                "SELECT * FROM `customer`, `subscription` WHERE " +
                        "`customer`.`subscription_id` = `subscription`.`id` AND " +
                        "`customer`.`card` = ?"
        );
        preparedStatement.setString(1, card);
        ResultSet result = preparedStatement.executeQuery();
        if(result.next()){
            Subscription subscription = Create.subscription(result);
            customer = Create.customer(result);
            customer.setSubscription(subscription);
        }
        return customer;
    }

    public Customer getByPhone(String phone) throws SQLException {
        Customer customer = null;
        PreparedStatement preparedStatement = DB.getConnection().prepareStatement(
                "SELECT * FROM `customer`, `subscription` WHERE " +
                        "`customer`.`subscription_id` IS NULL AND " +
                        "`customer`.`phone` = ? OR `customer`.`phone2` = ?"
        );
        preparedStatement.setString(1, phone);
        preparedStatement.setString(2, phone);

        ResultSet result = preparedStatement.executeQuery();
        if(result.next()){
            Subscription subscription = Create.subscription(result);
            customer = Create.customer(result);
            customer.setSubscription(subscription);
        }
        return customer;
    }

    public Date getLastVisit(Customer customer) throws SQLException {
        Date date = null;
        PreparedStatement preparedStatement = DB.getConnection().prepareStatement(
                "SELECT `date` FROM `archive`, `customer` WHERE " +
                        "`customer`.`id` = `archive`.`customer_id` AND " +
                        "`customer`.`id` = ? " +
                        "ORDER BY `archive`.`date` DESC LIMIT 1"
        );
        preparedStatement.setLong(1, customer.getId());
        ResultSet result = preparedStatement.executeQuery();
        if(result.next()){
            date = result.getDate("archive.date");
        }
        return date;
    }
}
