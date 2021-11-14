package com.fitness.DAO.Work;

import com.fitness.DAO.DAO;
import com.fitness.DataSource.DB;
import com.fitness.Model.Work.Employment;
import com.fitness.Service.Create;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmploymentDAO implements DAO<Employment> {
    @Override
    public void add(Employment employment) throws SQLException {
        if(employment == null) return;
        PreparedStatement preparedStatement = DB.getConnection().prepareStatement(
                "INSERT INTO `employment`(`name`, `price`) VALUES(?, ?)",
                Statement.RETURN_GENERATED_KEYS
        );
        preparedStatement.setString(1, employment.getName());
        preparedStatement.setInt(2, employment.getPrice());
        preparedStatement.executeUpdate();

        /* set generated id to new created employment */
        try (ResultSet generatedId = preparedStatement.getGeneratedKeys()) {
            if (generatedId.next()) {
                employment.setId(generatedId.getLong(1));
            }
        }
    }
    @Override
    public void edit(Employment employment) throws SQLException {
        PreparedStatement preparedStatement = DB.getConnection().prepareStatement(
                "UPDATE `employment` SET `name` = ?, `price` = ? WHERE `id` = ?"
        );
        preparedStatement.setString(1, employment.getName());
        preparedStatement.setLong(2, employment.getPrice());
        preparedStatement.setLong(3, employment.getId());
        preparedStatement.executeUpdate();
    }
    @Override
    public void remove(Employment employment, boolean removeHistory) throws SQLException {
        PreparedStatement preparedStatement;
        if(removeHistory) {
            /* when employment deleted, position, employee, employee's customers and customer visits will be deleted */
            preparedStatement = DB.getConnection().prepareStatement(
                    "DELETE FROM `employment` WHERE `id` = ?"
            );
        } else {
            preparedStatement = DB.getConnection().prepareStatement(
                    "UPDATE `employment` SET `archived` = 1 WHERE `id` = ?"
            );
        }
        preparedStatement.setLong(1, employment.getId());
        preparedStatement.executeUpdate();
    }

    public List<Employment> get(boolean actual) throws SQLException {
        List<Employment> employments = new ArrayList<>();
        PreparedStatement preparedStatement = DB.getConnection().prepareStatement(
                "SELECT * FROM `employment`" + (actual ? " WHERE `archived` = 0" : "")
        );
        ResultSet result = preparedStatement.executeQuery();
        while(result.next())
            employments.add(Create.employment(result));

        return employments;
    }
    @Override
    public List<Employment> getAll() throws SQLException {
        return this.get(false);
    }
    @Override
    public List<Employment> getActual() throws SQLException {
        return this.get(true);
    }
}
