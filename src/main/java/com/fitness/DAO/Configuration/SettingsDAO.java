package com.fitness.DAO.Configuration;

import com.fitness.Constant.Week;
import com.fitness.DAO.DAO;
import com.fitness.DataSource.DB;
import com.fitness.DataSource.Log.Log;
import com.fitness.Model.Archive.Archive;
import com.fitness.Model.Configuration.Settings;
import com.fitness.Model.Configuration.WeekDay;
import com.fitness.Model.Configuration.WorkingDay;
import com.fitness.Model.Person.Employee;
import com.fitness.Service.Create;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SettingsDAO implements DAO<Settings> {
    @Override
    public void add(Settings settings) throws SQLException {

    }

    @Override
    public void edit(Settings settings) throws SQLException {

    }

    @Override
    public void remove(Settings settings, boolean removeHistory) throws SQLException {

    }

    @Override
    public List<Settings> get(boolean actual) throws SQLException {
        return null;
    }

    @Override
    public List<Settings> getAll() throws SQLException {
        return null;
    }

    @Override
    public List<Settings> getActual() throws SQLException {
        return null;
    }

    public WorkingDay getWorkingDay(Employee employee) throws SQLException {
        Connection connection = DB.getConnection();
        if(connection == null) throw new SQLException();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM `working_days`, `employee` " +
                        "WHERE `working_days`.`employee_id` = `employee`.`id` AND " +
                        "`employee`.`id` = ?"
        );
        preparedStatement.setLong(1, employee.getId());
        ResultSet result = preparedStatement.executeQuery();

        if(result.next()) {
            WorkingDay workingDay = new WorkingDay();
            workingDay.setEmployee(Create.employee(result));

            List<WeekDay> employeeWorkingDays = new ArrayList<>();
            for(Week week: Week.values()) {
                WeekDay weekDay = new WeekDay();
                weekDay.setWeek(week);
                weekDay.setWorkingDay(result.getBoolean("working_days." + week.toString()));
                employeeWorkingDays.add(weekDay);
            }

            workingDay.setWorkingDays(employeeWorkingDays);
            return workingDay;
        }

        return null;
    }
    public List<WorkingDay> getWorkingDays() throws SQLException {
        List<WorkingDay> workingDays = new ArrayList<>();

        Connection connection = DB.getConnection();
        if(connection == null) throw new SQLException();

        PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM `working_days`, `employee` " +
                            "WHERE `working_days`.`employee_id` = `employee`.`id`"
            );
        ResultSet result = preparedStatement.executeQuery();

        while(result.next()) {
            WorkingDay workingDay = new WorkingDay();
            workingDay.setEmployee(Create.employee(result));

            List<WeekDay> employeeWorkingDays = new ArrayList<>();
            for(Week week: Week.values()) {
                WeekDay weekDay = new WeekDay();
                weekDay.setWeek(week);
                weekDay.setWorkingDay(result.getBoolean("working_days." + week.toString()));
                employeeWorkingDays.add(weekDay);
            }

            workingDay.setWorkingDays(employeeWorkingDays);
            workingDays.add(workingDay);
        }

        return workingDays;
    }

    public void setWorkingDays(List<WorkingDay> workingDays) throws SQLException {
        Connection connection = DB.getConnection();
        if(connection == null) throw new SQLException();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE `working_days` SET `monday` = ?, `tuesday` = ?, `wednesday` = ?, `thursday` = ?, `friday` = ?, `saturday` = ?, `sunday` = ? " +
                        "WHERE `employee_id` = ?"
        );

        for(WorkingDay workingDay: workingDays) {
            int weekNumber = 1;
            for(WeekDay employeeWorkingDay: workingDay.getWorkingDays())
                preparedStatement.setBoolean(weekNumber++, employeeWorkingDay.isWorkingDay());

            preparedStatement.setLong(8, workingDay.getEmployee().getId());
            preparedStatement.addBatch();
        }

        preparedStatement.executeBatch();
    }
}
