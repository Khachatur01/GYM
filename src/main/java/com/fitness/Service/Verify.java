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
            cardTextField.getStyleClass().add("textField_error");
            cardTextField.requestFocus();
            return false;
        }

        cardTextField.getStyleClass().remove("textField_error");
        return true;
    }
    public static boolean name(String name, TextField nameTextField){
        if(name.trim().equals("")){
            nameTextField.getStyleClass().add("textField_error");
            nameTextField.requestFocus();
            return false;
        }

        nameTextField.getStyleClass().remove("textField_error");
        return true;
    }
    public static boolean surname(String surname, TextField surnameTextField){
        if(surname.trim().equals("")){
            surnameTextField.getStyleClass().add("textField_error");
            surnameTextField.requestFocus();
            return false;
        }

        surnameTextField.getStyleClass().remove("textField_error");
        return true;
    }
    public static boolean address(String address, TextField addressTextField){
        if(address.trim().equals("")){
            addressTextField.getStyleClass().add("textField_error");
            addressTextField.requestFocus();
            return false;
        }

        addressTextField.getStyleClass().remove("textField_error");
        return true;
    }
    public static boolean phone(String phone, MaskField phoneMaskField){
        if(!MaskField.isFill(phone)){
            phoneMaskField.getStyleClass().add("textField_error");
            phoneMaskField.requestFocus();
            return false;
        }

        phoneMaskField.getStyleClass().remove("textField_error");
        return true;
    }

    public static boolean phone2(String phone2, MaskField phone2MaskField){
        if(!MaskField.isFill(phone2) && phone2MaskField.getPlainText().length() != 0){
            phone2MaskField.getStyleClass().add("textField_error");
            phone2MaskField.requestFocus();
            return false;
        }

        phone2MaskField.getStyleClass().remove("textField_error");
        return true;
    }

    public static boolean subscription(Subscription subscription, ComboBox<Subscription> subscriptionComboBox) {
        if(subscription == null){
            subscriptionComboBox.getStyleClass().add("combo-box_error");
            return false;
        }

        subscriptionComboBox.getStyleClass().remove("combo-box_error");
        return true;
    }

    public static boolean employment(Employment employment, ComboBox<Employment> employmentComboBox) {
        if(employment == null){
            employmentComboBox.getStyleClass().add("combo-box_error");
            return false;
        }

        employmentComboBox.getStyleClass().remove("combo-box_error");
        return true;
    }

    public static boolean position(Position position, ComboBox<Position> positionComboBox) {
        if(position == null){
            positionComboBox.getStyleClass().add("combo-box_error");
            return false;
        }

        positionComboBox.getStyleClass().remove("combo-box_error");
        return true;
    }

    public static boolean employmentName(String name, TextField nameTextField){
        if(name.trim().equals("")){
            nameTextField.getStyleClass().add("textField_error");
            nameTextField.requestFocus();
            return false;
        }

        nameTextField.getStyleClass().remove("textField_error");
        return true;
    }

    public static boolean price(String price, TextField priceTextField){
        if(price.trim().equals("")){
            priceTextField.getStyleClass().add("textField_error");
            priceTextField.requestFocus();
            return false;
        }

        priceTextField.getStyleClass().remove("textField_error");
        return true;
    }

    public static boolean positionName(String name, TextField nameTextField) {
        if(name.trim().equals("")){
            nameTextField.getStyleClass().add("textField_error");
            nameTextField.requestFocus();
            return false;
        }

        nameTextField.getStyleClass().remove("textField_error");
        return true;
    }

    public static boolean quantity(String quantity, TextField quantityTextField) {
        if(quantity.trim().equals("")){
            quantityTextField.getStyleClass().add("textField_error");
            quantityTextField.requestFocus();
            return false;
        }

        quantityTextField.getStyleClass().remove("textField_error");
        return true;
    }

    public static boolean employee(Employee employee, ComboBox<Employee> employeeComboBox) {
        if(employee == null){
            employeeComboBox.getStyleClass().add("combo-box_error");
            return false;
        }

        employeeComboBox.getStyleClass().remove("combo-box_error");
        return true;
    }
}
