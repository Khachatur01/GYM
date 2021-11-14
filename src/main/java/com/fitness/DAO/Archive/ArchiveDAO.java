package com.fitness.DAO.Archive;

import com.fitness.DAO.DAO;
import com.fitness.DataSource.DB;
import com.fitness.Model.Archive.Archive;
import com.fitness.Model.Person.Customer;
import com.fitness.Model.Person.Employee;
import com.fitness.Model.Person.Person;
import com.fitness.Model.Work.Employment;
import com.fitness.Model.Work.Position;
import com.fitness.Model.Work.Subscription;
import com.fitness.Service.Create;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ArchiveDAO implements DAO<Archive> {
    @Override
    public void add(Archive archive) throws SQLException {
        PreparedStatement preparedStatement = DB.getConnection().prepareStatement(
                "INSERT INTO `archive`(`customer_id`, `employee_id`, `registration`, `bonus`) " +
                        "VALUES (?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
        );
        preparedStatement.setLong(1, archive.getCustomer().getId());
        preparedStatement.setLong(2, archive.getEmployee().getId());
        preparedStatement.setBoolean(3, archive.isRegistration());
        preparedStatement.setBoolean(4, archive.isBonus());

        preparedStatement.executeUpdate();

        ResultSet generatedId = preparedStatement.getGeneratedKeys();
        if(generatedId.next()){
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

    public List<Archive> getByDate(Date startDate, Date endDate) throws SQLException {
        List<Archive> archives = new ArrayList<>();
        PreparedStatement preparedStatement = DB.getConnection().prepareStatement(
                "SELECT * FROM `archive`, `customer`, `subscription`, `employee`, `position`, `employment` WHERE " +
                        "`archive.customer_id` = `customer`.`id` AND " +
                        "`customer`.`subscription_id` = `subscription`.`id` AND " +
                        "`archive.employee_id` = `employee`.`id` AND " +
                        "`employee`.`position_id` = `position`.`id` AND " +
                        "`position`.`employment_id` = `employment`.`id` AND " +
                        "`date` BETWEEN ? AND ?"
        );
        preparedStatement.setDate(1, new java.sql.Date(startDate.getTime()));
        preparedStatement.setDate(2, new java.sql.Date(endDate.getTime()));
        ResultSet result = preparedStatement.executeQuery();
        while(result.next()){
            Subscription subscription = Create.subscription(result);

            Customer customer = Create.customer(result);
            customer.setSubscription(subscription);

            Employment employment = Create.employment(result);

            Position position = Create.position(result);
            position.setEmployment(employment);

            Employee employee = Create.employee(result);
            employee.setPosition(position);

            Archive archive = Create.archive(result);
            archive.setCustomer(customer);
            archive.setEmployee(employee);

            archives.add(archive);

        }
        return archives;
    }
}
