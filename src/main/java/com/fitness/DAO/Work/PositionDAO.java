package com.fitness.DAO.Work;

import com.fitness.DAO.DAO;
import com.fitness.DataSource.DB;
import com.fitness.Model.Work.Employment;
import com.fitness.Model.Work.Position;
import com.fitness.Service.Create;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PositionDAO implements DAO<Position> {
    @Override
    public void add(Position position) throws SQLException {
        PreparedStatement preparedStatement = DB.getConnection().prepareStatement(
                "INSERT INTO `position`(`name`, `employment_id`) VALUES(?, ?)"
        );
        preparedStatement.setString(1, position.getName());
        preparedStatement.setLong(2, position.getEmployment().getId());
        preparedStatement.executeUpdate();
    }

    @Override
    public void edit(Position position) throws SQLException {
        PreparedStatement preparedStatement = DB.getConnection().prepareStatement(
                "UPDATE `position` SET `name` = ?, `employment_id` = ? WHERE `id` = ?"
        );
        preparedStatement.setString(1, position.getName());
        preparedStatement.setLong(2, position.getEmployment().getId());
        preparedStatement.setLong(3, position.getId());
        preparedStatement.executeUpdate();
    }
    @Override
    public void remove(Position position, boolean removeHistory) throws SQLException {
        PreparedStatement preparedStatement;
        if(removeHistory) {
            /* when position deleted, employment, employee, customer and customer visits will be deleted */
            preparedStatement = DB.getConnection().prepareStatement(
                    "DELETE FROM `position` WHERE `id` = ?"
            );
        } else {
            preparedStatement = DB.getConnection().prepareStatement(
                    "UPDATE `position` SET `archived` = 1 WHERE `id` = ?"
            );
        }
        preparedStatement.setLong(1, position.getId());
        preparedStatement.executeUpdate();
    }

    @Override
    public List<Position> get(boolean actual) throws SQLException {
        List<Position> positions = new ArrayList<>();
        Statement statement = DB.getConnection().createStatement();
        ResultSet result = statement.executeQuery(
                "SELECT * FROM `position`, `employment` WHERE `position`.`employment_id` = `employment`.`id`"
                    + (actual ? " AND `position`.`archived` = 0 AND `employment`.`archived` = 0" : "")
        );
        while(result.next()){
            Employment employment = Create.employment(result);
            Position position = Create.position(result);
            position.setEmployment(employment);

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
