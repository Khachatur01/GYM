package com.fitness.DAO.Work;

import com.fitness.DataSource.DB;
import com.fitness.Model.Work.Employment;
import com.fitness.Model.Work.Position;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmploymentDAO {
    public List<Employment> getNonArchived() throws SQLException {
        Statement statement = DB.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(
                "select * from `employment`"
        );
        List<Employment> employments = new ArrayList<>();
        while(resultSet.next()){
            employments.add(
                    new Employment(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getInt("price"),
                            resultSet.getBoolean("archived")
                    )
            );
        }
        return employments;
    }

    public void add(Employment employment) throws SQLException {
        if(employment == null) return;
        PreparedStatement preparedStatement = DB.getConnection().prepareStatement(
                "INSERT INTO `employment`(`name`, `price`) VALUES(?, ?)"
        );
        preparedStatement.setString(1, employment.getName());
        preparedStatement.setInt(2, employment.getPrice());
        preparedStatement.executeUpdate();
    }

    public void edit(Employment employment) throws SQLException {
        PreparedStatement preparedStatement = DB.getConnection().prepareStatement(
                "UPDATE `position` SET `name` = ?, `price` = ? WHERE `id` = ?"
        );
        preparedStatement.setString(1, employment.getName());
        preparedStatement.setLong(2, employment.getPrice());
        preparedStatement.setLong(3, employment.getId());
        preparedStatement.executeUpdate();
    }
}
