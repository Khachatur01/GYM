package com.fitness.Service.Person;

import com.fitness.Model.Person.Employee;
import com.fitness.Model.Person.Person;
import com.fitness.Model.Work.Service;
import com.fitness.Model.Work.Position;
import com.fitness.Service.Verify;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class EmployeeService {
    private static Employee cache = null;


    private Employee makeCustomer(TextField nameTextField, TextField surnameTextField, TextField phoneTextField, TextField phone2TextField, TextField addressTextField, ComboBox<Service> offerComboBox, ComboBox<Position> positionComboBox){
        Employee employee = null;
        String name = nameTextField.getText();
        String surname = surnameTextField.getText();
        String phone = phoneTextField.getText();
        String phone2 = phone2TextField.getText();
        String address = addressTextField.getText();
        Service offer = offerComboBox.getValue();
        Position position = positionComboBox.getValue();

        if(     Verify.name(name, nameTextField) &&
                Verify.surname(surname, surnameTextField) &&
                Verify.phone(phone, phoneTextField) &&
                Verify.address(address, addressTextField) &&
                Verify.service(offer, offerComboBox) &&
                Verify.position(position, positionComboBox)
        ){
            employee = new Employee();
            employee.setName(new Person.Name(name, surname));
            employee.setPhone(phone);
            employee.setPhone2(phone2);
            employee.setAddress(address);
            employee.setService(offer);
            employee.setPosition(position);
        }
        return employee;
    }

    public boolean add(TextField nameTextField, TextField surnameTextField, TextField phoneTextField, TextField phone2TextField, TextField addressTextField, ComboBox<Service> offerComboBox, ComboBox<Position> positionComboBox){
        Employee employee = this.makeCustomer(nameTextField, surnameTextField, phoneTextField, phone2TextField, addressTextField, offerComboBox, positionComboBox);
        //@TODO add customer to database;
        return employee != null;
    }

    public boolean edit(TextField nameTextField, TextField surnameTextField, TextField phoneTextField, TextField phone2TextField, TextField addressTextField, ComboBox<Service> offerComboBox, ComboBox<Position> positionComboBox){
        Employee employee = this.makeCustomer(nameTextField, surnameTextField, phoneTextField, phone2TextField, addressTextField, offerComboBox, positionComboBox);
        //@TODO update customer in database;
        return employee != null;
    }

    public void remove(Employee employee, boolean removeHistory) {
        //@TODO
        System.out.println("removed");
    }

    public List<Employee> getEmployees() {
        List<Employee> employee = new ArrayList<>();
        employee.add(new Employee(-1, new Person.Name("name", "surname"), "123", "098", "addr", null, null));
        employee.add(new Employee(-1, new Person.Name("name1", "surname"), "456", "098", "addr", null, null));
        employee.add(new Employee(-1, new Person.Name("name2", "surname"), "789", "098", "addr", null, null));
        employee.add(new Employee(-1, new Person.Name("name3", "surname"), "147", "098", "addr", null, null));
        //@TODO get from database
        return employee;
    }

    public static Employee getCache() {
        return cache;
    }

    public static void setCache(Employee cache) {
        EmployeeService.cache = cache;
    }
}
