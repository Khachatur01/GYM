package com.fitness.Service.Person;

import com.fitness.DAO.Person.EmployeeDAO;
import com.fitness.Element.MaskField;
import com.fitness.Model.Person.Employee;
import com.fitness.Model.Person.Person;
import com.fitness.Model.Work.Employment;
import com.fitness.Model.Work.Position;
import com.fitness.Service.Verify;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class EmployeeService {
    private static Employee selected = null;
    private final EmployeeDAO employeeDAO = new EmployeeDAO();

    private Employee makeEmployee(TextField nameTextField, TextField surnameTextField, MaskField phoneMaskField, MaskField phone2MaskField, TextField addressTextField, ObservableList<Position> positions){
        Employee employee = null;
        String name = nameTextField.getText();
        String surname = surnameTextField.getText();
        String phone = phoneMaskField.getText();
        String phone2 = phone2MaskField.getText();
        String address = addressTextField.getText();

        if(     Verify.name(name, nameTextField) &&
                Verify.surname(surname, surnameTextField) &&
                Verify.phone(phone, phoneMaskField) &&
                Verify.phone2(phone2, phone2MaskField) &&
                Verify.address(address, addressTextField) &&
                !positions.isEmpty()
        ){
            if (phone2MaskField.getPlainText().length() == 0)
                phone2 = null;

            employee = new Employee();
            employee.setName(new Person.Name(name, surname));
            employee.setPhone(phone);
            employee.setPhone2(phone2);
            employee.setAddress(address);
            employee.setPositions(positions);
        }
        return employee;
    }

    public Employee add(TextField nameTextField, TextField surnameTextField, MaskField phoneMaskField, MaskField phone2MaskField, TextField addressTextField, ObservableList<Position> positions) throws SQLException {
        Employee newEmployee = this.makeEmployee(nameTextField, surnameTextField, phoneMaskField, phone2MaskField, addressTextField, positions);
        employeeDAO.add(newEmployee);
        return newEmployee;
    }

    public Employee edit(Employee employee, TextField nameTextField, TextField surnameTextField, MaskField phoneMaskField, MaskField phone2MaskField, TextField addressTextField, ObservableList<Position> positions) throws SQLException {
        Employee newEmployee = this.makeEmployee(nameTextField, surnameTextField, phoneMaskField, phone2MaskField, addressTextField, positions);
        newEmployee.setId(employee.getId());
        employeeDAO.edit(newEmployee);
        return newEmployee;
    }

    public void remove(Employee employee) throws SQLException {
        if(employee == null) return;

        ButtonType archive = new ButtonType("Արխիվացնել", ButtonBar.ButtonData.OK_DONE);
        ButtonType back = new ButtonType("Հետ", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType removeHistory = new ButtonType("Ջնջել պատմությունը", ButtonBar.ButtonData.OK_DONE);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Հաստատում");
        alert.setHeaderText("Հաստատեք, որ ցանկանում եք ջնջել աշխատողին");
        alert.setContentText("Ցանկանու՞մ եք ջնջել աշխատողին");
        alert.getButtonTypes().setAll(archive, removeHistory, back);

        Optional<ButtonType> result =  alert.showAndWait();

        if(result.isPresent()){
            if(result.get() == archive)
                employeeDAO.remove(employee, false);
            else if (result.get() == removeHistory)
                employeeDAO.remove(employee, true);
        }
    }

    public List<Employee> getActual() throws SQLException{
        return employeeDAO.getActual();
    }
    public List<Employee> getAll() throws SQLException{
        return employeeDAO.getAll();
    }
    public List<Employee> getBy(Employment selectedEmployment, boolean actual) throws SQLException {
        return employeeDAO.getBy(selectedEmployment, actual);
    }

    public Employee getSelected() {
        return selected;
    }

    public void select(Employee selected) {
        EmployeeService.selected = selected;
    }
    
    public void removeSelected(){
        EmployeeService.selected = null;
    }

    public List<Position> getPositions(Employee employee, boolean actual) throws SQLException {
        return employeeDAO.getPositions(employee, actual);
    }
}
