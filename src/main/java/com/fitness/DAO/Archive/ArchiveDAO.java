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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ArchiveDAO implements DAO<Archive> {
    private final EmployeeDAO employeeDAO = new EmployeeDAO();
    private final SubscriptionDAO subscriptionDAO = new SubscriptionDAO();

    @Override
    public void add(Archive archive) throws SQLException {
        PreparedStatement preparedStatement = DB.getConnection().prepareStatement(
                "INSERT INTO `archive`(`date`, `customer_id`, `employee_id`, `employment_id`, `bonus`) " +
                        "VALUES (?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
        );
        Employee employee = archive.getEmployee();
        Employment employment = archive.getEmployment();

        if(archive.getDate() == null)
            preparedStatement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
        else
            preparedStatement.setTimestamp(1, archive.getDate());

        preparedStatement.setLong(2, archive.getCustomer().getId());

        if(employee == null)
            preparedStatement.setNull(3, Types.BIGINT);
        else
            preparedStatement.setLong(3, employee.getId());
        if(employment == null)
            preparedStatement.setNull(4, Types.BIGINT);
        else
            preparedStatement.setLong(4, employment.getId());

        preparedStatement.setBoolean(5, archive.isBonus());

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
                "SELECT " +
                        "(`employment_id` IS NULL AND `employee_id` IS NULL) AS registration, " +
                        "(`customer`.`subscription_id` IS NULL) AS without_card, " +
                        "archive.*, customer.*, subscription.*, employee.*, employment.* " +
                        "FROM `archive` " +
                        "LEFT OUTER JOIN " +
                            "`customer` ON `customer`.`id` = `customer_id` " +
                        "LEFT OUTER JOIN " +
                            "`subscription` ON `subscription`.`id` = `customer`.`subscription_id` " +
                        "LEFT OUTER JOIN " +
                            "`employee` ON `employee`.`id` = `employee_id` " +
                        "LEFT OUTER JOIN " +
                            "`employment` ON `employment`.`id` = `employment_id` " +
                        "WHERE `archive`.`date` BETWEEN ? AND ?"
        );

        preparedStatement.setString(1, startDate.toString() + " 00:00:00");
        preparedStatement.setString(2, endDate.toString()   + " 23:59:59");

        ResultSet result = preparedStatement.executeQuery();
        while(result.next()) {
            Employee employee = null;
            Employment employment = null;

            Customer customer = Create.customer(result);

            if(!result.getBoolean("registration")) {
                employee = Create.employee(result);
                employee.setPositions(employeeDAO.getPositions(employee, false));
                employment = Create.employment(result);
            }
            if(!result.getBoolean("without_card")) {
                Subscription subscription = Create.subscription(result);
                subscription.setEmploymentsQuantities(subscriptionDAO.getById(subscription.getId()).getEmploymentsQuantities());
                customer.setSubscription(subscription);
            }

            Archive archive = Create.archive(result);
            archive.setCustomer(customer);
            archive.setEmployee(employee);
            archive.setEmployment(employment);

            archives.add(archive);

        }
        return archives;
    }
}
