package com.fitness.DAO.Person;

import com.fitness.DAO.DAO;
import com.fitness.DataSource.DB;
import com.fitness.Model.Person.Employee;
import com.fitness.Model.Person.Person;
import com.fitness.Model.Work.Employment;
import com.fitness.Model.Work.Position;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO implements DAO<Employee> {
    @Override
    public void add(Employee employee) throws SQLException {
        if(employee == null) return;
        PreparedStatement preparedStatement = DB.getConnection().prepareStatement(
                "INSERT INTO `employee`(`name`, `surname`, `phone`, `phone2`, `address`, `position_id`, `archived`) " +
                        "VALUES(?, ?, ?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
        );
        preparedStatement.setString(1, employee.getName().getFirstName());
        preparedStatement.setString(2, employee.getName().getLastName());
        preparedStatement.setString(3, employee.getPhone());
        preparedStatement.setString(4, employee.getPhone2());
        preparedStatement.setString(5, employee.getAddress());
        preparedStatement.setLong(6, employee.getPosition().getId());
        preparedStatement.setBoolean(7, employee.isArchived());
        preparedStatement.executeUpdate();

        /* set generated id to new created employment */
        try (ResultSet generatedId = preparedStatement.getGeneratedKeys()) {
            if (generatedId.next()) {
                employee.setId(generatedId.getLong(1));
            }
        }
    }

    @Override
    public void edit(Employee employee) throws SQLException {
        if(employee == null) return;
        PreparedStatement preparedStatement = DB.getConnection().prepareStatement(
                "UPDATE `employee` SET " +
                        "`name` = ?, `surname` = ?, `phone` = ?, `phone2` = ?, `address` = ?, `position_id` = ?, `archived` = ?",
                Statement.RETURN_GENERATED_KEYS
        );
        preparedStatement.setString(1, employee.getName().getFirstName());
        preparedStatement.setString(2, employee.getName().getLastName());
        preparedStatement.setString(3, employee.getPhone());
        preparedStatement.setString(4, employee.getPhone2());
        preparedStatement.setString(5, employee.getAddress());
        preparedStatement.setLong(6, employee.getPosition().getId());
        preparedStatement.setBoolean(7, employee.isArchived());
        preparedStatement.executeUpdate();
    }

    @Override
    public void remove(Employee employee, boolean removeHistory) throws SQLException {
        PreparedStatement preparedStatement;
        if(removeHistory) {
            /* when employee deleted, employee's customers visits will be deleted */
            preparedStatement = DB.getConnection().prepareStatement(
                    "DELETE FROM `employee` WHERE `id` = ?"
            );
        } else {
            preparedStatement = DB.getConnection().prepareStatement(
                    "UPDATE `employee` SET `archived` = 1 WHERE `id` = ?"
            );
        }
        preparedStatement.setLong(1, employee.getId());
        preparedStatement.executeUpdate();
    }

    @Override
    public List<Employee> get(boolean actual) throws SQLException {
        List<Employee> employees = new ArrayList<>();
        PreparedStatement preparedStatement = DB.getConnection().prepareStatement(
                "SELECT * FROM `employee`, `position`, `employment` WHERE " +
                        "`employee`.`position_id` = `position`.`id` AND" +
                        "`position`.`employment_id` = `employment`.`id`" +
                        (actual ? "  `archived` = 0" : "")
        );
        ResultSet result = preparedStatement.executeQuery();
        while(result.next()){
            Employee employee = new Employee(
                    result.getLong("employee.id"),
                    new Person.Name(
                            result.getString("employee.name"),
                            result.getString("employee.surname")
                    ),
                    result.getString("employee.phone"),
                    result.getString("employee.phone2"),
                    result.getString("employee.address"),
                    new Position(
                            result.getLong("position.id"),
                            result.getString("position.name"),
                            new Employment(
                                    result.getLong("employment.id"),
                                    result.getString("employment.name"),
                                    result.getInt("employment.price"),
                                    result.getBoolean("employment.archived")
                            ),
                            result.getBoolean("position.archived")
                    ),
                    result.getBoolean("archived")
            );

            employees.add(employee);
        }
        return employees;
    }

    @Override
    public List<Employee> getAll() throws SQLException {
        return this.get(false);
    }

    @Override
    public List<Employee> getActual() throws SQLException {
        return this.get(true);
    }
}
