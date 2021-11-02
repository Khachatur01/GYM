package com.fitness.DAO.Person;

import com.fitness.DAO.DAO;
import com.fitness.Model.Person.Employee;

import java.sql.SQLException;
import java.util.List;

public class EmployeeDAO implements DAO<Employee> {
    @Override
    public void add(Employee employee) throws SQLException {

    }

    @Override
    public void edit(Employee employee) throws SQLException {

    }

    @Override
    public void remove(Employee employee, boolean removeHistory) throws SQLException {

    }

    @Override
    public List<Employee> get(boolean actual) throws SQLException {
        return null;
    }

    @Override
    public List<Employee> getAll() throws SQLException {
        return null;
    }

    @Override
    public List<Employee> getActual() throws SQLException {
        return null;
    }
    //@TODO
}
