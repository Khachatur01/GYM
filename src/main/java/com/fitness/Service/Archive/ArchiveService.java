package com.fitness.Service.Archive;

import com.fitness.DAO.Archive.ArchiveDAO;
import com.fitness.Model.Archive.Archive;
import com.fitness.Model.Person.Customer;
import com.fitness.Model.Person.Employee;
import com.fitness.Service.Verify;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class ArchiveService {
    private ArchiveDAO archiveDAO = new ArchiveDAO();


    public Archive add(Customer customer, Employee employee, boolean isRegistration, boolean isBonus) throws SQLException {
        if(customer == null || employee == null) return  null;

        Archive archive = new Archive();
        archive.setCustomer(customer);
        archive.setEmployee(employee);
        archive.setRegistration(isRegistration);
        archive.setBonus(isBonus);

        archiveDAO.add(archive);

        return archive;
    }

    public void remove(Archive archive) throws SQLException {
        return;
    }

}
