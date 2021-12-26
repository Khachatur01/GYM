package com.fitness.DAO.Archive;

import com.fitness.DAO.DAO;
import com.fitness.DAO.Person.EmployeeDAO;
import com.fitness.DAO.Work.SubscriptionDAO;
import com.fitness.DataSource.DB;
import com.fitness.Model.Archive.Archive;
import com.fitness.Model.Person.Customer;
import com.fitness.Model.Person.Employee;
import com.fitness.Model.Work.Employment;
import com.fitness.Model.Work.Subscription;
import com.fitness.Service.Create;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ArchiveDAO implements DAO<Archive> {
    private final EmployeeDAO employeeDAO = new EmployeeDAO();
    private final SubscriptionDAO subscriptionDAO = new SubscriptionDAO();

    @Override
    public void add(Archive archive) throws SQLException {
        PreparedStatement preparedStatement = DB.getConnection().prepareStatement(
                "INSERT INTO `archive`(`customer_id`, `employee_id`, `employment_id`, `bonus`) " +
                        "VALUES (?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
        );
        preparedStatement.setLong(1, archive.getCustomer().getId());
        preparedStatement.setLong(2, archive.getEmployee().getId());
        preparedStatement.setLong(3, archive.getEmployment().getId());
        preparedStatement.setBoolean(4, archive.isBonus());

        preparedStatement.executeUpdate();

        ResultSet generatedId = preparedStatement.getGeneratedKeys();
        if(generatedId.next()) {
            archive.setId(generatedId.getLong(1));
        }
    }

    @Override
    public void edit(Archive archive) throws SQLException {
        //@TODO
    }

    @Override
    public void remove(Archive archive, boolean removeHistory) throws SQLException {
        PreparedStatement preparedStatement = DB.getConnection().prepareStatement(
                "DELETE FROM `archive` WHERE `id` = ?"
        );
        preparedStatement.setLong(1, archive.getId());

        preparedStatement.executeUpdate();
    }

    @Override
    public List<Archive> get(boolean actual) throws SQLException {
        return null;
    }

    @Override
    public List<Archive> getAll() throws SQLException {
        return null;
    }

    @Override
    public List<Archive> getActual() throws SQLException {
        return null;
    }

    public List<Archive> getByDateRange(LocalDate startDate, LocalDate endDate) throws SQLException {
        List<Archive> archives = new ArrayList<>();
        PreparedStatement preparedStatement = DB.getConnection().prepareStatement(
                "SELECT * FROM `archive`, `customer`, `subscription`, `employee`, `employment` WHERE " +
                        "`archive`.`customer_id` = `customer`.`id` AND " +
                        "`customer`.`subscription_id` = `subscription`.`id` AND " +
                        "`archive`.`employee_id` = `employee`.`id` AND " +
                        "`archive`.`employment_id` = `employment`.`id` AND " +
                        "`archive`.`date` BETWEEN ? AND ?"
        );

        preparedStatement.setString(1, startDate.toString() + " 00:00:00");
        preparedStatement.setString(2, endDate.toString()   + " 23:59:59");

        ResultSet result = preparedStatement.executeQuery();
        while(result.next()) {
            Subscription subscription = Create.subscription(result);
            subscription.setEmploymentsQuantities(subscriptionDAO.getById(subscription.getId()).getEmploymentsQuantities());

            Customer customer = Create.customer(result);
            customer.setSubscription(subscription);

            Employee employee = Create.employee(result);
            employee.setPositions(employeeDAO.getPositions(employee, false));

            Employment employment = Create.employment(result);

            Archive archive = Create.archive(result);
            archive.setCustomer(customer);
            archive.setEmployee(employee);
            archive.setEmployment(employment);

            archives.add(archive);

        }
        return archives;
    }
}
