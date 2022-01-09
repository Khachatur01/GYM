package com.fitness.Service.Configuration;

import com.fitness.DAO.Configuration.SettingsDAO;
import com.fitness.Model.Configuration.WorkingDay;

import java.sql.SQLException;
import java.util.List;

public class SettingsService {
    SettingsDAO settingsDAO = new SettingsDAO();
    public List<WorkingDay> getWorkingDays() throws SQLException {
        return settingsDAO.getWorkingDays();
    }
    public void setWorkingDays(List<WorkingDay> workingDays) throws SQLException {
        settingsDAO.setWorkingDays(workingDays);
    }
}
