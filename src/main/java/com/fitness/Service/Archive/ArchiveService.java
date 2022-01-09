package com.fitness.Service.Archive;

import com.fitness.DAO.Archive.ArchiveDAO;
import com.fitness.Model.Archive.Archive;
import com.fitness.Model.Person.Customer;
import com.fitness.Model.Person.Employee;
import com.fitness.Model.Report.Report;
import com.fitness.Model.Report.Salary;
import com.fitness.Model.Work.DateTime;
import com.fitness.Model.Work.Employment;
import com.fitness.Model.Work.Subscription;
import com.fitness.Service.Person.CustomerService;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ArchiveService {
    private final ArchiveDAO archiveDAO = new ArchiveDAO();
    private final CustomerService customerService = new CustomerService();

    public Archive add(DateTime dateTime, Customer customer, Employee employee, Employment employment, boolean isBonus) throws SQLException {
        if(customer == null) return  null;

        Archive archive = new Archive();
        archive.setDate(dateTime);
        archive.setCustomer(customer);
        archive.setEmployee(employee);
        archive.setEmployment(employment);
        archive.setBonus(isBonus);

        archiveDAO.add(archive);

        if(customer.getSubscription() != null && !customerService.hasAvailableEmployment(customer)) {
            customerService.remove(customer, false);
            customer.setArchived(true);
        }
        return archive;
    }

    public void remove(Archive archive) throws SQLException {
        if(archive == null) return;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Հաստատում");

        ButtonType delete = new ButtonType("Ջնջել", ButtonBar.ButtonData.OK_DONE);
        ButtonType back = new ButtonType("Հետ", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(delete, back);

        if(archive.isRegistration()) {
            alert.setHeaderText("Ջնջելով գրանցման օրը, ամբողջությամբ կջնջվեն բոլոր այցերը և տվյալ հաճախորդը ");
            alert.setContentText("Ցանկանու՞մ եք ջնջել գրանցումը");
        } else {
            alert.setHeaderText("");
            alert.setContentText("Ցանկանու՞մ եք ջնջել տվյալ այցը");
        }

        Optional<ButtonType> result =  alert.showAndWait();

        if(result.isPresent())
            if(result.get() == delete)
                if(archive.isRegistration())
                    customerService.remove(archive.getCustomer(), true);
                else
                    archiveDAO.remove(archive, true);


    }

    public List<Archive> getByDateRange(LocalDate startDate, LocalDate endDate) throws SQLException {
        return archiveDAO.getByDateRange(startDate, endDate);
    }

    private Report getReportByEmployment(List<Report> reports, Employment employment) {
        for(Report report: reports)
            if(report.getEmployment().getId() == employment.getId())
                return report;

        return null;
    }
    private Salary getSalaryByEmployee(List<Salary> salaries, Employee employee) {
        for(Salary salary: salaries)
            if(salary.getEmployee().getId() == employee.getId())
                return salary;
        return null;
    }

    private int getPrice(Subscription subscription, Archive archive) {
        /* if user has subscription, get that subscriptions that employment price */
        if(archive.isBonus())
            return 0;
        else if(subscription != null)
            return subscription.getEmploymentPrice(archive.getEmployment());
        else if(archive.getEmployment() != null)
            return archive.getEmployment().getPrice();
        else
            return 0;
    }

    public List<Report> getReports(List<Archive> archives) {
        List<Report> reports = new ArrayList<>();
        for(Archive archive: archives) {
            /* pass registrations */
            if(archive.isRegistration()) continue;

            Report report = getReportByEmployment(reports, archive.getEmployment());
            if(report != null) {
                Salary salary = getSalaryByEmployee(report.getSalaries(), archive.getEmployee());
                if(salary != null) {
                    salary.incrementQuantity();

                    Subscription subscription = archive.getCustomer().getSubscription();
                    salary.incrementPrice(getPrice(subscription, archive));
                } else { /* employee is not in salary */
                    Salary newSalary = new Salary();

                    newSalary.setEmployee(archive.getEmployee());

                    Subscription subscription = archive.getCustomer().getSubscription();
                    newSalary.setPrice(getPrice(subscription, archive));

                    newSalary.setQuantity(1);

                    report.addSalary(newSalary);
                }

            } else { /* employment is not in report */
                Report newReport = new Report();

                newReport.setEmployment(archive.getEmployment());

                Salary newSalary = new Salary();
                newSalary.setEmployee(archive.getEmployee());

                Subscription subscription = archive.getCustomer().getSubscription();
                newSalary.setPrice(getPrice(subscription, archive));

                newSalary.setQuantity(1);

                newReport.addSalary(newSalary);

                reports.add(newReport);
            }
        }
        return reports;
    }
}
