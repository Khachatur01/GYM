package com.fitness.DAO.Work;

import com.fitness.DataSource.DB;
import com.fitness.Model.Work.EmploymentQuantity;
import com.fitness.Model.Work.Subscription;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class SubscriptionDAO {
    private void addEmploymentsQuantities(Subscription subscription) throws SQLException {
        long subscriptionId = subscription.getId();
        List<EmploymentQuantity> employmentQuantities = subscription.getEmploymentsQuantities();
        PreparedStatement preparedStatement = DB.getConnection().prepareStatement(
                "INSERT INTO `subscription_employment`(`subscription_id`, `employment_id`, `quantity`) VALUES (?, ?, ?)"
        );
        for(EmploymentQuantity employmentQuantity: employmentQuantities){
            preparedStatement.setLong(1, subscriptionId);
            preparedStatement.setLong(2, employmentQuantity.getEmployment().getId());
            preparedStatement.setInt(3, employmentQuantity.getQuantity());
            preparedStatement.addBatch();
        }
        preparedStatement.executeBatch();
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

    public void add(Subscription subscription) throws SQLException {
        PreparedStatement preparedStatement = DB.getConnection().prepareStatement(
                "INSERT INTO `subscription`(`name`, `price`)"
        );
        preparedStatement.setString(1, subscription.getName());
        preparedStatement.setInt(1, subscription.getPrice());

        preparedStatement.executeUpdate();

        addEmploymentsQuantities(subscription);
    }

    public void edit(Subscription subscription) throws SQLException {
        this.editEmploymentsQuantities(subscription);
        PreparedStatement preparedStatement = DB.getConnection().prepareStatement(
                "UPDATE `subscription` SET `name` = ?, `price` = ? WHERE `id` = ?"
        );
        preparedStatement.setString(1, subscription.getName());
        preparedStatement.setInt(2, subscription.getPrice());
        preparedStatement.setLong(3, subscription.getId());
        preparedStatement.executeUpdate();
    }

    public void remove(Subscription subscription, boolean removeHistory) throws  SQLException {
        PreparedStatement preparedStatement;
        if(removeHistory) { // when subscription deleted, subscription quantities, customer and customer visits will be deleted
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
}
