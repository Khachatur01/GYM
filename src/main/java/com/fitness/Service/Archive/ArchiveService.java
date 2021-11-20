package com.fitness.Service.Archive;

import com.fitness.DAO.Archive.ArchiveDAO;
import com.fitness.Model.Archive.Archive;
import com.fitness.Model.Person.Customer;
import com.fitness.Model.Person.Employee;
import com.fitness.Model.Report.Report;
import com.fitness.Model.Work.EmploymentQuantity;
import com.fitness.Service.Person.CustomerService;
import com.fitness.Service.Verify;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ArchiveService {
    private ArchiveDAO archiveDAO = new ArchiveDAO();
    private CustomerService customerService = new CustomerService();

    public Archive add(Customer customer, Employee employee, boolean isRegistration, boolean isBonus) throws SQLException {
        if(customer == null || employee == null) return  null;

        Archive archive = new Archive();
        archive.setCustomer(customer);
        archive.setEmployee(employee);
        archive.setRegistration(isRegistration);
        archive.setBonus(isBonus);

        archiveDAO.add(archive);

        if(!customerService.hasAvailableEmployment(customer)) {
            customerService.remove(customer, false);
            customer.setArchived(true);
        }
        return archive;
    }

    public void remove(Archive archive) throws SQLException {
        archiveDAO.remove(archive, true);
    }

    public List<Archive> getByDateRange(Date startDate, Date endDate) throws SQLException {
        return archiveDAO.getByDateRange(startDate, endDate);
    }

    public List<Report> getReports(List<Archive> archives) {
        List<Report> reports = new ArrayList<>();
        for(Archive archive: archives) {
            // @TODO logic of one employee money count
        }
        return reports;
    }
}
