package com.fitness.Service.Person;

import com.fitness.DAO.Person.EmployeeDAO;
import com.fitness.Model.Person.Employee;
import com.fitness.Model.Person.Person;
import com.fitness.Model.Work.Position;
import com.fitness.Service.Verify;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class EmployeeService {
    private static Employee selected = null;
    private EmployeeDAO employeeDAO = new EmployeeDAO();

    private Employee makeEmployee(TextField nameTextField, TextField surnameTextField, TextField phoneTextField, TextField phone2TextField, TextField addressTextField, ComboBox<Position> positionComboBox){
        Employee employee = null;
        String name = nameTextField.getText();
        String surname = surnameTextField.getText();
        String phone = phoneTextField.getText();
        String phone2 = phone2TextField.getText();
        String address = addressTextField.getText();
        Position position = positionComboBox.getValue();

        if(     Verify.name(name, nameTextField) &&
                Verify.surname(surname, surnameTextField) &&
                Verify.phone(phone, phoneTextField) &&
                Verify.address(address, addressTextField) &&
                Verify.position(position, positionComboBox)
        ){
            employee = new Employee();
            employee.setName(new Person.Name(name, surname));
            employee.setPhone(phone);
            employee.setPhone2(phone2);
            employee.setAddress(address);
            employee.setPosition(position);
        }
        return employee;
    }

    public Employee add(TextField nameTextField, TextField surnameTextField, TextField phoneTextField, TextField phone2TextField, TextField addressTextField, ComboBox<Position> positionComboBox) throws SQLException {
        Employee newEmployee = this.makeEmployee(nameTextField, surnameTextField, phoneTextField, phone2TextField, addressTextField, positionComboBox);
        employeeDAO.add(newEmployee);
        return newEmployee;
    }

    public Employee edit(Employee employee, TextField nameTextField, TextField surnameTextField, TextField phoneTextField, TextField phone2TextField, TextField addressTextField, ComboBox<Position> positionComboBox) throws SQLException {
        Employee newEmployee = this.makeEmployee(nameTextField, surnameTextField, phoneTextField, phone2TextField, addressTextField, positionComboBox);
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


    public Employee getSelected() {
        return selected;
    }

    public void select(Employee selected) {
        EmployeeService.selected = selected;
    }
    
    public void removeSelected(){
        EmployeeService.selected = null;
    }
}
