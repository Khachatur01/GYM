package com.fitness.Service;

import com.fitness.Model.Archive.Archive;
import com.fitness.Model.Person.Customer;
import com.fitness.Model.Person.Employee;
import com.fitness.Model.Person.Person;
import com.fitness.Model.Work.Employment;
import com.fitness.Model.Work.EmploymentQuantity;
import com.fitness.Model.Work.Position;
import com.fitness.Model.Work.Subscription;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Create {
    public static EmploymentQuantity employmentQuantity(ResultSet result) throws SQLException {
        return new EmploymentQuantity(
                new Employment(
                        result.getLong("employment.id"),
                        result.getString("employment.name"),
                        result.getInt("employment.price"),
                        result.getBoolean("employment.archived")
                ),
                result.getInt("subscription_employment.quantity")
        );
    }
    public static Subscription subscription(ResultSet result) throws SQLException {
        return new Subscription(
                result.getLong("subscription.id"),
                result.getString("subscription.name"),
                result.getInt("subscription.price"),
                null,
                result.getBoolean("subscription.archived")
        );
    }
    public static Customer customer(ResultSet result) throws SQLException {
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
                null,
                result.getBoolean("customer.archived")
        );
    }
    public static Employment employment(ResultSet result) throws SQLException {
        return new Employment(
                result.getLong("employment.id"),
                result.getString("employment.name"),
                result.getInt("employment.price"),
                result.getBoolean("employment.archived")
        );
    }
    public static Position position(ResultSet result) throws SQLException {
        return new Position(
                result.getLong("position.id"),
                result.getString("position.name"),
                null,
                result.getBoolean("position.archived")
        );
    }
    public static Employee employee(ResultSet result) throws SQLException {
        return new Employee(
                result.getLong("employee.id"),
                new Person.Name(
                        result.getString("employee.name"),
                        result.getString("employee.surname")
                ),
                result.getString("employee.phone"),
                result.getString("employee.phone2"),
                result.getString("employee.address"),
                null,
                result.getBoolean("employee.archived")
        );
    }
    public static Archive archive(ResultSet result) throws SQLException {
        return new Archive(
                result.getLong("archive.id"),
                null,
                null,
                result.getBoolean("archive.registration"),
                result.getBoolean("archive.bonus")
        );
    }
}
