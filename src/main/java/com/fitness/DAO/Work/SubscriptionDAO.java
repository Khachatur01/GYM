package com.fitness.DAO.Work;

import com.fitness.DAO.DAO;
import com.fitness.DataSource.DB;
import com.fitness.Model.Work.EmploymentQuantity;
import com.fitness.Model.Work.Subscription;
import com.fitness.Service.Create;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SubscriptionDAO implements DAO<Subscription> {
    private List<EmploymentQuantity> getEmploymentsQuantities(Subscription subscription) throws SQLException {
        List<EmploymentQuantity> employmentQuantities = new ArrayList<>();
        PreparedStatement preparedStatement = DB.getConnection().prepareStatement(
                "SELECT * from `employment`, `subscription_employment` WHERE " +
                        "`subscription_employment`.`employment_id` = `employment`.`id` AND " +
                        "`subscription_employment`.`subscription_id` = ?"
        );
        preparedStatement.setLong(1, subscription.getId());
        ResultSet result = preparedStatement.executeQuery();
        while(result.next()){
            employmentQuantities.add(
                    Create.employmentQuantity(result)
            );
        }
        return employmentQuantities;
    }
    private void addEmploymentsQuantities(Subscription subscription) throws SQLException {
        long subscriptionId = subscription.getId();
        List<EmploymentQuantity> employmentQuantities = subscription.getEmploymentsQuantities();
        PreparedStatement preparedStatement = DB.getConnection().prepareStatement(
                "INSERT INTO `subscription_employment`(`subscription_id`, `employment_id`, `quantity`, `price`) VALUES (?, ?, ?, ?)"
        );

        DB.getConnection().setAutoCommit(false);
        for(EmploymentQuantity employmentQuantity: employmentQuantities){
            preparedStatement.setLong(1, subscriptionId);
            preparedStatement.setLong(2, employmentQuantity.getEmployment().getId());
            preparedStatement.setInt(3, employmentQuantity.getQuantity());
            preparedStatement.setInt(4, employmentQuantity.getPrice());
            preparedStatement.addBatch();
        }
        preparedStatement.executeBatch();
        DB.getConnection().setAutoCommit(true);
    }

    private void removeEmploymentsQuantities(Subscription subscription) throws SQLException {
        PreparedStatement preparedStatement = DB.getConnection().prepareStatement(
                "DELETE FROM `subscription_employment` WHERE `subscription_id` = ?"
        );
        preparedStatement.setLong(1, subscription.getId());
        preparedStatement.executeUpdate();
    }

    private void editEmploymentsQuantities(Subscription subscription) throws SQLException {
        this.removeEmploymentsQuantities(subscription);
        this.addEmploymentsQuantities(subscription);
    }

    @Override
    public void add(Subscription subscription) throws SQLException {
        if(subscription == null) return;
        PreparedStatement preparedStatement = DB.getConnection().prepareStatement(
                "INSERT INTO `subscription`(`name`) VALUES (?)",
                Statement.RETURN_GENERATED_KEYS
        );
        preparedStatement.setString(1, subscription.getName());

        preparedStatement.executeUpdate();

        /* set generated id to new created subscription */
        try (ResultSet generatedId = preparedStatement.getGeneratedKeys()) {
            if (generatedId.next()) {
                subscription.setId(generatedId.getLong(1));
            }
        }
        addEmploymentsQuantities(subscription);
    }
    @Override
    public void edit(Subscription subscription) throws SQLException {
        this.editEmploymentsQuantities(subscription);
        PreparedStatement preparedStatement = DB.getConnection().prepareStatement(
                "UPDATE `subscription` SET `name` = ? WHERE `id` = ?"
        );
        preparedStatement.setString(1, subscription.getName());
        preparedStatement.setLong(2, subscription.getId());
        preparedStatement.executeUpdate();
    }
    @Override
    public void remove(Subscription subscription, boolean removeHistory) throws  SQLException {
        PreparedStatement preparedStatement;
        if(removeHistory) {
            /* when subscription deleted, subscription quantities, customer and customer visits will be deleted */
            preparedStatement = DB.getConnection().prepareStatement(
                    "DELETE FROM `subscription` WHERE `id` = ?"
            );
        } else {
            preparedStatement = DB.getConnection().prepareStatement(
                    "UPDATE `subscription` SET `archived` = 1 WHERE `id` = ?"
            );
        }
        preparedStatement.setLong(1, subscription.getId());
        preparedStatement.executeUpdate();
    }

    @Override
    public List<Subscription> get(boolean actual) throws SQLException {
        List<Subscription> subscriptions = new ArrayList<>();
        PreparedStatement preparedStatement = DB.getConnection().prepareStatement(
                "SELECT * FROM `subscription`" + (actual ? " WHERE `archived` = 0" : "")
        );
        ResultSet result = preparedStatement.executeQuery();
        while(result.next()){
            Subscription subscription = Create.subscription(result);

            subscription.setEmploymentsQuantities(
                    this.getEmploymentsQuantities(subscription)
            );

            subscriptions.add(subscription);
        }
        return subscriptions;
    }
    @Override
    public List<Subscription> getAll() throws SQLException {
        return this.get(false);
    }
    @Override
    public List<Subscription> getActual() throws SQLException {
        return this.get(true);
    }

    public Subscription getById(long id) throws SQLException {
        Subscription subscription = null;
        PreparedStatement preparedStatement = DB.getConnection().prepareStatement(
                "SELECT * FROM `subscription` WHERE `id` = ?"
        );
        preparedStatement.setLong(1, id);
        ResultSet result = preparedStatement.executeQuery();
        if(result.next()) {
            subscription = Create.subscription(result);
            subscription.setEmploymentsQuantities(
                    this.getEmploymentsQuantities(subscription)
            );
        }
        return subscription;
    }
}