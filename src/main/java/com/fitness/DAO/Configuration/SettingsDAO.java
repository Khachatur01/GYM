package com.fitness.DAO.Configuration;

import com.fitness.DAO.DAO;
import com.fitness.Model.Configuration.Settings;

import java.sql.SQLException;
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
}
