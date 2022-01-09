package com.fitness.DAO.Person;

import com.fitness.DAO.DAO;
import com.fitness.DataSource.DB;
import com.fitness.Model.Person.Employee;
import com.fitness.Model.Work.Employment;
import com.fitness.Model.Work.Position;
import com.fitness.Service.Create;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO implements DAO<Employee> {
    public List<Position> getPositions(Employee employee, boolean actual) throws SQLException {
        if(employee == null) return null;
        List<Position> positions = new ArrayList<>();
        PreparedStatement preparedStatement = DB.getConnection().prepareStatement(
                "SELECT * FROM `position`, `employee_position` WHERE " +
                        "`position`.`id` = `employee_position`.`position_id` AND " +
                        "`employee_position`.`employee_id` = ?" +
                        (actual ? " AND `position`.`archived` = 0" : "")
        );
        preparedStatement.setLong(1, employee.getId());
        ResultSet result = preparedStatement.executeQuery();
        while (result.next()) {
            positions.add(Create.position(result));
        }
        return positions;
    }

    private void removePositions(Employee employee) throws SQLException {
        PreparedStatement preparedStatement = DB.getConnection().prepareStatement(
                "DELETE FROM `employee_position` WHERE `employee_id` = ?"
        );
        preparedStatement.setLong(1, employee.getId());
        preparedStatement.executeUpdate();
    }
    private void addPositions(Employee employee) throws SQLException {
        PreparedStatement preparedStatement = DB.getConnection().prepareStatement(
                "INSERT INTO `employee_position`(`employee_id`, `position_id`) " +
                        "VALUES(?, ?)"
        );
        DB.getConnection().setAutoCommit(false);
        for(Position position: employee.getPositions()){
            preparedStatement.setLong(1, employee.getId());
            preparedStatement.setLong(2, position.getId());
            preparedStatement.addBatch();
        }
        preparedStatement.executeBatch();
        DB.getConnection().setAutoCommit(true);
    }

    private void editPositions(Employee employee) throws SQLException {
        this.removePositions(employee);
        this.addPositions(employee);
    }

    @Override
    public void add(Employee employee) throws SQLException {
        if(employee == null) return;
        PreparedStatement preparedStatement = DB.getConnection().prepareStatement(
                "INSERT INTO `employee`(`name`, `surname`, `phone`, `phone2`, `address`, `archived`) " +
                        "VALUES(?, ?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
        );
        preparedStatement.setString(1, employee.getName().getFirstName());
        preparedStatement.setString(2, employee.getName().getLastName());
        preparedStatement.setString(3, employee.getPhone());
        preparedStatement.setString(4, employee.getPhone2());
        preparedStatement.setString(5, employee.getAddress());
        preparedStatement.setBoolean(6, employee.isArchived());
        preparedStatement.executeUpdate();

        /* set generated id to new created employment */
        try (ResultSet generatedId = preparedStatement.getGeneratedKeys()) {
            if (generatedId.next()) {
                employee.setId(generatedId.getLong(1));
            }
        }
        this.addPositions(employee);

        preparedStatement = DB.getConnection().prepareStatement(
                "INSERT INTO `working_days`(`employee_id`) " +
                        "VALUES(?)"
        );
        preparedStatement.setLong(1, employee.getId());
        preparedStatement.executeUpdate();
    }

    @Override
    public void edit(Employee employee) throws SQLException {
        if(employee == null) return;
        this.editPositions(employee);
        PreparedStatement preparedStatement = DB.getConnection().prepareStatement(
                "UPDATE `employee` SET " +
                        "`name` = ?, `surname` = ?, `phone` = ?, `phone2` = ?, `address` = ?, `archived` = ? WHERE" +
                        "`id` = ?"
        );
        preparedStatement.setString(1, employee.getName().getFirstName());
        preparedStatement.setString(2, employee.getName().getLastName());
        preparedStatement.setString(3, employee.getPhone());
        preparedStatement.setString(4, employee.getPhone2());
        preparedStatement.setString(5, employee.getAddress());
        preparedStatement.setBoolean(6, employee.isArchived());
        preparedStatement.setLong(7, employee.getId());
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

        preparedStatement = DB.getConnection().prepareStatement(
                "DELETE FROM `working_days` WHERE `employee_id` = ?"
        );
        preparedStatement.setLong(1, employee.getId());
        preparedStatement.executeUpdate();
    }

    @Override
    public List<Employee> get(boolean actual) throws SQLException {
        List<Employee> employees = new ArrayList<>();
        PreparedStatement preparedStatement = DB.getConnection().prepareStatement(
                "SELECT * FROM `employee` " +
                        (actual ? " WHERE `employee`.`archived` = 0" : "")
        );
        ResultSet result = preparedStatement.executeQuery();
        while(result.next()){
            Employee employee = Create.employee(result);

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

    public List<Employee> getBy(Employment employment, boolean actual) throws SQLException {
        if(employment == null) return new ArrayList<>();
        List<Employee> employees = new ArrayList<>();
        PreparedStatement preparedStatement = DB.getConnection().prepareStatement(
                "SELECT * FROM `employee`, `employee_position`, `position`, `employment` WHERE " +
                        "`employment`.`position_id` = `position`.`id` AND " +
                        "`employee_position`.`position_id` = `position`.`id` AND " +
                        "`employee_position`.`employee_id` = `employee`.`id` AND " +
                        "`employment`.`id` = ? " +
                        (actual ? " AND `employee`.`archived` = 0" : "")
        );
        preparedStatement.setLong(1, employment.getId());
        ResultSet result = preparedStatement.executeQuery();
        while(result.next()){
            Employee employee = Create.employee(result);
            employees.add(employee);
        }

        return employees;
    }
}
