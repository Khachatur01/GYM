package com.fitness.DAO;

import java.sql.SQLException;
import java.util.List;

public interface DAO<Model> {
    void add(Model model) throws SQLException;
    void edit(Model model) throws SQLException;
    void remove(Model model, boolean removeHistory) throws SQLException;
    List<Model> get(boolean actual) throws SQLException;
    List<Model> getAll() throws SQLException;
    List<Model> getActual() throws SQLException;
}
