package com.fitness.DAO.Archive;

import com.fitness.DAO.DAO;
import com.fitness.DataSource.DB;
import com.fitness.Model.Archive.Archive;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
}
