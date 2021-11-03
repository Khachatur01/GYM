package com.fitness.DAO.Archive;

import com.fitness.DAO.DAO;
import com.fitness.Model.Archive.Archive;

import java.sql.SQLException;
import java.util.List;

public class ArchiveDAO implements DAO<Archive> {
    @Override
    public void add(Archive archive) throws SQLException {

    }

    @Override
    public void edit(Archive archive) throws SQLException {

    }

    @Override
    public void remove(Archive archive, boolean removeHistory) throws SQLException {

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
