package com.fitness.DAO.Work;

import com.fitness.DAO.DAO;
import com.fitness.DataSource.DB;
import com.fitness.Model.Work.Employment;
import com.fitness.Model.Work.Position;
import com.fitness.Service.Create;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PositionDAO implements DAO<Position> {
    private List<Employment> getEmployments(Position position) throws SQLException {
        List<Employment> employments = new ArrayList<>();
        Connection connection = DB.getConnection();
        if(connection == null) throw new SQLException();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM `position`, `employment` WHERE " +
                        "`employment`.`position_id` = `position`.`id` AND " +
                        "`employment`.`position_id` = ?"
        );
        preparedStatement.setLong(1, position.getId());
        ResultSet result = preparedStatement.executeQuery();
        while (result.next()) {
            Employment employment = Create.employment(result);
            employments.add(employment);
        }
        return employments;
    }
    private void addEmployments(Position position) throws SQLException {
        Connection connection = DB.getConnection();
        if(connection == null) throw new SQLException();

        for(Employment employment: position.getEmployments()){
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE `employment` SET `position_id` = ? WHERE `id` = ?"
            );
            preparedStatement.setLong(1, position.getId());
            preparedStatement.setLong(2, employment.getId());
            preparedStatement.executeUpdate();
        }
    }
    private void removeEmployments(Position position) throws SQLException {
        Connection connection = DB.getConnection();
        if(connection == null) throw new SQLException();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE `employment` SET `position_id` = null WHERE `position_id` = ?"
        );
        preparedStatement.setLong(1, position.getId());
        preparedStatement.executeUpdate();
    }
    private void editEmployments(Position position) throws SQLException {
        this.removeEmployments(position);
        this.addEmployments(position);
    }
    @Override
    public void add(Position position) throws SQLException {
        Connection connection = DB.getConnection();
        if(connection == null) throw new SQLException();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO `position`(`name`) VALUES(?)",
                Statement.RETURN_GENERATED_KEYS
        );
        preparedStatement.setString(1, position.getName());
        preparedStatement.executeUpdate();
        /* set generated id to new created employment */
        try (ResultSet generatedId = preparedStatement.getGeneratedKeys()) {
            if (generatedId.next())
                position.setId(generatedId.getLong(1));
        }
        this.addEmployments(position);
    }

    @Override
    public void edit(Position position) throws SQLException {
        Connection connection = DB.getConnection();
        if(connection == null) throw new SQLException();

        this.editEmployments(position);

        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE `position` SET `name` = ? WHERE `id` = ?"
        );
        preparedStatement.setString(1, position.getName());
        preparedStatement.setLong(2, position.getId());
        preparedStatement.executeUpdate();
    }
    @Override
    public void remove(Position position, boolean removeHistory) throws SQLException {
        PreparedStatement preparedStatement;
        Connection connection = DB.getConnection();
        if(connection == null) throw new SQLException();

        if(removeHistory) {
            /* when position deleted, employment, employee, customer and customer visits will be deleted */
            preparedStatement = connection.prepareStatement(
                    "DELETE FROM `position` WHERE `id` = ?"
            );
        } else {
            preparedStatement = connection.prepareStatement(
                    "UPDATE `position` SET `archived` = 1 WHERE `id` = ?"
            );
        }
        preparedStatement.setLong(1, position.getId());
        preparedStatement.executeUpdate();
    }

    @Override
    public List<Position> get(boolean actual) throws SQLException {
        List<Position> positions = new ArrayList<>();
        Connection connection = DB.getConnection();
        if(connection == null) throw new SQLException();

        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(
                "SELECT * FROM `position` "
                    + (actual ? " WHERE `position`.`archived` = 0" : "")
        );
        while(result.next()){
            Position position = Create.position(result);
            position.setEmployments(this.getEmployments(position));
            positions.add(position);
        }
        return positions;
    }

    @Override
    public List<Position> getAll() throws SQLException {
        return this.get(false);
    }

    @Override
    public List<Position> getActual() throws SQLException {
        return this.get(true);
    }
}
