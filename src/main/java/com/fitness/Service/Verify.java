package com.fitness.Service;


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
    public static boolean phone(String phone, TextField phoneTextField){
        if(phone.trim().equals("")){
            phoneTextField.getStyleClass().add("textField_error");
            phoneTextField.requestFocus();
            return false;
        }

        phoneTextField.getStyleClass().remove("textField_error");
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
}
