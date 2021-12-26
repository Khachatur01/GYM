package com.fitness.Service;


import com.fitness.Element.MaskField;
import com.fitness.Model.Person.Employee;
import com.fitness.Model.Work.Employment;
import com.fitness.Model.Work.Position;
import com.fitness.Model.Work.Subscription;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class Verify {
    public static boolean card(String card, TextField cardTextField){
        if(card.trim().equals("")){
            cardTextField.requestFocus();
            return false;
        }

        return true;
    }
    public static boolean name(String name, TextField nameTextField){
        if(name.trim().equals("")){
            nameTextField.requestFocus();
            return false;
        }

        return true;
    }
    public static boolean surname(String surname, TextField surnameTextField){
        if(surname.trim().equals("")){
            surnameTextField.requestFocus();
            return false;
        }

        return true;
    }
    public static boolean address(String address, TextField addressTextField){
        if(address.trim().equals("")){
            addressTextField.requestFocus();
            return false;
        }

        return true;
    }
    public static boolean phone(String phone, MaskField phoneMaskField) {
        if(!MaskField.isFill(phone)) {
            phoneMaskField.requestFocus();
            return false;
        }

        return true;
    }

    public static boolean phone2(String phone2, MaskField phone2MaskField) {
        /* not fully field */
        if(!MaskField.isFill(phone2) && phone2MaskField.getPlainText().length() != 0) {
            phone2MaskField.requestFocus();
            return false;
        }

        return true;
    }

    public static boolean subscription(Subscription subscription, ComboBox<Subscription> subscriptionComboBox) {
        return subscription != null;
    }

    public static boolean employment(Employment employment, ComboBox<Employment> employmentComboBox) {
        return employment != null;
    }

    public static boolean position(Position position, ComboBox<Position> positionComboBox) {
        return position != null;
    }

    public static boolean employmentName(String name, TextField nameTextField){
        if(name.trim().equals("")){
            nameTextField.requestFocus();
            return false;
        }

        return true;
    }

    public static boolean price(String price, TextField priceTextField){
        if(price.trim().equals("")){
            priceTextField.requestFocus();
            return false;
        }

        return true;
    }

    public static boolean positionName(String name, TextField nameTextField) {
        if(name.trim().equals("")){
            nameTextField.requestFocus();
            return false;
        }

        return true;
    }

    public static boolean quantity(String quantity, TextField quantityTextField) {
        if(quantity.trim().equals("")){
            quantityTextField.requestFocus();
            return false;
        }

        return true;
    }

    public static boolean employee(Employee employee, ComboBox<Employee> employeeComboBox) {
        return employee != null;
    }
}
